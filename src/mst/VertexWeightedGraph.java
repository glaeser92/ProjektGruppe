package mst;

public class VertexWeightedGraph {
	private final int V;
	private int E;
	private Bag<WeightedVertex>[] adj;

	public VertexWeightedGraph(int V) {
		if (V < 0)
			throw new IllegalArgumentException(
					"Number of Vertices must be positive");
		this.V = V;
		this.E = 0;
		adj = (Bag<WeightedVertex>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<WeightedVertex>();
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(int i, int j, double weight) {
		adj[i].add(new WeightedVertex(j, weight));
		adj[j].add(new WeightedVertex(i, weight));
		E++;
	}
	
	public Iterable<WeightedVertex> adj(int v){
		return adj[v];
	}

}
