package data;

/**
 * This class provide an implementation of a Discrete Item type (eg. for Attribute type "pleytennis", value can be
 * a discrete type seen as a string)
 *
 * @author Simone Cicerello
 * @version 1.0
 */
public class DiscreteItem extends Item {

	/**
	 * Construct an instance of a discrete item
	 *
	 * @param attribute discrete attribute
	 * @param value attribute value
	 */
	DiscreteItem(DiscreteAttribute attribute, String value) {

		super(attribute, value);
	}

	/**
	 * Calculate distance between two objects assigning 0.0 in case of equals objects or 1.0 otherwise
	 *
	 * @param a object from which calculate the distance
	 * @return distance between two discrete objects
	 */
	double distance(Object a) {
		double dist = 1.0;
		
		if (this.getValue().equals(a)){
			dist = 0.0;
		}

		return dist;		
	}
}
