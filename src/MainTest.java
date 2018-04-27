import data.Data;
import keyboardinput.Keyboard;
import mining.KMeansMiner;
public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Data data =new Data();
		System.out.println(data);
		
		boolean continueFlag=false;
		do {
			System.out.println("Inserisci numero cluster (min 2 - max "+data.getNumberOfExamples()+")");
			int k=Keyboard.readInt();
				
			//KMeansMiner kmeans=new KMeansMiner(k);
			int numIter=8;
			System.out.println("Numero di Iterazione:"+numIter);
			//System.out.println(kmeans.getC().toString(data));
			
			continueFlag=false;
			System.out.println("Vuoi ripetere l'esecuzione del K-Means? (y/n)");
			char choice=Keyboard.readChar();
			if(choice=='y' || choice=='Y')
				continueFlag=true;
		}
		while(continueFlag==true);

		
		
		
	}

}
