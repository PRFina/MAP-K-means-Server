package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfiguration {

    private Properties config;
    private String configFileName;

    ServerConfiguration(String fileName){
        configFileName = fileName;

        try(FileInputStream in = new FileInputStream(configFileName)){
            config = new Properties();
            config.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        return config.getProperty(key);
    }
}
