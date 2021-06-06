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
 * This class is handles the Admin tab "Organisations" and how the data
 * is displayed and can be modified.
 */

public class OrganisationPane extends JTabbedPane implements ActionListener {

    ItAdministration user;

    private String displayName = "Organisations";
    private final int tableWidth = 600;

    private JTextField addOrganisation;
    private JTextField removeOrganisation;
    private JTextField updateOrganisation;
//    private JTextField organisation;
    private JTextField addCredits;
    private JTextField updateCredits;

    private final JButton addButton = new JButton("Add Organisation");
    private final JButton removeButton = new JButton("Remove Organisation");
    private final JButton updateButton = new JButton("Update Credits");

    public OrganisationPane(ItAdministration user) {
        this.user = user;
        setOrganisationPane();
        addActionEvent();
    }

    private void setOrganisationPane(){
        JPanel addOrganisationPanel = addOrganisationPanel();
        JPanel removeUserPanel = removeOrganisationPanel();
        JPanel updatePasswordPanel = updateOrganisationPanel();
        //
        addTab("Add", addOrganisationPanel);
        addTab("Remove", removeUserPanel);
        addTab("Update Credits", updatePasswordPanel);
    }

    private JPanel addOrganisationPanel() {
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

    private JPanel removeOrganisationPanel() {
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

    private JPanel updateOrganisationPanel() {
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
        JLabel title = new JLabel(displayName);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        pad.add(title);
        dataDisplay.add(pad);

        JTable userList = new JTable(user.getOrganisationList());

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
            }
        });

        JScrollPane scroller = new JScrollPane(userList);
        scroller.setViewportView(userList);
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
        JLabel passwordLabel = new JLabel("Credits");

        addOrganisation = new JTextField(20);
        addCredits = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
                .addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(addOrganisation)
                .addComponent(addCredits));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(addOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(addCredits));

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

        JLabel userLabel = new JLabel("Organisation");

        removeOrganisation = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(removeOrganisation));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(removeOrganisation));

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

        JLabel userLabel = new JLabel("Organisation");
        JLabel passwordLabel = new JLabel("Credits");

        updateOrganisation = new JTextField(20);
        updateCredits = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
                .addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(updateOrganisation)
                .addComponent(updateCredits));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(updateOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(updateCredits));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(updateButton);

        return panel;
    }



    private void addActionEvent() {
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        updateButton.addActionListener(this);
        ChangeListener changeListener = changeEvent -> user.refreshOrganisations();
        this.addChangeListener(changeListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {

                try {
                    int credits = parseInt(addCredits.getText());
                    user.addOrganisation(addOrganisation.getText(), credits);

                } catch (NumberFormatException error) {
                    String message = "Credits must be an integer.";
                    throw new InvalidValueException(message);
                }

            } else if (e.getSource() == removeButton) {
                user.removeOrganisation(removeOrganisation.getText());

            } else if (e.getSource() == updateButton) {

                try {
                    int credits = parseInt(updateCredits.getText());
                    user.updateCreditAmount(updateOrganisation.getText(), credits);
                } catch (NumberFormatException error) {
                    String message = "Credits must be an integer.";
                    throw new InvalidValueException(message);
                }

            }
        } catch (DuplicationException | UnknownDatabaseException | NullValueException | InvalidValueException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(),
                    error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        }
        user.refreshOrganisations();


    }
}
