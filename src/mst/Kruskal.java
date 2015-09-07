package mst;

import java.util.LinkedList;
import java.util.Queue;

public class Kruskal {
	private double weight;
	private Queue<Edge> mst = new LinkedList<Edge>();
	
	public Kruskal(EdgeWeightedGraph G){
		QuickHeapHashing<Edge> qh = new QuickHeapHashing<Edge>(G.E());
		for(Edge e : G.edges()) {
			qh.insert(e);
		}
		
		UnionFind uf = new UnionFind(G.V());
		while(mst.size() < G.V() - 1) {
			Edge e = qh.extractMin();
			int v = e.either();
			int w = e.other(v);
			if (!uf.connected(v, w)){
				uf.union(v, w);
				mst.add(e);
				weight += e.weight();
			}
		}
	}
	
	public Iterable<Edge> edges() {
		return mst;
	}
	
	public double weight(){
		return weight;
	}
	
}
