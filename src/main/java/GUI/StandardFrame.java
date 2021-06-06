package main.java.GUI;

import main.java.tradingPlatform.StandardUser;
import main.java.tradingPlatform.UnknownDatabaseException;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class implements methods in MainFrame specific to what a Member can see
 * and is responsible for setting up the main display GUI that all methods
 * implement it use.
 */

public class StandardFrame extends MainFrame{

    StandardUser user;

    JTabbedPane ordersPane;

    public StandardFrame(StandardUser user) {

        super();
        this.user = user;
        organisationName.setText(user.getOrganisation());
        username.setText(user.getUsername());
        creditsTitle.setText("Credits: ");
        setCredits();

        setupPanes();
        contentPane.add(setMainPanel());
        addActionEvent();
        setTitle("Trading Platform");

    }

    protected void setCredits() {
        try {
            credits.setText(Integer.toString(user.getCredits()));
        } catch (UnknownDatabaseException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(),
                    error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    JTabbedPane setMainPanel() {
        mainPane.setMinimumSize(new Dimension(600, 300));
        mainPane.addTab("Orders", ordersPane);
        mainPane.addTab("Assets", assetPane);
        mainPane.addTab("Market", marketPane);
        mainPane.addTab("History", historyPane);

        return mainPane;
    }

    @Override
    void setupPanes() {
        ordersPane = new OrdersPane(user, this);
        assetPane = new AssetPane(user);
        marketPane = new MarketPane(user);
        historyPane = new HistoryPane(user);
    }

    @Override
    void addActionEvent() {
        changePassword.addActionListener(this);
        refresh.addActionListener(this);
        ChangeListener changeListener = changeEvent -> {
            user.refreshAll();
            setCredits();
        };
        mainPane.addChangeListener(changeListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changePassword) {
            new ChangePassword(null, user);
        } else if (e.getSource() == refresh) {
            user.refreshAll();
        }
        setCredits();
    }
}
