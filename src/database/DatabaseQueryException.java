package database;

public class DatabaseQueryException extends Exception {

    DatabaseQueryException(String message) {
        super(message);
    }

    DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
