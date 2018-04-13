/**
 * This class represent the base structure for an attribute object, since the class is abstract it can't be instantiated.
 * 
 * @author prf
 * @version 1.0
 *
 */
abstract class Attribute {

	private String name;
	private int index;
	/**
	 * Construct an instance of an attribute.
	 * @param name name of the attribute
	 * @param index  index of the attribute
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	/**
	 * Getter for the attribute name.
	 * @return name of the attribute
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
	
	public String toString() {
		return this.name;
	}
}
