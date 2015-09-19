package balanced.disjoint.paths;

import java.util.ArrayList;

/**
 *
 * @author Aristides
 */

public class Main {

    public static void main(String[] args) {
        
        // teste com o grafo mais loco lá do exemplo do professor
        
        Graph g = new Graph(new String[]{"0", "1", "2", "3", "4", "5", "6"});
        BalancedDisjointPaths bjp = new BalancedDisjointPaths();
        ArrayList<Vertex> lowerCycle;
        String lblS;
        
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
        
        lowerCycle = bjp.lowerCycleCommonVertices(g, g.vertices.get(3), g.vertices.get(4));

        if (lowerCycle == null || lowerCycle.size() == 0)
            System.out.println("Não encontrado caminho de s a t...");
        else {
            lblS = lowerCycle.get(0).label;
            for (Vertex i : lowerCycle) {
                if (i.label.equals(lblS))
                    System.out.print("\n");
                else
                    System.out.print(" ");
                System.out.print(i.label);
            }
            System.out.println("");
        }
    }
    
}