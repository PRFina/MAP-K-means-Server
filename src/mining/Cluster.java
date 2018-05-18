package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

public class Cluster implements Serializable {
	private Tuple centroid;

	private Set<Integer> clusteredData;


	Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData=new HashSet<>();
		
	}
		
	Tuple getCentroid(){
		return centroid;
	}
	
	void computeCentroid(Data data){
		for(int i=0;i<centroid.getLength();i++){
			centroid.get(i).update(data,clusteredData);
			
		}
		
	}
	//return true if the tuple is changing cluster
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione � clusterizzata nell'array corrente
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	//remove the tuple that has changed the cluster
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

	public static void main(String[] args){
		Data d = new Data();


		Cluster c = new Cluster(d.getItemSet(2));

		c.addData(0);
		c.addData(2);
		c.addData(12);

		c.computeCentroid(d);



	}

}
