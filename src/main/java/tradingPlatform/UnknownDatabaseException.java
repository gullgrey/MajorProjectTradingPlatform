package main.java.tradingPlatform;

public class UnknownDatabaseException extends Exception {

    String message;

    public UnknownDatabaseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
