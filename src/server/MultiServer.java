package server;

import services.ServiceDispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class models a multi-thread specific-purpose server.
 * <p>
 * The main component of the server are:
 * <ul>
 * <li> Server Configuration: contains the server configuration settings</li>
 * <li> Service Dispatcher: mechanism to handle the client request and call the right service</li>
 * </ul>
 * The communication protocol used is defined in {@link protocol protocol} package.
 * Each client connection is handled by {@link server.ServeOneClient} object in a dedicated thread:
 * every request is analyzed and a response is generated accordingly by a service selected by
 * dispatcher mechanism defined in {@link services.ServiceDispatcher} class.
 *
 * @author Pio Raffaele Fina
 */

public final class MultiServer {
    private static ServerConfiguration config;
    private int serverPort;
    private ServiceDispatcher dispatcher;
    private ServerLogger logger;

    public MultiServer() throws ServerException {

        System.out.print("Init: configuration file... ");
        config = ServerConfiguration.getInstance();
        System.out.println("done!");

        System.out.print("Init: storage folder... ");
        config.initStorageFolder();
        System.out.println("done!");

        serverPort = Integer.parseInt(config.getProperty("server_port"));

        System.out.print("Init: services...");
        dispatcher = new ServiceDispatcher();
        dispatcher.load(getConfig().getProperty("services_config_file"));
        System.out.println("done!");

        System.out.print("Init: logger...");
        logger = new ServerLogger();
        System.out.println("done!");

    }

    /**
     * Return a ServerConfiguration object, the object is instantiated in place if not already.
     * This method help to implement the Singleton pattern
     * @return a ServerConfiguration object
     */
    public static ServerConfiguration getConfig() {
        return config;
    }

    /**
     * Start the server main activity.
     * Dispatch new thread to handle client connection.
     *
     * @throws IOException
     */
    void start() throws IOException {
        System.out.print("Init: network binding...");
        ServerSocket srvSocket = new ServerSocket(this.serverPort);
        System.out.println("done!");

        System.out.println("Server started on port " + serverPort + "!");
        while (true) {
            Socket socket = srvSocket.accept();
            ServeOneClient client = new ServeOneClient(socket, dispatcher, logger);
        }
    }

    public static void main(String[] args) {
        try {
            MultiServer server = new MultiServer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

}
