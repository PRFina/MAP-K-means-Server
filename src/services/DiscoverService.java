package services;
import data.Data;
import database.*;
import mining.KMeansMiner;
import protocol.MessageType;
import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.MultiServer;
import java.sql.SQLException;


// TODO ADD JAVADOC

public class DiscoverService implements Service{
    public ResponseMessage execute(RequestMessage req) {
        ResponseMessage resp = new ResponseMessage();
        resp.setResponseType(req.getRequestType());
        try{
            String tableName = req.getBodyField("table");
            int k = Integer.parseInt(req.getBodyField("clusters"));

            Data data = new Data(tableName);
            KMeansMiner mineDB = new KMeansMiner(k);
            mineDB.kmeans(data);

            resp.setStatus("OK");
            resp.addBodyField("data", mineDB.toString(data));

            String filePath = MultiServer.getConfig().getProperty("file_storage_root") + "/" + tableName + "_" + k + ".dmp";
            mineDB.salva(filePath);

        }catch(Exception e){
            e.printStackTrace();
            resp.setStatus("ERROR");
            resp.addBodyField("errorMsg", e.getMessage());
        }

        return resp;
    }


    public static void main(String[] args) throws SQLException, DatabaseConnectionException, ClassNotFoundException, EmptySetException, NoValueException {
        DiscoverService disc = new DiscoverService();
        RequestMessage req = new RequestMessage(MessageType.DISCOVER);
        req.addBodyField("data", "to");
        ResponseMessage resp = disc.execute(req);
        System.out.println(resp);
    }
}