package main.java.database;

import main.java.tradingPlatform.Asset;
import main.java.tradingPlatform.TPOrder;
import main.java.tradingPlatform.Transaction;

import java.io.IOException;
import java.sql.*;
import java.util.Set;
import java.util.TreeSet;


/**
 * TODO
 */
public class JDBCTradingPlatformDataSource implements TradingPlatformDataSource{

    public static final String CREATE_ORGANISATION_UNITS = "create table if not exists organisation_units (" +
            "organisation_name varchar(30) not null," +
            "credits int not null," +
            "primary key (organisation_name));";

    public static final String CREATE_ASSET = "create table if not exists asset (" +
            "asset_name varchar(30) not null," +
            "organisation_name varchar(30) not null," +
            "amount int not null," +
            "primary key (asset_name, organisation_name)," +
            "constraint fk_organisation_asset " +
            "foreign key (organisation_name)" +
            "references organisation_units(organisation_name)" +
            "on delete cascade);";

    public static final String CREATE_USER_INFORMATION = "create table if not exists user_information (" +
            "username varchar(30) not null," +
            "organisation_name varchar(30) not null," +
            "account_type varchar(8) not null," +
            "password varchar(30) not null," +  //TODO subject to change, check how hashing works.
            "primary key (username)," +
            "constraint fk_organisation_user " +
            "foreign key (organisation_name)" +
            "references organisation_units(organisation_name)" +
            "on delete cascade);";

    public static final String CREATE_CURRENT_TRADES = "create table if not exists current_trades (" +
            "order_id INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," +
            "organisation_name varchar(30) not null," +
            "asset_name varchar(30) not null," +
            "credits int not null," +
            "amount int not null," +
            "date datetime," +
            "type varchar(4) not null," +
            "constraint fk_organisation_trades " +
            "foreign key (organisation_name)" +
            "references organisation_units(organisation_name)" +
            "on delete cascade);";

    public static final String CREATE_TRADE_HISTORY = "create table if not exists trade_history (" +
            "transaction_id INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," +
            "buy_organisation_name varchar(30) not null," +
            "sell_organisation_name varchar(30) not null," +
            "asset_name varchar(30) not null," +
            "credits int not null," +
            "amount int not null," +
            "datetime datetime);";

    // Asset queries
    private static final String GET_ASSETS = "SELECT * FROM asset WHERE organisation_name=?";
    private static final String ADD_ASSET  = "INSERT INTO asset VALUES (?,?,?)";
    private static final String DELETE_ASSET = "DELETE FROM asset WHERE asset_name = ? AND organisation_name = ?";
    private static final String GET_ASSET_AMOUNT = "SELECT amount  FROM asset WHERE organisation_name = ? AND  asset_name = ?";
    private static final String UPDATE_ASSET_AMOUNT = "UPDATE asset SET amount = amount + ? WHERE asset_name = ? AND organisation_name = ?";

    // Organisation queries
    private static final String GET_CREDITS = "SELECT credits FROM organisation_units WHERE organisation_name=?";
    private static final String UPDATE_CREDITS = "UPDATE organisation_units SET credits = credits + ? WHERE organisation_name=?";
    private static final String GET_ORGANISATIONS = "SELECT organisation_name FROM organisation_units";
    private static final String ADD_ORGANISATION  = "INSERT INTO organisation_units VALUES (?,?)";
    private static final String DELETE_ORGANISATION = "DELETE FROM organisation_units WHERE organisation_name=?";

    // User queries
    private static final String GET_USERS = "SELECT username FROM user_information";
    private static final String GET_USER_PASSWORD = "SELECT password FROM user_information WHERE username=?";
    private static final String ADD_USER = "INSERT INTO user_information VALUES (?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM user_information WHERE username=?";
    private static final String UPDATE_PASSWORD  = "UPDATE user_information SET password = ? WHERE username = ?";
    private static final String GET_USER_ORGANISATION = "SELECT organisation_name FROM user_information WHERE username=?";

    // TPOrder queries
    private static final String GET_ORDER = "SELECT * FROM current_trades WHERE order_id=?";
    private static final String GET_ORDERS = "SELECT * FROM current_trades WHERE organisation_name=? AND  asset_name=? AND type=?";
    private static final String ADD_ORDER = "INSERT INTO current_trades (organisation_name, asset_name, credits, amount, date, type) VALUES (?,?,?,?,datetime('now'),?)"; //use NOW() as datetime for mariaDB.
    private static final String DELETE_ORDER = "DELETE FROM current_trades WHERE order_id=?";

