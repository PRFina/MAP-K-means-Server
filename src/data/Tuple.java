package data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

/**
 * 
 * @author Pio Raffaele Fina
 *
 */
public class Tuple implements Serializable {
	
	private Item[] tuple;
	
	Tuple(int size){
		
		tuple = new Item[size];
	}
	
	public int getLength() {
		return tuple.length;
	}
	
	public Item get(int i) {
		return tuple[i];
	}
	
	void add(Item c, int i) {
		// if (i < this.getLength())
			tuple[i] = c;
	}

    /**
     *
     * @param obj
     * @return
     */
	
	public double getDistance(Tuple obj) {
		double sum = 0.0;
		for (int i = 0; i < tuple.length; i++) {
			sum += tuple[i].distance(obj.get(i).getValue());
		}
		
		return sum;
	}
	
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
