package data;
/**
 * This class provide an implementation of a continuous attribute type, eg. weight, length, temperature values.
 * @author Pio Raffaele Fina
 * @version 1.0
 *
 */
class ContinuousAttribute extends Attribute {

	private double min;
	private double max;

	/**
	 * Construct an instance of a continuous attribute.
	 * @param name name of the attribute.
	 * @param index index of the attribute.
	 * @param min minimum value of the attribute.
	 * @param max maximum value of the attribute.
	 */
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}
	/**
	 * Normalize the value using min-max normalization
	 * @param v value to normalized.
	 * @return normalized v.
	 */
	double getScaledValues(double v) {
		 return (v-this.min)/(this.max-this.min);
	}

}
