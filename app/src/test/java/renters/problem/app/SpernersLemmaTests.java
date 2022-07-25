package renters.problem.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import renters.problem.simplex.Simplex;

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
    
}
