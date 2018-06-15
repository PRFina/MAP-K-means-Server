package services;

import protocol.RequestMessage;
import protocol.ResponseMessage;

/**
 * The Service interface should be implemented by any class whose instances are
 * intended to provide a "self-contained computation" on the server.
 * <p>
 * The term "self-contained computation" is loosely intended as computation
 * with no communication with other services or external components. A service
 * is in 1-1 relation with a client request.
 * </p>
 * <p>
 * The class that implements this interface must define the <tt>execute</tt> method
 */
@FunctionalInterface
public interface Service {
    /**
     * Encode the service computation logic. The response message is created in
     * the service based the request content
     *
     * @param req RequestMessage instance injected as context for service computation
     * @return the ResponseMessage instance
     */
    ResponseMessage execute(RequestMessage req);
}
