package mst;

public class Prim {
	private Edge[] edgeTo;
	private double[] distTo;
	private boolean[] marked;
	private QuickHeap<WeightedVertex> qh;
	
	public Prim(EdgeWeightedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		qh = new QuickHeap<WeightedVertex>(G.V());
		
		for(int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		
		for(int v = 0; v < G.V(); v++)
			if(!marked[v])
				prim(G, v);
	}

	private void prim(EdgeWeightedGraph G, int s) {
		distTo[s] = 0.0;
		qh.insert(new WeightedVertex(s, distTo[s]));
		while(!qh.isEmpty()){
			WeightedVertex v = qh.extractMin();
			scan(G, v.getV());
		}
		
		
	}

	private void scan(EdgeWeightedGraph G, int v) {
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);
			if (marked[w]) continue;
			if (e.weight() < distTo[w]){
				distTo[w] = e.weight();
				edgeTo[w] = e;
				if (qh.contains(w))
					qh.decreaseKey();
				else
					qh.insert(x);
			}
		}
		
	}

}
