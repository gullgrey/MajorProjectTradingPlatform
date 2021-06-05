package main.java.tradingPlatform;

public class InvalidValueException extends Exception {

    private final String message;

    public InvalidValueException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
