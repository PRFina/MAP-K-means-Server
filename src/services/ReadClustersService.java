package services;

import data.Data;
import mining.KMeansMiner;
import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.MultiServer;

public class ReadClustersService implements Service {

    public ResponseMessage execute(RequestMessage req) {
        String tableName = req.getBodyField("table");

        ResponseMessage resp = new ResponseMessage();
        resp.setResponseType(req.getRequestType());
        try{
            Data data = new Data(tableName);
            String fileName = MultiServer.getConfig().getProperty("file_storage_root") +
                    "/" +
                    tableName +
                    "_" +
                    req.getBodyField("clusters") +
                    ".dmp";

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

