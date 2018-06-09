package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    int PORT;

    public MultiServer(int port){
        this.PORT = port;
    }

    void start() throws IOException {
        ServerSocket srvSocket = new ServerSocket(this.PORT);

        while(true){
            Socket socket = srvSocket.accept();
            ServeOneClient client = new ServeOneClient(socket);

            // ASK When close the socket?
        }
    }
    public static void main(String[] args) {
        MultiServer server = new MultiServer(9999);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