    // Transaction and History queries
    private static final String ADD_TRANSACTION = "INSERT INTO trade_history (buy_organisation_name, sell_organisation_name, asset_name, credits, amount, datetime) VALUES (?,?,?,?,?,datetime('now'))";
    private static final String GET_ORDER_HISTORY  = "SELECT * FROM trade_history WHERE buy_organisation_name=? AND sell_organisation_name=? AND asset_name=?";

    private static final String CLEAR_ASSET = "delete from asset;";
    private static final String CLEAR_USER_INFORMATION = "delete from user_information;";
    private static final String CLEAR_CURRENT_TRADES = "delete from current_trades;";
    private static final String CLEAR_TRADE_HISTORY = "delete from trade_history;";
    private static final String CLEAR_ORGANISATION_UNITS = "delete from organisation_units;";

    private Connection connection;

    private PreparedStatement getCredits;
    private PreparedStatement updateCredits;
    private PreparedStatement getAssets;
    private PreparedStatement addAsset;
    private PreparedStatement getAssetAmount;
    private PreparedStatement deleteAsset;
    private PreparedStatement updateAssetAmount;
    private PreparedStatement getOrganisations;
    private PreparedStatement getUserOrganisation;
    private PreparedStatement addOrganisation;
    private PreparedStatement deleteOrganisation;
    private PreparedStatement getUsers;
    private PreparedStatement getUserPassword;
    private PreparedStatement addUser;
    private PreparedStatement deleteUser;
    private PreparedStatement updatePassword;
    private PreparedStatement getOrder;
    private PreparedStatement getOrders;
    private PreparedStatement addOrder;
    private PreparedStatement deleteOrder;
    private PreparedStatement addTransaction;
    private PreparedStatement getOrderHistory;
    private PreparedStatement deleteAll;


    public JDBCTradingPlatformDataSource(String propsFile) throws IOException, SQLException {
        connection = DBConnection.getInstance(propsFile);
        prepareDatabase();
        prepareQueries();

    }

    /**
     * TODO
     */
    private void prepareDatabase() throws SQLException {

        Statement organisation_table = connection.createStatement();
        organisation_table.execute(CREATE_ORGANISATION_UNITS);
        Statement asset_table = connection.createStatement();
        asset_table.execute(CREATE_ASSET);
        Statement user_table = connection.createStatement();
        user_table.execute(CREATE_USER_INFORMATION);
        Statement trade_table = connection.createStatement();
        trade_table.execute(CREATE_CURRENT_TRADES);
        Statement history_table = connection.createStatement();
        history_table.execute(CREATE_TRADE_HISTORY);


    }

    private void prepareQueries() throws SQLException {
        getCredits = connection.prepareStatement(GET_CREDITS);
        updateCredits = connection.prepareStatement(UPDATE_CREDITS);
        getAssets = connection.prepareStatement(GET_ASSETS);
        addAsset = connection.prepareStatement(ADD_ASSET);
        getAssetAmount = connection.prepareStatement(GET_ASSET_AMOUNT);
        deleteAsset = connection.prepareStatement(DELETE_ASSET);
        updateAssetAmount = connection.prepareStatement(UPDATE_ASSET_AMOUNT);
        getOrganisations = connection.prepareStatement(GET_ORGANISATIONS);
        getUserOrganisation = connection.prepareStatement(GET_USER_ORGANISATION);
        addOrganisation = connection.prepareStatement(ADD_ORGANISATION);
        deleteOrganisation = connection.prepareStatement(DELETE_ORGANISATION);
        getUsers = connection.prepareStatement(GET_USERS);
        getUserPassword = connection.prepareStatement(GET_USER_PASSWORD);
        addUser = connection.prepareStatement(ADD_USER);
        deleteUser = connection.prepareStatement(DELETE_USER);
        updatePassword = connection.prepareStatement(UPDATE_PASSWORD);
        getOrder = connection.prepareStatement(GET_ORDER);
        getOrders = connection.prepareStatement(GET_ORDERS);
        addOrder = connection.prepareStatement(ADD_ORDER);
        deleteOrder  = connection.prepareStatement(DELETE_ORDER);
        addTransaction  = connection.prepareStatement(ADD_TRANSACTION);
        getOrderHistory  = connection.prepareStatement(GET_ORDER_HISTORY);
    }

    /**
     * @see TradingPlatformDataSource#getCredits(String)
     */
    @Override
    public int getCredits(String organisation) throws SQLException {
        getCredits.setString(1, organisation);
        ResultSet credits = getCredits.executeQuery();
        return credits.getInt("credits");
    }

