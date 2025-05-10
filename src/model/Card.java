package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * 
 */
public class Card {

	private Rank rank;
	private Suit suit;
	private String imagePath;
	private BufferedImage cardImage;

	/*
	 * Normal constructor for Card
	 */
	public Card(Rank rank, Suit suit, String imagePath) {
		this.rank = rank;
		this.suit = suit;
		this.imagePath = imagePath;
	}

	/*
	 * Return cardImage from resources
	 */
	public BufferedImage getCardImage(String imagePath) {
		BufferedImage cardImage = null;
		try {
			cardImage = ImageIO.read(getClass().getResourceAsStream("/cards/" + imagePath + ".png"));
		} catch (IOException e) { // catch if a unexpected request comes in
			e.printStackTrace();
		}
		return cardImage;
	}

	/*
	 * Set cardImage
	 */
	public void setCardImage(BufferedImage cardImage) {
		this.cardImage = cardImage;
	}

	/*
	 * Return value of Card
	 */
	public int getValue() {
		switch (rank) {
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
		case SIX:
			return 6;
		case SEVEN:
			return 7;
		case EIGHT:
			return 8;
		case NINE:
			return 9;
		case TEN:
		case JACK:
		case QUEEN:
		case KING:
			return 10;
		case ACE:
			return 11; // Will later make equal to 1 if total is greater than 21;
		default:
			throw new IllegalStateException("Unknown rank: " + rank);
		}
	}

}
