package services;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.SAXException;

import protocol.RequestMessage;
import protocol.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the services dispatch mechanism implementation.
 *<p>
 * At conceptual level the ServiceDispatcher model the
 * mapping between the client request
 * and a computation executed on server.
 * provide an implementation of the
 * registered services management process.
 *</p>
 *
 * <p>This class act like the "Invoker entity" of the
 * <a href="https://en.wikipedia.org/wiki/Command_pattern" >Command Pattern</a>
 * </p>
 *
 *
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
    private void register(String name, Service service) {

        services.put(name, service);
    }

    /**
     * Get the registered service indexed by specified key
     * @param key the index key of the registered service
     * @return the registered service instance or if key
     * is not an indexed return a NoService instance as
     * default value
     */
    private Service getService(String key){
        Service s = services.get(key);
        if (s == null)
            return services.get("NO_SERVICE");

        return s;
    }

    /**
     * Dispatch and call the service requested from client.
     *
     * @param req requestMessage object sent from client
     * @return responseMessage object associated with the service execution
     */
    public ResponseMessage dispatch(RequestMessage req){
        Service service = getService(req.getRequestType().name());
        System.out.println(service);
        return service.execute(req);
    }

    /**
     * Parse the .xml file specified as parameter and register
     * the services declared in it.
     * Each service declared is loaded and instatiated with reflection
     * mechanism
     * @param fileName the name of the file to be parsed
     */
    public void load(String fileName){
        try {
            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new FileInputStream("services.xml"));
            NodeList services = doc.getElementsByTagName("service");

            for (int i = 0; i < services.getLength(); i++) {

                Element nodeEl = (Element) services.item(i);
                String name = (nodeEl.getTextContent())
                        .replaceAll("\\s+",""); //remove white spaces for normalization
                String className = nodeEl.getAttribute("class");

                Service service = (Service) Class.forName(className).newInstance(); //use reflection
                register(name, service);

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
