package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class MultiServer {
    int PORT;

    // instead to inject settings as dependency to each class constructor, we choose to
    //expose as public class attribute.
    public static final Properties settings;

    static{
        settings = new Properties();

        try (FileInputStream in = new FileInputStream("settings.properties") ){

            settings.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public MultiServer(int port) { this.PORT = port;}

    void start() throws IOException {
        ServerSocket srvSocket = new ServerSocket(this.PORT);

        while(true){
            Socket socket = srvSocket.accept();
            ServeOneClient client = new ServeOneClient(socket);

            // ASK When close the socket?
        }
    }
    public static void main(String[] args) {
        MultiServer server = new MultiServer(Integer.parseInt(MultiServer.settings.getProperty("server_port")));
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
