package mst;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Test {

    public static void main(String[] args) {
//        EdgeWeightedGraph graph;
//
//        QuickHeap<Edge> qh;
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("src/mst/Instanz_2"));
//            StringTokenizer st;
//            int vertices = Integer.parseInt(reader.readLine());
//            int edges = Integer.parseInt(reader.readLine());
//
//            String line;
//            graph = new EdgeWeightedGraph(vertices);
//            while ((line = reader.readLine()) != null) {
//                st = new StringTokenizer(line);
//                graph.addEdge(new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken())));
//            }
//
//            PrimBH primB = new PrimBH(graph);
//            System.out.println(primB.weight());
//            PrimQH primQ = new PrimQH(graph);
//            System.out.println(primQ.weight());
//
//            System.out.println("Läuft");
//
//        } catch (FileNotFoundException e) {
//        } catch (NumberFormatException | IOException e) {
//        }
        
//        QuickHeap<Integer> qh = new QuickHeap<>(5);
//        
//        for (int i = 0; i < 10; i+=2) {
//            qh.insert(i);
//        }
//        
//        for (int i = 0; i < 5; i++) {
//            System.out.println(qh.extractMin());
//        }
//        
//        qh.insert(20);
//        qh.insert(30);
//        qh.insert(40);
//        qh.insert(50);
//        qh.insert(60);
//        
//        qh.decreaseKey(20, 15);
//       
//        System.out.println("läuft");

        EdgeWeightedGraph graph = new EdgeWeightedGraph(9);
        graph.addEdge(new Edge(0, 1, 4));
        graph.addEdge(new Edge(0, 7, 8));
        graph.addEdge(new Edge(1, 2, 8));
        graph.addEdge(new Edge(1, 7, 11));
        graph.addEdge(new Edge(2, 3, 7));
        graph.addEdge(new Edge(2, 8, 2));
        graph.addEdge(new Edge(2, 5, 4));
        graph.addEdge(new Edge(3, 4, 9));
        graph.addEdge(new Edge(3, 5, 14));
        graph.addEdge(new Edge(4, 5, 10));
        graph.addEdge(new Edge(5, 6, 2));
        graph.addEdge(new Edge(6, 7, 1));
        graph.addEdge(new Edge(6, 8, 6));
        graph.addEdge(new Edge(7, 8, 7));
        
        PrimBH primb = new PrimBH(graph);
        PrimQH primq = new PrimQH(graph);
        
        System.out.println(primb.weight());
        System.out.println(primq.weight());
        
        System.out.println("Läuft");
    }

}
