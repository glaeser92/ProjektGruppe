package mst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Test {
	
	public static void main(String[] args){
		EdgeWeightedGraph graph;
		File file = new File("Ue11_small.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/mst/Ue11_small.txt"));
			StringTokenizer st;
			int vertices = Integer.parseInt(reader.readLine());
			int edges = Integer.parseInt(reader.readLine());
			String line;
			graph = new EdgeWeightedGraph(vertices);
			while((line = reader.readLine()) != null){
				st = new StringTokenizer(line);
				graph.addEdge(new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken())));
			}
			
			for(Edge e : graph.edges())
				System.out.println(e);
			
			PrimQH prim = new PrimQH(graph);
			System.out.println(prim.weight());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
