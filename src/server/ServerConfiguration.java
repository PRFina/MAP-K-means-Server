package server;

import java.io.*;
import java.util.Properties;

/**
 * Load server configuration file.
 *
 * load try to load a custom config.property file if fail
 * load the default one deployed in the jar
 *
 * the custom.property file must be located in working directory
 */
public class ServerConfiguration {

    private Properties config;
    private String configFileName;

    ServerConfiguration(String fileName) throws ServerException{
        configFileName = fileName;

        //Try to load custom config file
        try(FileInputStream customPropFile = new FileInputStream(fileName)){

            config = new Properties();
            config.load(customPropFile);

        } catch (FileNotFoundException e) {
            // Try to load default config file
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(configFileName)) {
                config = new Properties();
                config.load(in);
            } catch (IOException e1) {
                throw new ServerException("Server setup error: can't load default configuration file");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
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
