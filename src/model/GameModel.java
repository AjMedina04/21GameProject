package model;

import java.util.List;

/**
 * GameModel orchestrates core game logic:
 * - manages deck, human player, and dealer
 * - tracks wins and losses
 * - handles round lifecycle and outcome determination
 */
public class GameModel {

	// A GameEngine has-a deck
	private Deck deck;
	// A GameEngine has-a humanPlayer
	private HumanPlayer human;
	// A GameEngine has-a dealer
	private Dealer dealer;
	// A GameModel has-a count of wins
	private int wins = 0;
	// A GameModel has-a count of losses
	private int losses = 0;
	// A GameModel has-a fileManager
	private final FileManager fileManager;
	// Round active flag: true while a round is in progress
	private boolean roundActive = false;

	/**
	 * Constructs a new GameModel.
	 *
	 * Initializes and shuffles the deck, sets up the human player and dealer,
	 * and loads win/loss record from persistent storage.
	 */
	public GameModel() {
		this.deck = new Deck();
		this.deck.shuffle();

		// Initialize players
		this.human = new HumanPlayer();
		this.dealer = new Dealer();

		// Initialize fileManager
		this.fileManager = new FileManager();
		int[] record = this.fileManager.loadRecord();
		this.wins = record[0];
		this.losses = record[1];

	}

	/**
	 * Starts a new round. Reshuffles deck if low, clears hands,
	 * deals two cards to player and dealer (one hidden).
	 *
	 * @return WIN, LOSS, or PUSH if a natural blackjack occurs immediately;
	 *         null to continue normal play.
	 */
	public GameResult startNewRound() {
		roundActive = true;
		// reshuffle when deck runs low (or empty)
		if (deck.getSize() < 4) {
			deck.resetDeck();
		}

		dealer.clearHand();
		human.clearHand();

		human.addCard(deck.dealCard());
		human.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard()); // up card
		dealer.addCard(deck.dealCard()); // down card
		dealer.getHand().get(1).setIsFaceDown(true); // sets down card to faceDown for UI

		// Check for blackjack immediately after deal
		GameResult autoResult = checkForBlackjack();
		if (autoResult != null) {
			return autoResult;
		}
		// No blackjack — continue normal play
		return null;
	}

	/**
	 * Player hits: deals one card to the human player's hand and checks for natural
	 * blackjack.
	 *
	 * @return WIN, LOSS, or PUSH if a natural blackjack occurs; null otherwise.
	 */
	public GameResult playerHits() {
		human.addCard(deck.dealCard());
		// Check for blackjack after hit
		return checkForBlackjack();
	}

	/**
	 * Player stands: reveals dealer's down card, executes dealer's turn,
	 * determines outcome, and updates win/loss counters.
	 *
	 * @return GameResult WIN, LOSS, or PUSH based on final hand comparison.
	 */
	public GameResult playerStands() {
		// reveal down card before dealer plays
		revealDealerDownCard();
		// let dealer play
		dealerTurn();
		// resolve outcome
		GameResult result = determineOutcome();
		if (result == GameResult.WIN) {
			wins++;
		} else if (result == GameResult.LOSS) {
			losses++;
		}
		roundActive = false;
		return result;
	}

	/**
	 * Dealer's turn: hits until standing threshold is reached.
	 */
	public void dealerTurn() {
		while (dealer.decideAction(null) == PlayerAction.HIT) {
			dealer.addCard(deck.dealCard());
		}
	}

	/**
	 * Determines the outcome by comparing human and dealer hand values.
	 *
	 * @return WIN if human wins, LOSS if dealer wins, or PUSH for a tie.
	 */
	public GameResult determineOutcome() {
		int playerTotal = human.calculateHandValue();
		if (playerTotal > 21) {
			return GameResult.LOSS;
		}
		int dealerTotal = dealer.calculateHandValue();
		if (dealerTotal > 21) {
			return GameResult.WIN;
		}
		if (playerTotal > dealerTotal) {
			return GameResult.WIN;
		}
		if (playerTotal < dealerTotal) {
			return GameResult.LOSS;
		}
		return GameResult.PUSH;
	}

	/**
	 * @return the deck used for drawing cards
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * @return the number of wins recorded
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @return the number of losses recorded
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @return the human player instance
	 */
	public HumanPlayer getHuman() {
		return human;
	}

	/**
	 * @return the dealer instance
	 */
	public Dealer getDealer() {
		return dealer;
	}

	// Accessors for UI/controller
	public List<Card> getHumanHand() {
		return human.getHand();
	}

	public List<Card> getDealerHand() {
		return dealer.getHand();
	}

	public int getDealerUpCardValue() {
		Card up = dealer.getHand().get(0);
		return up.getValue();
	}

	/**
	 * @return the dealer's up-card
	 */
	public Card getDealerUpCard() {
		return dealer.getHand().get(0);
	}

	/**
	 * @return the player's hand total value
	 */
	public int getPlayerTotal() {
		return human.calculateHandValue();
	}

	/**
	 * @return the dealer's hand total value
	 */
	public int getDealerTotal() {
		return dealer.calculateHandValue();
	}

	/**
	 * @return the remaining cards in the deck
	 */
	public int getDeckSize() {
		return deck.getSize();
	}

	/**
	 * Reveals the dealer's down card by setting its face-down flag to false.
	 */
	public void revealDealerDownCard() {
		dealer.getHand().get(1).setIsFaceDown(false);
	}

	/**
	 * Updates in-memory and persisted win/loss record, ignoring PUSH.
	 *
	 * @param result the GameResult to record
	 */
	private void updateRecord(GameResult result) {
		if (result == GameResult.WIN) {
			wins++;
		} else if (result == GameResult.LOSS) {
			losses++;
		}
		fileManager.saveRecord(wins, losses);
	}

	/**
	 * @return true if a round is currently active
	 */
	public boolean isRoundActive() {
		return roundActive;
	}

	/**
	 * @return true if the human player's hand value exceeds 21 (bust)
	 */
	public boolean isPlayerBust() {
		return human.calculateHandValue() > 21;
	}

	/**
	 * Checks for blackjack for both human and dealer.
	 * If detected, reveals dealer's down card, updates record, and ends round.
	 *
	 * @return WIN, LOSS, or PUSH if blackjack detected; otherwise null.
	 */
	private GameResult checkForBlackjack() {
		// Determine if either hand is a blackjack (exactly two cards totaling 21)
		boolean playerBlackjack = human.getHand().size() == 2 && human.calculateHandValue() == 21;
		boolean dealerBlackjack = dealer.getHand().size() == 2 && dealer.calculateHandValue() == 21;
		if (playerBlackjack || dealerBlackjack) {
			revealDealerDownCard();
			GameResult result;
			if (playerBlackjack && dealerBlackjack) {
				result = GameResult.PUSH;
			} else if (playerBlackjack) {
				result = GameResult.WIN;
			} else {
				result = GameResult.LOSS;
			}
			updateRecord(result);
			roundActive = false;
			return result;
		}
		return null;
	}
}
