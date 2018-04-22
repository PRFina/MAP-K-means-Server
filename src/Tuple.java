/**
 * 
 * @author Pio Raffaele Fina
 *
 */
class Tuple {
	
	private Item[] tuple;
	
	Tuple(int size){
		
		tuple = new Item[size];
	}
	
	int getLength() {
		return tuple.length;
	}
	
	Item get(int i) {
		return tuple[i];
	}
	
	void add(Item c, int i) {
		if (i < this.getLength())
			tuple[i] = c;
	}
	
	double getDistance(Tuple obj) {
		double sum = 0.0;
		for (int i = 0; i < tuple.length; i++) {
			sum += tuple[i].distance(obj.get(i));
		}
		
		return sum;
	}
	
	double getAvgDistance(Data data, int clusteredData[]) {
		double sum = 0.0, avg = 0.0;
		for (int i = 0; i < clusteredData.length; i++) {
			sum += this.getDistance(data.getItemSet(clusteredData[i]));
		}
		avg = sum /clusteredData.length;
		return avg;
	}
	
	public String toString() {
		String out= "[";
		
		for (int i = 0; i < tuple.length; i++) {
			if( i == tuple.length-1)
				out += tuple[i];
			else
				out += tuple[i] + ",";
		}
		out +="]";		
		return out;
	}

}
