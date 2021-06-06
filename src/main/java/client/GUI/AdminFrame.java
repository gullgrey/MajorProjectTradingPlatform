package main.java.client.GUI;

import main.java.client.tradingPlatform.ItAdministration;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class implements methods in MainFrame specific to what an Admin can see
 * and is responsible for setting up the main display GUI that all methods
 * that implement it use.
 */

public class AdminFrame extends MainFrame{

    ItAdministration user;

    JTabbedPane userPane;
    JTabbedPane organisationPane;

    public AdminFrame(ItAdministration user) {

        super();
        this.user = user;
        organisationName.setText(user.getOrganisation());
        username.setText(user.getUsername());

        setupPanes();
        contentPane.add(setMainPanel());
        addActionEvent();
        setTitle("IT Administration");
    }

    @Override
    JTabbedPane setMainPanel() {
        mainPane.setMinimumSize(new Dimension(600, 300));
        mainPane.addTab("Users", userPane);
        mainPane.addTab("Organisations", organisationPane);
        mainPane.addTab("Assets", assetPane);
        mainPane.addTab("Market", marketPane);
        mainPane.addTab("History", historyPane);

        return mainPane;
    }

    @Override
    void setupPanes() {

        userPane = new UserPane(user);
        organisationPane = new OrganisationPane(user);
        assetPane = new AdminAssetPane(user);
        marketPane = new MarketPane(user);
        historyPane = new HistoryPane(user);

    }

    @Override
    void addActionEvent() {
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
