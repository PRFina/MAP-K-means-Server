package server;

import services.ReadClustersService;
import services.Service;
import mining.KMeansMiner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import protocol.ResponseMessage;
import protocol.RequestMessage;
import services.ServiceDispatcher;

public class ServeOneClient extends Thread{

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
    public void run(){
        /*
            read the request, pass to the dispatcher,
            call the right service and
            return a response message to client.
         */


        try {
            while(true){
                System.out.println(socket);

                RequestMessage req = (RequestMessage) in.readObject();
                System.out.println(req); // DEBUG

                ResponseMessage  resp = dispatcher.dispatch(req);

                out.writeObject(resp);
            }

        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
