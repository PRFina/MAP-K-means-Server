package mining;
import data.Data;
import data.Tuple;

public class ClusterSet {
	private Cluster C[];
	private int i;
	
	public ClusterSet(int k){
		C = new Cluster[k];
		int i = 0;
	}
	
	void add(Cluster c) {
		C[i]=c;
		i++;
	}

	Cluster get(int i) {
		return C[i];
	}
	
	void initializeCentroids(Data data){
		int centroidIndexes[]=data.sampling(C.length);
		for(int i=0;i<centroidIndexes.length;i++)
		{
			Tuple centroidI=data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}
	
	/**
	 * 
	 * @param tuple
	 * @return
	 */
	//confrontare con computeCluster in Data, questo è più leggibile!
	Cluster nearestCluster(Tuple tuple) {
		Cluster minCluster = null;
		for(int i = 0 ; i < C.length-1 ; i++ ) {
			double dist1 = tuple.getDistance(C[i].getCentroid());
			double dist2 = tuple.getDistance(C[i+1].getCentroid());
			if(dist1 <= dist2) {
				minCluster = C[i];
			}
		}
		return minCluster;
	}
	
	Cluster currentCluster(int id) {
		for (int i = 0; i < C.length; i++) {
			if(C[i].contain(id)) {
				return C[i];
			}
		}
		return null;
	}
	
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
