package main.java.network;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Integer.parseInt;

public class NetworkServer {

    //Todo make from config file
    private static int PORT;

    //This is the timeout in between accepting clients, not reading from the socket itself.
    private static int SOCKET_ACCEPT_TIMEOUT;

    //This timeout is used for the actual clients.
    private static int SOCKET_READ_TIMEOUT;
    private static String propsFile;

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final TradingPlatformDataSource database;

    public NetworkServer() throws IOException, SQLException {
        initializeConfigFile(); // Read from the config file
        database = new JDBCTradingPlatformDataSource(propsFile);
    }

    /**
     * Handles the connection received from ServerSocket.
     * Does not return until the client disconnects.
     * @param socket The socket used to communicate with the currently connected client
     */
    private void handleConnection(Socket socket) throws SQLException {
        try {

            final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            while (running.get()) {
                try {
                    final NetworkDataSource.Command command = (NetworkDataSource.Command) inputStream.readObject();
                    handleCommand(inputStream, outputStream, command);
                } catch (SocketTimeoutException | StreamCorruptedException ignored) {

                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
            //Todo change
            System.out.printf("Connection %s closed%n", socket.toString());
        }
    }

    /**
     * Method used to read the server information from a configuration file.
     */
    private void initializeConfigFile(){
        try{
            ConfigReader configReader = new ConfigReader();
            Set<String> configSet = configReader.readServerFile();
            String[] array = new String[5];
            array = configSet.toArray(array);
            SOCKET_ACCEPT_TIMEOUT = parseInt(array[0]);
            SOCKET_READ_TIMEOUT = parseInt(array[1]);
            propsFile = array[2];
            PORT = parseInt(array[3]);

        }catch (IOException e){
            //TODO handle this better
            System.out.println("Config File problem.");
        }
    }

    /**
     * Handles a request from the client.
     * @param inputStream input stream to read objects from
     * @param outputStream output stream to write objects to
     * @param command command we're handling
     * @throws IOException if the client has disconnected
     * @throws ClassNotFoundException if the client sends an invalid object
     */
    private void handleCommand(ObjectInputStream inputStream, ObjectOutputStream outputStream,
                               NetworkDataSource.Command command) throws IOException, ClassNotFoundException {

        switch (command) {
            case GET_ASSETS -> {
                synchronized (database) {
                    Set<Asset> assets = database.getAssets();
                    outputStream.writeObject(assets);
                }
                outputStream.flush();
            }

            case ADD_ASSET -> {
                final String organisation = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                final int amount = inputStream.readInt();
                synchronized (database) {
                    int rowsAffected = database.addAsset(organisation, asset, amount);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case DELETE_ASSET -> {
                final String organisation = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                synchronized (database) {
                    int rowsAffected = database.deleteAsset(organisation, asset);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case GET_ASSET_AMOUNT -> {
                final String organisation = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                synchronized (database) {
                    int amount = database.getAssetAmount(organisation, asset);
                    outputStream.writeInt(amount);
                }
                outputStream.flush();
            }

            case UPDATE_ASSET_AMOUNT -> {
                final String organisation = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                final int amount = inputStream.readInt();
                synchronized (database) {
                    int rowsAffected = database.updateAssetAmount(organisation, asset, amount);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case GET_CREDITS -> {
                final String organisation = (String) inputStream.readObject();
                synchronized (database) {
                    int credits = database.getCredits(organisation);
                    outputStream.writeInt(credits);
                }
                outputStream.flush();
            }

            case UPDATE_CREDITS -> {
                final String organisation = (String) inputStream.readObject();
                final int credits = inputStream.readInt();
                synchronized (database) {
                    int rowsAffected = database.updateCredits(organisation, credits);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case GET_ORGANISATIONS -> {
                synchronized (database) {
                    Set<Organisation> organisations = database.getOrganisations();
                    outputStream.writeObject(organisations);
                }
                outputStream.flush();
            }

            case ADD_ORGANISATION -> {
                final String organisation = (String) inputStream.readObject();
                final String credits = (String) inputStream.readObject();
                int newCredits = parseInt(credits);
                System.out.println(organisation);
                System.out.println(credits);
                synchronized (database) {
                    int rowsAffected = database.addOrganisation(organisation, newCredits);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case DELETE_ORGANISATION -> {
                final String organisation = (String) inputStream.readObject();
                synchronized (database) {
                    int rowsAffected = database.deleteOrganisation(organisation);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case GET_USERS -> {
                synchronized (database) {
                    Set<UserOrganisation> users = database.getUsers();
                    outputStream.writeObject(users);
                }
                outputStream.flush();
            }

            case GET_USER_PASSWORD -> {
                final String username = (String) inputStream.readObject();
                synchronized (database) {
                    String password = database.getUserPassword(username);
                    outputStream.writeObject(password);
                }
                outputStream.flush();
            }

            case ADD_USER -> {
                final String username = (String) inputStream.readObject();
                final String password = (String) inputStream.readObject();
                final String type = (String) inputStream.readObject();
                final String organisation = (String) inputStream.readObject();
                synchronized (database) {
                    int rowsAffected = database.addUser(username, password, type, organisation);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case DELETE_USER -> {
                final String username = (String) inputStream.readObject();
                synchronized (database) {
                    int rowsAffected = database.deleteUser(username);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case UPDATE_PASSWORD -> {
                final String username = (String) inputStream.readObject();
                final String password = (String) inputStream.readObject();
                synchronized (database) {
                    int rowsAffected = database.updatePassword(username, password);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case GET_USER_ORGANISATION -> {
                final String username = (String) inputStream.readObject();
                synchronized (database) {
                    String organisation = database.getUserOrganisation(username);
                    outputStream.writeObject(organisation);
                }
                outputStream.flush();
            }

            case GET_ORDER -> {
                final int idx = inputStream.readInt();
                synchronized (database) {
                    TPOrder order = database.getOrder(idx);
                    outputStream.writeObject(order);
                }
                outputStream.flush();
            }

            case GET_ORDERS -> {
                final String in = (String) inputStream.readObject();
                boolean isBuyOrder = true;
                if (in.equals("false")){
                    isBuyOrder = false;
                }
                synchronized (database) {
                    Set<TPOrder> orders = database.getOrders(isBuyOrder);
                    outputStream.writeObject(orders);
                }
                outputStream.flush();
            }

            case ADD_ORDER -> {
                NewTransaction newTransaction = new NewTransaction(database);
                TPOrder order = new TPOrder();
                order.setOrganisation((String) inputStream.readObject());
                order.setAsset((String) inputStream.readObject());
                order.setAmount(inputStream.readInt());
                order.setCredits(inputStream.readInt());
                final boolean isBuyOrder = inputStream.readBoolean();
                synchronized (database) {
                    int rowsAffected;
                    if (isBuyOrder) {
                        rowsAffected = newTransaction.addBuyOrder(order);
                    } else {
                        rowsAffected = newTransaction.addSellOrder(order);
                    }
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case DELETE_ORDER -> {
                NewTransaction newTransaction = new NewTransaction(database);
                final int orderId = inputStream.readInt();
                synchronized (database) {
                    int rowsAffected = newTransaction.removeOrder(orderId);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case ADD_TRANSACTION -> {
                final String buyingOrganisation = (String) inputStream.readObject();
                final String sellingOrganisation = (String) inputStream.readObject();
                final String asset = (String) inputStream.readObject();
                final int amount = inputStream.readInt();
                final int credits = inputStream.readInt();
                synchronized (database) {
                    int rowsAffected = database.addTransaction(buyingOrganisation, sellingOrganisation,
                            asset, amount, credits);
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }

            case GET_ORDER_HISTORY -> {
                synchronized (database) {
                    Set<Transaction> transactions = database.getOrderHistory();
                    outputStream.writeObject(transactions);
                }
                outputStream.flush();
            }

            case DELETE_ALL -> {
                synchronized (database) {
                    int rowsAffected = database.deleteAll();
                    outputStream.writeInt(rowsAffected);
                }
                outputStream.flush();
            }
        }
    }

    /**
     * Returns the port the networkServer is configured to use.
     *
     * @return int The port number
     */
    public static int getPort() {
        return PORT;
    }

    /**
     * Starts the networkServer running on the default port.
     */
    public void start() throws IOException, SQLException {

//        database = new JDBCTradingPlatformDataSource(propsFile);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
            while (running.get()) {

                try {
                    final Socket socket = serverSocket.accept();
                    socket.setSoTimeout(SOCKET_READ_TIMEOUT);

                    // We have a new connection from a client. Use a runnable and thread to handle
                    // the client. The lambda here wraps the functional interface (Runnable).
                    final Thread clientThread = new Thread(() -> {
                        try {
                            handleConnection(socket);
                        } catch (SQLException throwables) {
                            //todo change
                            throwables.printStackTrace();
                        }
                    });
                    clientThread.start();
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the networkExercise.server is still running
                } catch (Exception e) {
                    //todo change
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            //todo change
            // If we get an error starting up, show an error dialog then exit
            e.printStackTrace();
            System.exit(1);
        }

        // Close down the networkExercise.server
        System.exit(0);
    }

    /**
     * Requests the networkServer to shut down
     */
    public void shutdown() {
        running.set(false);
    }
}
