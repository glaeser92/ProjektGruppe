package mst;

public class WeightedVertex {
	
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

}
