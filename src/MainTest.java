import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

import java.sql.SQLException;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoValueException, EmptySetException, DatabaseConnectionException {
		Data data =new Data("playtennis");
		System.out.println(data);
		
		boolean continueFlag=false;
		do {
			System.out.println("Inserisci numero cluster (min 1 - max "+data.getNumberOfExamples()+")");
			int k=Keyboard.readInt();
				
			KMeansMiner kmeans=new KMeansMiner(k);
			try {

				int numIter= kmeans.kmeans(data);
				System.out.println("Numero di iterazioni eseguite: "+numIter+"\n\n");
				System.out.println(kmeans.getC().toString(data));
			}
			catch(OutOfRangeSampleSize e){
				System.out.println(e.getMessage() + "");
			}


			
			continueFlag=false;
			System.out.println("Vuoi ripetere l'esecuzione del K-Means? (y/n)");
			char choice=Keyboard.readChar();
			if(choice=='y' || choice=='Y')
				continueFlag=true;
		}
		while(continueFlag==true);

		
		
		
	}

}
