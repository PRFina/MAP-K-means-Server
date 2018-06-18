package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Iterable;

/**
 * This class provide an implementation of a discrete attribute type (eg. categorical data like blood type,
 * music genres, days of the week).
 *
 * @author Pio Raffaele Fina
 * @version 1.0
 *
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
	
	private  TreeSet<String> values;
	
	/**
	 * Construct an instance of a discrete attribute.
	 * @param name name of the attribute.
	 * @param index index of the attribute.
	 * @param values array of string, each string in the array is one of the attribute possible value.
	 */
	DiscreteAttribute(String name, int index, TreeSet<String> values) {
		super(name, index);
		this.values = values;
	}


	/**
	 * Get the values number that attribute can assume.
	 *
	 * @return the number of all attribute values
	 */
	int getNumberOfDistinctValues() {
		return this.values.size();
	}


	/**
	 * Get the number of occurrences of v in the Data's row indexed by idList.
	 *
	 * @param data represents a tabular view of transactions
	 * @param idList list of tuples indexes
	 * @param v the value of which the occurrences are to be looked for
	 * @return
	 */
	int frequency(Data data, Set<Integer> idList, String v) {
		int freqCount = 0;
		Integer[] idArray = idList.toArray(new Integer[idList.size()]);
		String sample;
		
		for(int i=0; i < idArray.length;i++) {
			sample = data.getAttributeValue(idArray[i], this.getIndex()).toString();
			if (sample.equals(v))
				freqCount++;
		}
		return freqCount;
	}

	public String toString(){
	    return this.getName() +
				" (@ "+ this.getIndex() + ") : " +
				this.values.toString();
    }

	@Override
	public Iterator<String> iterator() {
		return this.values.iterator();
	}

}
