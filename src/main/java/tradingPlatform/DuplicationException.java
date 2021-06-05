package main.java.tradingPlatform;

public class DuplicationException extends Exception {

    private final String message;

    public DuplicationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
