package data;

import database.DatabaseConnectionException;
import database.EmptySetException;
import mining.KMeansMiner;

import java.lang.Math;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * This class provide an implementation of a Contonuous Item type (eg. for Attribute type "temperature", value can be
 * a double type like 38,2)
 *
 * @author Simone Cicerello
 * @version 1.0
 */
public class ContinuousItem extends Item {

    private static DecimalFormat df2 = new DecimalFormat("###,###.##"); // needed for string formatting

    /**
     * Constructs a continuous item.
     *
     * @param attribute attribute name
     * @param value attribute value
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Calculates distance between two continuous attribute using absolute value.
     *
     * @param a object from which calculate the distance
     * @return absolute values between two double quantities
     */
    @Override
    double distance(Object a) {
        double val1 = ((ContinuousAttribute)this.getAttribute()).getScaledValues((Double)this.getValue());
        double val2 = ((ContinuousAttribute)this.getAttribute()).getScaledValues((Double)(a));
        return Math.abs(val1-val2);
    }

    @Override
    public String toString() {

        return df2.format(getValue());
    }
}
