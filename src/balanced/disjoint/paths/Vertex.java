/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanced.disjoint.paths;

import java.util.ArrayList;

/**
 *
 * @author Aristides
 */
public class Vertex {
    
    String label;
    ArrayList<Vertex> listOfAdjacency;
    StateColor stateColor;
    Vertex searchParent;
    
    Vertex(String label){
        this.label = label;
        this.listOfAdjacency = new ArrayList<>();
        this.stateColor = StateColor.White;
        this.searchParent = null;
    }
    
}