package model;

import java.util.ArrayList;

/*
 * 
 */
public class Deck {

	// A Deck has-many cards
	private ArrayList<Card> cards;

	/*
	 * Builds an ordered deck of 52 cards
	 */
	public Deck() {
		cards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				String imagePath = rank.name().toLowerCase() + "_of_" + suit.name().toLowerCase();
				cards.add(new Card(rank, suit, imagePath));
			}
		}
	}

	/*
	 * 
	 */
	public void shuffle() {

	}

	/*
	 * 
	 */
	public Card dealCard() {
		return null;

	}

	/*
	 * 
	 */
	public int getSize() {
		return 0;
	}
}
