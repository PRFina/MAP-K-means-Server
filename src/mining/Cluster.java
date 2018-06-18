package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

/**
 * This class models the cluster entity, represented by centroid and a set of related (nearest) tuples.
 *
 * @author Simone Cicerello
 * @version 1.0
 *
 */
public class Cluster implements Serializable {

	private Tuple centroid;
	private Set<Integer> clusteredData;

	/**
	 * Constructs an instance of cluster.
	 *
	 * @param centroid
	 */
	Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData=new HashSet<>();
	}

	/**
	 * Getter of the centroid.
	 *
	 * @return centroid
	 */
	Tuple getCentroid(){
		return centroid;
	}

	/**
	 * Calculate new centroids from tuples of table.
	 *
	 * @param data table
	 */
	void computeCentroid(Data data){
		for(int i=0;i<centroid.getLength();i++){
			centroid.get(i).update(data,clusteredData);
		}
	}

	/**
	 * Check if a tuple has changed, return true if its is changed, false otherwise.
	 *
	 * @param id tuple id
	 * @return boolean value
	 */
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione � clusterizzata nell'array corrente

	/**
	 * Check if a tuple is contained in current clusteredData, through its id.
	 *
	 * @param id tuple id
	 * @return boolean value
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	//remove the tuple that has changed the cluster

	/**
	 * Remove tuple that has changed in clusteredData, indicated by the id.
	 *
	 * @param id tuple id
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
		
	}
	
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+", ";
		str+=")";
		return str;
	}
	

	
	public String toString(Data data){

		StringBuilder out = new StringBuilder("Centroid = ");
		out.append(this.centroid.toString());
		out.append("\nExamples:\n");

		for(int i: clusteredData){
			out.append(data.getItemSet(i).toString());
			out.append(" dist = "+this.centroid.getDistance(data.getItemSet(i))+"\n");
		}

		out.append("-----AvgDistance="+this.centroid.avgDistance(data, clusteredData)+"-----\n\n");

		return out.toString();
	}
}
