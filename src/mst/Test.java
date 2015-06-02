package mst;

import java.util.Hashtable;

public class Test {
	
	public static void main(String[] args){
//		EdgeWeightedGraph Graph = new EdgeWeightedGraph(4);
//		Graph.addEdge(new Edge(0, 1, 10));
//		Graph.addEdge(new Edge(0, 2, 6));
//		Graph.addEdge(new Edge(0, 3, 5));
//		Graph.addEdge(new Edge(2, 3, 4));
//		Graph.addEdge(new Edge(1, 3, 15));
//		
//		Kruskal kruskal = new Kruskal(Graph);
//		
//		System.out.println(kruskal.weight());
		
		Edge[] edges = new Edge[3];
		edges[0] = new Edge(0, 1, 5);
		edges[1] = new Edge(0, 2, 3);
		edges[2] = new Edge(1, 2, 6);

		Hashtable<Integer, Edge> ht = new Hashtable<Integer, Edge>();
		ht.put(5, edges[0]);
		ht.put(3, edges[1]);
		ht.put(6, edges[2]);
		
		System.out.println(ht.get(10));
		

	}
}
