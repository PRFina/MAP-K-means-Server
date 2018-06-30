package database;

/**
 * Exception to be thrown in case the results of queries is empty.
 */
public class EmptySetException extends Exception {
    EmptySetException(String message) {
        super(message);
    }
}
