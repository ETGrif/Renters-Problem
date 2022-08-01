package renters.problem.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import renters.problem.simplex.Simplex;
import renters.problem.simplex.Subsimplex;
import renters.problem.simplex.Vertex;

public class App {

    private static RentersProblem rp;

    public static void main(String[] args) {
        // make the structure
        rp = new RentersProblem(8, 3, 5.00);

        // ask for questions
        for (Agent a : rp.agents) {
            askQuestionsToAgent(a);
        }
        
        //for the sake of knowing what the graph looks like
        printAgentsToSys();
        printChoicesToSys();

        // probe for solution
        List<Subsimplex> solutions = rp.getSolutions();
        System.out.println(solutions.size());
        System.out.println();

    }

    public static void askQuestionsToAgent(Agent a) {

        for (Vertex v : rp.anchoredSets.get(a)) {
            askQuestionOnNode(rp.nodeMap.get(v));
        }
    }

    public static void askQuestionOnNode(Node n) {
        // TODO this needs to be redone to not be random later
        Random rand = new Random();
        int i = rand.nextInt() % rp.resources.length;
        if (i < 0) {
            i *= -1;
        }
        n.setChoice(rp.resources[i]);
    }

    public static void printChoicesToSys() {
        Simplex s = rp.getSimplex();
        Map<Resource, String> letterMap = new HashMap<Resource, String>();
        String letters = "abc";
        for (int i = 0; i < rp.resources.length; i++) {
            letterMap.put(rp.resources[i], letters.substring(i, i + 1));
        }
        letterMap.put(null, " ");

        String spacing = " ";
        String prefix = "";
        for (int j = 0; j < s.size; j++) {
            System.out.print(prefix);
            for (int i = 0; i < s.size - j; i++) {
                System.out.print(letterMap.get(rp.nodeMap.get(s.getVert(i, j)).getChoice()));
                System.out.print(spacing);
            }
            System.out.println();
            prefix += spacing;
        }

    }

    public static void printAgentsToSys() {
        Simplex s = rp.getSimplex();
        Map<Agent, String> letterMap = new HashMap<Agent, String>();
        String letters = "ABC";
        for (int i = 0; i < rp.agents.length; i++) {
            letterMap.put(rp.agents[i], letters.substring(i, i + 1));
        }
        letterMap.put(rp.unused, "X");

        String spacing = " ";
        String prefix = "";
        for (int j = 0; j < s.size; j++) {
            System.out.print(prefix);
            for (int i = 0; i < s.size - j; i++) {
                System.out.print(letterMap.get(rp.nodeMap.get(s.getVert(i, j)).getAnchoredAgent()));
                System.out.print(spacing);
            }
            System.out.println();
            prefix += spacing;
        }

    }
}
