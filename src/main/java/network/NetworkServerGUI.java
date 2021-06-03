package main.java.network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class NetworkServerGUI {
    public static void main(String[] args) {
        try {
            NetworkServer server = new NetworkServer();
            SwingUtilities.invokeLater(() -> createAndShowGUI(server));
            server.start();
        } catch (IOException | SQLException e) {
            // In the case of an exception, show an error message and terminate
            SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                            null, e.getMessage(),
                            "Error starting networkExercise.server", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
            });
        }
    }

    private static void createAndShowGUI(NetworkServer server) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Network networkExercise.server for Address Book");
        JButton shutdownButton = new JButton("Shut down networkExercise.server");
        // This button will simply close the dialog. CLosing the dialog
        // will shut down the networkExercise.server
        shutdownButton.addActionListener(e -> dialog.dispose());

        // When the dialog is closed, shut down the networkExercise.server
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                server.shutdown();
            }
        });

        // Create a label to show networkExercise.server info
        JLabel serverLabel = new JLabel("Server running on port " + NetworkServer.getPort());

        // Add the button and label to the dialog
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(shutdownButton, BorderLayout.SOUTH);
        dialog.getContentPane().add(serverLabel, BorderLayout.NORTH);
        dialog.pack();

        // Centre the dialog on the screen
        dialog.setLocationRelativeTo(null);

        // Show the dialog
        dialog.setVisible(true);
    }
}
