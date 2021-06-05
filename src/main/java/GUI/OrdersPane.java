package main.java.GUI;

import main.java.tradingPlatform.InvalidValueException;
import main.java.tradingPlatform.NullValueException;
import main.java.tradingPlatform.StandardUser;
import main.java.tradingPlatform.UnknownDatabaseException;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class OrdersPane extends JTabbedPane implements ActionListener {
    StandardUser user;
    StandardFrame frame;

    private String buyName = "Buy Orders";
    private String sellName = "Sell Orders";
    private final int tableWidth = 600;

    private JTextField buyIdField;
    private JTextField buyAssetField;
    private JTextField buyAmountField;
    private JTextField buyCreditsField;

    private JTextField sellIdField;
    private JTextField sellAssetField;
    private JTextField sellAmountField;
    private JTextField sellCreditsField;

    private final JButton buyOrderButton = new JButton("Add Buy Order");
    private final JButton sellOrderButton = new JButton("Add Sell Order");
    private final JButton buyRemoveButton = new JButton("Remove Buy Order");
    private final JButton sellRemoveButton = new JButton("Remove Sell Order");

    public OrdersPane(StandardUser user, StandardFrame frame) {
        this.user = user;
        this.frame = frame;
        setUserPane();
        addActionEvent();
    }

    private void setUserPane(){
        JPanel addBuyPanel = addBuyPanel();
        JPanel addSellPanel = addSellPanel();
        JPanel addBuyRemove = addBuyRemove();
        JPanel addSellRemove = addSellRemove();
        //
        addTab("Buy Orders", addBuyPanel);
        addTab("Sell Orders", addSellPanel);
        addTab("Remove Buy Order", addBuyRemove);
        addTab("Remove Sell Order", addSellRemove);
    }

    private JPanel addBuyPanel() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeSellListPane());
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
        addUser.add(makeBuyListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeSellFieldsPanel());
        addUser.add(Box.createVerticalStrut(horizontalStrut));

        return addUser;
    }

    private JPanel addBuyRemove() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeBuyListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeBuyRemovePanel());
        addUser.add(Box.createVerticalStrut(horizontalStrut));

        return addUser;
    }

    private JPanel addSellRemove() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        int horizontalStrut = 20;
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeSellListPane());
        addUser.add(Box.createVerticalStrut(horizontalStrut));
        addUser.add(makeSellRemovePanel());
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

        userList.getSelectionModel().addListSelectionListener(event -> {

            if (!event.getValueIsAdjusting() && userList.getSelectedRow() > -1) {
                buyIdField.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
                sellAssetField.setText(userList.getValueAt(userList.getSelectedRow(), 2).toString());
                sellAmountField.setText(userList.getValueAt(userList.getSelectedRow(), 3).toString());
                sellCreditsField.setText(userList.getValueAt(userList.getSelectedRow(), 4).toString());
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

        userList.getSelectionModel().addListSelectionListener(event -> {

            if (!event.getValueIsAdjusting() && userList.getSelectedRow() > -1) {
                sellIdField.setText(userList.getValueAt(userList.getSelectedRow(), 0).toString());
                buyAssetField.setText(userList.getValueAt(userList.getSelectedRow(), 2).toString());
                buyAmountField.setText(userList.getValueAt(userList.getSelectedRow(), 3).toString());
                buyCreditsField.setText(userList.getValueAt(userList.getSelectedRow(), 4).toString());
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

        JLabel assetLabel = new JLabel("Asset");
        JLabel amountLabel = new JLabel("Amount");
        JLabel creditsLabel = new JLabel("Credits");


        buyAssetField = new JTextField(20);
        buyAmountField = new JTextField(20);
        buyCreditsField = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(assetLabel)
                .addComponent(amountLabel).addComponent(creditsLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyAssetField)
                .addComponent(buyAmountField).addComponent(buyCreditsField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(buyAssetField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(amountLabel).addComponent(buyAmountField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(creditsLabel).addComponent(buyCreditsField));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(buyOrderButton);

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

        JLabel assetLabel = new JLabel("Asset");
        JLabel amountLabel = new JLabel("Amount");
        JLabel creditsLabel = new JLabel("Credits");


        sellAssetField = new JTextField(20);
        sellAmountField = new JTextField(20);
        sellCreditsField = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(assetLabel)
                .addComponent(amountLabel).addComponent(creditsLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(sellAssetField)
                .addComponent(sellAmountField).addComponent(sellCreditsField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(sellAssetField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(amountLabel).addComponent(sellAmountField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(creditsLabel).addComponent(sellCreditsField));

        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(sellOrderButton);

        return panel;
    }

    private JPanel makeBuyRemovePanel() {
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

        JLabel idLabel = new JLabel("Id");


        buyIdField = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(idLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyIdField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(idLabel).addComponent(buyIdField));
        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(buyRemoveButton);

        return panel;
    }

    private JPanel makeSellRemovePanel() {
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

        JLabel idLabel = new JLabel("Id");


        sellIdField = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(idLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(sellIdField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();


        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(idLabel).addComponent(sellIdField));
        layout.setVerticalGroup(vGroup);

        panel.add(inputPanel);
        panel.add(sellRemoveButton);

        return panel;
    }

    private void addActionEvent() {
        buyOrderButton.addActionListener(this);
        sellOrderButton.addActionListener(this);
        buyRemoveButton.addActionListener(this);
        sellRemoveButton.addActionListener(this);
        ChangeListener changeListener = changeEvent -> {
            user.refreshOrders();
            frame.setCredits();
        };
        this.addChangeListener(changeListener);
    }

    private void placeBuyOrder() throws InvalidValueException, UnknownDatabaseException {
        int amount;
        int credits;
        try {
            amount = parseInt(buyAmountField.getText());
        } catch (NumberFormatException error) {
            String message = "Credits must be an integer.";
            throw new InvalidValueException(message);
        }

        try {
            credits = parseInt(buyCreditsField.getText());
        } catch (NumberFormatException error) {
            String message = "Credits must be an integer.";
            throw new InvalidValueException(message);
        }
        user.buyAsset(buyAssetField.getText(), amount, credits);
    }

    private void placeSellOrder() throws InvalidValueException, UnknownDatabaseException {
        int amount;
        int credits;
        try {
            amount = parseInt(sellAmountField.getText());
        } catch (NumberFormatException error) {
            String message = "Credits must be an integer.";
            throw new InvalidValueException(message);
        }

        try {
            credits = parseInt(sellCreditsField.getText());
        } catch (NumberFormatException error) {
            String message = "Credits must be an integer.";
            throw new InvalidValueException(message);
        }
        user.sellAsset(sellAssetField.getText(), amount, credits);
    }

    private void removeBuyOrder() throws InvalidValueException, UnknownDatabaseException, NullValueException {
        int id;
        try {
            id = parseInt(buyIdField.getText());
        } catch (NumberFormatException error) {
            String message = "Credits must be an integer.";
            throw new InvalidValueException(message);
        }
        user.removeOrder(id);
    }

    private void removeSellOrder() throws InvalidValueException, UnknownDatabaseException, NullValueException {
        int id;
        try {
            id = parseInt(sellIdField.getText());
        } catch (NumberFormatException error) {
            String message = "Credits must be an integer.";
            throw new InvalidValueException(message);
        }
        user.removeOrder(id);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == buyOrderButton) {

                placeBuyOrder();
            } else if (e.getSource() == sellOrderButton) {
                placeSellOrder();
            } else if (e.getSource() == buyRemoveButton) {
                removeBuyOrder();
            } else if (e.getSource() == sellRemoveButton) {
                removeSellOrder();
            }

        } catch (InvalidValueException | UnknownDatabaseException | NullValueException error) {
            JOptionPane.showMessageDialog(this, error.getMessage(),
                    error.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        }

        user.refreshOrders();
        frame.setCredits();
    }
}
