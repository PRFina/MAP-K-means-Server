package data;
import java.util.Arrays;

import utility.ArraySet;
/**
 * This class provide an implementation of a discrete attribute type, categorical data like blood type, music genres, days of the week.
 * @author Pio Raffaele Fina
 * @version 1.0
 *
 */
public class DiscreteAttribute extends Attribute {
	
	private String values[];
	
	/**
	 * Construct an instance of a discrete attribute.
	 * @param name name of the attribute.
	 * @param index index of the attribute.
	 * @param values array of string, each string in the array is one of the attribute possible value.
	 */
	DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values = values;
	}
	/**
	 * Get the values number that attribute can assume.
	 * @return the number of all attribute values
	 */
	int getNumberOfDistinctValues() {
		return this.values.length;
	}
	/**
	 * Get the i-th attribute value.
	 * @param i index of the i-th attribute value
	 * @return the i-th attribute value
	 */
	String getValue(int i) {
		return this.values[i];
	}
	/**
	 * Get the number of occurrences of v in the Data's row indexed by idList 
	 * @param data
	 * @param idList
	 * @param v
	 * @return
	 */
	int frequency(Data data, ArraySet idList, String v) {
		//TODO test 
		int freqCount = 0;
		int[] idArray = idList.toArray();
		String sample;
		
		for(int i=0; i < idArray.length;i++) {
			sample = data.getAttributeValue(idArray[i], this.getIndex()).toString();
			if (sample.equals(v))
				freqCount++;
		}
		return freqCount;
	}

	public String toString(){
	    return this.getName() +" (@ "+ this.getIndex() + ") : " + Arrays.toString(values);
    }

}
