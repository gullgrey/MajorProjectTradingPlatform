package main.java.tradingPlatform;

public class WrongCredentialException extends Exception{
    String message;

    public WrongCredentialException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
