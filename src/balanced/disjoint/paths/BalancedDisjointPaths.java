package balanced.disjoint.paths;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Aristides
 */
public class BalancedDisjointPaths {

    private Path path = new Path();
    
/*  public ArrayList<Vertex> balancedDisjointPaths(Graph g, Vertex s, Vertex t) {
        
        aqui faremos A função que resolve nosso problema:
        - listar todos os caminhos entre s e t, que utilizem os vértices dos caminhos encontrados
            pela função lowerCycleCommonVertices
        - ordenar estes caminhos por tamanho
        - eliminar os caminhos que repetem aresta (do 2º em diante)
        - verificar se existem p1, p2 e p3 e p4 tais que p1 < p2 <= p3 < p4 e p1 + p4 = p2 + p3, que é o
            caso em que temos dois pares (um mais e outro menos balanceado)
            do contrário, p1 e p2 serão o par mais e menos balanceado
    }
    */
    
    // obtém o menor ciclo que contém s e t, dando preferência para ciclos não hamiltonianos
    public ArrayList<Vertex> lowerCycleCommonVertices(Graph g, Vertex s, Vertex t) {
        
        ArrayList<Vertex> path1, path2;
        
        // encontra o menor caminho (se existir)
        path1 = this.path.shortestPath(s, t);
        
        // marca os vértices já usados para que tenham prioridade nas listas de adjacência
        for (Vertex v : g.vertices)
            v.prefer = path1.indexOf(v) != -1;
        
        // comparator para ordenar as listas de adjacências pelo critério acima
        // OBS: este tipo de expressão (lambda) só está disponível no Java 8
        // mudei a versão do Java do projeto pois não estava conseguindo entender o Comparator das versões
        // anteriores, que necessita precisa ser implementado de forma explícita
        // se alguém quiser mudar isso, pode, contando que continue ordenando certo... hahaha!!!
        Comparator<Vertex> comparator = (v, u) -> {
            return Boolean.compare(u.prefer, v.prefer); 
        };
        
        // remove as arestas do caminho mais curto´
        for (int i = 0; i < path1.size() - 1; i++)
            g.removeEdge(path1.get(i), path1.get(i + 1));
        
        System.out.print(g.toString());
        
        // itera nos vértices para:
        // ordernar as listas de adjacência
        // marcar todos como não visitados
        // remover os pais para que não tenha perigo de utilizar algum pai setado na busca anterior
        // ao fazer a extração do caminho
        for (Vertex v : g.vertices) {
            v.listOfAdjacency.sort(comparator);
            v.stateColor = StateColor.White;
            v.parent = null;
        }
        
        // encontra o segundo menor caminho (se existir)
        path2 = this.path.shortestPath(s, t);
        path1.addAll(path2);
        
        return path1;
        
    }
    
    
}
