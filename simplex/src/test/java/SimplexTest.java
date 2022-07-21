import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import renters.problem.simplex.Simplex;
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

        //test getVertex
        existing1 = simplex.getVert(1,1);
        nonexisting1 = simplex.getVert(8,8);
        assertEquals(new Vertex(1,1), simplex.getVert(1,1));
        assertNull(simplex.getVert(8,8));

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

}
