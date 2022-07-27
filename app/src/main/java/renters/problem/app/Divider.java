package renters.problem.app;

import java.util.Map;

import renters.problem.simplex.Simplex;
import renters.problem.simplex.Vector2D;
// import renters.problem.JMat.jmat.core.*;
import renters.problem.simplex.Vertex;

public class Divider {
    
    public static Map<Resource, Double> getDivision(Simplex simplex, Vertex v){
        Double[] lengths = vivianis(simplex, v);
        
        System.out.println(lengths.toString());
        return cleanUp(null);
    }

    

    private static Map<Resource, Double> cleanUp(Map<Resource, Double> division){
        //TODO decide if this should exist, and what it should do
        return division;
    }

    private static Double[] vivianis(Simplex simplex, Vertex vertex){
        Vertex[] mv = simplex.getMajorVerts();

        Vector2D c = new Vector2D(mv[0].getPos());

        Vector2D p = new Vector2D(vertex.getPos()).getSubtracted(c);

        Vector2D u = new Vector2D(mv[1].getPos()).getSubtracted(c);
        Vector2D v = new Vector2D(mv[2].getPos()).getSubtracted(c);
        // Vector2D w = v.getSubtracted(u);

        Vector2D s = u.getProjectedVector(p).getSubtracted(p);
        Vector2D r = v.getProjectedVector(p).getSubtracted(p);
        // Vector2D t = w.getProjectedVector(p).getSubtracted(p);

        double l1 = s.getLength();
        double l2 = r.getLength();
        // double l3 = t.getLength();

        double alt = u.getLength() * Math.sqrt(3)/2;
        double l3 = alt - l1 - l2;

        return new Double[]{l1, l2, l3};



    }

}
