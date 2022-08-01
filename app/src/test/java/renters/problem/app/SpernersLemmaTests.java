package renters.problem.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import renters.problem.simplex.Simplex;
import renters.problem.simplex.Vertex;

public class SpernersLemmaTests {
    static RentersProblem rp;

@BeforeAll
public static void setUp(){
    rp = new RentersProblem(1000, 3, 5.00);

}

@Test
public void simplexCreationTest2D(){
    Simplex simplex = rp.getSimplex();
    assertEquals(2, simplex.dimention);
    assertEquals(8, simplex.size);
}

@Test
public void anchoredSetsTest(){
    Agent unused = rp.unused;
    Map<Agent, Set<Vertex>> anchoredSets = rp.getAnchoredSets();
    Simplex simplex = rp.getSimplex();
    
    //im just gonna check some key points in the unused set, and then the size for all
    // contains (0,0) , (3,0) , (0,7) , (4,3)
    // does not contain (1,1)
    assertEquals(21, anchoredSets.get(unused).size());
    assertTrue(anchoredSets.get(unused).contains(simplex.getVert(0,0)));//left corner
    assertTrue(anchoredSets.get(unused).contains(simplex.getVert(3,0)));//botom edge
    assertTrue(anchoredSets.get(unused).contains(simplex.getVert(0,7)));//Top corner
    assertTrue(anchoredSets.get(unused).contains(simplex.getVert(4,3)));//right edge
    assertFalse(anchoredSets.get(unused).contains(simplex.getVert(1,1)));//center

    //other agents
    Agent[] agents = rp.getAgents();
    for(Agent a : agents){
        assertEquals( 5, anchoredSets.get(a).size());
    }

}
    
}
