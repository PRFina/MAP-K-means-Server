package server;

import services.DiscoverService;
import services.ReadClustersService;
import services.ServiceDispatcher;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * This class model a multi-thread specific-purpose server.
 *
 * The main component of the server are:
 * <ul>
 *  <li> Server Configuration: contains the server configuration settings</li>
 *  <li> Service Dispatcher: mechanism to handle the client request and call the right service</li>
 * </ul>
 * The communication protocol used is defined in {@link protocol protocol} package.
 * Each client connection is handled by {@link server.ServeOneClient} object in a dedicated thread:
 * every request is analyzed and a response is generated accordingly by a service selected by
 * dispatcher mechanism defined in {@link services.ServiceDispatcher} class.
 *
 *
 * @Author Pio Raffaele Fina
 *
 */

public class MultiServer {
    int serverPort;
    private static ServerConfiguration config;
    private ServiceDispatcher dispatcher;




    public MultiServer(int port) {
        serverPort = port;
        config = new ServerConfiguration( "config.properties");
        dispatcher = new ServiceDispatcher();
        initDispatcher();
    }

    /**
     * Return a ServerConfiguration object, the object is instantiated in place if not already.
     * @return a ServerConfiguration object
     */
    public static ServerConfiguration getConfig(){

        if(config == null){
            config = new ServerConfiguration( "config.properties");
            return config;
        }
        else
            return config;
    }

    /**
     * Start the server main activity.
     * Dispatch new thread to handle client connection.
     * @throws IOException
     */
    void start() throws IOException {
        ServerSocket srvSocket = new ServerSocket(this.serverPort);

        while(true){
            Socket socket = srvSocket.accept();
            ServeOneClient client = new ServeOneClient(socket, dispatcher);
        }
    }

    /**
     * Register service to the dispatcher.
     * // TODO maybe replace with service.xml and reflection to register serivces in a declarative way
     */
    private void initDispatcher(){

        // Service registration
        dispatcher.register("READ", new ReadClustersService());
        dispatcher.register("DISCOVER", new DiscoverService());

    }

    public static void main(String[] args) {
        MultiServer server = new MultiServer(Integer.parseInt(MultiServer.getConfig().getProperty("server_port")));
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
