package balanced.disjoint.paths;
/**
 *
 * @author Aristides
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Graph {

    public ArrayList<Vertex> vertices;
    
    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public Graph(String labels[]) {
        this.vertices = new ArrayList<>();
        for (String lbl : labels)
            this.vertices.add(new Vertex(lbl));
    }
    
    public Graph(Path fileName) throws FileNotFoundException, IOException {
        ArrayList<String> graph;
        graph = new ArrayList<>(Files.readAllLines((java.nio.file.Path) fileName));
        
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
    
    public int indexOf(String label) {
        if (this.vertices == null || this.vertices.isEmpty())
            return -1;
        for (int i = 0; i < this.vertices.size(); i++)
            if (this.vertices.get(i).label.equals(label))
                return i;
        return -1;
    }
    
    public Graph copyOf() {
        
        Graph gTemp = new Graph();
        Vertex t;
        int ixNeighbour;
        
        for (Vertex c : this.vertices) {
            t = new Vertex(c.label);
            t.parent = c.parent;
            t.stateColor = c.stateColor;
            t.prefer = c.prefer;
            gTemp.vertices.add(t);
        }
        
        for (int i = 0; i < this.vertices.size(); i++) {
            for (Vertex q : this.vertices.get(i).listOfAdjacency) {
                ixNeighbour = gTemp.indexOf(q.label);
                gTemp.vertices.get(i).listOfAdjacency.add(gTemp.vertices.get(ixNeighbour));
            }
        }
        
        return gTemp;
    }
    
}