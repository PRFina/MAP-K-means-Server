package mining;
import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;

/**
 * This class represents an array of clusters.
 *
 * @author Simone Cicerello
 * @version 1.0
 */
public class ClusterSet implements Serializable {

	private Cluster[] C;
	private int i;

	/**
	 * Constructs an instance of clusterSet.
	 *
	 * @param k array index
	 */
	public ClusterSet(int k){
		this.C = new Cluster[k];
		this.i = 0;
	}

	/**
	 * Add a cluster at the end of the array.
	 *
	 * @param c cluster to be added
	 */
	void add(Cluster c) {
		C[i]=c;
		i++;
	}

	/**
	 * Getter of the cluster by its id.
	 *
	 * @param i index that identify the cluster to be getted
	 * @return cluster looked for
	 */
	Cluster get(int i) {
		return C[i];
	}

	/**
	 * Constructs the first set of centroids, using random function.
	 *
	 * @param data talbe
	 * @throws OutOfRangeSampleSize
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int centroidIndexes[]=data.sampling(C.length);
		for(int i=0;i<centroidIndexes.length;i++)
		{
			Tuple centroidI=data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}
	
	/**
	 * Calculates the nearest cluster to the current tuple.
	 *
	 * @param tuple current tuple
	 * @return nearest cluster
	 */
	Cluster nearestCluster(Tuple tuple) {
		Cluster minCluster = C[0];
		for(int i = 1 ; i < C.length ; i++ ) {
			double dist = tuple.getDistance(C[i].getCentroid());
			double distMinCluster = tuple.getDistance(minCluster.getCentroid());

			if(dist <= distMinCluster) {
				minCluster = C[i];
			}

		}
		return minCluster;
	}

	/**
	 * Represents cluster to be used in other operations like "current".
	 *
	 * @param id
	 * @return null
	 */
	Cluster currentCluster(int id) {
		for (int i = 0; i < C.length; i++) {
			if(C[i].contain(id)) {
				return C[i];
			}
		}
		return null;
	}

	/**
	 * Update all previously calculated centroids.
	 *
	 * @param data table
	 */
	void updateCentroids(Data data) {
		for (int i = 0; i < C.length; i++) {
			C[i].computeCentroid(data);
		}
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < C.length; i++) {
			out += i + ":" + C[i].toString() + System.lineSeparator();
		}
		return out;
	}
	
	public String toString(Data data) {
		String out="";
		for(int i = 0; i < C.length; i++){
			if (C[i]!=null){
				out += i + ":" + C[i].toString(data) + System.lineSeparator();
			}
		}
		return out;
	}
}
