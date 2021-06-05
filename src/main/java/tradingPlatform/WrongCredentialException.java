package main.java.tradingPlatform;

public class WrongCredentialException extends Exception{

    private final String message;

    public WrongCredentialException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
