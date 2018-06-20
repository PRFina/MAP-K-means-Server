package data;

import java.io.Serializable;

/**
 * Class that represents the base structure for an attribute object.
 * 
 * @author Simone Cicerello
 * @version 1.0
 *
 */
abstract class Attribute implements Serializable {

	private String name;
	private int index;
	/**
	 * Construct an instance of an attribute.
	 * @param name attribute name
	 * @param index attribute index
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	/**
	 * Getter for the attribute name.
	 * @return attribute name
	 */
	String getName() {
		return name;
	}
	/**
	 * Getter for the attribute index.
	 * @return index of the attribute
	 */
	int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return this.name;
	}


}
