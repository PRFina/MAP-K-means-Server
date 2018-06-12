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
import protocol.MessageType;


public class ServeOneClient extends Thread{

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    KMeansMiner miner;

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
                System.out.println(req); // DEBUG

                ResponseMessage resp = new ResponseMessage();

                switch (req.getRequestType()){
                    case READ: {
                        Service serv = new ReadClustersService(req.getBodyField("file"));

                        resp.setResponseType(MessageType.READ);
                        try {
                            resp.addBodyField("data", serv.execute());
                            resp.setStatus("OK");
                        } catch (ServerException e) {
                            resp.addBodyField("errorMsg", e.getMessage());
                            resp.setStatus("ERROR");
                        }

                        break;
                    }

                    case DISCOVER: {
                        resp.setResponseType(MessageType.DISCOVER);
                        resp.addBodyField("data","data is received");
                        resp.setStatus("OK");
                        break;
                    }

                    case INFO: {
                        resp.setResponseType(MessageType.INFO);
                        resp.addBodyField("data","metadata about metadata that talk about data");
                        resp.setStatus("OK");
                        break;

                    }
                }

                out.writeObject(resp);
            }

        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

}
