package database;

import server.MultiServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Realize DB connection, contains useful parameters to connect to DB.
 *
 * @author Simone Cicerello
 * @version 1.0
 */

public class DbAccess {
    final private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
    final private String DBMS = MultiServer.getConfig().getProperty("db_driver");
    final private String SERVER = MultiServer.getConfig().getProperty("db_server");
    final private String PORT = MultiServer.getConfig().getProperty("db_port");
    final private String DATABASE = MultiServer.getConfig().getProperty("db_name");
    final private String USER_ID = MultiServer.getConfig().getProperty("db_user");
    final private String PASSWORD = MultiServer.getConfig().getProperty("db_pass");

    private Connection conn;

    /**
     * Establishes connection to DB using url string that contains parameters like url and user credentials.
     *
     * @throws DatabaseConnectionException
     * @throws ClassNotFoundException
     */
    public void initConnection() throws DatabaseConnectionException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        String url = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
        try{
            conn = DriverManager.getConnection(url, USER_ID, PASSWORD);
        }catch (SQLException e){
            throw new DatabaseConnectionException("Connection Failed:"+e.getMessage());
        }
    }

    /**
     * Closes connection with DB.
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * Getter of the connection.
     *
     * @return connection
     */
    Connection getConnection(){
        return conn;
    }
}
