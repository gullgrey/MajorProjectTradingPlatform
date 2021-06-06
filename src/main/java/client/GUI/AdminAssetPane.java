package main.java.client.GUI;

import main.java.client.tradingPlatform.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Integer.parseInt;

/**
 * This class handles the GUI layout for an Admin "Asset" tab. Provides additional
 * widgets that allow and Admin to add assets to an specific Organisation.
 */
public class AdminAssetPane extends JTabbedPane implements ActionListener {

    ItAdministration user;

    private JTextField addOrganisation;
    private JTextField removeOrganisation;
    private JTextField updateOrganisation;
    private JTextField addAsset;
    private JTextField removeAsset;
    private JTextField updateAsset;
    private JTextField addAmount;
    private JTextField updateAmount;

    private final JButton addButton = new JButton("Add Asset");
    private final JButton removeButton = new JButton("Remove Asset");
    private final JButton updateButton = new JButton("Update Asset");

    public AdminAssetPane(ItAdministration user) {
        this.user = user;
        setUserPane();
        addActionEvent();
    }

    private void setUserPane(){
        JPanel addUserPanel = addUserPanel();
        JPanel removeUserPanel = removeUserPanel();
        JPanel updatePasswordPanel = updateUserPanel();
        //
        addTab("Add", addUserPanel);
        addTab("Remove", removeUserPanel);
        addTab("Update Asset", updatePasswordPanel);
    }

    private JPanel addUserPanel() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeUserListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeAddFieldsPanel());
        addUser.add(Box.createVerticalStrut(horizontalStrut));

        return addUser;
    }

    private JPanel removeUserPanel() {
        JPanel removeUser = new JPanel();
        removeUser.setLayout(new BoxLayout(removeUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        removeUser.add(Box.createVerticalStrut(horizontalStrut));
        removeUser.add(makeUserListPane());
        removeUser.add(Box.createVerticalStrut(horizontalStrut));
        removeUser.add(makeRemoveFieldsPanel());
        removeUser.add(Box.createVerticalStrut(horizontalStrut));

        return removeUser;
    }

    private JPanel updateUserPanel() {
        JPanel updateUser = new JPanel();
        updateUser.setLayout(new BoxLayout(updateUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        updateUser.add(Box.createVerticalStrut(horizontalStrut));
        updateUser.add(makeUserListPane());
        updateUser.add(Box.createVerticalStrut(horizontalStrut));
        updateUser.add(makeUpdateFieldsPanel());
        updateUser.add(Box.createVerticalStrut(horizontalStrut));

        return updateUser;
    }

    private JPanel makeUserListPane() {

        JPanel dataDisplay = new JPanel();
        dataDisplay.setLayout(new BoxLayout(dataDisplay, BoxLayout.Y_AXIS));

        JPanel pad = new JPanel();
        pad.setLayout(new BoxLayout(pad, BoxLayout.X_AXIS));
        String displayName = "Assets";
        JLabel title = new JLabel(displayName);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        pad.add(title);
        dataDisplay.add(pad);

        JTable userList = new JTable(user.getAssetList());

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(userList.getModel());
        sorter.setComparator(0, Comparator.naturalOrder());
        sorter.setSortsOnUpdates(true);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        userList.setRowSorter(sorter);

        userList.getSelectionModel().addListSelectionListener(event -> {

            if (!event.getValueIsAdjusting() && userList.getSelectedRow() > -1) {
                addOrganisation.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
                removeOrganisation.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
                updateOrganisation.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
                addAsset.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
                removeAsset.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
                updateAsset.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
            }
        });

        JScrollPane scroller = new JScrollPane(userList);
        scroller.setViewportView(userList);
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        int tableWidth = 600;
        scroller.setMinimumSize(new Dimension(tableWidth, 150));
        scroller.setPreferredSize(new Dimension(tableWidth, 150));
        scroller.setMaximumSize(new Dimension(tableWidth, 200));

        dataDisplay.add(scroller);
        return dataDisplay;
    }

    private JPanel makeAddFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel userLabel = new JLabel("Organisation");
        JLabel organisationLabel = new JLabel("Asset");
        JLabel passwordLabel = new JLabel("Amount");

        addOrganisation = new JTextField(20);
        addAsset = new JTextField(20);
        addAmount = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
                .addComponent(organisationLabel).addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(addOrganisation)
                .addComponent(addAsset).addComponent(addAmount));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(addOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(organisationLabel).addComponent(addAsset));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(addAmount));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(addButton);

        return panel;
    }

    private JPanel makeRemoveFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel organisationLabel = new JLabel("Organisation");
        JLabel assetLabel = new JLabel("Asset");

        removeOrganisation = new JTextField(20);
        removeAsset = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(organisationLabel).addComponent(assetLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(removeOrganisation).addComponent(removeAsset));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(organisationLabel).addComponent(removeOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(removeAsset));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(removeButton);

        return panel;
    }

    private JPanel makeUpdateFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel organisationLabel = new JLabel("Organisation");
        JLabel assetLabel = new JLabel("Asset");
        JLabel amountLabel = new JLabel("Amount");

        updateOrganisation = new JTextField(20);
        updateAsset = new JTextField(20);
        updateAmount = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(organisationLabel)
                .addComponent(assetLabel).addComponent(amountLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(updateOrganisation)
                .addComponent(updateAsset).addComponent(updateAmount));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(organisationLabel).addComponent(updateOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(updateAsset));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(amountLabel).addComponent(updateAmount));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(updateButton);

        return panel;
    }



    private void addActionEvent() {
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        updateButton.addActionListener(this);
        ChangeListener changeListener = changeEvent -> user.refreshAssets();
        this.addChangeListener(changeListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {

                try {
                    int amount = parseInt(addAmount.getText());
                    user.addAsset(addOrganisation.getText(), addAsset.getText(), amount);

                } catch (NumberFormatException error) {
                    String message = "Amount must be an integer.";
                    throw new InvalidValueException(message);
                }

            } else if (e.getSource() == removeButton) {

                user.removeAsset(removeOrganisation.getText(), removeAsset.getText());

            } else if (e.getSource() == updateButton) {

                try {
                    int amount = parseInt(updateAmount.getText());
                    user.updateAssetAmount(updateOrganisation.getText(), updateAsset.getText(), amount);

                } catch (NumberFormatException error) {
                    String message = "Amount must be an integer.";
                    throw new InvalidValueException(message);
                }

            }
        } catch (DuplicationException | UnknownDatabaseException | NullValueException | InvalidValueException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(),
                    error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        }
        user.refreshAssets();


    }
}
