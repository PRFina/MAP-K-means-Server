package data;
import java.io.Serializable;
import java.util.Set;

public abstract class Item implements Serializable {

	private Attribute attribute;
	private Object value;
	
	Item(Attribute attribute, Object value){
		this.attribute = attribute;
		this.value = value;
	}

	Attribute getAttribute() {
		return attribute;
	}

    void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	Object getValue() {
		return value;
	}

	void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	abstract double distance(Object a);
	
	public void update(Data data, Set<Integer> clusteredData) {
		this.value = data.computePrototype(clusteredData, this.attribute);
	}
	
	
}
