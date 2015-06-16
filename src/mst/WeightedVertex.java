package mst;

public class WeightedVertex implements Comparable<WeightedVertex> {
	
	private final int v;
	private double distance;
	
	public WeightedVertex(int v, double distance){
		this.v = v;
		this.distance = distance;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	public int getV(){
		return v;
	}

	public int compareTo(WeightedVertex that) {
		if (this.getDistance() < that.getDistance())
			return -1;
		else if (this.getDistance() > that.getDistance())
			return 1;
		else
			return 0;
	}

}
