package unbalanceLowerCircuit;

import java.util.ArrayList;

/**
 *
 * @author Aristides
 */

public class Main {

    public static void main(String[] args) {
        
        // teste com o grafo mais loco lá do exemplo do professor
        
        final int graph = 2;
        
        Graph g;
        //Graph g = new Graph(new String[]{"0", "1", "2", "3", "4", "5", "6", "7"});
        UnbalanceLowerCircuit ulc = new UnbalanceLowerCircuit();
        ArrayList<ArrayList<Vertex>> unbalanceLowerCircuit;
        String lblS;

        if (graph == 0) {
            g = new Graph(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"});
            g.addEdge(g.vertices.get(0), g.vertices.get(1));
            g.addEdge(g.vertices.get(0), g.vertices.get(8));
            g.addEdge(g.vertices.get(1), g.vertices.get(0));
            g.addEdge(g.vertices.get(1), g.vertices.get(2));
            g.addEdge(g.vertices.get(2), g.vertices.get(1));
            g.addEdge(g.vertices.get(2), g.vertices.get(3));
            g.addEdge(g.vertices.get(3), g.vertices.get(8));
            g.addEdge(g.vertices.get(3), g.vertices.get(2));
            g.addEdge(g.vertices.get(3), g.vertices.get(4));
            g.addEdge(g.vertices.get(4), g.vertices.get(3));
            g.addEdge(g.vertices.get(4), g.vertices.get(5));
            g.addEdge(g.vertices.get(4), g.vertices.get(7));
            g.addEdge(g.vertices.get(5), g.vertices.get(4));
            g.addEdge(g.vertices.get(5), g.vertices.get(6));
            g.addEdge(g.vertices.get(6), g.vertices.get(5));
            g.addEdge(g.vertices.get(6), g.vertices.get(7));
            g.addEdge(g.vertices.get(7), g.vertices.get(4));
            g.addEdge(g.vertices.get(7), g.vertices.get(6));
            g.addEdge(g.vertices.get(7), g.vertices.get(8));
            g.addEdge(g.vertices.get(8), g.vertices.get(7));
            g.addEdge(g.vertices.get(8), g.vertices.get(0));
            g.addEdge(g.vertices.get(8), g.vertices.get(3));
            unbalanceLowerCircuit = ulc.unbalancesOfLowerCircuitWithSAndT(g, g.vertices.get(0), g.vertices.get(5));
            
            if (unbalanceLowerCircuit == null || unbalanceLowerCircuit.isEmpty())
                System.out.println("Não encontrado caminho de s a t...");
            else {
                for (ArrayList<Vertex> arr : unbalanceLowerCircuit) {
                    for (Vertex i : arr)
                        System.out.print(i.label + " ");
                    System.out.print("(" + (arr.size() - 1) + ")\n");
                }
            }
        }
        else if (graph == 1) {
        
            g = new Graph(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
            g.addEdge(g.vertices.get(0), g.vertices.get(1));
            g.addEdge(g.vertices.get(0), g.vertices.get(9));
            g.addEdge(g.vertices.get(1), g.vertices.get(0));
            g.addEdge(g.vertices.get(1), g.vertices.get(2));
            g.addEdge(g.vertices.get(1), g.vertices.get(9));
            g.addEdge(g.vertices.get(1), g.vertices.get(10));
            g.addEdge(g.vertices.get(2), g.vertices.get(1));
            g.addEdge(g.vertices.get(2), g.vertices.get(3));
            g.addEdge(g.vertices.get(2), g.vertices.get(4));
            g.addEdge(g.vertices.get(2), g.vertices.get(10));
            g.addEdge(g.vertices.get(3), g.vertices.get(2));
            g.addEdge(g.vertices.get(3), g.vertices.get(5));
            g.addEdge(g.vertices.get(4), g.vertices.get(2));
            g.addEdge(g.vertices.get(4), g.vertices.get(5));
            g.addEdge(g.vertices.get(5), g.vertices.get(3));
            g.addEdge(g.vertices.get(5), g.vertices.get(4));
            g.addEdge(g.vertices.get(5), g.vertices.get(6));
            g.addEdge(g.vertices.get(5), g.vertices.get(7));
            g.addEdge(g.vertices.get(6), g.vertices.get(5));
            g.addEdge(g.vertices.get(6), g.vertices.get(8));
            g.addEdge(g.vertices.get(7), g.vertices.get(5));
            g.addEdge(g.vertices.get(7), g.vertices.get(8));
            g.addEdge(g.vertices.get(8), g.vertices.get(6));
            g.addEdge(g.vertices.get(8), g.vertices.get(7));
            g.addEdge(g.vertices.get(9), g.vertices.get(0));
            g.addEdge(g.vertices.get(9), g.vertices.get(1));
            g.addEdge(g.vertices.get(10), g.vertices.get(1));
            g.addEdge(g.vertices.get(10), g.vertices.get(2));
            System.out.println(g.toString());
            unbalanceLowerCircuit = ulc.unbalancesOfLowerCircuitWithSAndT(g, g.vertices.get(0), g.vertices.get(8));
            
            if (unbalanceLowerCircuit == null || unbalanceLowerCircuit.isEmpty())
                System.out.println("Não encontrado caminho de s a t...");
            else {
                for (ArrayList<Vertex> arr : unbalanceLowerCircuit) {
                    for (Vertex i : arr)
                        System.out.print(i.label + " ");
                    System.out.print("(" + (arr.size() - 1) + ")\n");
                }
            }
            
        }
        else if (graph == 2) {
        
            g = new Graph(new String[]{"0", "1", "2", "3", "4", "5", "6"});
            g.addEdge(g.vertices.get(0), g.vertices.get(1));
            g.addEdge(g.vertices.get(0), g.vertices.get(2));
            g.addEdge(g.vertices.get(1), g.vertices.get(0));
            g.addEdge(g.vertices.get(1), g.vertices.get(3));
            g.addEdge(g.vertices.get(1), g.vertices.get(5));
            g.addEdge(g.vertices.get(2), g.vertices.get(0));
            g.addEdge(g.vertices.get(2), g.vertices.get(4));
            g.addEdge(g.vertices.get(3), g.vertices.get(1));
            g.addEdge(g.vertices.get(3), g.vertices.get(5));
            g.addEdge(g.vertices.get(4), g.vertices.get(2));
            g.addEdge(g.vertices.get(4), g.vertices.get(5));
            g.addEdge(g.vertices.get(4), g.vertices.get(6));
            g.addEdge(g.vertices.get(5), g.vertices.get(1));
            g.addEdge(g.vertices.get(5), g.vertices.get(3));
            g.addEdge(g.vertices.get(5), g.vertices.get(4));
            g.addEdge(g.vertices.get(5), g.vertices.get(6));
            g.addEdge(g.vertices.get(6), g.vertices.get(5));
            g.addEdge(g.vertices.get(6), g.vertices.get(4));
            
            System.out.println(g.toString());
            
            for (int s = 0; s <= 6; s++) {
                for (int t = 0; t <= 6; t++) {
                    if (s != t) {
                        System.out.println("Verificando " + s + " a " + t + "...");
                        unbalanceLowerCircuit = ulc.unbalancesOfLowerCircuitWithSAndT(g, g.vertices.get(s), g.vertices.get(t));
                        if (unbalanceLowerCircuit == null || unbalanceLowerCircuit.isEmpty())
                            System.out.println("Não encontrado caminho de s a t...");
                        else {
                            for (ArrayList<Vertex> arr : unbalanceLowerCircuit) {
                                for (Vertex i : arr)
                                    System.out.print(i.label + " ");
                                System.out.print("(" + (arr.size() - 1) + ")\n");
                            }
                        }
                    }
                }
            }
        }
        else if (graph == 3) {
        
            g.addEdge(g.vertices.get(0), g.vertices.get(1));
            g.addEdge(g.vertices.get(0), g.vertices.get(6));
            g.addEdge(g.vertices.get(1), g.vertices.get(0));
            g.addEdge(g.vertices.get(1), g.vertices.get(2));
            g.addEdge(g.vertices.get(1), g.vertices.get(4));
            g.addEdge(g.vertices.get(2), g.vertices.get(1));
            g.addEdge(g.vertices.get(2), g.vertices.get(3));
            g.addEdge(g.vertices.get(2), g.vertices.get(7));
            g.addEdge(g.vertices.get(3), g.vertices.get(2));
            g.addEdge(g.vertices.get(3), g.vertices.get(5));
            g.addEdge(g.vertices.get(4), g.vertices.get(1));
            g.addEdge(g.vertices.get(4), g.vertices.get(5));
            g.addEdge(g.vertices.get(5), g.vertices.get(3));
            g.addEdge(g.vertices.get(5), g.vertices.get(4));
            g.addEdge(g.vertices.get(6), g.vertices.get(0));
            g.addEdge(g.vertices.get(6), g.vertices.get(7));
            g.addEdge(g.vertices.get(7), g.vertices.get(6));
            g.addEdge(g.vertices.get(7), g.vertices.get(2));
            
            System.out.println(g.toString());
            unbalanceLowerCircuit = ulc.unbalancesOfLowerCircuitWithSAndT(g, g.vertices.get(0), g.vertices.get(8));
            
            if (unbalanceLowerCircuit == null || unbalanceLowerCircuit.isEmpty())
                System.out.println("Não encontrado caminho de s a t...");
            else {
                for (ArrayList<Vertex> arr : unbalanceLowerCircuit) {
                    for (Vertex i : arr)
                        System.out.print(i.label + " ");
                    System.out.print("(" + (arr.size() - 1) + ")\n");
                }
            }
            
        }
        
    }
    
}