package database;

/**
 * Exception to be thrown in case a request value is not in the result set.
 */
public class NoValueException extends Exception {
    NoValueException(String message){
        super(message);
    }
}
