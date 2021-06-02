package main.java.network;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.io.IOException;
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
        try {

            socket = new Socket(HOSTNAME, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            //TODO handle this better
            System.out.println("Failed to connect to networkExercise.server");
        }
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
    public Set<Asset> getAssets() {

        try {
            outputStream.writeObject(Command.GET_ASSETS);
            outputStream.flush();

            @SuppressWarnings("unchecked")
            Set<Asset> assets = (Set<Asset>) inputStream.readObject();
            return assets;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int addAsset(String organisation, String asset, int amount) {

        try {
            outputStream.writeObject(Command.ADD_ASSET);
            outputStream.writeObject(organisation);
            outputStream.writeObject(asset);
            outputStream.writeInt(amount);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int getAssetAmount(String organisation, String asset) {
        try {
            outputStream.writeObject(Command.GET_ASSET_AMOUNT);
            outputStream.writeObject(organisation);
            outputStream.writeObject(asset);
            outputStream.flush();

            return inputStream.readInt();
        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int deleteAsset(String organisation, String asset) {
        try {
            outputStream.writeObject(Command.DELETE_ASSET);
            outputStream.writeObject(organisation);
            outputStream.writeObject(asset);
            outputStream.flush();

            return inputStream.readInt();
        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
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

        try {
            outputStream.writeObject(Command.ADD_ORGANISATION);
            outputStream.writeObject(organisation);
            outputStream.writeInt(credits);
            outputStream.flush();

            return inputStream.readInt();
        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
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
    public Set<TPOrder> getOrders(boolean isBuyOrder) {
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
    public Set<Transaction> getOrderHistory() {
        return null;
    }

    @Override
    public int deleteAll() {

        return 0;
    }
}
