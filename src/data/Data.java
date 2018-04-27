package data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import utility.ArraySet;

/**
 * 
 * @author prf
 * This class model a tabular view of transactions
 */

public class Data {
	Object data [][];
	int numberOfExamples;
	Attribute explanatorySet[];
	
	
	public Data(){
		
		//data
		this.data = new Object [14][5];
		// numberOfExamples
		this.numberOfExamples=14;		 
		 
		
		//explanatory Set
		this.explanatorySet = new Attribute[5];
		
		String outLookValues[]=new String[3];
		outLookValues[0]="overcast";
		outLookValues[1]="rain";
		outLookValues[2]="sunny";
		explanatorySet[0] = new DiscreteAttribute("Outlook",0, outLookValues);
		
		String temperatureValues[]=new String[3];
		outLookValues[0]="hot";
		outLookValues[1]="mild";
		outLookValues[2]="cool";
		explanatorySet[1] = new DiscreteAttribute("Temperature",1, temperatureValues);
		
		String humidityValues[]=new String[2];
		humidityValues[0]="high";
		humidityValues[1]="normal";
		explanatorySet[2] = new DiscreteAttribute("Humidity",2, humidityValues);
		
		String windValues[]=new String[2];
		windValues[0]="strong";
		windValues[1]="weak";
		explanatorySet[3] = new DiscreteAttribute("Wind",3, windValues);
		
		String playTennisValues[]=new String[2];
		playTennisValues[0]="no";
		playTennisValues[1]="yes";
		explanatorySet[4] = new DiscreteAttribute("PlayTennis",4, playTennisValues);
		
		//Manually fill data table
		this.data[0][0]="sunny";
		this.data[0][1]="hot";
		this.data[0][2]="high";
		this.data[0][3]="weak";
		this.data[0][4]="no";

		this.data[1][0]="sunny";
		this.data[1][1]="hot";
		this.data[1][2]="high";
		this.data[1][3]="strong";
		this.data[1][4]="no";

		this.data[2][0]="overcast";
		this.data[2][1]="hot";
		this.data[2][2]="high";
		this.data[2][3]="weak";
		this.data[2][4]="yes";

		this.data[3][0]="rain";
		this.data[3][1]="mild";
		this.data[3][2]="high";
		this.data[3][3]="weak";
		this.data[3][4]="yes";

		this.data[4][0]="rain";
		this.data[4][1]="cool";
		this.data[4][2]="normal";
		this.data[4][3]="weak";
		this.data[4][4]="yes";

		this.data[5][0]="rain";
		this.data[5][1]="cool";
		this.data[5][2]="normal";
		this.data[5][3]="strong";
		this.data[5][4]="no";

		this.data[6][0]="overcast";
		this.data[6][1]="cool";
		this.data[6][2]="normal";
		this.data[6][3]="strong";
		this.data[6][4]="yes";

		this.data[7][0]="sunny";
		this.data[7][1]="mild";
		this.data[7][2]="high";
		this.data[7][3]="weak";
		this.data[7][4]="no";

		this.data[8][0]="sunny";
		this.data[8][1]="cool";
		this.data[8][2]="normal";
		this.data[8][3]="weak";
		this.data[8][4]="yes";

		this.data[9][0]="rain";
		this.data[9][1]="mild";
		this.data[9][2]="normal";
		this.data[9][3]="weak";
		this.data[9][4]="yes";

		this.data[10][0]="sunny";
		this.data[10][1]="mild";
		this.data[10][2]="normal";
		this.data[10][3]="strong";
		this.data[10][4]="yes";

		this.data[11][0]="overcast";
		this.data[11][1]="mild";
		this.data[11][2]="high";
		this.data[11][3]="strong";
		this.data[11][4]="yes";

		this.data[12][0]="overcast";
		this.data[12][1]="hot";
		this.data[12][2]="normal";
		this.data[12][3]="weak";
		this.data[12][4]="yes";

		this.data[13][0]="rain";
		this.data[13][1]="mild";
		this.data[13][2]="high";
		this.data[13][3]="strong";
		this.data[13][4]="no";
		
	}
	
	public int getNumberOfExamples(){
		return this.numberOfExamples;
	}
	
