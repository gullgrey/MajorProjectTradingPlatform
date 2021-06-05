package main.java.network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NetworkServerGUI {

    public static void createAndShowGUI(NetworkServer server) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Network server for Trading Platform");
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton shutdownButton = new JButton("Shut down the server");
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
        dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.getContentPane().add(serverLabel);
        dialog.getContentPane().add(shutdownButton);

        dialog.pack();

        // Centre the dialog on the screen
        dialog.setLocationRelativeTo(null);

        // Show the dialog
        dialog.setVisible(true);
    }
}
