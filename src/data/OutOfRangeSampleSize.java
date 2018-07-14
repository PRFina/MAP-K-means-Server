package data;

/**
 * Exception to be thrown if the number of clusters selected is out of range:
 * range min: minumum number of clusters, 1;
 * range max: maximum number of clusters, corresponds to number of unique tuples in data table.
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
