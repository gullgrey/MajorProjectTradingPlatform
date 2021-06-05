package main.java.tradingPlatform;

/**
 * todo
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
