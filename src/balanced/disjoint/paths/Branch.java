/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balanced.disjoint.paths;

/**
 *
 * @author Aristides
 */
public class Branch {

    Vertex start;
    Vertex end;
    int size;
    
    Branch(Vertex start, Vertex end, int size) {
        this.start = start;
        this.end = end;
        this.size = size;
    }
    
}