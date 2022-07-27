package renters.problem.app;

import renters.problem.simplex.Simplex;

public class RentersProblem {

    private double total;
    private int numAgents;
    private double accuracy;

    public Agent[] agents;
    public Resource[] resources;


    private Simplex simplex;
    private double simplexLength;

    public RentersProblem(double total, int numAgents, double accuracy) {
        this.total = total;
        this.numAgents = numAgents;
        this.simplexLength = total * 2 / Math.sqrt(3);

        // TODO create simplex
        int divisions = chooseDivisions();
        simplex = new Simplex(divisions, getMajorPoints());

        // TODO create users?
        // TODO make anchored sets

    }

    private int chooseDivisions() {
        // TODO this is for if there is a better way to make the divisions, like
        // lowering th enumber of questions asked.
        // for now ill have it always choose 8, because for 3 agents, 8 makes a clean 5
        // questions per agent
        return 8;
    }

    private double[] getMajorPoints() {
        double[] points = new double[6];
        points[0] = 0;
        points[1] = 0;
        points[2] = simplexLength;
        points[3] = 0;
        points[4] = simplexLength / 2;
        points[5] = simplexLength * Math.sqrt(3) / 2;

        return points;
    }

    public Simplex getSimplex() {
        return this.simplex;
    }



}
