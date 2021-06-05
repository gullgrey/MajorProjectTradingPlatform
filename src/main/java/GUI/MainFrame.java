package main.java.GUI;

import main.java.tradingPlatform.ItAdministration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    ItAdministration user;

    Container contentPane = getContentPane();
    JPanel bottomPanel = new JPanel();
    JTabbedPane mainPane = new JTabbedPane();

    JTabbedPane userPane;
    JTabbedPane organisationPane;
    JTabbedPane assetPane;
    JTabbedPane marketPane;
    JTabbedPane historyPane;

    JButton changePassword = new JButton("Change Password");
    JButton usersButton = new JButton("Users");
    JButton organisationButton = new JButton("Organisation");
    JButton assetButton = new JButton("Assets");
    JButton marketButton = new JButton("Market");
    JButton historyButton = new JButton("History");

    public MainFrame(ItAdministration user) {
        this.user = user;
        setupPanes();
        setLayoutManager();

        setTitle("IT Administration");
        setVisible(true);
        setMinimumSize(new Dimension(1000, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setResizable(false);
    }

    private void setLayoutManager() {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(setDisplayPanel());
        contentPane.add(setMainPanel());
    }

    private JPanel setDisplayPanel() {
        JLabel organisationName = new JLabel(user.getOrganisation());
        JLabel username = new JLabel(user.getUsername());
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(0, 5));
        int horizontalStrut = 50;
        displayPanel.add(organisationName);
        displayPanel.add(Box.createHorizontalStrut(horizontalStrut));
        displayPanel.add(Box.createHorizontalStrut(horizontalStrut));
        displayPanel.add(username);
        displayPanel.add(changePassword);
        return displayPanel;
    }

    private JPanel setButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        int verticalStrut = 5;
        buttonPanel.add(Box.createHorizontalStrut(verticalStrut));
        buttonPanel.add(usersButton);
        buttonPanel.add(Box.createHorizontalStrut(verticalStrut));
        buttonPanel.add(organisationButton);
        buttonPanel.add(Box.createHorizontalStrut(verticalStrut));
        buttonPanel.add(assetButton);
        buttonPanel.add(Box.createHorizontalStrut(verticalStrut));
        buttonPanel.add(marketButton);
        buttonPanel.add(Box.createHorizontalStrut(verticalStrut));
        buttonPanel.add(historyButton);
        buttonPanel.add(Box.createHorizontalStrut(verticalStrut));

        JPanel frame = new JPanel();
        frame.setLayout(new GridLayout(0, 3));
        int horizontalStrut = 50;
        frame.add(Box.createHorizontalStrut(horizontalStrut));
        frame.add(buttonPanel);
        frame.add(Box.createHorizontalStrut(horizontalStrut));
        return frame;
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

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
                userPane = new UserPane(user);
                organisationPane = new OrganisationPane(user);
                assetPane = new AdminAssetPane(user);
                marketPane = new MarketPane(user);
                historyPane = new HistoryPanel(user);
//            }
//        });




    }



    private void setOrganisationPane(){

    }

    private void setAssetPane(){

    }

    private void setMarketPane(){

    }

    private void setHistoryPane(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
