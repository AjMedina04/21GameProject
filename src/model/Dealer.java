package model;

/**
 * Dealer represents the house player in blackjack.
 * It follows fixed rules to hit until at least soft 17, then stands.
 */
public class Dealer extends Player {
	
	/**
	 * Dealer stands on 17 or above; hits on 16 or below (soft 17 still hits).
	 */
	public static final int STAND_THRESHOLD = 17;

	/**
	 * Creates a new Dealer with an empty hand.
	 */
	public Dealer() {

	}

	/**
	 * Decides the dealer's action based on blackjack rules.
	 * Hits on totals below threshold or on soft 17; stands otherwise.
	 *
	 * @param dealerUpCard ignored for dealer logic
	 * @return PlayerAction.HIT or PlayerAction.STAND
	 */
	@Override
	PlayerAction decideAction(Card dealerUpCard) {
		int total = calculateHandValue();
		// detect soft 17
		int sumWithAcesAsOne = 0;
		int aceCount = 0;
		for (Card card : hand) {
			if (card.getRank() == Rank.ACE) {
				aceCount++;
				sumWithAcesAsOne += 1;
			} else {
				sumWithAcesAsOne += card.getValue();
			}
		}
		boolean isSoft17 = aceCount > 0 && sumWithAcesAsOne + 10 == total && total == STAND_THRESHOLD;
		if (total < STAND_THRESHOLD || isSoft17) {
			return PlayerAction.HIT;
		} else {
			return PlayerAction.STAND;
		}
	}

}
