package renters.problem.simplex;

import java.util.ArrayList;
import java.util.List;

public class Subsimplex extends Simplex {

    private int i;
    private int j;

    List<Subsimplex> adjacentSimplexes = new ArrayList<Subsimplex>();

    // this one should be for testing only, really
    public Subsimplex(int i, int j) {
        super(2);

        this.i = i;
        this.j = j;

        if (j % 2 == 1) {
            verts[0] = new Vertex(i, (j + 1) / 2);
            verts[1] = new Vertex(i + 1, (j + 1) / 2);
            verts[2] = new Vertex(i + 1, (j - 1) / 2);
        } else {
            verts[0] = new Vertex(i, j / 2);
            verts[1] = new Vertex(i, j / 2 + 1);
            verts[2] = new Vertex(i + 1, j / 2);
        }

        // TODO Auto-generated constructor stub
    }

    public Subsimplex(int i, int j, Vertex vertex, Vertex vertex2, Vertex vertex3) {
        super(2);
        this.i = i;
        this.j = j;
        verts[0] = vertex;
        verts[1] = vertex2;
        verts[2] = vertex3;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Subsimplex)) {
            return false;
        }

        Subsimplex other = (Subsimplex) o;

        // check (i, j)
        if (this.i != other.getI() || this.j != other.getJ()) {
            return false;
        }

        // check for verts
        List<Vertex> oVerts = other.getAllVerts();
        if (this.verts.length != oVerts.size())
            return false;
        for (Vertex v : verts) {
            if (!oVerts.contains(v)) {
                return false;
            }
        }

        // if no errors found
        return true;
    }

    public List<Subsimplex> getAdjacent() {
        return adjacentSimplexes;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void addAdjacent(Subsimplex subsimplex) {
        if (subsimplex == null)
            return;
        adjacentSimplexes.add(subsimplex);
    }

}
