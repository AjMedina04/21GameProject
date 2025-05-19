package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents a single playing card with a rank, suit, and face-down state.
 * Loads its image from resources when requested.
 */
public class Card {

	// A Card has-a rank
    private Rank rank;
	// A Card has-a suit
    private Suit suit;
	// A Card has-a cardImage
    private BufferedImage cardImage;
	// A Card has-a isFaceDown flag
    private boolean isFaceDown = false;

    /**
     * Constructs a Card with specified rank and suit, face-up by default.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the image representing this card face, loading it from resources if necessary.
     * If the card is face-down, returns the back image.
     *
     * @return the card image
     */
    public BufferedImage getCardImage() {
        if (isFaceDown) {
            try {
                cardImage = ImageIO.read(getClass().getResourceAsStream("/cards/card_of_back.png"));
            } catch (IOException e) { // catch if a unexpected request comes in
                e.printStackTrace();
            }
            return cardImage;
        } else {
            try {
                cardImage = ImageIO.read(getClass().getResourceAsStream(
                        "/cards/" + rank.name().toLowerCase() + "_of_" + suit.name().toLowerCase() + ".png"));
            } catch (IOException e) { // catch if a unexpected request comes in
                e.printStackTrace();
            }
            return cardImage;
        }
    }

    /**
     * Sets the image representing this card face.
     *
     * @param cardImage the new card image
     */
    public void setCardImage(BufferedImage cardImage) {
        this.cardImage = cardImage;
    }

    /**
     * Returns the point value of this card.
     * For numbered cards, returns the number.
     * For face cards, returns 10.
     * For Ace, returns 11 (will later be adjusted to 1 if total is greater than 21).
     *
     * @return the card value
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

    /**
     * Returns the rank of this card.
     *
     * @return the card's rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of this card.
     *
     * @return the card's suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Sets whether the card is face-down (back shown).
     *
     * @param isFaceDown true to show back, false to show face
     */
    public void setIsFaceDown(boolean isFaceDown) {
        this.isFaceDown = isFaceDown;
    }
}
