package data;

import java.util.Arrays;

/**
 * 
 * @author Pio Raffaele Fina
 *
 */
public class Tuple {
	
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
	
	public double avgDistance(Data data, int clusteredData[]) {
		double sum = 0.0, avg = 0.0;
		for (int i = 0; i < clusteredData.length; i++) {
			sum += this.getDistance(data.getItemSet(clusteredData[i]));
		}
		avg = sum /clusteredData.length;
		return avg;
	}
	
	public String toString(){
		return Arrays.toString(tuple);
	}

/*
	public static void main(String[] args){
		Tuple t1 = new Tuple(3);
		Tuple t2 = new Tuple(3);

		String[] vals1= {"hot","mild","cold"};
		String[] vals2= {"sunny","overcast","rain"};
		String[] vals3= {"normal","high"};
		DiscreteAttribute d1 = new DiscreteAttribute("Temperature",0,vals1);
		DiscreteAttribute d2 = new DiscreteAttribute("Outlook",1,vals2);
		DiscreteAttribute d3 = new DiscreteAttribute("Humidity",2,vals3);

		DiscreteItem i10 = new DiscreteItem(d1,"hot");
		DiscreteItem i11 = new DiscreteItem(d2,"rain");
		DiscreteItem i12 = new DiscreteItem(d3,"high");
		t1.add(i10,0);
		t1.add(i11,1);
		t1.add(i12,2);

		DiscreteItem i20 = new DiscreteItem(d1,"hot");
		DiscreteItem i21 = new DiscreteItem(d2,"rain");
		DiscreteItem i22 = new DiscreteItem(d3,"high");
		t2.add(i20,0);
		t2.add(i21,1);
		t2.add(i22,2);

		System.out.println(t1);
		System.out.println(t2.getDistance(t1));

	}
*/

}
