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

import java.util.ArrayList;

public class Graph implements Cloneable {

    public ArrayList<Vertex> vertices;
    
    public Graph(short nVertices) {
        vertices = new ArrayList<>(nVertices);
    }

    public Graph(string labels[]) {
        vertices = new ArrayList<>(nVertices);
    }
    
    public void addEdge(Vertex v, Vertex w) {
        if (this.vertices.indexOf(v) >= 0)
            this.vertices.get(this.vertices.indexOf(v)).listOfAdjacency.add(w);
    }
    
    public void removeVertex(Vertex u) {
        for (Vertex x : this.vertices)
            x.listOfAdjacency.remove(u);
    }
    
    public void removeEdge(Vertex u, Vertex w) {
        if (this.vertices.indexOf(u) >= 0)
            this.vertices.get(this.vertices.indexOf(u)).listOfAdjacency.remove(w);
        if (this.vertices.indexOf(w) >= 0)
            this.vertices.get(this.vertices.indexOf(w)).listOfAdjacency.remove(u);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for (Vertex v : this.vertices) {
            sb.append(v.label).append(" -> ");
            for (Vertex w : v.listOfAdjacency)
                sb.append(w.label).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
    
}