package renters.problem.simplex;

import java.util.Arrays;
import java.util.List;

public class Simplex {

    public final int size;

    Vertex[] majorVerts;
    Vertex[] verts;

    Subsimplex[] subsimplexes;

    public Simplex(int size) {
        this.size = size;

        // create vertices
        int numOfVerts = (size * size + size) / 2; // formula for count of verticies
        verts = new Vertex[numOfVerts];
        fillVerts();

        // create subsimplexes
        if (size > 2) { // n = 2 & n = 1 both dont need subdivisions
            int numOfSubsimplexes = (size - 1) * (size - 1);
            subsimplexes = new Subsimplex[numOfSubsimplexes];
            fillSubsimplexes();
            setAdjacencies();
        }

    }

    private void fillSubsimplexes() {
        // loop through possible indecies
        int i = 0;
        for (int a = 0; a <= size - 2; a++) {
            for (int b = 0; b <= 2 * (size - 2 - a); b++) {
                // get the vertex objects, and add them
                Vertex v1;
                Vertex v2;
                Vertex v3;

                // if b is even
                if (b % 2 == 0) {
                    v1 = getVert(a, b / 2);
                    v2 = getVert(a, b / 2 + 1);
                    v3 = getVert(a + 1, b / 2);
                } else {
                    // if b is odd
                    v1 = getVert(a, (b + 1) / 2);
                    v2 = getVert(a + 1, (b + 1) / 2);
                    v3 = getVert(a + 1, (b - 1) / 2);
                }

                Subsimplex simplex = new Subsimplex(a, b, v1, v2, v3);
                subsimplexes[i] = simplex;
                i++;
            }
        }

    }

    private void fillVerts() {
        // loop through the possible indecies
        int i = 0;
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size - a; b++) {
                Vertex newVert = new Vertex(a, b);
                verts[i] = newVert;
                i++;
            }
        }
    }

    public List<Vertex> getAllVerts() {
        return Arrays.asList(verts);
    }

    public Vertex getVert(int a, int b) {
        int ind = getVertIndFromCoords2D(a, b);
        if (ind == -1)
            return null;
        return verts[ind];
    }

    // this uses the coords to calculate the index it should be in the array
    // indexes from bootom to top, then left to right
    // returns -1 if out of bounds
    private int getVertIndFromCoords2D(int x, int y) {
        if (x < 0 || x > size)
            return -1;
        if (y < 0 || y > size - x -1)
            return -1;

        int ind = (int) (x * (size + .5) - (.5 * x * x) + y);
        if (ind < 0 || ind >= verts.length) {
            return -1;
        }
        return ind;
    }

    public int getSubIndFromCoords2D(int x, int y) {
        if (x < 0 || x > size - 2)
            return -1;
        if (y < 0 || y > 2 * (size - 2 - x))
            return -1;

        int ind = (int) (2 * x * (size - 1) - (x * x) + y);
        if (ind < 0 || ind >= subsimplexes.length) {
            return -1;
        }
        return ind;
    }

    public List<Subsimplex> getAllSubsimplexes() {
        return Arrays.asList(subsimplexes);
    }

    public Subsimplex getSubsimplex(int x, int y) {
        int ind = getSubIndFromCoords2D(x, y);
        if (ind == -1)
            return null;
        return subsimplexes[ind];
    }

    private void setAdjacencies(){
        for(Subsimplex s : subsimplexes){
            //determine if major or minor (odd or even)
            int x = s.getX();
            int y = s.getY();
            boolean isMajor = s.getY() % 2 == 1;

            // all simplexes have the one above, and below
            s.addAdjacent(getSubsimplex(x, y + 1));
            s.addAdjacent(getSubsimplex(x, y - 1));

            //these sides are dependant on if its major or minor
            if (isMajor) {
                s.addAdjacent(getSubsimplex(x + 1, y - 1));
            } else {
                s.addAdjacent(getSubsimplex(x - 1, y + 1));

            }
            

        }
    }
}
