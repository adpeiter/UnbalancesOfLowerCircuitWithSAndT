/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanced.disjoint.paths;

import java.util.ArrayList;

/**
 *
 * @author Aristides
 */
public class BFS {
        
    ArrayList<Vertex> shortestPath(Graph g, Vertex s, Vertex t) {
        
        ArrayList<Vertex> queue = new ArrayList<>();
        
        queue.add(s);
        s.searchParent = s;
        s.stateColor = StateColor.Gray;
        
        while (queue.size() > 0) {
            
            for (Vertex u : queue.get(0).listOfAdjacency) {
                if (u.stateColor == StateColor.White) {
                    u.stateColor = StateColor.Gray;
                    u.searchParent = queue.get(0);
                    queue.add(u);
                    if (u.equals(t))
                        return extractPath(g, s, t);
                }
            }
            
            queue.remove(0);
            
        }
        return null;
    }
    
    private ArrayList<Vertex> extractPath(Graph g, Vertex u, Vertex v) {
        
        ArrayList<Vertex> path = new ArrayList<>();
        
        path.add(v);
        
        while (true) {
            path.add(0, path.get(0).searchParent);
            if (path.get(0).searchParent.equals(path.get(0)))
                return path;
            else if (path.get(0).searchParent == null)
                return null;
        }
    }
    
}