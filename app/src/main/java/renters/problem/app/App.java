package renters.problem.app;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import renters.problem.gui.AgentsAndResourcesPanel;
import renters.problem.gui.QuestionPanel;
import renters.problem.gui.SetUpPanel;
import renters.problem.gui.ShowAnswerPane;
import renters.problem.simplex.Simplex;
import renters.problem.simplex.Subsimplex;
import renters.problem.simplex.Vertex;

public class App extends JFrame {

    public static RentersProblem rp;

    private App frame;

    public static void main(String[] args) {
        App frame = new App();
        frame.pack();
        frame.setVisible(true);

        //add a new comment

        // ask for questions
        // for (Agent a : rp.agents) {
        //     askQuestionsToAgent(a);
        // }

        // // for the sake of knowing what the graph looks like
        // printAgentsToSys();
        // printChoicesToSys();

        // // probe for solution
        // List<Subsimplex> solutions = rp.getSolutions();
        // System.out.println(solutions.size());
        // System.out.println();
    }

    public App() {
        this.frame = this; //reference for later
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Renter's Problem");
        setPreferredSize(new Dimension(600, 200));
        frame.add(new SetUpPanel(frame));
    }

    public void createRentersProblem(double total, int numAgents){
        // make the structure
        rp = new RentersProblem(total, numAgents, 5.00);
        

        //remove the SetUpPanel
        frame.getContentPane().removeAll();
        
        
        //add the Agent and Resource pane
        add(new AgentsAndResourcesPanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    //this happens after the agents and resorces are made
    public void startQuestionsPane() {

        frame.getContentPane().removeAll();

        frame.add(new QuestionPanel(frame));

        frame.revalidate();
        frame.repaint();
        
    }

    public static void askQuestionsToAgent(Agent a) {

        for (Node n : rp.anchoredSets.get(a)) {
            askQuestionOnNode(n);
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

    public RentersProblem getRentersProblem(){
        return rp;
    }

	public void afterQuestions() {
        frame.getContentPane().removeAll();

        //check for solutions
        List<Subsimplex> solutions = rp.getSolutions();
        
        //choose a random one I guess?
        int rand = (int) (Math.random()*solutions.size());
        Subsimplex solutionSimplex = solutions.get(rand);

        //check for the accuracy
        Vertex center = solutionSimplex.getCenterOfSimplex();
        Map<Resource, Double> solution = Divider.getDivision(rp, center);

        double dist = 0;
        for(Vertex c : solutionSimplex.getMajorVerts()){
            Map<Resource, Double> subDivision = rp.nodeMap.get(c).getDivision();
            for(Resource r : subDivision.keySet()){
                dist += Math.abs(solution.get(r) - subDivision.get(r));
            }
        }
        dist /= Math.pow(solutionSimplex.getMajorVerts().length,2);

        //itterate
        if(dist > rp.getAccuracy()){
            System.out.println("There is a need for itteration. TODO");
        }

        //OR show the solution
        frame.add(new ShowAnswerPane(frame, solution, solutionSimplex));

        frame.revalidate();
        frame.repaint();

	}
}
