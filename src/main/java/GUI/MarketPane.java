package main.java.GUI;

import main.java.tradingPlatform.TPUser;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class MarketPane extends JTabbedPane implements ActionListener {

    TPUser user;

    private String buyName = "Buy Orders";
    private String sellName = "Sell Orders";
    private final int tableWidth = 600;

    private JTextField buyOrganisationFilter;
    private JTextField buyAssetFilter;
    private JTextField sellOrganisationFilter;
    private JTextField sellAssetFilter;

    private TableRowSorter<TableModel> buySorter;
    private TableRowSorter<TableModel> sellSorter;

    private final JButton buyFilterButton = new JButton("Filter");
    private final JButton sellFilterButton = new JButton("Filter");
    private final JButton buyClear = new JButton("Clear");
    private final JButton sellClear = new JButton("Clear");

    public MarketPane(TPUser user) {
        this.user = user;
        setUserPane();
        addActionEvent();
    }

    private void setUserPane(){
        JPanel addUserPanel = addBuyPanel();
        JPanel addSellPanel = addSellPanel();
        //
        addTab("Buy Orders", addUserPanel);
        addTab("Sell Orders", addSellPanel);
    }

    private JPanel addBuyPanel() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeBuyListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeBuyFieldsPanel());
        addUser.add(Box.createVerticalStrut(horizontalStrut));

        return addUser;
    }

    private JPanel addSellPanel() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeSellListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeSellFieldsPanel());
        addUser.add(Box.createVerticalStrut(horizontalStrut));

        return addUser;
    }

    private JPanel makeBuyListPane() {

        JPanel dataDisplay = new JPanel();
        dataDisplay.setLayout(new BoxLayout(dataDisplay, BoxLayout.Y_AXIS));

        JPanel pad = new JPanel();
        pad.setLayout(new BoxLayout(pad, BoxLayout.X_AXIS));
        JLabel title = new JLabel(buyName);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        pad.add(title);
        dataDisplay.add(pad);

        JTable userList = new JTable(user.getBuyOrderList());

        buySorter = new TableRowSorter<>(userList.getModel());
        buySorter.setComparator(0, Comparator.naturalOrder());
        buySorter.setSortsOnUpdates(true);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        buySorter.setSortKeys(sortKeys);
        userList.setRowSorter(buySorter);

        userList.getSelectionModel().addListSelectionListener(event -> {

            if (!event.getValueIsAdjusting() && userList.getSelectedRow() > -1) {
                buyOrganisationFilter.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
                buyAssetFilter.setText(userList.getValueAt(userList.getSelectedRow(), 2).toString());
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

    private JPanel makeSellListPane() {

        JPanel dataDisplay = new JPanel();
        dataDisplay.setLayout(new BoxLayout(dataDisplay, BoxLayout.Y_AXIS));

        JPanel pad = new JPanel();
        pad.setLayout(new BoxLayout(pad, BoxLayout.X_AXIS));
        JLabel title = new JLabel(sellName);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        pad.add(title);
        dataDisplay.add(pad);

        JTable userList = new JTable(user.getSellOrderList());

        sellSorter = new TableRowSorter<>(userList.getModel());
        sellSorter.setComparator(0, Comparator.naturalOrder());
        sellSorter.setSortsOnUpdates(true);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sellSorter.setSortKeys(sortKeys);
        userList.setRowSorter(sellSorter);

        userList.getSelectionModel().addListSelectionListener(event -> {

            if (!event.getValueIsAdjusting() && userList.getSelectedRow() > -1) {
                sellOrganisationFilter.setText(userList.getValueAt(userList.getSelectedRow(), 1).toString());
                sellAssetFilter.setText(userList.getValueAt(userList.getSelectedRow(), 2).toString());
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

    private JPanel makeBuyFieldsPanel() {
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

        JLabel buyLabel = new JLabel("Organisation");
        JLabel assetLabel = new JLabel("Asset");

        buyOrganisationFilter = new JTextField(20);
        buyAssetFilter = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyLabel)
                .addComponent(assetLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyOrganisationFilter)
                .addComponent(buyAssetFilter));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyLabel).addComponent(buyOrganisationFilter));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(buyAssetFilter));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(buyFilterButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buyClear);

        return panel;
    }

    private JPanel makeSellFieldsPanel() {
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

        JLabel buyLabel = new JLabel("Organisation");
        JLabel assetLabel = new JLabel("Asset");

        sellOrganisationFilter = new JTextField(20);
        sellAssetFilter = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyLabel)
                .addComponent(assetLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(sellOrganisationFilter)
                .addComponent(sellAssetFilter));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyLabel).addComponent(sellOrganisationFilter));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(sellAssetFilter));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(sellFilterButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sellClear);

        return panel;
    }



    private void addActionEvent() {
        buyFilterButton.addActionListener(this);
        sellFilterButton.addActionListener(this);
        buyClear.addActionListener(this);
        sellClear.addActionListener(this);
        ChangeListener changeListener = changeEvent -> user.refreshOrders();
        this.addChangeListener(changeListener);
    }

    private void filterBuyOrders() {
        RowFilter<Object, Object> buyOrg;
        RowFilter<Object, Object> sellOrg;
        ArrayList<RowFilter<Object,Object>> andFilters = new ArrayList<>();
        //If current expression doesn't parse, don't update.
        try {
            buyOrg = RowFilter.regexFilter(buyOrganisationFilter.getText(),1);
            sellOrg = RowFilter.regexFilter(buyAssetFilter.getText(),2);
            andFilters.add(buyOrg);
            andFilters.add(sellOrg);
        } catch (java.util.regex.PatternSyntaxException error) {
            return;
        }
        buySorter.setRowFilter(RowFilter.andFilter(andFilters));
    }

    private void filterSellOrders() {
        RowFilter<Object, Object> buyOrg;
        RowFilter<Object, Object> sellOrg;
        ArrayList<RowFilter<Object,Object>> andFilters = new ArrayList<>();
        //If current expression doesn't parse, don't update.
        try {
            buyOrg = RowFilter.regexFilter(sellOrganisationFilter.getText(),1);
            sellOrg = RowFilter.regexFilter(sellAssetFilter.getText(),2);
            andFilters.add(buyOrg);
            andFilters.add(sellOrg);
        } catch (java.util.regex.PatternSyntaxException error) {
            return;
        }
        sellSorter.setRowFilter(RowFilter.andFilter(andFilters));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buyFilterButton) {

            filterBuyOrders();
        } else if (e.getSource() == sellFilterButton) {

            filterSellOrders();
        } else if (e.getSource() == buyClear || e.getSource() == sellClear) {
            buyOrganisationFilter.setText("");
            buyAssetFilter.setText("");
            sellOrganisationFilter.setText("");
            sellAssetFilter.setText("");
            filterBuyOrders();
            filterSellOrders();
        }
        user.refreshOrders();
    }
}
