import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import renters.problem.simplex.Simplex;
import renters.problem.simplex.Subsimplex;
import renters.problem.simplex.Vector2D;
import renters.problem.simplex.Vertex;

public class SimplexTest {

    @Test
    public void simplexVertexCreationTest() {

        // create a symplex
        // get possible vertices
        Simplex simplex = new Simplex(8);
        List<Vertex> verts = simplex.getAllVerts();
        assertEquals(36, verts.size());

        // check for existing verticies
        Vertex existing1 = new Vertex(0, 0); // origin
        Vertex existing2 = new Vertex(0, 7); // upper corner
        Vertex existing3 = new Vertex(4, 3); // center edge
        Vertex existing4 = new Vertex(2, 3); // in the middle

        assertTrue(verts.contains(existing1));
        assertTrue(verts.contains(existing2));
        assertTrue(verts.contains(existing3));
        assertTrue(verts.contains(existing4));

        // check for non existing verticies

        Vertex nonexisting1 = new Vertex(0, 8); // above uppercorner
        Vertex nonexisting2 = new Vertex(2, 6); // above the edge
        Vertex nonexisting3 = new Vertex(7, 7); // at the corner if it were square

        assertFalse(verts.contains(nonexisting1));
        assertFalse(verts.contains(nonexisting2));
        assertFalse(verts.contains(nonexisting3));

        // test getVertex
        existing1 = simplex.getVert(1, 1);
        nonexisting1 = simplex.getVert(8, 8);
        assertEquals(new Vertex(1, 1), simplex.getVert(1, 1));
        assertNull(simplex.getVert(8, 8));
        assertNull(simplex.getVert(1, 7));

        //
        // again for a different size
        //

        simplex = new Simplex(3);
        verts = simplex.getAllVerts();
        assertEquals(6, verts.size());

        // check for existing verticies
        existing1 = new Vertex(0, 0); // origin
        existing2 = new Vertex(0, 2); // upper corner
        existing3 = new Vertex(1, 1); // center edge

        assertTrue(verts.contains(existing1));
        assertTrue(verts.contains(existing2));
        assertTrue(verts.contains(existing3));

        // check for non existing verticies

        nonexisting1 = new Vertex(0, 3); // above uppercorner
        nonexisting2 = new Vertex(1, 2); // above the edge
        nonexisting3 = new Vertex(2, 2); // at the corner if it were square

        assertFalse(verts.contains(nonexisting1));
        assertFalse(verts.contains(nonexisting2));
        assertFalse(verts.contains(nonexisting3));
    }

    @Test
    public void simplexSubsimplexCreationTest() {
        Simplex simplex = new Simplex(8);
        List<Subsimplex> subsimplexes = simplex.getAllSubsimplexes();

        assertEquals(49, subsimplexes.size());

        // test subsimplexes
        Subsimplex existing1 = new Subsimplex(0, 0, new Vertex(0, 0), new Vertex(0, 1), new Vertex(1, 0));
        Subsimplex existing2 = new Subsimplex(3, 5, new Vertex(3, 3), new Vertex(4, 3), new Vertex(4, 2));
        Subsimplex nonexisting1 = new Subsimplex(2, 9, new Vertex(2, 5), new Vertex(3, 5), new Vertex(3, 4));

        assertTrue(subsimplexes.contains(existing1));
        assertTrue(subsimplexes.contains(existing2));
        assertFalse(subsimplexes.contains(nonexisting1));

        // test the get function
        assertEquals(existing1, simplex.getSubsimplex(0, 0));
        assertEquals(existing2, simplex.getSubsimplex(3, 5));
        assertNull(simplex.getSubsimplex(0, 13));
        assertNull(simplex.getSubsimplex(3, 8));

        //
        // Check again for size 3
        //

        simplex = new Simplex(3);
        subsimplexes = simplex.getAllSubsimplexes();

        assertEquals(4, subsimplexes.size());

        // test subsimplexes
        existing1 = new Subsimplex(0, 0, new Vertex(0, 0), new Vertex(0, 1), new Vertex(1, 0));
        existing2 = new Subsimplex(0, 2, new Vertex(0, 1), new Vertex(0, 2), new Vertex(1, 1));
        nonexisting1 = new Subsimplex(1, 1, new Vertex(1, 1), new Vertex(2, 1), new Vertex(2, 0));

        assertTrue(subsimplexes.contains(existing1));
        assertTrue(subsimplexes.contains(existing2));
        assertFalse(subsimplexes.contains(nonexisting1));

        // test the get function
        assertEquals(existing1, simplex.getSubsimplex(0, 0));
        assertEquals(existing2, simplex.getSubsimplex(0, 2));
        assertNull(simplex.getSubsimplex(1, 1));
    }

