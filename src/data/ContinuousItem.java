package data;

import java.lang.Math;

public class ContinuousItem extends Item {
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    @Override
    double distance(Object a) {
        //TODO: test!
            double val1 = ((ContinuousAttribute)this.getAttribute()).getScaledValues((Double)this.getValue());
            double val2 = ((ContinuousAttribute)this.getAttribute()).getScaledValues((Double)a);
        return Math.abs(val1-val2);
    }
}
