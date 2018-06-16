package server;

import protocol.RequestMessage;
import protocol.ResponseMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ServerLogger {
    private Logger logger;

    ServerLogger() throws ServerException{
        logger = Logger.getLogger(ServerLogger.class.getName());

        try {
            FileHandler fh = new FileHandler(MultiServer.getConfig().getProperty("log_file"),true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
            logger.setUseParentHandlers(false); //remove from root logger to avoid default console handler

        } catch (IOException e) {
            throw new ServerException("Server setup error: can't create the logger");
        }
    }
    void log(String msg){
        logger.info(msg);
    }

    void logConnection(Socket socket, RequestMessage request, ResponseMessage response){

        logger.info( String.format("Connected with: %s request: %s response: %s",socket.getInetAddress().getHostAddress(),
                request.getRequestType().name(), response.getStatus()));
    }
}
