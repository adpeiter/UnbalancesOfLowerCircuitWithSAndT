/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unbalanceLowerCircuit;

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
        String s, t; 
        ArrayList<ArrayList<Vertex>> balancedDisjointPaths;
        
        N = netPlan.getNumberOfNodes();
        E = netPlan.getNumberOfLinks();
        
        if(N == 0 || E == 0) throw new Net2PlanException("It must possess Nodes and Link in the topology.");
        
        int nodeStart, nodeEnd;
        
        nodeStart = 0;
        nodeEnd = 0;
        t = algorithmParameters.get("End");
        s = algorithmParameters.get("Begin");
            
        try {
            
            if (s.equals("") && t.equals("")) {
                nodeStart = -1;
                nodeEnd = -1;
                System.out.print("testing of all for all\n");
            }
            else if (s.matches("\\d+") == false || t.matches("\\d+") == false) {
               return "input numeric values or leave blank\n";
            }
            else {
                nodeStart = Integer.parseInt(s);
                nodeEnd = Integer.parseInt(t);
                System.out.print("testing just a pair\n");
            }
        }
        catch (Exception e) {
            return "Fail: don't can run...\n";
        }
        
        Graph graph = makeGraph(getLinkGraphNet2Plan(netPlan), netPlan);
        
        ArrayList<int[]> pairs = new ArrayList<>();

        if (nodeStart + nodeEnd == -2) {
            
            for (Vertex v : graph.vertices)
                for (Vertex u : graph.vertices.subList(graph.indexOf(v.label) + 1, graph.vertices.size() - 1)) {
                    if (!v.label.equals(u.label)) {
                        int pair[] = {Integer.parseInt(v.label), Integer.parseInt(u.label)}; 
                        pairs.add(pair);
                    }
                }
        }
        else {
            if (graph.indexOf(s) < 0 || graph.indexOf(t) < 0) {
                return "Choose a exsisting node...\n";
            }
            int pair[] = {nodeStart, nodeEnd}; 
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
        UnbalanceLowerCircuit ulc = new UnbalanceLowerCircuit();
        
        balancedDisjointPaths = ulc.unbalancesOfLowerCircuitWithSAndT(graph, graph.vertices.get(inicio), graph.vertices.get(fim));
        
        return balancedDisjointPaths;
    }    
}