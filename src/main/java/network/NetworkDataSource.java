package main.java.network;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Set;

/**
 * Todo
 */
public class NetworkDataSource implements TradingPlatformDataSource {

    //TODO Make these an initialised input using config file.
    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 10000;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public enum Command {
        //Asset commands
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

            Socket socket = new Socket(HOSTNAME, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            //TODO handle this better
            System.out.println("Failed to connect to networkExercise.server");
        }
    }

    @Override
    public int getCredits(String organisation) {
        try {
            outputStream.writeObject(Command.GET_CREDITS);
            outputStream.writeObject(organisation);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int updateCredits(String organisation, int credits) {
        try {
            outputStream.writeObject(Command.UPDATE_CREDITS);
            outputStream.writeObject(organisation);
            outputStream.writeInt(credits);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
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
        try {
            outputStream.writeObject(Command.UPDATE_ASSET_AMOUNT);
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
    public Set<Organisation> getOrganisations() {
        try {
            outputStream.writeObject(Command.GET_ORGANISATIONS);
            outputStream.flush();

            @SuppressWarnings("unchecked")
            Set<Organisation> organisations = (Set<Organisation>) inputStream.readObject();
            return organisations;

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public String getUserOrganisation(String username) {
        try {
            outputStream.writeObject(Command.GET_USER_ORGANISATION);
            outputStream.writeObject(username);
            outputStream.flush();

            return (String) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
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
        try {
            outputStream.writeObject(Command.DELETE_ORGANISATION);
            outputStream.writeObject(organisation);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public Set<UserOrganisation> getUsers() {
        try {
            outputStream.writeObject(Command.GET_USERS);
            outputStream.flush();

            @SuppressWarnings("unchecked")
            Set<UserOrganisation> users = (Set<UserOrganisation>) inputStream.readObject();
            return users;

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public String getUserPassword(String username) {
        try {
            outputStream.writeObject(Command.GET_USER_PASSWORD);
            outputStream.writeObject(username);
            outputStream.flush();

            return (String) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public int addUser(String username, String password, String type, String organisation) {

        try {
            outputStream.writeObject(Command.ADD_USER);
            outputStream.writeObject(username);
            outputStream.writeObject(password);
            outputStream.writeObject(type);
            outputStream.writeObject(organisation);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int deleteUser(String username) {
        try {
            outputStream.writeObject(Command.DELETE_USER);
            outputStream.writeObject(username);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int updatePassword(String username, String password) {
        try {
            outputStream.writeObject(Command.UPDATE_PASSWORD);
            outputStream.writeObject(username);
            outputStream.writeObject(password);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public TPOrder getOrder(int idx) {
        try {
            outputStream.writeObject(Command.GET_ORDER);
            outputStream.writeInt(idx);

            outputStream.flush();

            return (TPOrder) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Set<TPOrder> getOrders(boolean isBuyOrder) {
        try {
            outputStream.writeObject(Command.GET_ORDERS);
            outputStream.writeBoolean(isBuyOrder);
            outputStream.flush();

            @SuppressWarnings("unchecked")
            Set<TPOrder> orders = (Set<TPOrder>) inputStream.readObject();
            return orders;

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public int addOrder(String organisation, String asset, int amount, int credits, boolean isBuyOrder) {

        try {
            outputStream.writeObject(Command.ADD_ORDER);
            outputStream.writeObject(organisation);
            outputStream.writeObject(asset);
            outputStream.writeInt(amount);
            outputStream.writeInt(credits);
            outputStream.writeBoolean(isBuyOrder);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int deleteOrder(int idx) {
        try {
            outputStream.writeObject(Command.DELETE_ORDER);
            outputStream.writeInt(idx);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public int addTransaction(String buyingOrganisation, String sellingOrganisation,
                              String asset, int amount, int credits) {

        try {
            outputStream.writeObject(Command.ADD_TRANSACTION);
            outputStream.writeObject(buyingOrganisation);
            outputStream.writeObject(sellingOrganisation);
            outputStream.writeObject(asset);
            outputStream.writeInt(amount);
            outputStream.writeInt(credits);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }

    @Override
    public Set<Transaction> getOrderHistory() {
        try {
            outputStream.writeObject(Command.GET_ORDER_HISTORY);
            outputStream.flush();

            @SuppressWarnings("unchecked")
            Set<Transaction> transactions = (Set<Transaction>) inputStream.readObject();
            return transactions;

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public int deleteAll() {

        try {
            outputStream.writeObject(Command.DELETE_ALL);
            outputStream.flush();

            return inputStream.readInt();

        } catch (IOException e) {
            return PlatformGlobals.getGeneralSQLFail();
        }
    }
}
