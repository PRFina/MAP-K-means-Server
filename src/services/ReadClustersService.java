package services;

import data.Data;
import mining.KMeansMiner;
import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.MultiServer;

/**
 * This class provides the read service implementation.
 * The discover service could be broken in 3 main sub-activities:
 * <ul>
 * <li> Build the dataset from database using {@link data.Data}
 * and {@link database.DbAccess}  classes</li>
 * <li> Deserialize the KMeansMiner instance from the fileSystem </li>
 * <li> build the client response </li>
 * </ul>
 * Note: as convention the file name is retrieved using client information
 * encoded in the request message:
 * <p>
 * {@code <request.table>_<request.clusters>.dmp}
 * </p>
 */
public class ReadClustersService implements Service {

    public ResponseMessage execute(RequestMessage req) {

        ResponseMessage resp = new ResponseMessage();
        resp.setResponseType(req.getRequestType());
        try {
            String tableName = req.getBodyField("table");
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
        } catch (Exception e) {
            resp.addBodyField("errorMsg", e.getMessage());
            resp.setStatus("ERROR");
            e.printStackTrace();
        }

        return resp;
    }
}

