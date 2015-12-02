/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanced.disjoint.paths;

import com.net2plan.interfaces.networkDesign.IAlgorithm;
import com.net2plan.interfaces.networkDesign.IReport;
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
        
        if(N == 0 || E == 0) throw new Net2PlanException("It must possess Nodes and Link in the topology.");
        
        int nodeInicio, nodeFim;
        
        try {
            nodeInicio = Integer.parseInt(algorithmParameters.get("Begin"));
            nodeFim = Integer.parseInt(algorithmParameters.get("End"));
            System.out.print("testing just a pair\n");
        }
        catch (Exception e) {
            nodeInicio = -1;
            nodeFim = -1;
            System.out.print("testing of all for all\n");
        }
                
        //if(nodeInicio < 0 || nodeFim < 0) throw new Net2PlanException("Os Valores dos parametros precisão ser positivos!");
        
        Graph graph = makeGraph(getLinkGraphNet2Plan(netPlan), netPlan);
        
        ArrayList<int[]> pairs = new ArrayList<>();

        if (nodeInicio + nodeFim == -2) {
            for (Vertex v : graph.vertices)
                for (Vertex u : graph.vertices) {
                    if (!v.label.equals(u.label)) {
                        int pair[] = {Integer.parseInt(v.label), Integer.parseInt(u.label)}; 
                        pairs.add(pair);
                    }
                }
        }
        else {
            int pair[] = {nodeInicio, nodeFim}; 
            pairs.add(pair);
        }
        
        System.out.println("amount pairs: " + pairs.size());
        
        for (int k = 0; k < pairs.size(); k++ ) {
            //Executa algoritimo Balanced Disjoint Paths
            balancedDisjointPaths = bjp(graph, pairs.get(k)[0], pairs.get(k)[1]);

            System.out.println("checking couple " + (int)(k+1) + ": " + pairs.get(k)[0] + " " + pairs.get(k)[1]);
            
            if (balancedDisjointPaths == null || balancedDisjointPaths.isEmpty())
                throw new Net2PlanException("Not Found in path s to t...");
            else {
                System.out.println("paths: " + balancedDisjointPaths.size());
                for (ArrayList<Vertex> arr : balancedDisjointPaths) {
                    for (Vertex i : arr)
                        System.out.print(i.label + " ");
                    System.out.print("(" + (arr.size() - 1) + ")\n");
                }
            }
            System.out.print("\n");
            
        }
        
        return "Ok";
    }

    @Override
    public String getDescription() {
        return "This algorithm finds, if any, two pairs A and B of disjoint paths with each other"
                + " a source of a T sa destination, the length of A being equal to the length B, with the,"
                + " difference in size between the largest paths of the paths B ... If no such pair, the algorithm"
                + " finds only two smaller disjoint paths sa t. Finally, even if there are these two paths, the"
                + " algorithm reports that the network is not 2-edge-connected ...";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {
        List<Triple<String, String, String>> algorithmParameters = new ArrayList<>();
        
        algorithmParameters.add(Triple.of("End", "", "End Node to search"));
        algorithmParameters.add(Triple.of("Begin", "", "Begin Node to search"));
        
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
        for(long i : netPlan.getLinkIdsVector()){
            
            //Retorna o vertice de origem e destino do Link
            Pair<Long,Long> p = netPlan.getLinkOriginDestinationNodePair(i);
            
            g.addEdge(
                g.vertices.get(g.indexOf(p.getFirst().toString())), 
                g.vertices.get(g.indexOf(p.getSecond().toString()))
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