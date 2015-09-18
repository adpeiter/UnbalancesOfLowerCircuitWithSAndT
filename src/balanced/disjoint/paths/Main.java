/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanced.disjoint.paths;

/**
 *
 * @author cleiton
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Graph g = new Graph();
        BFS bfs = new BFS();
        
        g.addEdge((short)0, (short)1);
        g.addEdge((short)0, (short)2);
        g.addEdge((short)1, (short)0);
        g.addEdge((short)1, (short)3);
        g.addEdge((short)1, (short)5);
        g.addEdge((short)2, (short)0);
        g.addEdge((short)2, (short)4);
        g.addEdge((short)3, (short)1);
        //g.addEdge((short)3, (short)5);
        g.addEdge((short)4, (short)2);
        g.addEdge((short)4, (short)5);
        g.addEdge((short)4, (short)6);
        g.addEdge((short)5, (short)1);
        g.addEdge((short)5, (short)3);
        //g.addEdge((short)5, (short)4);
        g.addEdge((short)5, (short)6);
        g.addEdge((short)6, (short)5);
        g.addEdge((short)6, (short)4);
        
        System.out.println(g.toString());
        
        short path[] = bfs.shortestPath(g, (short)3, (short)4);

        for (short i = 0; i < path.length; i++)
            System.out.print(path[i] + " ");
        System.out.println("");
        
    }
    
}