package data;

/**
 * Exception to be thrown in case the number of cluster selected in out of range
 *
 * @author Simone Cicerello
 * @version 1.0
 *
 */
public class OutOfRangeSampleSize extends Exception {

    OutOfRangeSampleSize(String message){
        super(message);
    }
}
