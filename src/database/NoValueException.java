package database;

/**
 * Exception to be thrown if requested value is not in the result set.
 */
public class NoValueException extends Exception {
    NoValueException(String message) {
        super(message);
    }
}
