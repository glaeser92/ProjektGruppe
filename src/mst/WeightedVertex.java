package mst;

public class WeightedVertex {
	
	private final int v;
	private double weight;
	
	public WeightedVertex(int v, double weight){
		this.v = v;
		this.weight = weight;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	public int getV(){
		return v;
	}

}
