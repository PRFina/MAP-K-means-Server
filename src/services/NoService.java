package services;

import protocol.RequestMessage;
import protocol.ResponseMessage;
import server.ServerException;

/**
 * This class provides the "default" service.
 * <p>An instance of this class is created when client
 * request a service not registered in the ServiceDispatcher.
 * </p>
 */
public class NoService implements Service {
    /**
     * @param req RequestMessage instance injected as context for service computation
     * @return The responseMessage with ERROR status to signal the anomaly to the client.
     */
    @Override
    public ResponseMessage execute(RequestMessage req) {
        ResponseMessage resp = new ResponseMessage();

        resp.setResponseType(req.getRequestType());
        resp.setStatus("ERROR");
        resp.addBodyField("errorMsg", "The requested action is not available");

        return resp;
    }
}
