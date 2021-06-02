package main.java.network;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.Asset;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkServer {

    private static final int PORT = 10000;

    /**
     * this is the timeout inbetween accepting clients, not reading from the socket itself.
     */
    private static final int SOCKET_ACCEPT_TIMEOUT = 100;

    /**
     * This timeout is used for the actual clients.
     */
    private static final int SOCKET_READ_TIMEOUT = 5000;

    private AtomicBoolean running = new AtomicBoolean(true);

    /**
     * The connection to the database where everything is stored.
     */
    private TradingPlatformDataSource database;

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
                    handleCommand(socket, inputStream, outputStream, command);
                } catch (SocketTimeoutException e) {

                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            //Todo change
            System.out.printf("Connection %s closed%n", socket.toString());
        }
    }

    /**
     * Handles a request from the client.
     * @param socket socket for the client
     * @param inputStream input stream to read objects from
     * @param outputStream output stream to write objects to
     * @param command command we're handling
     * @throws IOException if the client has disconnected
     * @throws ClassNotFoundException if the client sends an invalid object
     */
    private void handleCommand(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream,
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



            case ADD_ORGANISATION -> {
                final String organisation = (String) inputStream.readObject();
                final int credits = inputStream.readInt();
                System.out.println("Hi there");
                synchronized (database) {
                    int rowsAffected = database.addOrganisation(organisation, credits);
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
     * @param propsFile
     */
    public void start(String propsFile) throws IOException, SQLException {

        database = new JDBCTradingPlatformDataSource(propsFile);

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
