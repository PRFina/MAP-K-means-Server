package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
    final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
    final String DBMS = "jdbc:mysql";
    final String SERVER = "localhost";
    final String PORT = "3306";
    final String DATABASE = "map";
    final String USER_ID = "root";
    final String PASSWORD = "admin";
    Connection conn;

    public void initConnection() throws DatabaseConnectionException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        String url = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
        try{
            conn = DriverManager.getConnection(url, USER_ID, PASSWORD);
        }catch (SQLException e){
            throw new DatabaseConnectionException("Connection Failed:"+e.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    Connection getConnection(){
        return conn;
    }
}
