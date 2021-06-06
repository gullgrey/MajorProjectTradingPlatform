package main.java.GUI;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.NullValueException;
import main.java.tradingPlatform.TPUser;
import main.java.tradingPlatform.UnknownDatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class is responsible for generating a new Frame to either an Admin
 * or Member when they wish to modify their current password.
 */

public class ChangePassword extends LoginFrame{

    TPUser user;

    public ChangePassword(TradingPlatformDataSource dataSource, TPUser user) {
        super(dataSource);
        this.user = user;
        contentPane.remove(1);
        loginButton.setText("Change Password");
        setMinimumSize(new Dimension(450, 220));
        setTitle("Change Password");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            String userText = user.getUsername();
            String passwordText = passwordField.getText();

            try {
                user.changeUserPassword(userText, passwordText);
                setVisible(false);
                dispose();
            } catch (UnknownDatabaseException | NullValueException error) {
                JOptionPane.showMessageDialog(this, error.getMessage(),
                        error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
