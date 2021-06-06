package main.java.client.tradingPlatform;

/**
 * This class is used to handle the invalid exception thrown when
 * an incorrect input was initialized.
 */
public class InvalidValueException extends Exception {

    private final String message;

    public InvalidValueException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
