package renters.problem.simplex;

import java.util.Arrays;
import java.util.List;

public class Simplex {

    public final int size;
    int[] coords;

    Vertex[] majorVerts;
    Vertex[] verts;

    Subsimplex[] subsimplexes;

    public Simplex(int size) {
        this.size = size;

        // create vertices
        fillVerts();

        // create subsimplexes
        fillSubsimplexes();

    }

    public Simplex(int i, int[] coords) {
        this.size = i;
        this.coords = coords;
        this.verts = new Vertex[1];

        // create vertices
        fillVerts();

        // create subsimplexes
        fillSubsimplexes();
    }

    private void fillSubsimplexes() {
        if (size <= 2) { // n = 2 & n = 1 both dont need subdivisions
            return;
        }

        int numOfSubsimplexes = (size - 1) * (size - 1);
        subsimplexes = new Subsimplex[numOfSubsimplexes];

        // loop through possible indecies
        int ind = 0;
        for (int i = 0; i <= size - 2; i++) {
            for (int j = 0; j <= 2 * (size - 2 - i); j++) {
                // get the vertex objects, and add them
                Vertex v1;
                Vertex v2;
                Vertex v3;

                // if b is even
                if (j % 2 == 0) {
                    v1 = getVert(i, j / 2);
                    v2 = getVert(i, j / 2 + 1);
                    v3 = getVert(i + 1, j / 2);
                } else {
                    // if b is odd
                    v1 = getVert(i, (j + 1) / 2);
                    v2 = getVert(i + 1, (j + 1) / 2);
                    v3 = getVert(i + 1, (j - 1) / 2);
                }

                Subsimplex simplex = new Subsimplex(i, j, v1, v2, v3);
                subsimplexes[ind] = simplex;
                ind++;
            }
        }

        setAdjacencies();
    }

    private void fillVerts() {
        int numOfVerts = (size * size + size) / 2; // formula for count of verticies
        verts = new Vertex[numOfVerts];

        // loop through the possible indecies
        int ind = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                Vertex newVert = new Vertex(i, j);
                verts[ind] = newVert;
                ind++;
            }
        }
    }

    public List<Vertex> getAllVerts() {
        return Arrays.asList(verts);
    }

    public Vertex getVert(int i, int j) {
        int ind = getVertIndFromCoords2D(i, j);
        if (ind == -1)
            return null;
        return verts[ind];
    }

    // this uses the coords to calculate the index it should be in the array
    // indexes from bootom to top, then left to right
    // returns -1 if out of bounds
    private int getVertIndFromCoords2D(int i, int j) {
        if (i < 0 || i > size)
            return -1;
        if (j < 0 || j > size - i - 1)
            return -1;

        int ind = (int) (i * (size + .5) - (.5 * i * i) + j);
        if (ind < 0 || ind >= verts.length) {
            return -1;
        }
        return ind;
    }

    public int getSubIndFromCoords2D(int i, int j) {
        if (i < 0 || i > size - 2)
            return -1;
        if (j < 0 || j > 2 * (size - 2 - i))
            return -1;

        int ind = (int) (2 * i * (size - 1) - (i * i) + j);
        if (ind < 0 || ind >= subsimplexes.length) {
            return -1;
        }
        return ind;
    }

    public List<Subsimplex> getAllSubsimplexes() {
        return Arrays.asList(subsimplexes);
    }

    public Subsimplex getSubsimplex(int i, int j) {
        int ind = getSubIndFromCoords2D(i, j);
        if (ind == -1)
            return null;
        return subsimplexes[ind];
    }

    private void setAdjacencies() {
        for (Subsimplex s : subsimplexes) {
            // determine if major or minor (odd or even)
            int i = s.getI();
            int j = s.getJ();
            boolean isMajor = s.getJ() % 2 == 1;

            // all simplexes have the one above, and below
            s.addAdjacent(getSubsimplex(i, j + 1));
            s.addAdjacent(getSubsimplex(i, j - 1));

            // these sides are dependant on if its major or minor
            if (isMajor) {
                s.addAdjacent(getSubsimplex(i + 1, j - 1));
            } else {
                s.addAdjacent(getSubsimplex(i - 1, j + 1));

            }

        }
    }
}
