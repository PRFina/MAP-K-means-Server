package server;

import com.sun.security.ntlm.Server;

import java.io.*;
import java.util.Properties;

/**
 * Load a configuration file which contains server settings.
 */
public final class ServerConfiguration {

    private Properties config;
    private String configFileName;
    private static ServerConfiguration instance;

    /**
     * Construct a new instance with the specified .properties file.
     *
     * <p>
     *     The loading mechanism looks for a custom .property file to load in the
     * current working directory, if no custom file is provided
     * the default one distributed in the .jar archive will be loaded
     * </p>
     *
     * @param fileName the name of the configuration file.
     */
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
