package model;

/**
 * HumanPlayer represents a user-controlled player in the game.
 * Decision-making is driven by UI interaction rather than automatic rules.
 */
public class HumanPlayer extends Player {

    /**
     * Decides the action for the human player. This method is a placeholder
     * as decisions come from user input via the UI.
     *
     * @param dealerUpCard the dealer's visible card (for UI context)
     * @return null; actual action is handled by the controller based on user clicks
     */
    @Override
    PlayerAction decideAction(Card dealerUpCard) {
        return null;
    }

}
