package main.java.GUI;

import main.java.tradingPlatform.ItAdministration;
import main.java.tradingPlatform.TPUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    ItAdministration user;

    Container contentPane = getContentPane();
    JPanel bottomPanel = new JPanel();
    JPanel mainPanel = new JPanel();

    JTabbedPane userPane;
    JTabbedPane organisationPane;
    JTabbedPane assetPane = new JTabbedPane();
    JTabbedPane marketPane = new JTabbedPane();
    JTabbedPane historyPane = new JTabbedPane();

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
        setMinimumSize(new Dimension(1400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setResizable(false);
    }

    private void setLayoutManager() {
        contentPane.setLayout(new GridLayout(2, 0));
        bottomPanel.setLayout(new GridLayout(0, 2));
        bottomPanel.add(setButtonPanel());
        bottomPanel.add(setMainPanel());
        contentPane.add(setDisplayPanel());
        contentPane.add(bottomPanel);
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

    private JPanel setMainPanel() {
        mainPanel.add(organisationPane);
        return mainPanel;
    }

    private void setupPanes() {

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
                userPane = new UserPane(user);
                organisationPane = new OrganisationPane(user);
                setAssetPane();
                setMarketPane();
                setHistoryPane();
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
