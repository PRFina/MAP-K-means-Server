/**
 * 
 * @author prf
 * This class model a tabular view of transactions
 */

class Data {
	Object data [][];
	int numberOfExamples;
	Attribute attributeSet[];
	
	
	Data(){
		
		//data
		this.data = new Object [14][5];
		// numberOfExamples
		this.numberOfExamples=14;		 
		 
		
		//explanatory Set
		this.attributeSet = new Attribute[5];
		
		String outLookValues[]=new String[3];
		outLookValues[0]="overcast";
		outLookValues[1]="rain";
		outLookValues[2]="sunny";
		attributeSet[0] = new DiscreteAttribute("Outlook",0, outLookValues);
		
		String temperatureValues[]=new String[3];
		outLookValues[0]="hot";
		outLookValues[1]="mild";
		outLookValues[2]="cool";
		attributeSet[1] = new DiscreteAttribute("Temperature",1, temperatureValues);
		
		String humidityValues[]=new String[2];
		humidityValues[0]="high";
		humidityValues[1]="normal";
		attributeSet[2] = new DiscreteAttribute("Humidity",2, humidityValues);
		
		String windValues[]=new String[2];
		windValues[0]="strong";
		windValues[1]="weak";
		attributeSet[3] = new DiscreteAttribute("Wind",3, windValues);
		
		String playTennisValues[]=new String[2];
		playTennisValues[0]="no";
		playTennisValues[1]="yes";
		attributeSet[4] = new DiscreteAttribute("PlayTennis",4, playTennisValues);
		
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
	
	int getNumberOfExamples(){
		return this.numberOfExamples;
	}
	
	int getNumberOfAttributes(){
		return this.attributeSet.length;
	}
	
	
	
	Object getAttributeValue(int exampleIndex, int attributeIndex){
		return this.data[exampleIndex][attributeIndex];
	}
	
	Attribute getAttribute(int index){
		return this.attributeSet[index];
	}

	
	public String toString(){
		String out = "";
		//concatenate table header
		for(Attribute attr: attributeSet){
			out += attr.getName() + ",";
		}
		//concatenate table data
		out += "\n";
		for (int i = 0; i < this.numberOfExamples; i++) {
			out += (i+1) +": ";
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


	
	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
		
		
	}

}

























