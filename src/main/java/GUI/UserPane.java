package main.java.GUI;

import main.java.tradingPlatform.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class UserPane extends JTabbedPane implements ActionListener {

    ItAdministration user;

//    private JTable userList;
    private JTextField addUsername;
    private JTextField removeUsername;
    private JTextField updateUsername;
    private JTextField organisation;
    private JTextField addPassword;
    private JTextField updatePassword;

    private final JButton addButton = new JButton("Add User");
    private final JButton removeButton = new JButton("Remove User");
    private final JButton updateButton = new JButton("Update Password");

//    private JTable userList;



    public UserPane(ItAdministration user) {
        this.user = user;
        setUserPane();
        addActionEvent();
    }

    private void setUserPane(){
        JPanel addUserPanel = addUserPanel();
        JPanel removeUserPanel = removeUserPanel();
//        JPanel updatePasswordPanel = updateUserPanel();
        //
        addTab("Add", addUserPanel);
        addTab("Remove", removeUserPanel);
//        addTab("Update Password", updatePasswordPanel);
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

    private JScrollPane makeUserListPane() {

        JTable userList = new JTable(user.getUserList());

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(userList.getModel());
        sorter.setComparator(0, Comparator.naturalOrder());
        sorter.setSortsOnUpdates(true);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        userList.setRowSorter(sorter);

        userList.getSelectionModel().addListSelectionListener(event -> {

            addUsername.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
            removeUsername.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
//            updateUsername.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
            organisation.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
        });

        JScrollPane scroller = new JScrollPane(userList);
        scroller.setViewportView(userList);
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
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

        JLabel userLabel = new JLabel("Username");
        JLabel organisationLabel = new JLabel("Organisation");
        JLabel passwordLabel = new JLabel("Password");

        addUsername = new JTextField(20);
        organisation = new JTextField(20);
        addPassword = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
                .addComponent(organisationLabel).addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(addUsername)
                .addComponent(organisation).addComponent(addPassword));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(addUsername));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(organisationLabel).addComponent(organisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(addPassword));

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

        JLabel userLabel = new JLabel("Username");

        removeUsername = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(removeUsername));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(removeUsername));

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

        JLabel userLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        updateUsername = new JTextField(20);
        updatePassword = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
                .addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(updateUsername)
                .addComponent(updatePassword));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(updateUsername));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(updatePassword));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(updateButton);

        return panel;
    }



    private void addActionEvent() {
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        updateButton.addActionListener(this);
        ChangeListener changeListener = changeEvent -> SwingUtilities.invokeLater(() -> user.refreshUsers());
        this.addChangeListener(changeListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {
                if (organisation.getText().equals(PlatformGlobals.getAdminOrganisation())){
                    user.addItUser(addUsername.getText(), addPassword.getText());
                } else {
                    user.addStandardUser(addUsername.getText(), addPassword.getText(), organisation.getText());
                }

            } else if (e.getSource() == removeButton) {
                user.removeUser(addUsername.getText());

            } else if (e.getSource() == updateButton) {
                user.changeUserPassword(updateUsername.getText(), updatePassword.getText());
            }
        } catch (DuplicationException | UnknownDatabaseException | NullValueException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(),
                    error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        }
        SwingUtilities.invokeLater(() -> user.refreshUsers());


    }
}
