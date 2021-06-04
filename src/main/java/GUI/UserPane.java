package main.java.GUI;

import main.java.tradingPlatform.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class UserPane extends JTabbedPane implements ActionListener {

    ItAdministration user;

    private JTable userList;
    private JTextField username;
    private JTextField organisation;
    private JTextField password;
    private final JButton addButton = new JButton("Add User");
    JScrollPane scroller;
    JPanel addUserPanel;

    public UserPane(ItAdministration user) {
        this.user = user;
        setUserPane();
        addActionEvent();
    }

    private void setUserPane(){
        addUserPanel = addUserPanel();
        //
        addTab("Add", addUserPanel);
    }

    private JPanel addUserPanel() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeUserListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeUserFieldsPanel());
        addUser.add(Box.createVerticalStrut(horizontalStrut));

        return addUser;
    }

    private JScrollPane makeUserListPane() {

        userList = new JTable(user.getUserList());
        userList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                username.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
                organisation.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
//                password.setText(userList.getValueAt(userList.getSelectedRow(), 2).toString());
            }
        });

        scroller = new JScrollPane(userList);
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

    private JPanel makeUserFieldsPanel() {
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

        username = new JTextField(20);
        organisation = new JTextField(20);
        password = new JTextField(20);
//        setFieldsEditable(false);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
                .addComponent(organisationLabel).addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(username)
                .addComponent(organisation).addComponent(password));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(username));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(organisationLabel).addComponent(organisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(password));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(addButton);

        return panel;
    }

    private void addActionEvent() {
        addButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {
                if (organisation.getText().equals(PlatformGlobals.getAdminOrganisation())){
                    user.addItUser(username.getText(), password.getText());
                } else {
                    user.addStandardUser(username.getText(), password.getText(), organisation.getText());
                }
                user.refreshUsers();
            }
        } catch (DuplicationException | UnknownDatabaseException | NullValueException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(),
                    "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
}
