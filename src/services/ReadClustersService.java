package services;

import data.Data;
import mining.KMeansMiner;
import protocol.MessageType;
import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.MultiServer;
import server.ServerException;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadClustersService implements Service {

    public ResponseMessage execute(RequestMessage req) {
        String tableName = req.getBodyField("table");

        ResponseMessage resp = new ResponseMessage();
        resp.setResponseType(req.getRequestType());
        try{
            Data data = new Data(tableName);
            String fileName = MultiServer.settings.getProperty("file_storage_root") + "/" + tableName + "_" + req.getBodyField("clusters") + ".dmp";
            KMeansMiner miner = new KMeansMiner(fileName);

            resp.addBodyField("data", miner.toString(data));
            resp.setStatus("OK");
        }catch (Exception e){
            resp.addBodyField("errorMsg", e.getMessage());
            resp.setStatus("ERROR");
            e.printStackTrace();
        }



        return resp;
    }
}

