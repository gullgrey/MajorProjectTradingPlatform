package main.java.tradingPlatform;

/**
 * This class is used to handle the Unknown database exception thrown when
 * an update to the database was unsuccessful.
 */
public class UnknownDatabaseException extends Exception {

    private final String message;

    public UnknownDatabaseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
