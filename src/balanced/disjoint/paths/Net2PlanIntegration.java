/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanced.disjoint.paths;

import com.net2plan.interfaces.networkDesign.IAlgorithm;
import com.net2plan.interfaces.networkDesign.Net2PlanException;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Cleiton
 */

public class Net2PlanIntegration implements IAlgorithm{

    @Override
    public String executeAlgorithm(NetPlan netPlan, Map<String, String> algorithmParameters, Map<String, String> net2planParameters) {
        final int N, E;
        ArrayList<ArrayList<Vertex>> balancedDisjointPaths;
        
        N = netPlan.getNumberOfNodes();
        E = netPlan.getNumberOfLinks();
        
        if(N == 0 || E == 0) throw new Net2PlanException("É preciso possuir Nodes e Link na sua topologia.");
        
        final int nodeInicio = Integer.parseInt(algorithmParameters.get("Inicio"));
        final int nodeFim    = Integer.parseInt(algorithmParameters.get("Fim"));
        
        if(nodeInicio < 0 || nodeFim < 0) throw new Net2PlanException("Os Valores dos parametros precisão ser positivos!");
        
        Graph graph = makeGraph(getLinkGraphNet2Plan(netPlan), netPlan);
        
        //Executa algoritimo Balanced Disjoint Paths
        balancedDisjointPaths = bjp(graph, nodeInicio, nodeFim);
        
        if (balancedDisjointPaths == null || balancedDisjointPaths.isEmpty())
            throw new Net2PlanException("Não encontrado caminho do Vertice de Inicio a Fim");
        else {
            for (ArrayList<Vertex> arr : balancedDisjointPaths) {
                for (Vertex i : arr)
                    System.out.print(i.label + " ");
                System.out.print("(" + (arr.size() - 1) + ")\n");
            }
        }
        
        return "OK";
    }

    @Override
    public String getDescription() {
        return "Criar alguma descricao...";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {
        List<Triple<String, String, String>> algorithmParameters = new ArrayList<>();
        
        algorithmParameters.add(Triple.of("Fim", "", "Node de Fim para fazer a busca"));
        algorithmParameters.add(Triple.of("Inicio", "", "Node de Inicio para fazer a busca"));
        
        return algorithmParameters;        
    }
    
    private String[] getLinkGraphNet2Plan(NetPlan netPlan){
        
        long[] nodesId = netPlan.getNodeIdsVector();
        String[] nodes = new String[nodesId.length];
        
        for(int i = 0; i < nodesId.length; i++){
            nodes[i] = String.valueOf(nodesId[i]);
        }
        return nodes;
    }
    
    private Graph makeGraph(String[] nodes, NetPlan netPlan){
        Graph g = new Graph(nodes);
        
        //Para cada  link do net2plan é criado arestas do grafo
        for(int i = 0; i < netPlan.getNumberOfLinks(); i++){
            
            //Retorna o vertice de origem e destino do Link
            Pair<Long,Long> p = netPlan.getLinkOriginDestinationNodePair(i);
            
            g.addEdge(
                g.vertices.get(Integer.valueOf(p.getFirst().toString() )), 
                g.vertices.get(Integer.valueOf(p.getSecond().toString() ))
            );
        }
        
        return g;
    }
    
    private ArrayList<ArrayList<Vertex>> bjp(Graph graph, int inicio, int fim){
        ArrayList<ArrayList<Vertex>> balancedDisjointPaths;
        BalancedDisjointPaths bjp = new BalancedDisjointPaths();
        
        balancedDisjointPaths = bjp.balancedDisjointPaths(graph, graph.vertices.get(inicio), graph.vertices.get(fim));
        
        return balancedDisjointPaths;
    }    
}