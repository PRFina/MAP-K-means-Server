package mining;
import data.Data;
import data.OutOfRangeSampleSize;

import java.io.*;

/**
 * Class that represents the action of "Mining" on a data table with KMeans algorithm, choosing number of clusters.
 * This class, furthermore, allow to serialize on a file an instance of Mining.
 *
 * @author Simone Cicerello
 * @verison 1.0
 *
 */
public class KMeansMiner implements Serializable {

	private ClusterSet C;

	/**
	 * Constructs an instance of the KMeans miner with k clusters.
	 * @param k number of clusters
	 */
	public KMeansMiner(int k){
		C = new ClusterSet(k);
	}

	/**
	 * Constructs an instance of the KMeans miner, loading from previously serialized file.
	 *
	 * @param fileName file name that contains a serialized instance of KMeans
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public KMeansMiner(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream inStream = new FileInputStream(fileName);
		ObjectInputStream objStream = new ObjectInputStream(inStream);
		this.C = (ClusterSet) objStream.readObject();
		objStream.close();
		inStream.close();
	}

	/**
	 * Getter of the clusterSet.
	 *
	 * @return clusterSet
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Implements KMeans algorithm divided in three step:
	 * step 1) initializes the centroids by choosing k tuples randomly;
	 * step 2) iteratively, calculates and adds each other tuples to the nearests cluster,
	 * step 3) update new centroids;
	 *
	 * @param data table
	 * @return number of iterations
	 * @throws OutOfRangeSampleSize
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize {
		int numberOfIterations=0;
		//STEP 1
		C.initializeCentroids(data);
		boolean changedCluster=false;
		
		do{
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

	/**
	 * Serialize and save an instance of KMeans in a file.
	 *
	 * @param fileName
	 * @throws IOException
	 */
	public void salva(String fileName) throws IOException {
		// REVIEW vedere come usare il try-with-resources
		FileOutputStream fileStream = new FileOutputStream(fileName);
		ObjectOutputStream objStream = new ObjectOutputStream(fileStream);

		objStream.writeObject(this.C);

		objStream.close();
		fileStream.close();
	}

	public String toString(Data data){
		return C.toString(data);
	}
}
