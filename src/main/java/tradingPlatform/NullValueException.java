package main.java.tradingPlatform;

/**
 * This class is used to handle the Null value exception thrown when
 * a queried value from the database does not exist.
 */
public class NullValueException extends Exception{

    private final String message;

    public NullValueException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
