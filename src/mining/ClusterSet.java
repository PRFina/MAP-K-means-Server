package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;
import org.json.simple.JSONArray;

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
	 * Get the clusters number in the cluster-set
	 * @return the clusters number in the cluster-set
	 */
	int getSize(){return i;}

	/**
	 * Constructs the first set of centroids, using random function.
	 *
	 * @param data table
	 * @throws OutOfRangeSampleSize if the number of clusters selected is out of range
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
     * @param id int number that represents the index of current cluster
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
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < C.length; i++) {
			out.append(i);
			out.append(":");
			out.append(C[i].toString());
			out.append(System.lineSeparator());
		}

		return out.toString();
	}
	
	public String toString(Data data) {
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < C.length; i++) {
			out.append(i);
			out.append(":");
			out.append(C[i].toString(data));
			out.append(System.lineSeparator());
		}

		return out.toString();
	}

    /**
     * Serialize an instance of ClusterSet
     *
     * @param data table
     * @return JSON object
     */
    JSONArray toJson(Data data){
		JSONArray clusters = new JSONArray();

		for(int i = 0; i < C.length; i++){
			if (C[i]!=null){
				clusters.add(C[i].toJson(data));
			}
		}

		return clusters;
	}
}
