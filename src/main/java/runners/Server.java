package main.java.runners;

import main.java.server.network.NetworkServer;
import main.java.server.network.NetworkServerGUI;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Server {

    public static void main(String[] args) {
        try {
            NetworkServer server = new NetworkServer();
            SwingUtilities.invokeLater(() -> NetworkServerGUI.createAndShowGUI(server));
            server.start();
        } catch (IOException | SQLException e) {
            // In the case of an exception, show an error message and terminate
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        null, e.getMessage(),
                        "Error starting the server", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            });
        }
    }
}
