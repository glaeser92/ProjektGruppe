package mst;

import java.util.LinkedList;
import java.util.Queue;

public class PrimQH {
	private Edge[] edgeTo;
	private WeightedVertex[] distTo;
	private boolean[] marked;
	private BinaryHeap<WeightedVertex> bh;

	public PrimQH(EdgeWeightedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new WeightedVertex[G.V()];
		marked = new boolean[G.V()];
		bh = new BinaryHeap<WeightedVertex>(G.V());

		for (int v = 0; v < G.V(); v++)
			distTo[v] = new WeightedVertex(v, Double.POSITIVE_INFINITY);

		for (int v = 0; v < G.V(); v++)
			if (!marked[v])
				prim(G, v);
	}

	private void prim(EdgeWeightedGraph G, int s) {
		distTo[s].setDistance(0.0);
		bh.insert(distTo[s]);
		while (!bh.isEmpty()) {
			WeightedVertex v = bh.delMin();
			scan(G, v.getV());
		}

	}

	private void scan(EdgeWeightedGraph G, int v) {
		WeightedVertex temp;
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);
			if (marked[w])
				continue;
			if (e.weight() < distTo[w].getDistance()) {
				temp = new WeightedVertex(distTo[w].getV(), e.weight());
				distTo[w].setDistance(e.weight());
				edgeTo[w] = e;
				if (bh.contains(distTo[w]))
					bh.decreaseKey(distTo[w], temp);
				else
					bh.insert(temp);
			}
		}
	}
	
	public Iterable<Edge> edges(){
		Queue<Edge> mst = new LinkedList<Edge>();
		for(int v = 0; v < edgeTo.length; v++){
			Edge e = edgeTo[v];
			if(e != null){
				mst.add(e);
			}
		}
		return mst;
	}
	
	public double weight(){
		double weight = 0.0;
		for (Edge e : edges())
			weight += e.weight();
		return weight;
	}

}
