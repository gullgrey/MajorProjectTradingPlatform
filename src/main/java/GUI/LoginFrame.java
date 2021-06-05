package main.java.GUI;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    TradingPlatformDataSource dataSource;

    Container contentPane = getContentPane();
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userTextField = new JTextField();
    JTextField passwordField = new JTextField();
    JButton loginButton = new JButton("LOGIN");

    public LoginFrame(TradingPlatformDataSource dataSource) {

        this.dataSource = dataSource;

        setLayoutManager();

        addActionEvent();

        setTitle("Client Login");
        setVisible(true);
        setMinimumSize(new Dimension(370, 220));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    private void setLayoutManager() {
        contentPane.setLayout(new GridLayout(7, 0));
        int verticalStrut = 20;
        userTextField.setMaximumSize(new Dimension(150, 30));
        passwordField.setMaximumSize(new Dimension(150, 30));
        contentPane.add(Box.createVerticalStrut(verticalStrut));
        contentPane.add(makeUserPanel());
        contentPane.add(Box.createVerticalStrut(0));
        contentPane.add(makePasswordPanel());
        contentPane.add(Box.createVerticalStrut(verticalStrut));
        contentPane.add(makeLoginPanel());
        contentPane.add(Box.createVerticalStrut(verticalStrut));
    }

    private JPanel makeUserPanel() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        int horizontalStrut = 50;
        userPanel.add(Box.createHorizontalStrut(horizontalStrut));
        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(horizontalStrut));
        userPanel.add(userTextField);
        userPanel.add(Box.createHorizontalStrut(horizontalStrut));
        return userPanel;
    }

    private JPanel makePasswordPanel() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        int horizontalStrut = 50;
        passwordPanel.add(Box.createHorizontalStrut(horizontalStrut));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(horizontalStrut));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalStrut(horizontalStrut));
        return passwordPanel;
    }

    private JPanel makeLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(0, 3));
        int horizontalStrut = 40;
        loginPanel.add(Box.createHorizontalStrut(horizontalStrut));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createHorizontalStrut(horizontalStrut));
        return loginPanel;
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            //todo login logic
            String userText;
            String passwordText;
            userText = userTextField.getText();
            passwordText = passwordField.getText();
            Login login = new Login(userText, passwordText, dataSource);
            try {
                if (login.checkSuppliedCredentials()) {
                    ItAdministration user = login.getItAdministration();
                    SwingUtilities.invokeLater(() -> new AdminFrame(user));

                } else {
                    StandardUser user = login.getStandardUser();
                    SwingUtilities.invokeLater(() -> new StandardFrame(user));
                }
                setVisible(false);
                dispose();
            } catch (UnknownDatabaseException | WrongCredentialException | NetworkException error) {
                JOptionPane.showMessageDialog(this, error.getMessage(),
                        error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
