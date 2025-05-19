package model;

import java.util.ArrayList;

/**
 * Abstract base class for a game participant, representing common behaviors.
 * Maintains a hand of cards and provides utility methods for hand management.
 */
public abstract class Player {

	// A player has-many cards that are in his hand
    ArrayList<Card> hand = new ArrayList<>();

    /**
     * Adds a card to this player's hand.
     *
     * @param card the Card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Clears all cards from this player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Returns the list of cards in this player's hand.
     *
     * @return the hand as an ArrayList of Card
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Calculates the total value of the hand, treating Aces as 11 or 1 as needed.
     *
     * @return the hand value (<= 21 if possible)
     */
    public int calculateHandValue() {
        int handValue = 0;
        int aces = 0;
        for (Card card : hand) {
            if (card.getRank().equals(Rank.ACE)) {
                aces++;
            } else {
                handValue += card.getValue();
            }
        }
		// add aces as 11 initially
        handValue += aces * 11;
		// downgrade aces from 11 to 1 if bust
        while (handValue > 21 && aces > 0) {
            handValue -= 10;
            aces--;
        }
        return handValue;
    }

    /**
     * Determines the next action for this player (HIT or STAND).
     *
     * @param dealerUpCard the dealer's visible card
     * @return the chosen PlayerAction
     */
    abstract PlayerAction decideAction(Card dealerUpCard);
}
