package main.java.runners;

import main.java.client.GUI.LoginFrame;
import main.java.client.tradingPlatform.NetworkDataSource;

import javax.swing.*;

public class Client {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> new LoginFrame(new NetworkDataSource()));
    }
}
