package mining;
import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

public class ClusterSet {
	private Cluster[] C;
	private int i;
	
	public ClusterSet(int k){
		this.C = new Cluster[k];
		this.i = 0;
	}
	
	void add(Cluster c) {
		C[i]=c;
		i++;
	}

	Cluster get(int i) {
		return C[i];
	}
	
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
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
		ClusterSet c = new ClusterSet(3);

		Data trainingSet = new Data();


		c.add(new Cluster(trainingSet.getItemSet(6)));
		c.add(new Cluster(trainingSet.getItemSet(10)));
		c.add(new Cluster(trainingSet.getItemSet(6)));

		System.out.println(c.nearestCluster(trainingSet.getItemSet(0)));
	}

}
