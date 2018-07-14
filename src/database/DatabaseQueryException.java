package database;

/**
 * Exception to be thrown if query can't be executed.
 */
public class DatabaseQueryException extends Exception {

    DatabaseQueryException(String message) {
        super(message);
    }

    DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
