package mining;
import data.Data;
import data.OutOfRangeSampleSize;

public class KMeansMiner {

	ClusterSet C;
	
	public KMeansMiner(int k){
		C = new ClusterSet(k);
	}
	
	public ClusterSet getC() {
		return C;
	}
	
	public int kmeans(Data data) throws OutOfRangeSampleSize {
		int numberOfIterations=0;
		//STEP 1
		C.initializeCentroids(data);
		boolean changedCluster=false;
		
		do{
			//System.out.println("\t\tIterazione n:"+numberOfIterations+"\n"+this.getC().toString(data)+"**************************************"); //DEBUG PRINT
			numberOfIterations++;

			//STEP 2
			changedCluster=false;
			for(int i=0;i<data.getNumberOfExamples();i++){
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
				Cluster oldCluster=C.currentCluster(i);
				boolean currentChange=nearestCluster.addData(i);
				
				if(currentChange)
					changedCluster=true;
				
				//rimuovo la tupla dal vecchio cluster
				if(currentChange && oldCluster!=null)
				
					//il nodo va rimosso dal suo vecchio cluster
					oldCluster.removeTuple(i);
			}
			//STEP 3
			C.updateCentroids(data);
		}
		while(changedCluster);
		
		return numberOfIterations;
	}
	
	
	
	
	
	public static void main(String[] args) {
		Data trainingSet=new Data();

		KMeansMiner kmm = new KMeansMiner(3);
		//kmm.kmeans(trainingSet);


	}

}
