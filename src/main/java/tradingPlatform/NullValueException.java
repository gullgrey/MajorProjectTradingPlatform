package main.java.tradingPlatform;

public class NullValueException extends Exception{

    private final String message;

    public NullValueException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
