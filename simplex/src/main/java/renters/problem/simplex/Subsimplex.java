package renters.problem.simplex;

import java.util.ArrayList;
import java.util.List;

public class Subsimplex extends Simplex {

    private int x;
    private int y;

    List<Subsimplex> adjacentSimplexes = new ArrayList<Subsimplex>();

    //this one should be for testing only, really
    public Subsimplex(int x, int y) {
        super(2);

        this.x = x;
        this.y = y;

        if(y % 2 == 1){
            verts[0] = new Vertex(x, (y + 1) / 2);
            verts[1] = new Vertex(x + 1, (y + 1) / 2);
            verts[2] = new Vertex(x + 1, (y - 1) / 2);
        }else{
            verts[0] = new Vertex(x, y / 2);
            verts[1] = new Vertex(x, y / 2 + 1);
            verts[2] = new Vertex(x + 1, y / 2);
        }

        // TODO Auto-generated constructor stub
    }

    public Subsimplex(int x, int y, Vertex vertex, Vertex vertex2, Vertex vertex3) {
        super(2);
        this.x = x;
        this.y = y;
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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void addAdjacent(Subsimplex subsimplex) {
        if (subsimplex == null)
            return;
        adjacentSimplexes.add(subsimplex);
    }

}
