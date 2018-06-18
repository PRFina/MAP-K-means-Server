package data;

import java.lang.Math;

/**
 * This class provide an implementation of a Contonuous Item type (eg. for Attribute type "temperature", value can be
 * a double type like 38,2)
 *
 * @author Simone Cicerello
 * @version 1.0
 */
public class ContinuousItem extends Item {

    /**
     * Construct a continuous item.
     *
     * @param attribute
     * @param value
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Calculate distance between two continuous attribute using absolute value.
     *
     * @param a
     * @return absolute values between two double quantities
     */
    @Override
    double distance(Object a) {
        double val1 = ((ContinuousAttribute)this.getAttribute()).getScaledValues((Double)this.getValue());
        double val2 = ((ContinuousAttribute)this.getAttribute()).getScaledValues((Double)(a));
        return Math.abs(val1-val2);
    }
}
