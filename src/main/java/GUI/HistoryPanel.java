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

public class HistoryPanel extends JTabbedPane implements ActionListener {

    TPUser user;

    private String displayName = "History";

    private JTextField buyOrganisation;
    private JTextField sellOrganisation;
    private JTextField assetFilter;

    private TableRowSorter<TableModel> sorter;

    private final JButton filterButton = new JButton("Filter");

    public HistoryPanel(TPUser user) {
        this.user = user;
        setUserPane();
        addActionEvent();
    }

    private void setUserPane(){
        JPanel addUserPanel = addUserPanel();
        //
        addTab("History", addUserPanel);
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

    private JPanel makeUserListPane() {

        JPanel dataDisplay = new JPanel();
        dataDisplay.setLayout(new BoxLayout(dataDisplay, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(displayName);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        dataDisplay.add(title);

        JTable userList = new JTable(user.getTransactionList());

        sorter = new TableRowSorter<>(userList.getModel());
        sorter.setComparator(0, Comparator.naturalOrder());
        sorter.setSortsOnUpdates(true);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        userList.setRowSorter(sorter);

        userList.getSelectionModel().addListSelectionListener(event -> {

            if (!event.getValueIsAdjusting() && userList.getSelectedRow() > -1) {
                buyOrganisation.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
                sellOrganisation.setText(userList.getValueAt(userList.getSelectedRow(), 2).toString());
                assetFilter.setText(userList.getValueAt(userList.getSelectedRow(), 3).toString());
            }
        });

        JScrollPane scroller = new JScrollPane(userList);
        scroller.setViewportView(userList);
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(600, 150));
        scroller.setPreferredSize(new Dimension(600, 150));
        scroller.setMaximumSize(new Dimension(600, 200));

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

        JLabel buyLabel = new JLabel("Buying Organisation");
        JLabel sellLabel = new JLabel("Selling Organisation");
        JLabel passwordLabel = new JLabel("Asset");

        buyOrganisation = new JTextField(20);
        sellOrganisation = new JTextField(20);
        assetFilter = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyLabel)
                .addComponent(sellLabel).addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyOrganisation)
                .addComponent(sellOrganisation).addComponent(assetFilter));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyLabel).addComponent(buyOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sellLabel).addComponent(sellOrganisation));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel).addComponent(assetFilter));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(filterButton);

        return panel;
    }



    private void addActionEvent() {
        filterButton.addActionListener(this);
        ChangeListener changeListener = changeEvent -> user.refreshTransactions();
        this.addChangeListener(changeListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == filterButton) {

            RowFilter<Object, Object> buyOrg;
            RowFilter<Object, Object> sellOrg;
            RowFilter<Object, Object> assets;
            ArrayList<RowFilter<Object,Object>> andFilters = new ArrayList<>();
            //If current expression doesn't parse, don't update.
            try {
                buyOrg = RowFilter.regexFilter(buyOrganisation.getText(),1);
                sellOrg = RowFilter.regexFilter(sellOrganisation.getText(),2);
                assets = RowFilter.regexFilter(assetFilter.getText(),3);
                andFilters.add(buyOrg);
                andFilters.add(sellOrg);
                andFilters.add(assets);
            } catch (java.util.regex.PatternSyntaxException error) {
                return;
            }
            sorter.setRowFilter(RowFilter.andFilter(andFilters));

        }
        user.refreshTransactions();
    }
}
