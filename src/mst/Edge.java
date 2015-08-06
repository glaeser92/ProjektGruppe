package mst;

public class Edge implements Comparable<Edge> {
	private final int v;
	private final int w;
	private final double weight;
	private int priority;
	
	private static int timestamp = 0;

	public Edge(int v, int w, double weight) {
		if (v < 0)
			throw new IndexOutOfBoundsException("Vertex name must be positive");
		if (w < 0)
			throw new IndexOutOfBoundsException("Vertex name must be positive");
		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Weight is NaN");
		this.v = v;
		this.w = w;
		this.weight = weight;
		this.priority = timestamp;
		timestamp++;
	}

	public double weight() {
		return weight;
	}

	public int either() {
		return v;
	}
	
	public int priority(){
		return priority;
	}

	public int other(int vertex) {
		if (vertex == v)
			return w;
		if (vertex == w)
			return v;
		else
			throw new IllegalArgumentException("illegal endpoint");
	}

	public int compareTo(Edge that) {
		if (this.weight() < that.weight())
			return -1;
		else if (this.weight() > that.weight)
			return 1;
		else{
			if(this.priority() < that.priority()){
				return -1;
			}
			else{
				return 1;
			}
		}
	}

	public String toString() {
		return String.format("%d-%d %.5f", v, w, weight);
	}
}