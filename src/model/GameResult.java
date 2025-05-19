package model;

/**
 * Represents the possible outcomes of a blackjack round,
 * each with its own default display message.
 */
public enum GameResult {
    // Player wins the round.
    WIN("You win!"),
    // Player loses the round.
    LOSS("You lose!"),
    // Round ends in a tie (push).
    PUSH("Push!");

    private final String defaultMsg;

    GameResult(String msg) {
        this.defaultMsg = msg;
    }

    /**
     * Returns the default display message for this result.
     *
     * @return the default UI message.
     */
    public String defaultMessage() {
        return defaultMsg;
    }
}
