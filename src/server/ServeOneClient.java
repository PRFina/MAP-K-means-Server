package server;

import mining.KMeansMiner;
import protocol.MessageType;
import protocol.RequestMessage;
import protocol.ResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServeOneClient extends Thread{

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    KMeansMiner kmeans;

    public ServeOneClient(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.in = new ObjectInputStream(this.socket.getInputStream());
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.start();

    }

    @Override
    public void run(){
        try {
            while(true){

                System.out.println(socket);

                RequestMessage req = (RequestMessage) in.readObject();
                System.out.println(req);

                ResponseMessage resp = null;

                if(req.getRequestType() == MessageType.READ){
                    resp = new ResponseMessage(MessageType.READ);
                    resp.addBodyField("data","data is received");
                    resp.setStatus("OK");
                }
                else if(req.getRequestType() == MessageType.DISCOVER){

                    resp = new ResponseMessage(MessageType.DISCOVER);
                    resp.addBodyField("data","data is received");
                    resp.setStatus("OK");
                }
                out.writeObject(resp);
            }

        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

}
