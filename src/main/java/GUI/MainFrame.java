package main.java.GUI;

import main.java.tradingPlatform.ItAdministration;
import main.java.tradingPlatform.TPUser;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MainFrame extends JFrame implements ActionListener {

    Container contentPane = getContentPane();
    JTabbedPane mainPane = new JTabbedPane();

    JLabel organisationName = new JLabel();
    JLabel username = new JLabel();
    JLabel creditsTitle = new JLabel();
    JLabel credits = new JLabel();

    JTabbedPane assetPane;
    JTabbedPane marketPane;
    JTabbedPane historyPane;

    JButton changePassword = new JButton("Change Password");
    JButton refresh = new JButton("Refresh");

    public MainFrame() {
//        this.user = user;

        setLayoutManager();
        setVisible(true);
        setMinimumSize(new Dimension(1000, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setLayoutManager() {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(setDisplayPanel());
    }

    private JPanel setDisplayPanel() {

        Font displayFont = new Font("Arial", Font.BOLD, 16);
        organisationName.setFont(displayFont);
        username.setFont(displayFont);
        creditsTitle.setFont(displayFont);
        credits.setFont(displayFont);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));
        int horizontalStrut = 230;

        //Left structure
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JPanel orgPanel = new JPanel();
        orgPanel.setLayout(new BoxLayout(orgPanel, BoxLayout.X_AXIS));
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.X_AXIS));

        //Right structure
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));

        //Organisation
        JLabel orgTitle = new JLabel("Organisation: ");
        orgTitle.setFont(displayFont);
        orgPanel.add(orgTitle);
        orgPanel.add(organisationName);
        leftPanel.add(orgPanel);

        //Credits
        creditsPanel.add(creditsTitle);
        creditsPanel.add(credits);
        leftPanel.add(creditsPanel);
        displayPanel.add(leftPanel);

        displayPanel.add(Box.createHorizontalStrut(horizontalStrut));

        //Refresh
        displayPanel.add(refresh);

        displayPanel.add(Box.createHorizontalStrut(horizontalStrut));

        //Username
        JLabel userTitle = new JLabel("Username: ");
        userTitle.setFont(displayFont);
        usernamePanel.add(userTitle);
        usernamePanel.add(username);
        rightPanel.add(usernamePanel);

        //Change Password
        passwordPanel.add(changePassword);
        rightPanel.add(passwordPanel);
        displayPanel.add(rightPanel);

        return displayPanel;
    }

    abstract JTabbedPane setMainPanel();

    abstract void setupPanes();

    abstract void addActionEvent();
}
