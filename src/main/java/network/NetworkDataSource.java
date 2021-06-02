package main.java.network;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Set;

public class NetworkDataSource implements TradingPlatformDataSource {

    //TODO Make these an initialised input using config file.
    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 10000;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public enum Command {
        ///Asset commands
        GET_ASSETS,
        ADD_ASSET,
        DELETE_ASSET,
        GET_ASSET_AMOUNT,
        UPDATE_ASSET_AMOUNT,
        // Organisation commands
        GET_CREDITS,
        UPDATE_CREDITS,
        GET_ORGANISATIONS,
        ADD_ORGANISATION,
        DELETE_ORGANISATION,

        // User commands
        GET_USERS,
        GET_USER_PASSWORD,
        ADD_USER,
        DELETE_USER,
        UPDATE_PASSWORD,
        GET_USER_ORGANISATION,

        // TPOrder commands
        GET_ORDER,
        GET_ORDERS,
        ADD_ORDER,
        DELETE_ORDER,

        // Transaction and History commands
        ADD_TRANSACTION,
        GET_ORDER_HISTORY,
        DELETE_ALL
    }

    public NetworkDataSource() {

    }

    @Override
    public int getCredits(String organisation) {
        return 0;
    }

    @Override
    public int updateCredits(String organisation, int credits) {
        return 0;
    }

    @Override
    public Set<Asset> getAssets(String organisation) {
        return null;
    }

    @Override
    public int addAsset(String organisation, String asset, int amount) {

        return 0;
    }

    @Override
    public int getAssetAmount(String organisation, String asset) {
        return 0;
    }

    @Override
    public int deleteAsset(String organisation, String asset) {
        return 0;
    }

    @Override
    public int updateAssetAmount(String organisation, String asset, int amount) {
        return 0;
    }

    @Override
    public Set<Organisation> getOrganisations() {
        return null;
    }

    @Override
    public String getUserOrganisation(String username) {
        return null;
    }

    @Override
    public int addOrganisation(String organisation, int credits) {

        return 0;
    }

    @Override
    public int deleteOrganisation(String organisation) {
        return 0;
    }

    @Override
    public Set<UserOrganisation> getUsers() {
        return null;
    }

    @Override
    public String getUserPassword(String username) {
        return null;
    }

    @Override
    public int addUser(String username, String password, String type, String organisation) {

        return 0;
    }

    @Override
    public int deleteUser(String username) {
        return 0;
    }

    @Override
    public int updatePassword(String username, String password) {
        return 0;
    }

    @Override
    public TPOrder getOrder(int idx) {
        return null;
    }

    @Override
    public Set<TPOrder> getOrders(String organisation, String asset, boolean isBuyOrder) {
        return null;
    }

    @Override
    public int addOrder(String organisation, String asset, int amount, int credits, boolean isBuyOrder) {

        return 0;
    }

    @Override
    public int deleteOrder(int idx) {
        return 0;
    }

    @Override
    public int addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount, int credits) {

        return 0;
    }

    @Override
    public Set<Transaction> getOrderHistory(String buyingOrganisation, String sellingOrganisation, String asset) {
        return null;
    }

    @Override
    public int deleteAll() {

        return 0;
    }
}
