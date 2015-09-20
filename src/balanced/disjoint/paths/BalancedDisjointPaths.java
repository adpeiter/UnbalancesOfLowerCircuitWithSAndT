package balanced.disjoint.paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Aristides
 */
public class BalancedDisjointPaths {

    private Path path = new Path();
    
    public ArrayList<Vertex> balancedDisjointPaths(Graph g, Vertex s, Vertex t) {
        /*
        aqui faremos A função que resolve nosso problema:
        - listar todos os caminhos entre s e t, que utilizem os vértices dos caminhos encontrados
            pela função lowerCycleEdgeDisjoint
        - ordenar estes caminhos por tamanho
        - eliminar os caminhos que repetem aresta (do 2º em diante)
        - verificar se existem p1, p2 e p3 e p4 tais que p1 < p2 <= p3 < p4 e p1 + p4 = p2 + p3, que é o
            caso em que temos dois pares (um mais e outro menos balanceado)
            do contrário, p1 e p2 serão o par mais e menos balanceado
        */
        return null;
    }
    
    public ArrayList<Vertex> lowerCycleEdgeDisjoint(Graph g, Vertex s, Vertex t) {
    
        // função que busca dois caminhos disjuntos por aresta
        
        Graph gTemp;
        ArrayList<Vertex> cycle;
        ArrayList<Vertex[]> commonEdges = new ArrayList<>();
        int ixPath2, ixVertexX, ixVertexR;
        Vertex x, r;

        while (true) {
            
            gTemp = g.copyOf(); // copia o grafo
            ixVertexX = gTemp.indexOf(s.label); // obtém o índice do vértice correspondente a s no novo grafo
            if (ixVertexX == -1) return null;
            ixVertexR = gTemp.indexOf(t.label); // obtém o índice do vértice correspondente a t no novo grafo
            if (ixVertexR == -1) return null;
            x = gTemp.vertices.get(ixVertexX); // obtém o vértice correspondente a s no novo grafo
            r = gTemp.vertices.get(ixVertexR); // obtém o vértice correspondente a t no novo grafo

            // remove as arestas com extremidades em comum, encontradas na iteração anterior
            for (Vertex ce[] : commonEdges) {
                ixVertexX = gTemp.indexOf(ce[0].label);
                ixVertexR = gTemp.vertices.get(ixVertexX).indexOfNeighbour(ce[1].label);
                gTemp.vertices.get(ixVertexX).listOfAdjacency.remove(ixVertexR);
            }

            commonEdges.clear();
            
            // calcula o menor ciclo com x e r
            cycle = lowerCycle(gTemp, x, r);
            // obtém o índice onde começa o segundo caminho
            ixPath2 = cycle.lastIndexOf(x);

            // compara as arestas do primeiro e do segundo caminho para verificar se existe
            // arestas com extremidades em comum
            // se sim, adiciona-as em commonEdges para removê-las do grafo na próxima iteração
            for (int i = 0; i < ixPath2 - 1; i++) {
                for (int j = ixPath2; j < cycle.size() - 1; j++) {
                    if (cycle.get(i).label.equals(cycle.get(j + 1).label)) {
                        if (cycle.get(i + 1).label.equals(cycle.get(j).label)) {
                            commonEdges.add(new Vertex[]{cycle.get(i), cycle.get(i + 1)});
                            commonEdges.add(new Vertex[]{cycle.get(i + 1), cycle.get(i)});
                        }
                    }
                }
            }
            if (commonEdges.isEmpty()) // significa que não há mais arestas com extremidades em comum
               break;
        }
        
        return cycle;
        
    }
    
    // obtém o menor ciclo que contém s e t, dando preferência para ciclos não hamiltonianos
    public ArrayList<Vertex> lowerCycle(Graph g, Vertex s, Vertex t) {
        
        ArrayList<Vertex> cycle;
        
        // encontra o menor caminho (se existir)
        cycle = this.path.shortestPath(s, t);
        
        if (cycle == null)
            return null;
        
        // marca os vértices já usados para que tenham prioridade nas listas de adjacência
        for (Vertex v : g.vertices)
            v.prefer = cycle.indexOf(v) != -1;
        
        // remove as arestas do caminho mais curto´
        for (int i = 0; i < cycle.size() - 1; i++)
            g.removeEdge(cycle.get(i), cycle.get(i + 1));
        
        // itera nos vértices para:
        // ordernar as listas de adjacência
        // marcar todos como não visitados
        // remover os pais para que não tenha perigo de utilizar algum pai setado na busca anterior
        // ao fazer a extração do caminho
        for (Vertex v : g.vertices) {
            // ordena as listas de adjacências pelo critério prefer (vértices já utilizados no caminho
            // anterior vão para o início
            Collections.sort(v.listOfAdjacency, new Comparator<Vertex>() {
                @Override
                public int compare(Vertex  a, Vertex b) {
                    return Boolean.compare(b.prefer, a.prefer);
                }
            });
            v.stateColor = StateColor.White;
            v.parent = null;
        }
        
        // encontra o segundo menor caminho (se existir)
        cycle.addAll(this.path.shortestPath(s, t));
        
        return cycle;
        
    }
}
