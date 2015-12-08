/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unbalanceLowerCircuit;

import java.util.ArrayList;
import java.lang.Math;
/**
 *
 * @author Aristides
 */
public class Branch {

    Vertex start;
    Vertex end;
    int sizeA;
    int sizeB;
    
    Branch(Vertex start, Vertex end, int sizeA, int sizeB) {
        this.start = start;
        this.end = end;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
    }
    
    @Override
    public String toString() {
        return this.start.label + "-" + this.end.label + " (" + this.sizeA + "," + this.sizeB + ")";
    }
    
}