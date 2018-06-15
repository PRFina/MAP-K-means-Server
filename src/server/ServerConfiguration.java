package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfiguration {

    private Properties config;
    private String configFileName;

    ServerConfiguration(String fileName) throws ServerException{
        configFileName = fileName;

        try (FileInputStream in = new FileInputStream(configFileName)) {
            config = new Properties();
            config.load(in);
        } catch (IOException e) {
            throw new ServerException("Server setup error: can't load configuration file");
        }
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    void initStorageFolder() throws ServerException {
        File file = new File(config.getProperty("file_storage_root"));

        if(file.exists() == false){
            if(file.mkdirs() == false){
                throw new ServerException("Server setup error: can't create storage folder");
            }
        }


    }
}
