package data;

public class DiscreteItem extends Item {
	
	DiscreteItem(DiscreteAttribute attribute, String value) {

		super(attribute, value);
	}
	
	double distance(Object a) {
		double dist = 1.0;
		
		if (this.getValue().equals(a)){
			dist = 0.0;
		}

		return dist;		
	}
}
