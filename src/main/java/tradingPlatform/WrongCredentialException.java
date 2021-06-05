package main.java.tradingPlatform;

/**
 * This class is used to handle the Wrong Credentials exception thrown when
 * a user has entered the wrong input.
 */
public class WrongCredentialException extends Exception{

    private final String message;

    public WrongCredentialException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
