package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Class that represents a single item of table
 *
 * @author Simone Cicerello
 * @version 1.0
 */
public abstract class Item implements Serializable {

	private Attribute attribute;
	private Object value;

	/**
	 * Construct an instance of an item
	 *
     * @param attribute a generic attribute
     * @param value attribute value
	 */
	Item(Attribute attribute, Object value){
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Getter for the attribute field
	 *
	 * @return attribute
	 */
	Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Setter for the attribute field
	 *
     * @param attribute a generic attribute
	 */
    void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * Getter for the value field
	 *
	 * @return value object of an item
	 */
	Object getValue() {
		return value;
	}

	/**
	 * Setter for the value field
	 *
     * @param value with which the attribute will be set
	 */
	void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * Calculate distance between two objects
	 *
     * @param a object from which calculate the distance
     * @return distance between two objects
	 */
	abstract double distance(Object a);

	/**
	 * Update item values.
	 *
	 * @param data represents a tabular view of transactions
	 * @param clusteredData set of cluster indexes
	 */
	public void update(Data data, Set<Integer> clusteredData) {
		this.value = data.computePrototype(clusteredData, this.attribute);
	}
	
	
}
