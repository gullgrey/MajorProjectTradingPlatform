package main.java.tradingPlatform;

/**
 * This class is used to handle any issues on the network side and failing to
 * connect to the network.
 */
public class NetworkException extends Exception{

    private final String message;

    public NetworkException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
