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

public class MultiServer {
    private static ServerConfiguration config;
    int serverPort;
    private ServiceDispatcher dispatcher;

    public MultiServer() throws ServerException{
        config = new ServerConfiguration("resources/config.properties");
        serverPort = Integer.parseInt( config.getProperty("server_port"));

        dispatcher = new ServiceDispatcher();
        dispatcher.load(getConfig().getProperty("services_config_file"));

        config.initStorageFolder();
    }

    /**
     * Return a ServerConfiguration object, the object is instantiated in place if not already.
     *
     * @return a ServerConfiguration object
     */
    public static ServerConfiguration getConfig() throws ServerException {

        if (config == null) {
            config = new ServerConfiguration("resources/config.properties");
            return config;
        } else
            return config;
    }

    /**
     * Start the server main activity.
     * Dispatch new thread to handle client connection.
     *
     * @throws IOException
     */
    void start() throws IOException {

        ServerSocket srvSocket = new ServerSocket(this.serverPort);

        while (true) {
            Socket socket = srvSocket.accept();
            ServeOneClient client = new ServeOneClient(socket, dispatcher);
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
