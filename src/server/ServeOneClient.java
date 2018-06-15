package server;

import services.ReadClustersService;
import services.Service;
import mining.KMeansMiner;

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
public class ServeOneClient extends Thread {

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    ServiceDispatcher dispatcher;

    public ServeOneClient(Socket clientSocket, ServiceDispatcher dispatcher) throws IOException {
        socket = clientSocket;
        in = new ObjectInputStream(this.socket.getInputStream());
        out = new ObjectOutputStream(this.socket.getOutputStream());

        this.dispatcher = dispatcher;

        this.start();

    }

    @Override
    public void run() {
        /*
            read the request, pass to the dispatcher,
            call the right service and
            return a response message to client.
         */
        System.out.println(socket);
        try {
            while (true) {
                RequestMessage req = (RequestMessage) in.readObject();
                //TODO replace with logger: disconnect
                System.out.println(req); // DEBUG

                ResponseMessage resp = dispatcher.dispatch(req);

                out.writeObject(resp);
            }

        } catch (EOFException e) {
            System.out.println("DEBUG: client connection dropped!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //TODO replace with logger: disconnect
    }

}
