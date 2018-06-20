package services;

import data.Data;

import mining.KMeansMiner;
import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.MultiServer;

/**
 * This class provides the discover service implementation.
 * The discover service could be broken in 4 main sub-activities:
 * <ul>
 * <li> Build the dataset from database using {@link data.Data}
 * and {@link database.DbAccess}  classes</li>
 * <li> Mining the dataset trough {@link mining.KMeansMiner} </li>
 * <li> Serialize the KMeansMiner instance into the fileSystem </li>
 * <li> build the client response </li>
 * </ul>
 * Note: as convention the file name is stored using client information
 * encoded in the request message:
 * <p>
 * {@code <request.table>_<request.clusters>.dmp}
 * </p>
 *
 * @author Pio Raffaele Fina
 */

public class DiscoverService implements Service {
    /**
     * @param req RequestMessage instance injected as context for service computation
     * @return the associated client response
     */
    public ResponseMessage execute(RequestMessage req) {

        ResponseMessage resp = new ResponseMessage();
        resp.setResponseType(req.getRequestType());
        try {
            String tableName = req.getBodyField("table");
            int k = Integer.parseInt(req.getBodyField("clusters"));

            Data data = new Data(tableName);
            KMeansMiner mineDB = new KMeansMiner(k);
            mineDB.kmeans(data);

            resp.setStatus("OK");
            if("true".equals(req.getBodyField("sendJson"))) {
                resp.addBodyField("data", mineDB.toJson(data));
            }
            else{
                resp.addBodyField("data", mineDB.toString(data));
            }

            String filePath = MultiServer.getConfig().getProperty("file_storage_root") + "/" + tableName + "_" + k + ".dmp";
            mineDB.salva(filePath);

        } catch (Exception e) {
            e.printStackTrace(); //TODO DEBUG
            resp.setStatus("ERROR");
            resp.addBodyField("errorMsg", e.getMessage());
        }

        return resp;
    }
}