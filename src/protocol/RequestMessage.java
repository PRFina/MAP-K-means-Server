package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class model a request message sent from client to the server.
 *
 * <p>
 *     From Client point of view, an object of this class should be created to encapsulate
 *     information to send to the server.
 * </p>
 *
 * <p>
 *     From Server point of view, an object of this class shouldn't never be created
 *     to send information to client, instead should always retrieved from socket and
 *     analyzed to call the right service.
 * </p>
 *
 * A request message is composed of 2 main component:
 * <ul>
 *     <li> Request type: signal to the server which service should be called</li>
 *     <li> body: contains the payload information like data or parameters</li>
 * </ul>
 */
public class RequestMessage implements Serializable {

    private MessageType type;
    private Map<String,String> body;

    /**
     * Constructs an instance of requestMessage
     *
     * @param msgType type of message
     */
    public RequestMessage(MessageType msgType) {
        this.type = msgType;
        body = new HashMap<>();
    }

    /**
     * Getter of requestMessage type
     *
     * @return the type of requestMessage
     */
    public MessageType getRequestType(){return type;}

    /**
     * Adds body field in hash map
     *
     * @param key attribute
     * @param value field value
     */
    public void addBodyField(String key, String value){
        body.put(key,value);
    }

    /**
     * Getter of body field
     *
     * @param fieldKey field attribute
     * @return the selected field
     */
    public String getBodyField(String fieldKey){
        return body.get(fieldKey);
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "type=" + type +
                ", body=" + body +
                '}';
    }
}