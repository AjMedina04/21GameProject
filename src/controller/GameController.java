package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Card;
import model.GameModel;
import model.GameResult;
import view.GameView;

/**
 * GameController manages user interactions and mediates between GameModel and
 * GameView.
 * It listens for Deal, Hit, and Stand actions, updating the game state and UI
 * accordingly.
 */
public class GameController implements ActionListener {

	// A GameController has-a model
	private GameModel model;
	// A GameController has-a view
	private GameView view;

	/**
	 * Constructs a GameController linking the given model and view.
	 *
	 * @param model the game logic model
	 * @param view  the UI for displaying game state
	 */
	public GameController(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Registers action listeners on Deal, Hit, and Stand buttons.
	 */
	public void initialize() {
		// hook up control buttons
		view.getDealButton().addActionListener(this);
		view.getHitButton().addActionListener(this);
		view.getStandButton().addActionListener(this);
	}

	/**
	 * Starts a new round and updates the display (used at app launch).
	 */
	public void updateInitialDisplay() {
		handleDeal();
	}

	/**
	 * Handles button actions: Deal, Hit, or Stand, delegating to helpers.
	 *
	 * @param e the action event triggered by a button click
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Deal".equals(cmd)) {
			handleDeal();
			return;
		} else if ("Hit".equals(cmd)) {
			handleHit();
			return;
		} else if ("Stand".equals(cmd)) {
			handleStand();
			return;
		}
	}

	// ----- Helper methods for actions -----
	/**
	 * Renders an entire hand on specified row.
	 */
	private void showHand(int row, List<Card> hand) {
		for (int i = 0; i < hand.size(); i++) {
			view.showCard(row, i, hand.get(i).getCardImage());
		}
	}

	/**
	 * Update status using dealer's up-card value.
	 */
	private void refreshStatusUp() {
		view.updateStatus(model.getPlayerTotal(), model.getDealerUpCardValue(), model.getWins(), model.getLosses());
	}

	/**
	 * Update status using dealer's full total.
	 */
	private void refreshStatus() {
		view.updateStatus(model.getPlayerTotal(), model.getDealerTotal(), model.getWins(), model.getLosses());
	}

	/**
	 * Resets the view and starts a new round.
	 */
	private void handleDeal() {
		view.resetBoard();
		GameResult result = model.startNewRound();
		// render hands
		showHand(0, model.getDealer().getHand());
		showHand(1, model.getHuman().getHand());
		refreshStatusUp();
		if (result != null) {
			displayResult(result);
		} else {
			view.setMessage("Game started. Hit or Stand!");
		}
	}

	/**
	 * Processes a Hit: deals a card and handles blackjack or bust outcomes.
	 */
	private void handleHit() {
		GameResult result = model.playerHits();
		showHand(1, model.getHuman().getHand());
		refreshStatusUp();
		if (result != null) {
			displayResult(result);
		} else if (model.isPlayerBust()) {
			displayResult(GameResult.LOSS, "Bust! Dealer wins.");
		}
	}

	/**
	 * Processes a Stand: executes dealer turn and handles outcome.
	 */
	private void handleStand() {
		GameResult result = model.playerStands();
		displayResult(result);
	}

	/**
	 * Reveals dealer's down card, displays result message, and updates status.
	 *
	 * @param result    the game outcome to display
	 * @param customMsg optional custom message (e.g., bust message)
	 */
	private void displayResult(GameResult result, String customMsg) {
		model.revealDealerDownCard();
		showHand(0, model.getDealer().getHand());
		String msg;
		if (customMsg != null) {
			msg = customMsg;
		} else {
			msg = result.defaultMessage();
		}
		view.setMessage(msg);
		refreshStatus();
	}

	/**
	 * Reveals dealer's hand and displays the given result.
	 */
	private void displayResult(GameResult result) {
		displayResult(result, null);
	}
}
