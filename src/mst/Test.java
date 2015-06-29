package mst;

import java.util.Hashtable;
import java.util.PriorityQueue;

public class Test {
	
	public static void main(String[] args){
//		EdgeWeightedGraph Graph = new EdgeWeightedGraph(4);
//		Graph.addEdge(new Edge(0, 1, 10));
//		Graph.addEdge(new Edge(0, 2, 6));
//		Graph.addEdge(new Edge(0, 3, 5));
//		Graph.addEdge(new Edge(2, 3, 4));
//		Graph.addEdge(new Edge(1, 3, 15));
//		
//		long startTime = System.nanoTime();
//		
//		Prim prim = new Prim(Graph);
//		
//		long endTime = System.nanoTime();
//		
//		for(Edge e : prim.edges()){
//			System.out.println(e.toString());
//		}
//		
//		System.out.println("Time: " + (endTime - startTime));
//		
		QuickHeap<Integer> qh = new QuickHeap<>(5);
		
		for (int i = 0; i < 7; i++) {
			qh.insert(new Integer(i));
		}
		
		for(int i = 0; i < 7; i++){
			System.out.println(qh.extractMin());
		}
		
	}
}
