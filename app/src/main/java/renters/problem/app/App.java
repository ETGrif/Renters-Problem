package renters.problem.app;

import renters.problem.simplex.Simplex;
import renters.problem.simplex.Vertex;

public class App {
    public static void main(String[] args) {
        RentersProblem rp = new RentersProblem(8, 3, 5.00);

        Simplex s = rp.getSimplex();
        
        
    }
}
