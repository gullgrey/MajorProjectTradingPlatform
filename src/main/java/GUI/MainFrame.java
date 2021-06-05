package main.java.GUI;

import main.java.tradingPlatform.ItAdministration;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    ItAdministration user;

    Container contentPane = getContentPane();
    JTabbedPane mainPane = new JTabbedPane();

    JTabbedPane userPane;
    JTabbedPane organisationPane;
    JTabbedPane assetPane;
    JTabbedPane marketPane;
    JTabbedPane historyPane;

    JButton changePassword = new JButton("Change Password");
    JButton refresh = new JButton("Refresh");

    public MainFrame(ItAdministration user) {
        this.user = user;
        setupPanes();
        setLayoutManager();
        addActionEvent();

        setTitle("IT Administration");
        setVisible(true);
        setMinimumSize(new Dimension(1000, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setLayoutManager() {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(setDisplayPanel());
        contentPane.add(setMainPanel());
    }

    private JPanel setDisplayPanel() {

        Font displayFont = new Font("Arial", Font.BOLD, 16);
        JLabel organisationName = new JLabel(user.getOrganisation());
        JLabel username = new JLabel(user.getUsername());
        organisationName.setFont(displayFont);
        username.setFont(displayFont);

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
        addCredits(creditsPanel);
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

    private void addCredits(JPanel panel) {
        panel.add(new JLabel(""));
    }

    private JTabbedPane setMainPanel() {
        mainPane.setMinimumSize(new Dimension(600, 300));
        mainPane.addTab("Users", userPane);
        mainPane.addTab("Organisations", organisationPane);
        mainPane.addTab("Assets", assetPane);
        mainPane.addTab("Market", marketPane);
        mainPane.addTab("History", historyPane);

        return mainPane;
    }

    private void setupPanes() {

        userPane = new UserPane(user);
        organisationPane = new OrganisationPane(user);
        assetPane = new AdminAssetPane(user);
        marketPane = new MarketPane(user);
        historyPane = new HistoryPane(user);
    }

    private void addActionEvent() {
        changePassword.addActionListener(this);
        refresh.addActionListener(this);
        ChangeListener changeListener = changeEvent -> user.refreshAll();
        mainPane.addChangeListener(changeListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changePassword) {
            new ChangePassword(null, user);
        } else if (e.getSource() == refresh) {
            user.refreshAll();
        }
    }
}
