package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class model a message sent from client to server.
 * The request message have 2 main section:
 * -message type
 * - message body
 */
public class RequestMessage implements Serializable {

    private MessageType type;
    private Map<String, String> body;

    public RequestMessage(MessageType msgType) {
        this.type = msgType;
        body = new HashMap<>();
    }

    public MessageType getRequestType() {
        return type;
    }

    public void addBodyField(String key, String value) {
        body.put(key, value);
    }

    public String getBodyField(String fieldKey) {
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