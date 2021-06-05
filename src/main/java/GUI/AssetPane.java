package main.java.GUI;

import main.java.tradingPlatform.StandardUser;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AssetPane extends JTabbedPane{
    StandardUser user;

    private final String displayName = "Assets";
    private final int tableWidth = 600;

    public AssetPane(StandardUser user) {
        this.user = user;
        setUserPane();
    }

    private void setUserPane(){
        JPanel addUserPanel = addUserPanel();

        addTab("Assets", addUserPanel);
    }

    private JPanel addUserPanel() {
        JPanel addUser = new JPanel();
        addUser.setLayout(new BoxLayout(addUser, BoxLayout.X_AXIS));
        addUser.add(makeUserListPane());

        return addUser;
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

        JTable userList = new JTable(user.getAssetList());

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(userList.getModel());
        sorter.setComparator(0, Comparator.naturalOrder());
        sorter.setSortsOnUpdates(true);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        userList.setRowSorter(sorter);

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
}
