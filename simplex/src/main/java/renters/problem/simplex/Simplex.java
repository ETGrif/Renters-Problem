package renters.problem.simplex;

import java.util.Arrays;
import java.util.List;

public class Simplex {

    public final int size;

    Vertex[] majorVerts;
    Vertex[] verts;

    public Simplex(int size) {
        this.size = size;

        int numOfVerts = (size*size + size)/2; // formula for count of verticies
        verts = new Vertex[numOfVerts];
        //create vertecies
        fillVerts();

    }

    private void fillVerts() {
        //loop through the possible indecies
        int i = 0;
        for(int a = 0; a < size; a++){
            for(int b = 0; b < size - a; b++){
                Vertex newVert = new Vertex(a,b);
                verts[i] = newVert;
                i++;
            }
        }
    }

    public List<Vertex> getAllVerts() {
        return Arrays.asList(verts);
    }

    public Vertex getVert(int a, int b) {
        int ind = getIndFromCoords(a,b);
        if(ind == -1) return null;
        return verts[ind];
    }


    //this uses the coords to calculate the index it should be in the array
    //indexes from bootom to top, then left to right
    //returns -1 if out of bounds
    private int getIndFromCoords(int x, int y){
        int ind = (int) (x * (size + .5) - (.5 * x * x) + y);
        if(ind < 0 || ind >= verts.length){
            return -1;
        }
        return ind;
    }
    
}
