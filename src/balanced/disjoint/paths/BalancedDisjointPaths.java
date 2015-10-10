package balanced.disjoint.paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;
/**
 *
 * @author Aristides
 */
public class BalancedDisjointPaths {

    private Path path = new Path();
    private ArrayList<Branch> effectiveSolution; 
    
    @SuppressWarnings("empty-statement")
    public ArrayList<ArrayList<Vertex>> balancedDisjointPaths(Graph g, Vertex s, Vertex t) {
        
        ArrayList<ArrayList<Vertex>> paths = new ArrayList<>();
        ArrayList<Vertex> cycle, pathA, pathB;
        ArrayList<Branch> branches = new ArrayList<>();
        Vertex w, u;
        int i, idealBallance;
        
        cycle = lowerCycleEdgeDisjoint(g, s, t);
        
        /*  depois de calcular o menor ciclo (menor par de caminhos)
            a ideia é encontrar desvios, se existirem
            achar um vértice comum e percorrer os dois caminhos até a confluência num novo vértice comum
            sempre haverá pelo menos 1 desvio, com origem em s e destino em, portanto, nosso interesse
            está em encontrar desvios que envolvam vértices intermediários 
            depois, temos que ver de todos os desvios quais são os mais interessantes
            para alcançarmos o maior balanceamento
            se houver um grupo de desvios cuja soma seja igual ao dobro do desbalanceamento dos caminhos
            encontrados, basta fazer o caminho mais curto usar estes desvios, e o caminho mais longo usar as
            ligações antes utilizadas pelo caminho mais curto
            EX: se tivermos um par (a, b) com #A(a) = 10 e #A(b) = 5 e tivermos um desvio 2,
            fazendo o caminho a utilizar este desvio e dando ao caminho b as arestas que eram utilizadas
            por a, teremos #A(a) = 7 e #A(b) = 8, ou seja, o maior balanceamento possível
        */
        i = cycle.lastIndexOf(cycle.get(0));
        pathA = new ArrayList<>();
        pathB = new ArrayList<>();
        
        for (Vertex a : cycle.subList(0, i)) {
            pathA.add(a);
        }
        for (Vertex b : cycle.subList(i, cycle.size())) {
            pathB.add(b);
        }
        
        paths.add(pathA);
        paths.add(pathB);
        
        w = pathA.get(0);
        for (i = 1; i < pathA.size(); i++) {
            u = pathA.get(i);
            if (pathB.indexOf(u) > -1) {
                branches.add(new Branch(w, u, Math.abs(pathA.indexOf(w) - pathA.indexOf(u)), Math.abs(pathB.indexOf(w) - pathB.indexOf(u))));
                w = u;
            }
        }
        
        if (branches.size() > 1) {
        
            // aqui começa a confusão
            ArrayList<Branch> tempBranches;
            ArrayList<Vertex> pathC, pathD, tempBC, tempBD;
            ArrayList<Branch> solution;
            int bsPC, bePC, bsPD, bePD;

            effectiveSolution = new ArrayList<>();;
            solution = new ArrayList<>();
            tempBranches = new ArrayList<>();
            tempBC = new ArrayList<>();
            tempBD = new ArrayList<>();
            idealBallance = (cycle.size() - 2) / 2;

            while (idealBallance > 0) {

                tempBranches = (ArrayList<Branch>) branches.clone();

                // fazer o subset sum nos branches
                // o Zazá n te deixa nem nas outras matérias :(

                subsetSum(tempBranches, 0, 0, Math.abs(pathA.size() - idealBallance - 1), solution);
                if (effectiveSolution.size() > 0) {

                    pathC = (ArrayList<Vertex>) pathA.clone();
                    pathD = (ArrayList<Vertex>) pathB.clone();

                    // fazer as trocas entre os caminhos
                    for (Branch b : effectiveSolution) {

                        bsPC = indexOfLabel(pathC, b.start.label);
                        bsPD = indexOfLabel(pathD, b.start.label);
                        bePC = indexOfLabel(pathC, b.end.label);
                        bePD = indexOfLabel(pathD, b.end.label);    

                        for (i = bsPC + 1; i < bePC; i++) {
                            tempBC.add(pathC.get(i));
                            pathC.remove(i);
                        }
                        for (i = bsPD + 1; i < bePD; i++) {
                            tempBD.add(pathD.get(i));
                            pathD.remove(i);

                        }
                        pathC.addAll(bsPC + 1, tempBD);
                        pathD.addAll(bsPD + 1, tempBC);                    
                    }

                    paths.add(pathC);
                    paths.add(pathD);
                    idealBallance = 0;

                }
                effectiveSolution.clear();
                idealBallance--;

            }
        }
        
        return paths;
        
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
    
    private void subsetSum(
        ArrayList<Branch> A, int currSum, int index, int sum,
        ArrayList<Branch> solution
    ) {
	if (effectiveSolution.size() > 0)
            return;
        
        if (solution == null)
            solution = new ArrayList<>();
        
        if (currSum == sum) {
            effectiveSolution = (ArrayList<Branch>) solution.clone();
            return;
        }

        if (index == A.size()) {
            solution.clear();
            effectiveSolution.clear();
            return;
        }
        
        solution.add(A.get(index));// select the element
	currSum += Math.abs(A.get(index).sizeA - A.get(index).sizeB);
	subsetSum(A, currSum, index + 1, sum, solution);
        solution.remove(A.get(index));// do not select the element
	currSum -= Math.abs(A.get(index).sizeA - A.get(index).sizeB);
	subsetSum(A, currSum, index + 1, sum, solution);
	
    }
    
    int indexOfLabel(ArrayList<Vertex> a, String label) {
        
        for (int i = 0; i < a.size(); i++)
            if (a.get(i).label.equals(label))
                return i;
        
        return -1;
    }

}