    @Test
    public void simplexAdjacencyList() {
        Simplex simplex = new Simplex(8);
        // inside major (1, 5) -> (1, 4) (1, 6) (2, 4)
        Subsimplex subsimplex = simplex.getSubsimplex(1, 5);
        List<Subsimplex> adjacent = subsimplex.getAdjacent();
        assertEquals(3, adjacent.size());
        assertTrue(adjacent.contains(simplex.getSubsimplex(1, 6)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(1, 4)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(2, 4)));

        // inside minor (4,2) -> (3, 3) (4, 3) (4, 1)
        subsimplex = simplex.getSubsimplex(4, 2);
        adjacent = subsimplex.getAdjacent();
        assertEquals(3, adjacent.size());
        assertTrue(adjacent.contains(simplex.getSubsimplex(3, 3)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(4, 3)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(4, 1)));

        // corner (6, 0) -> (5, 1)
        subsimplex = simplex.getSubsimplex(6, 0);
        adjacent = subsimplex.getAdjacent();
        assertEquals(1, adjacent.size());
        assertTrue(adjacent.contains(simplex.getSubsimplex(5, 1)));

        // bottom edge (1, 0) -> (0, 1) (1, 1)
        subsimplex = simplex.getSubsimplex(1, 0);
        adjacent = subsimplex.getAdjacent();
        assertEquals(2, adjacent.size());
        assertTrue(adjacent.contains(simplex.getSubsimplex(0, 1)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(1, 1)));

        // left edge (0, 2) -> (0, 3) (0, 1)
        subsimplex = simplex.getSubsimplex(0, 2);
        adjacent = subsimplex.getAdjacent();
        assertEquals(2, adjacent.size());
        assertTrue(adjacent.contains(simplex.getSubsimplex(0, 3)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(0, 1)));

        // top edge (5, 2) -> (4, 3) (5, 1)
        subsimplex = simplex.getSubsimplex(5, 2);
        adjacent = subsimplex.getAdjacent();
        assertEquals(2, adjacent.size());
        assertTrue(adjacent.contains(simplex.getSubsimplex(4, 3)));
        assertTrue(adjacent.contains(simplex.getSubsimplex(5, 1)));
    }

    @Test
    public void defaultSimplexPosition() {
        Simplex simplex = new Simplex(3);

        // by default the simplex will have subsimplex side-length on 1. with the left
        // lower vertex is on the origin and the bottom edge is on the x axis

        // Points I'd like to check
        // (0, 0) -> (0, 0)
        // (0, 3) -> (3/2, 3sqrt(3)/2)
        // (1, 1) -> (3/2, sqrt(3)/2)
        // (1, 2) -> (2, sqrt(3))
        // (3, 0) -> (3, 0)

        double ix = 1.0;
        double iy = 0.0;
        double jx = 1.0 / 2;
        double jy = 3 * Math.sqrt(3) / 2;

        assertEquals(0, simplex.getVert(0, 0).getX());
        assertEquals(0, simplex.getVert(0, 0).getY());

        assertEquals(3 * jx, simplex.getVert(0, 3).getX());
        assertEquals(3 * jy, simplex.getVert(0, 3).getY());

        assertEquals(ix + jx, simplex.getVert(1, 1).getX());
        assertEquals(iy + jy, simplex.getVert(1, 1).getY());

        assertEquals(ix + 2 * jx, simplex.getVert(1, 2).getX());
        assertEquals(iy + 2 * jy, simplex.getVert(1, 2).getY());

        assertEquals(3 * ix, simplex.getVert(3, 0).getX());
        assertEquals(3 * iy, simplex.getVert(3, 0).getY());

    }

    @Test
    public void placedSimplexPosition() {
        // this simplex will be positioned with these major verticies:
        // (1,1), (3,4), (4,1)

        int[] coords = { 1, 1, 3, 4, 4, 1 };
        Simplex simplex = new Simplex(3, coords);

        // Points I'd like to check -- these are the same as before but will be
        // stationed elsewhere
        // (0, 0)
        // (0, 3)
        // (1, 1)
        // (1, 2)
        // (3, 0)

        double ix = 1.0;
        double iy = 0.0;
        double jx = 2.0 / 3;
        double jy = 1.0;
        double cx = 1.0;
        double cy = 1.0;

        assertEquals(0 + cx, simplex.getVert(0, 0).getX());
        assertEquals(0 + cy, simplex.getVert(0, 0).getY());

        assertEquals(3 * jx + cx, simplex.getVert(0, 3).getX());
        assertEquals(3 * jy + cy, simplex.getVert(0, 3).getY());

        assertEquals(ix + jx + cx, simplex.getVert(1, 1).getX());
        assertEquals(iy + jy + cy, simplex.getVert(1, 1).getY());

        assertEquals(ix + 2 * jx + cx, simplex.getVert(1, 2).getX());
        assertEquals(iy + 2 * jy + cy, simplex.getVert(1, 2).getY());

        assertEquals(3 * ix + cx, simplex.getVert(3, 0).getX());
        assertEquals(3 * iy + cy, simplex.getVert(3, 0).getY());

    }
}
