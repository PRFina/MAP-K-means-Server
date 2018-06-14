package services;


import protocol.RequestMessage;
import protocol.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the services dispatch mechanism implementation.
 *
 * At conceptual level the ServiceDispatcher model the
 * registered services management process,
 * provides a mapping between the client request
 * and a computation executed on server.

 * <p>This class act like the "Invoker entity" of the
 * <a href="https://en.wikipedia.org/wiki/Command_pattern" >Command Pattern</a>
 * </p>
 */
public class ServiceDispatcher {

    private Map<String, Service> services;


    public ServiceDispatcher(){
        services = new HashMap<>();
    }

    /**
     * Register the service to the dispatcher.
     *
     *
     * @param name the logic name associated with the service instance,
     *             the name parameter <b>must</b> match one of the value specified
     *             in the {@link protocol.MessageType} enum
     * @param service the service instance to register.
     */
    public void register(String name, Service service) {
        services.put(name, service);
    }

    /**
     * Dispatch and call the service requested from client.
     *
     * @param req requestMessage object sent from client
     * @return responseMessage object associated with the service execution
     */
    public ResponseMessage dispatch(RequestMessage req){
        Service service = services.getOrDefault(req.getRequestType().name(),
                new NoService());

        return service.execute(req);
    }
}