	public int getNumberOfAttributes(){
		return this.explanatorySet.length;
	}
	
	
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return this.data[exampleIndex][attributeIndex];
	}
	
	public Attribute getAttribute(int index){
		return this.explanatorySet[index];
	}
	
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(explanatorySet.length); // ASK why code in kmeans2.pdf refer to explanatory set??
		for (int i = 0; i < explanatorySet.length; i++) {
			DiscreteItem tupleItem = new DiscreteItem((DiscreteAttribute)explanatorySet[i], 
					(String) data[index][i]); //ASK perchè dobbiamo usare discrete attribute quando attribute set è un array di tipo attribute (suo supertipo), il cast eventualmente è giusto??
			tuple.add(tupleItem, i);
		}
		return tuple;
	}
	/**
	 * Generate array of k unique random indexes for centroids
	 * @param k
	 * @return array of indexes
	 */
	public int[] sampling(int k) {
		int centroidIndexes[] = new int[k];
		Random rand = new Random(System.currentTimeMillis());
		
		//generate random indexes for centroids 
		for (int i = 0; i < centroidIndexes.length; i++) {
			
			boolean found = false;
			int c;
			//check the uniqueness of centroid index
			do {
				found = false;
				c = rand.nextInt(this.getNumberOfExamples());
				
				for(int j = 0; j < i; j++) {
					if(this.compare(centroidIndexes[j],c)){
						found = true;
						break;
					}
				}
			}
			while(found);
			centroidIndexes[i] = c;
		}
		return centroidIndexes;		
	}
	/**
	 * element wise tuple comparison. Given 2 index i,j check if tuple at index i and tuple at index j are equals. 
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean compare(int i, int j) {
		boolean isTrue = true;
		
		for (int k = 0; k < this.getNumberOfAttributes(); k++) {
			if( !(this.getAttributeValue(i,k).equals(this.getAttributeValue(j, k))) ) {
				isTrue = false;
				break;
			}
		}
		return isTrue;
	}

	
	public String toString(){
		String out = "";
		//concatenate table header
		for(Attribute attr: explanatorySet){
			out += attr.getName() + ",";
		}
		//concatenate table data
		out += "\n";
		for (int i = 0; i < this.numberOfExamples; i++) {
			out += (i) +": ";
			for( int j = 0; j < this.getNumberOfAttributes(); j++){
				if(j == this.getNumberOfAttributes()-1)
					out += this.getAttributeValue(i,j);
				else
					out += this.getAttributeValue(i,j) + ",";
			}
			out += "\n";
		}
		
		return out;
		
	}
	
	Object computePrototype(ArraySet idList, Attribute attribute) {
		return computePrototype(idList, (DiscreteAttribute)attribute);
	}
	/**
	 * Return the most frequent attribute's value in tuples indexed by idList
	 * 
	 * @param idList
	 * @param attribute
	 * @return
	 */
	//TODO: review efficiency of method
	String computePrototype(ArraySet idList, DiscreteAttribute attribute) {
		List<Integer> valuesFrequencies = new ArrayList<Integer> (attribute.getNumberOfDistinctValues());
		for (int i = 0; i < attribute.getNumberOfDistinctValues(); i++) {
			valuesFrequencies.add(attribute.frequency(this, idList, attribute.getValue(i)));
		}
		int max=Collections.max(valuesFrequencies);
		return attribute.getValue(valuesFrequencies.indexOf(max));
	}

	
	
	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
		
		ArraySet as = new ArraySet();
		as.add(0);
		as.add(1);
		as.add(2);
		as.add(3);
		as.add(4);
		as.add(5);
		as.add(6);
		as.add(7);
		as.add(8);
		as.add(9);
		as.add(10);
		as.add(11);
		as.add(12);
		as.add(13);
			
		String[] s = {"hot","mild","cool"};
		
		DiscreteAttribute da = new DiscreteAttribute("Temperature", 1, s);
		
		//System.out.println(da.frequency(trainingSet, as, "sunny"));
		
		//System.out.println(trainingSet.getItemSet(13));
		
		System.out.println(trainingSet.computePrototype(as, da));
		
	}

}
























