package services;

import protocol.RequestMessage;
import protocol.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

public class ServiceDispatcher {

    private Map<String, Service> services;


    public ServiceDispatcher(){
        services = new HashMap<>();
    }

    public void register(String name, Service s) {
        services.put(name,s);
    }

    public ResponseMessage dispatch(RequestMessage req){
        Service service = services.get(req.getRequestType().name()); //Get the right service
        ResponseMessage  resp = null;

        if( service != null){
            return service.execute(req);
        }

        return resp;
    }
}
