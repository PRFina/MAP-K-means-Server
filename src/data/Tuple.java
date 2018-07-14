package data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

/**
 * This class represents implementation of tuple like array of items.
 *
 * @author Pio Raffaele Fina
 * @version 1.0
 */
public class Tuple implements Serializable {
	
	private Item[] tuple;

	/**
	 * Construct an instance of tuple.
	 *
	 * @param size int number that represents array size
	 */
	Tuple(int size){
		tuple = new Item[size];
	}

	/**
	 * Getter of the tuple length.
	 *
	 * @return tuple length
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * Getter of the i-th element of tuple.
	 *
     * @param i int number, index of array
     * @return i-th element of tuple
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Add a specified item in specified position of array
	 * @param c item
	 * @param i int number, index of array
	 */
	void add(Item c, int i) {
		// if (i < this.getLength())
			tuple[i] = c;
	}

    /**
     * Calculate distance between two tuples by sum of element-to-element distance
	 *
     * @param obj tuple from which calculate the distance
     * @return distance between two tuples
     */
	public double getDistance(Tuple obj) {
		double sum = 0.0;
		for (int i = 0; i < tuple.length; i++) {
			sum += tuple[i].distance(obj.get(i).getValue());
		}
		
		return sum;
	}

	/**
	 * Calculate average distance between transactions indexed in clusteredData set
	 *
     * @param data table
     * @param clusteredData set of tuples in
     * @return average distance
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double sum = 0.0, avg = 0.0;

		for(int i: clusteredData){
			sum += this.getDistance(data.getItemSet(i));
		}

		avg = sum /clusteredData.size();
		return avg;
	}
	
	public String toString(){
		return Arrays.toString(tuple);
	}

}
