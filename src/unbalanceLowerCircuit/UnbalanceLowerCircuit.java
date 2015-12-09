package unbalanceLowerCircuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;
/**
 *
 * @author Aristides
 */
public class UnbalanceLowerCircuit {

    private Path path = new Path();
    private ArrayList<Branch> effectiveSolution; 
    
    @SuppressWarnings("empty-statement")
    public ArrayList<ArrayList<Vertex>> unballancesOfLowerCircuitWithSAndT(Graph g, Vertex s, Vertex t) {
             
        ArrayList<ArrayList<Vertex>> paths = new ArrayList<>();
        ArrayList<Vertex> circuit, pathA, pathB;
        ArrayList<Branch> branches = new ArrayList<>();
        Vertex w, u;
        int i, idealUnbalance;
        
        circuit = lowerUndirectedCircuit(g, s, t);
        
        if (circuit == null) return null;
        
        i = circuit.lastIndexOf(circuit.get(0));
        pathA = new ArrayList<>();
        pathB = new ArrayList<>();
        
        for (Vertex a : circuit.subList(0, i)) {
            pathA.add(a);
        }
        for (Vertex b : circuit.subList(i, circuit.size())) {
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
        
            ArrayList<Branch> tempBranches;
            ArrayList<Vertex> pathC, pathD, tempBC, tempBD;
            ArrayList<Branch> solution;
            int ixsPathC, ixsPathD;

            effectiveSolution = new ArrayList<>();;
            solution = new ArrayList<>();
            tempBC = new ArrayList<>();
            tempBD = new ArrayList<>();
            idealUnbalance = (circuit.size() - 2) / 2;

            while (idealUnbalance > 0 && Math.abs(pathA.size() - idealUnbalance - 1) > 0) {

                tempBranches = (ArrayList<Branch>) branches.clone();

                subsetSum(tempBranches, 0, 0, Math.abs(pathA.size() - idealUnbalance - 1), solution);
                
                if (effectiveSolution.size() > 0) {

                    pathC = (ArrayList<Vertex>) pathA.clone();
                    pathD = (ArrayList<Vertex>) pathB.clone();

                    for (Branch b : effectiveSolution) {

                        if (b.sizeA == b.sizeB) continue;
                        
                        ixsPathC = indexOfLabel(pathC, b.start.label);
                        ixsPathD = indexOfLabel(pathD, b.start.label);
                        
                        while (!pathC.get(ixsPathC + 1).label.equals(b.end.label)) {
                            tempBC.add(pathC.get(ixsPathC + 1));
                            pathC.remove(ixsPathC + 1);
                        }                   

                        while (!pathD.get(ixsPathD + 1).label.equals(b.end.label)) {
                            tempBD.add(pathD.get(ixsPathD + 1));
                            pathD.remove(ixsPathD + 1);
                        }
                                                
                        pathC.addAll(ixsPathC + 1, tempBD);
                        pathD.addAll(ixsPathD + 1, tempBC);
                        tempBC.clear();
                        tempBD.clear();
                        
                    }

                    paths.add(pathC);
                    paths.add(pathD);
                    idealUnbalance = 1;

                }
                effectiveSolution.clear();
                idealUnbalance--;

            }
        }
        
        return paths;
        
    }
    
    public ArrayList<Vertex> lowerUndirectedCircuit(Graph g, Vertex s, Vertex t) {

        Graph gTemp;
        ArrayList<Vertex> circuit;
        ArrayList<Vertex[]> commonEdges = new ArrayList<>();
        int ixPath2, ixVertexX, ixVertexR, pvceSize;
        Vertex x, r;

        pvceSize = 0;

        while (true) {
            
            gTemp = g.copyOf();
            ixVertexX = gTemp.indexOf(s.label);
            if (ixVertexX == -1) {
                return null;
            }
            ixVertexR = gTemp.indexOf(t.label);
            if (ixVertexR == -1) {
                return null;
            }
            x = gTemp.vertices.get(ixVertexX);
            r = gTemp.vertices.get(ixVertexR);

            for (Vertex ce[] : commonEdges) {
                ixVertexX = gTemp.indexOf(ce[0].label);
                ixVertexR = gTemp.vertices.get(ixVertexX).indexOfNeighbour(ce[1].label);
                gTemp.vertices.get(ixVertexX).listOfAdjacency.remove(ixVertexR);
            }

            circuit = lowerCircuit(gTemp, x, r);
            ixPath2 = circuit.lastIndexOf(x);

            for (int i = 0; i < ixPath2 - 1; i++) {
                for (int j = ixPath2; j < circuit.size() - 1; j++) {
                    if (circuit.get(i).label.equals(circuit.get(j + 1).label)) {
                        if (circuit.get(i + 1).label.equals(circuit.get(j).label)) {
                            commonEdges.add(new Vertex[]{circuit.get(i), circuit.get(i + 1)});
                            commonEdges.add(new Vertex[]{circuit.get(i + 1), circuit.get(i)});
                        }
                    }
                }
            }
            if (commonEdges.size() == pvceSize) {
                break;
            }
            pvceSize = commonEdges.size();
        }

        return circuit;

    }

    public ArrayList<Vertex> lowerCircuit(Graph g, Vertex s, Vertex t) {

        ArrayList<Vertex> circuit;

        circuit = this.path.shortestPath(s, t);

        if (circuit == null) {
            return null;
        }

        for (int i = 0; i < circuit.size() - 1; i++) {
            g.removeEdge(circuit.get(i), circuit.get(i + 1));
        }

        for (Vertex x : circuit) {
            for (Vertex y : x.listOfAdjacency) {
                y.prefer++;
            }
        }

        for (Vertex v : g.vertices) {

            Collections.sort(v.listOfAdjacency, new Comparator<Vertex>() {
                @Override
                public int compare(Vertex a, Vertex b) {
                    return Integer.compare(b.prefer, a.prefer);
                }
            });
            v.stateColor = StateColor.White;
            v.parent = null;
        }

        circuit.addAll(this.path.shortestPath(s, t));

        return circuit;

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
       
        solution.add(A.get(index));
	currSum += Math.abs(A.get(index).sizeA - A.get(index).sizeB);
	subsetSum(A, currSum, index + 1, sum, solution);
        solution.remove(A.get(index));
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
