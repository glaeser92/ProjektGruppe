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
		
		Integer[] arr = {3,8,7,5};
		
		QuickHeap<Integer> qh = new QuickHeap<Integer>(arr, arr.length);
		
		qh.insert((Integer)1);
		
		while(!qh.isEmpty()){
			System.out.println(qh.extractMin());
		}
		
	}
}
