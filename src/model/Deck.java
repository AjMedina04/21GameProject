package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a standard 52-card deck used in the game.
 */
public class Deck {

	// A Deck has-many cards
    private ArrayList<Card> cards;

    /**
     * Builds an ordered deck of 52 cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Resets the deck to a full, ordered set of cards and shuffles it.
     */
    public void resetDeck() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deals the top card from the deck and removes it.
     *
     * @return the dealt Card.
     */
    public Card dealCard() {
        return cards.remove(0);
    }

    /**
     * Returns the number of cards currently in the deck.
     *
     * @return the count of cards.
     */
    public int getSize() {
        return cards.size();
    }
}
