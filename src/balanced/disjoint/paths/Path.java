package balanced.disjoint.paths;

import java.util.ArrayList;

/**
 *
 * @author Aristides
 */
public class Path {
    
    // calcula o menor caminho de s a t com a busca em largura
    ArrayList<Vertex> shortestPath(Vertex s, Vertex t) {
        
        ArrayList<Vertex> queue = new ArrayList<>();
        
        queue.add(s);
        s.parent = s;
        s.stateColor = StateColor.Gray;
        
        while (queue.size() > 0) {
            
            for (Vertex u : queue.get(0).listOfAdjacency) {
                if (u.stateColor == StateColor.White) {
                    u.stateColor = StateColor.Gray;
                    u.parent = queue.get(0);
                    queue.add(u);
                    if (u.equals(t))
                        return extractPath(t);
                }
            }
            queue.get(0).stateColor = StateColor.Black;
            queue.remove(0);
            
        }
        return null;
    }
    
    // cria um array list com o caminho de s a t, fazendo o percurso "de volta" a partir de t
    // o caminho está completo ao chegar em s, cujo pai na árvore da BFS é o próprio s
    private ArrayList<Vertex> extractPath(Vertex v) {
        
        ArrayList<Vertex> path = new ArrayList<>();
        
        path.add(v);
        
        while (true) {
            path.add(0, path.get(0).parent);
            if (path.get(0).parent.equals(path.get(0)))
                return path;
            else if (path.get(0).parent == null)
                return null;
        }
    }
    
}