package services;

import server.MultiServer;
import server.ServerException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadClustersService implements Service {

    String fileName;

    public ReadClustersService(String fileName){
        this.fileName = fileName;

    }

    public String execute() throws ServerException {
        //TODO remove dummy implementation
        String filePath = MultiServer.settings.getProperty("file_storage_root") + "/" + fileName;
        String out;

        try (  BufferedReader in = new BufferedReader(new FileReader(filePath))){
            out = in.readLine();
        }catch (FileNotFoundException e){
            throw new ServerException("Resource not available!");
        }
        catch (IOException e) {
            throw new ServerException("Cannot handle the resources!");
        }


        // TODO add real implementation with kmeansMiner instance


        return out;
    }
}

