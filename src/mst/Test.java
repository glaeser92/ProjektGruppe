package mst;

public class Test {
	
	public static void main(String[] args){
	/*	EdgeWeightedGraph Graph = new EdgeWeightedGraph(4);
		Graph.addEdge(new Edge(0, 1, 10));
		Graph.addEdge(new Edge(0, 2, 6));
		Graph.addEdge(new Edge(0, 3, 5));
		Graph.addEdge(new Edge(2, 3, 4));
		Graph.addEdge(new Edge(1, 3, 15));
		
		
		PrimQH prim = new PrimQH(Graph);
		
		for(Edge e : prim.edges()){
			System.out.println(e.toString());
		}*/
		

		QuickHeap<Integer> qh = new QuickHeap<>(3);
		
		for (int i = 20; i > 0; i-=2) {
			qh.insert(new Integer(i));
		}
		
		for (int i = 0; i < 5; i++) {
			System.out.println(qh.extractMin());
		}
		
		System.out.println("Test");
		
	}
}
