package renters.problem.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import renters.problem.simplex.Simplex;
import renters.problem.simplex.Subsimplex;
import renters.problem.simplex.Vertex;

public class RentersProblem {

    private double total;
    private int numAgents;
    private double accuracy;

    public Agent unused;
    public Agent[] agents;
    public Resource[] resources;

    public Map<Vertex, Node> nodeMap;
    public Map<Agent, Set<Vertex>> anchoredSets;

    private Simplex simplex;
    private double simplexLength;

    public RentersProblem(double total, int numAgents, double accuracy) {
        this.total = total;
        this.numAgents = numAgents;
        this.simplexLength = total * 2 / Math.sqrt(3);

        // TODO create simplex
        int divisions = chooseDivisions();
        simplex = new Simplex(divisions, getMajorPoints());

        // create resources
        makeResources();
        // create nodes
        makeNodes();

        // create users
        makeUsers();
        // TODO make anchored sets
        anchorSets();

        System.out.println("Completed the creation.");

    }

    private void anchorSets() {
        // TODO make check to see if the simplex is large enough to make an anchored
        // set?

        anchoredSets = new HashMap<Agent, Set<Vertex>>();
        // instantiate the anchor sets
        unused = new Agent("Unused");
        anchoredSets.put(unused, new HashSet<Vertex>());
        for (Agent a : agents) {
            anchoredSets.put(a, new HashSet<Vertex>());
        }

        int maxInd = simplex.size - 1;
        // get all the edges
        for (int i = 0; i < simplex.size; i++) {
            // left edge
            anchorANode(0, i, unused);
            // bottom egde
            anchorANode(i, 0, unused);
            // the top edge
            anchorANode(i, maxInd - i, unused);
        }

        // then recursively do the rest?
        // plant a "seed" (simp (1,2) then recursively check the adjacent simplexes and
        // fill in the remaining
        Subsimplex seed = simplex.getSubsimplex(1, 2);
        nodeMap.get(seed.getMajorVerts()[0]).setAnchoredAgent(agents[0]);
        nodeMap.get(seed.getMajorVerts()[1]).setAnchoredAgent(agents[1]);
        nodeMap.get(seed.getMajorVerts()[2]).setAnchoredAgent(agents[2]);

        // start off the chain of anchors with the with the subsimplex above the seed
        anchorSimplex(simplex.getSubsimplex(1, 3));

    }

    private void anchorSimplex(Subsimplex s) {
        // Node[] nodes = new Node[s.getMajorVerts().length];
        // for (int i = 0; i < nodes.length; i++) {
        //     nodes[i] = nodeMap.get(s.getMajorVerts()[i]);
        // }

        // make copy of possible agents
        // remove all existing agents
        // if there is more than 1 possible agent remaining, break

        Node nullAgentNode = null;
        // Array.asList() return an immutable list, so we make a copy that is mutable
        List<Agent> possibleAgents = new ArrayList<Agent>(Arrays.asList(agents));
        // for (Node n : nodes) {
        //     Agent nodeAgent = n.getAnchoredAgent();
        //     if (nodeAgent == null) {
        //         nullAgentNode = n;
        //     } else {
        //         possibleAgents.remove(nodeAgent);
                
        //     }
        // }

        Vertex[] verts = s.getMajorVerts();
        for (int i = 0; i < verts.length; i++) {
            Vertex v = verts[i];
            Node n = nodeMap.get(v);

            Agent nodeAgent = n.getAnchoredAgent();
            if (nodeAgent == null) {
                nullAgentNode = n;
            } else {
                possibleAgents.remove(nodeAgent);
                anchorANode(v, nodeAgent);
            }
        }

        if (possibleAgents.size() != 1 || nullAgentNode == null) {
            // check for a null node, because if theres an unused node, there will still be
            // a possible node
            return;
        }

        // there should only be one possible agent left, so anchor the node
        nullAgentNode.setAnchoredAgent(possibleAgents.get(0));

        // recursively attempt the same on the adjacent subsimplexes
        for (Subsimplex adj : s.getAdjacent()) {
            anchorSimplex(adj);
        }

    }

    public void anchorANode(int i, int j, Agent agent) {
        anchorANode(simplex.getVert(i, j), agent);
    }

    public void anchorANode(Vertex v, Agent agent) {
        anchoredSets.get(agent).add(v);
        nodeMap.get(v).setAnchoredAgent(agent);
    }

    private void makeUsers() {
        // TODO update this to be dynamic, this will be fine for now
        String[] names = { "Alice", "Barb", "Chris" };
        agents = new Agent[numAgents];
        for (int i = 0; i < numAgents; i++) {
            agents[i] = new Agent(names[i]);
        }
    }

    private void makeNodes() {
        // for each vertex in the simplex, assign a node object that holds its division.
        // It will have space to know who it is anchored to and its decision, but thats
        // not chosen yet
        nodeMap = new HashMap<Vertex, Node>();

        for (Vertex v : simplex.getAllVerts()) {
            Node n = new Node();
            n.setDivision(Divider.getDivision(this, simplex, v));
            nodeMap.put(v, n);
        }
    }

    private void makeResources() {
        // For now it will be a preset thing, but I think id want to make it a file read
        // or a gui page
        // TODO this is bad practice, but in case it happens and I need to figure out
        // whats going on
        if (simplex.dimention != 2)
            throw new RuntimeException("There should only be 2d nodes rn");

        resources = new Resource[simplex.size];
        resources[0] = new Resource("Room A");
        resources[1] = new Resource("Room B");
        resources[2] = new Resource("Room C");

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

    public Map<Agent, Set<Vertex>> getAnchoredSets() {
        return this.anchoredSets;
    }

    public Agent[] getAgents() {
        return this.agents;
    }

}
