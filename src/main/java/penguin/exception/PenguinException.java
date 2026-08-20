package penguin.exception;

/** Represents a user-input error specific to the Penguin chatbot. */
public class PenguinException extends Exception {
    /**
     * Creates an exception with a message suitable for displaying to the user.
     *
     * @param message explanation of how the input is invalid
     */
    public PenguinException(String message) {
        super(message);
    }
}
