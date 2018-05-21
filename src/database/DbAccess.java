package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
    final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
    final String DBMS = "jdbc:mysql";
    final String SERVER = "localhost";
    final String DATABASE = "MapDB";
    final String PORT = "3306";
    final String USER_ID = "MapUser";
    final String PASSWORD = "map";
    Connection conn;

    public void initConnection() throws DatabaseConnectionException, ClassNotFoundException, SQLException {
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


    public static void main(String[] args) throws SQLException, DatabaseConnectionException, ClassNotFoundException {
        DbAccess db = new DbAccess();
        db.initConnection();
    }
}
