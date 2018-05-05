package data;

import java.util.Iterator;
import java.util.TreeSet;
import java.lang.Iterable;
import utility.ArraySet;
/**
 * This class provide an implementation of a discrete attribute type, categorical data like blood type, music genres, days of the week.
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
	 * @return the number of all attribute values
	 */
	int getNumberOfDistinctValues() {
		return this.values.size();
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
	    return this.getName() +
				" (@ "+ this.getIndex() + ") : " +
				this.values.toString();
    }

	@Override
	public Iterator<String> iterator() {
		return this.values.iterator();
	}

	public static void main(String[] args){

		TreeSet<String> ts = new TreeSet<>();
		ts.add("A");
		ts.add("B");
		ts.add("C");

		DiscreteAttribute a = new DiscreteAttribute("Humidty",2,ts);
		System.out.println(a.toString());





	}
}
