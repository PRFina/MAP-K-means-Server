package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import protocol.ResponseMessage;
import protocol.RequestMessage;
import services.ServiceDispatcher;

/**
 * - the main bridge communication with client, 1-1 direct channel with client
 */
public final class ServeOneClient extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServiceDispatcher dispatcher;
    private ServerLogger logger;

    public ServeOneClient(final Socket clientSocket, final ServiceDispatcher dispatcher, final ServerLogger logger)
            throws IOException {
        socket = clientSocket;
        in = new ObjectInputStream(this.socket.getInputStream());
        out = new ObjectOutputStream(this.socket.getOutputStream());

        this.dispatcher = dispatcher;
        this.logger = logger;

        this.start();

    }

    @Override
    public void run() {
        /*
            read the request, pass to the dispatcher,
            call the right service and
            return a response message to client.
         */
        logger.log("Start connection with:" + socket.getInetAddress().getHostAddress());
        try {
            while (true) {
                RequestMessage req = (RequestMessage) in.readObject();
                ResponseMessage resp = dispatcher.dispatch(req);

                out.writeObject(resp);

                logger.logConnection(socket, req, resp);
            }

        } catch (EOFException e) {
            System.out.println("DEBUG: client connection dropped!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                logger.log("Start connection with:" + socket.getInetAddress().getHostAddress());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //TODO replace with logger: disconnect
    }

}
