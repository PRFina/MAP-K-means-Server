package server;

import com.sun.security.ntlm.Server;

import java.io.*;
import java.util.Properties;

/**
 * Load server configuration file.
 *
 * load try to load a custom config.property file if fail
 * load the default one deployed in the jar
 *
 * the custom.property file must be located in working directory
 *
 * this class use Singleton pattern
 */
public final class ServerConfiguration {

    private Properties config;
    private String configFileName;
    private static ServerConfiguration instance;

    private ServerConfiguration(String fileName) throws ServerException{
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

    static ServerConfiguration getInstance() throws ServerException {
        if (instance == null){
            instance = new ServerConfiguration("config.properties");
        }

        return instance;
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
