package database;

/**
 * Exception to be thrown in case of problems with DB connection.
 */
public class DatabaseConnectionException extends Exception {
    DatabaseConnectionException(String message) {
        super(message);
    }
}