    /**
     * @see TradingPlatformDataSource#updateCredits(String, int)
     * @return
     */
    @Override
    public int updateCredits(String organisation, int credits) throws SQLException {
        updateCredits.setInt(1, credits);
        updateCredits.setString(2, organisation);
        return addAsset.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#getAssets(String) 
     */
    @Override
    public Set<Asset> getAssets(String organisation) throws SQLException {
        Set<Asset> assetSet = new TreeSet<>();
        getAssets.setString(1, organisation);
        ResultSet assetData = getAssets.executeQuery();
        while(assetData.next()) {
            Asset asset = new Asset();
            asset.setOrganisation(assetData.getString("organisation_name"));
            asset.setAsset(assetData.getString("asset_name"));
            asset.setAmount(assetData.getInt("amount"));
            assetSet.add(asset);
        }
        return assetSet;
    }

    /**
     * @see TradingPlatformDataSource#addAsset(String, String, int)
     */
    @Override
    public void addAsset(String organisation, String asset, int amount) throws SQLException {
        addAsset.setString(1, asset);
        addAsset.setString(2, organisation);
        addAsset.setInt(3, amount);
        addAsset.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#getAssetAmount(String, String)
     */
    @Override
    public int getAssetAmount(String organisation, String asset) throws SQLException {
        getAssetAmount.setString(1, organisation);
        getAssetAmount.setString(2, asset);
        ResultSet amountData = getAssetAmount.executeQuery();
        amountData.next();
        return amountData.getInt("amount");
    }

    /**
     * @see TradingPlatformDataSource#deleteAsset(String, String)
     * @return
     */
    @Override
    public int deleteAsset(String organisation, String asset) throws SQLException {
        deleteAsset.setString(1, asset);
        deleteAsset.setString(2, organisation);
        return deleteAsset.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#updateAssetAmount(String, String, int)
     * @return
     */
    @Override
    public int updateAssetAmount(String organisation, String asset, int amount) throws SQLException {
        updateAssetAmount.setInt(1, amount);
        updateAssetAmount.setString(2, asset);
        updateAssetAmount.setString(3, organisation);
        return updateAssetAmount.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#getOrganisations()
     */
    @Override
    public Set<String> getOrganisations() throws SQLException {
        Set<String> organisations = new TreeSet<>();
        ResultSet organisation_data = getOrganisations.executeQuery();
        while (organisation_data.next()) {
            organisations.add(organisation_data.getString("organisation_name"));
        }
        return organisations;
    }

    /**
     * @see TradingPlatformDataSource#getUserOrganisation(String)
     */
    @Override
    public String getUserOrganisation(String username) throws SQLException {
        getUserOrganisation.setString(1, username);
        ResultSet organisation_data = getUserOrganisation.executeQuery();
        organisation_data.next();
        return organisation_data.getString("organisation_name");
    }

    /**
     * @see TradingPlatformDataSource#addOrganisation(String, int)
     */
    @Override
    public void addOrganisation(String organisation, int credits) throws SQLException {
        addOrganisation.setString(1, organisation);
        addOrganisation.setInt(2, credits);
        addOrganisation.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#deleteOrganisation(String)
     * @return
     */
    @Override
    public int deleteOrganisation(String organisation) throws SQLException {
        deleteOrganisation.setString(1, organisation);
        return deleteOrganisation.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#getUsers()
     */
    @Override
    public Set<String> getUsers() throws SQLException {
        Set<String> users = new TreeSet<String>();
        ResultSet user_data = getUsers.executeQuery();
        while(user_data.next()) {
            users.add(user_data.getString("username"));
        }
        return users;
    }

    /**
     * @see TradingPlatformDataSource#getUserPassword(String)
     */
    @Override
    public String getUserPassword(String username) throws SQLException {
        getUserPassword.setString(1, username);
        ResultSet password = getUserPassword.executeQuery();
        password.next();
        return password.getString("password");
    }

    /**
     * @see TradingPlatformDataSource#addUser(String, String, String, String)
     */
    @Override
    public void addUser(String username, String password, String type, String organisation) throws SQLException  {
        addUser.setString(1, username);
        addUser.setString(2, organisation);
        addUser.setString(3, type);
        addUser.setString(4, password);
        addUser.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#deleteUser(String)
     * @return
     */
    @Override
    public int deleteUser(String username) throws SQLException  {
        deleteUser.setString(1, username);
        return deleteUser.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#updatePassword(String, String)
     * @return
     */
    @Override
    public int updatePassword(String username, String password) throws SQLException  {
        updatePassword.setString(2, username);
        updatePassword.setString(1, password);
        return updatePassword.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#getOrder(int)
     */
    @Override
    public TPOrder getOrder(int idx) throws SQLException  {
        TPOrder order = new TPOrder();
        getOrder.setInt(1, idx);
        ResultSet order_data = getOrder.executeQuery();
        order.setId(order_data.getInt("order_id"));
        order.setOrganisation(order_data.getString("organisation_name"));
        order.setAsset(order_data.getString("asset_name"));
        order.setAmount(order_data.getInt("amount"));
        order.setDateTime(order_data.getString("date"));
        order.setType(order_data.getString("type"));
        return order;
    }

    /**
     * @see TradingPlatformDataSource#getOrders(String, String, boolean)
     */
    @Override
    public Set<TPOrder> getOrders(String organisation, String asset, boolean isBuyOrder) throws SQLException  {
        Set<TPOrder> orders = new TreeSet<>();
        getOrders.setString(1, organisation);
        getOrders.setString(2, asset);
        String type;
        if (isBuyOrder) {
            type = "BUY";
        } else {
            type = "SELL";
        }
        getOrders.setString(3, type);
        ResultSet orderData = getOrders.executeQuery();
        while (orderData.next()) {
            TPOrder order = new TPOrder();
            order.setId(orderData.getInt("order_id"));
            order.setOrganisation(orderData.getString("organisation_name"));
            order.setAsset(orderData.getString("asset_name"));
            order.setCredits(orderData.getInt("credits"));
            order.setAmount(orderData.getInt("amount"));
            order.setDateTime(orderData.getString("date"));
            order.setType(orderData.getString("type"));
            orders.add(order);
        }
        return orders;
    }

    /**
     * @see TradingPlatformDataSource#addOrder(String, String, int, int, boolean)
     */
    @Override
    public void addOrder(String organisation, String asset, int amount,
                         int credits, boolean isBuyOrder) throws SQLException  {
        addOrder.setString(1, organisation);
        addOrder.setString(2, asset);
        addOrder.setInt(3, credits);
        addOrder.setInt(4, amount);
        String type;
        if (isBuyOrder) {
            type = "BUY";
        } else {
            type = "SELL";
        }
        addOrder.setString(5, type);
        addOrder.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#deleteOrder(int)
     * @return
     */
    @Override
    public int deleteOrder(int idx) throws SQLException {
        deleteOrder.setInt(1, idx);
        return deleteOrder.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#addTransaction(String, String, String, int, int)
     */
    @Override
    public void addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount,
                               int credits) throws SQLException {
        addTransaction.setString(1, buyingOrganisation);
        addTransaction.setString(2, sellingOrganisation);
        addTransaction.setString(3, asset);
        addTransaction.setInt(4,credits);
        addTransaction.setInt(5, amount);
        addTransaction.executeUpdate();
    }

    /**
     * @see TradingPlatformDataSource#getOrderHistory(String, String, String)
     */
    @Override
    public Set<Transaction> getOrderHistory(String buyingOrganisation,
                                            String sellingOrganisation, String asset) throws SQLException  {
        Set<Transaction> transactions = new TreeSet<>();
        getOrderHistory.setString(1, buyingOrganisation);
        getOrderHistory.setString(2, sellingOrganisation);
        getOrderHistory.setString(3, asset);
        ResultSet transaction_data = getOrderHistory.executeQuery();
        while (transaction_data.next()) {
            Transaction transaction = new Transaction();
            transaction.setId(transaction_data.getInt("transaction_id"));
            transaction.setBuyingOrganisation(transaction_data.getString("buy_organisation_name"));
            transaction.setSellingOrganisation(transaction_data.getString("sell_organisation_name"));
            transaction.setAsset(transaction_data.getString("asset_name"));
            transaction.setCredits(transaction_data.getInt("credits"));
            transaction.setAmount(transaction_data.getInt("amount"));
            transaction.setDateTime(transaction_data.getString("datetime"));
            transactions.add(transaction);
        }
        return transactions;
    }

    /**
     * @see TradingPlatformDataSource#deleteAll()
     */
    @Override
    public void deleteAll() throws SQLException {
        Statement dropTable = connection.createStatement();
        dropTable.execute(CLEAR_ASSET);
        dropTable.execute(CLEAR_TRADE_HISTORY);
        dropTable.execute(CLEAR_CURRENT_TRADES);
        dropTable.execute(CLEAR_USER_INFORMATION);
        dropTable.execute(CLEAR_ORGANISATION_UNITS);
    }

//    /**
//     *
//     * @param password
//     * @return
//     */
//    private String hashPassword(String password) {
//        return null;
//    }
}
