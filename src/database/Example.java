package database;

import com.sun.xml.internal.bind.v2.runtime.property.StructureLoaderBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *Models a transaction returned from DB.
 *
 * @author Simone Cicerello
 * @version 1.0
 *
 */
public class Example implements Comparable<Example>{
	private List<Object> example=new ArrayList<Object>();

	/**
	 * Adds an object to transaction
	 *
	 * @param o transaction object
	 */
	public void add(Object o){
		example.add(o);
	}

	/**
	 * Gets an object from transaction by index.
	 *
	 * @param i index
	 * @return transaction object
	 */
	public Object get(int i){
		return example.get(i);
	}

	public int compareTo(Example ex) {
		
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i))) {
				return ((Comparable)o).compareTo(example.get(i));
			}
			i++;
		}
		return 0;
	}

	public String toString(){
		StringBuilder str = new StringBuilder();

		for(Object o:example){

			str.append(o.toString());
			str.append(" ");
		}
		return str.toString();
	}
	
}