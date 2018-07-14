package database;

/**
 * Exception to be thrown if result set of queries is empty.
 */
public class EmptySetException extends Exception {
    EmptySetException(String message) {
        super(message);
    }
}
