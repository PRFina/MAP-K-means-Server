package database;

/**
 * Exception to be thrown if DB can't be connected.
 */
public class DatabaseConnectionException extends Exception {
    DatabaseConnectionException(String message) {
        super(message);
    }
}
