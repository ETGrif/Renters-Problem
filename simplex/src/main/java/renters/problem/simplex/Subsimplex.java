package renters.problem.simplex;

import java.util.List;

public class Subsimplex extends Simplex{


    public Subsimplex() {
        super(2);
        //TODO Auto-generated constructor stub
    }

    public Subsimplex(Vertex vertex, Vertex vertex2, Vertex vertex3) {
        super(2);
        verts[0] = vertex;
        verts[1] = vertex2;
        verts[2] = vertex3;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Subsimplex)){
            return false;
        }

        Subsimplex other = (Subsimplex) o;

        //check for verts
        List<Vertex> oVerts = other.getAllVerts();
        if(this.verts.length != oVerts.size()) return false;
        for(Vertex v : verts){
            if(!oVerts.contains(v)){
                return false;
            }
        }

        //if no errors found
        return true;
    }

}
