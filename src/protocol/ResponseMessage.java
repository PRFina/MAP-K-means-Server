package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * This class model a response message sent from server to client .
 *
 * <p>
 *     From Server point of view, an object of
 *     this class should be created to send
 *     information to the client like data or some feedback
 *     if any errors occours at any point in time on the server.
 * </p>
 *
 * <p>
 *     From Client point of view,
 *     an object of this class
 *     shouldn't never be created
 *     to send information to server,
 *     instead should always retrieved from socket and
 *     analyzed to retrieve payload data or server error.
 * </p>
 *
 * A request message is composed of 3 main component:
 * <ul>
 *     <li> Request type: signal to the
 *     client which service has been called</li>
 *     <li> Status: signal to the client if computation
 *     have succeed (OK value) or failed ("ERROR")</li>
 *     <li> Body: contains the payload information
 *     data or a message if any errors occurs</li>
 * </ul>
 */
public class ResponseMessage implements Serializable {
    private MessageType type;

    private String status;

    private Map<String,String> body;
    public ResponseMessage(){

        body = new HashMap<>();
    }

    public void setResponseType(MessageType type){
        this.type = type;
    }

    public void setStatus(String status){
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void addBodyField(String key, String value){
        body.put(key,value);
    }

    public String getBodyField(String key) {
        return body.get(key);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "type=" + type +
                ", status='" + status + '\'' +
                ", body=" + body +
                '}';
    }
}
