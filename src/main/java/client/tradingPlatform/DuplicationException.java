package main.java.client.tradingPlatform;

/**
 * This class is used to handle the duplication exception thrown when
 * a primary key constraint is thrown in the database.
 */
public class DuplicationException extends Exception {

    private final String message;

    public DuplicationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
