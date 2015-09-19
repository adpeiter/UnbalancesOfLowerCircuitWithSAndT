package balanced.disjoint.paths;
/**
 *
 * @author Aristides
 */

import java.util.ArrayList;

public class Graph implements Cloneable {

    public ArrayList<Vertex> vertices;
    
    public Graph(short nVertices) {
        this.vertices = new ArrayList<>(nVertices);
    }

    public Graph(String labels[]) {
        this.vertices = new ArrayList<>();
        for (String lbl : labels)
            this.vertices.add(new Vertex(lbl));
    }
    
    public void addEdge(Vertex v, Vertex w) {
        if (this.vertices.indexOf(v) >= 0)
            this.vertices.get(this.vertices.indexOf(v)).listOfAdjacency.add(w);
    }
    
    public void removeVertex(Vertex u) {
        for (Vertex x : this.vertices)
            x.listOfAdjacency.remove(u);
        this.vertices.remove(u);
    }
    
    public void removeEdge(Vertex u, Vertex w) {
        if (this.vertices.indexOf(u) >= 0)
            this.vertices.get(this.vertices.indexOf(u)).listOfAdjacency.remove(w);
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