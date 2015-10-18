package balanced.disjoint.paths;

import java.util.ArrayList;

/**
 *
 * @author Aristides
 */
public class Vertex {
    
    // rótulo do vértice
    // para manipular melhor os grafos gerados após remoção de algum vértice
    // o seu label será mantido, enquanto o índice irá mudar, caso seja removido algum vértice anterior
    String label;
    ArrayList<Vertex> listOfAdjacency;
    // indica estado do vértice (não visitado, visitando ou visitado)
    StateColor stateColor;
    // apontamento para o pai
    // (é atribuido ao fazer a busca e é usado depois para fazer o caminho de volta)
    // o vértice de início do caminho é o pai de si próprio
    Vertex parent;
    // inicialmente usado para ordenar as listas de adjacências
    // se deve dar preferência para os nós que já estão no caminho descoberto anteriormente
    int prefer;
    
    Vertex(String label){
        this.label = label;
        this.listOfAdjacency = new ArrayList<>();
        this.stateColor = StateColor.White;
        this.parent = null;
        this.prefer = 0;
    }
    
    public int indexOfNeighbour(String label) {
        if (this.listOfAdjacency == null || this.listOfAdjacency.isEmpty())
            return -1;
        for (int i = 0; i < this.listOfAdjacency.size(); i++)
            if (this.listOfAdjacency.get(i).label.equals(label))
                return i;
        return -1;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("L -> ");
        sb.append(this.label);
        sb.append(" N -> ");
        for (Vertex v : this.listOfAdjacency) {
            sb.append(v.label);
            sb.append(" ");
        }
        sb.append("P -> ");
        sb.append(this.prefer);
        sb.append(" C -> ");
        sb.append(this.stateColor);
        return sb.toString();
    }
}