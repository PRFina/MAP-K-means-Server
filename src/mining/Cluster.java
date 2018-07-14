package mining;

import data.*;
import database.DatabaseConnectionException;
import database.DatabaseQueryException;
import database.EmptySetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.sql.SQLException;
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
	 * @param centroid of cluster
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

	/**
	 * Check if a tuple is contained in current clusteredData, through its id.
	 *
	 * @param id tuple id
	 * @return boolean value
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}

	/**
	 * Remove tuple that has changed in clusteredData, indicated by the id.
	 *
	 * @param id tuple id
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
		
	}
	
	public String toString(){

		StringBuilder str = new StringBuilder("Centroid=(");

		for(int i=0;i<centroid.getLength();i++){
			str.append(centroid.get(i));
			str.append(", ");
		}
		str.append(")");

		return str.toString();
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

	/**
	 * Serializes a Cluster instance in JSON object.
	 *
	 * @param data table
	 * @return JSon object
	 */
	JSONObject toJson(Data data){
		JSONObject clusterObj = new JSONObject();

		// Build centroid object
		JSONArray centroid = new JSONArray();
		for (int i = 0; i < this.centroid.getLength(); i++) {
			centroid.add(this.centroid.get(i).toString());
		}
		clusterObj.put("centroid", centroid);


		//Build examples array
		JSONArray examples = new JSONArray();
		for(int i: clusteredData){
			JSONObject example = new JSONObject();

			//Build single example
			JSONArray values = new JSONArray();
			for (int j = 0; j < data.getItemSet(i).getLength(); j++) {
				values.add(data.getItemSet(i).get(j).toString());
			}

			example.put("values", values);
			example.put("distance", this.centroid.getDistance(data.getItemSet(i)));

			examples.add(example);

		}

		clusterObj.put("examples", examples);

		clusterObj.put("avg_distance", this.centroid.avgDistance(data,clusteredData));


		return clusterObj;
	}
}
