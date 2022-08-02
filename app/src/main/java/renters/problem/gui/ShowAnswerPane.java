package renters.problem.gui;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import renters.problem.app.Agent;
import renters.problem.app.App;
import renters.problem.app.Node;
import renters.problem.app.Resource;
import renters.problem.simplex.Subsimplex;
import renters.problem.simplex.Vertex;

public class ShowAnswerPane extends JPanel{
    
    Map<Resource, Double> solution;
    Subsimplex solutionSimplex;
    App app;

    public ShowAnswerPane(App app, Map<Resource, Double> solution, Subsimplex solutionSimplex){
        this.solution = solution;
        this.solutionSimplex = solutionSimplex;
        this.app = app;


        setLayout(new GridLayout(1,0));
        
        Map<Resource, Agent> ownershipMap = new HashMap<>();
        for(Vertex v: solutionSimplex.getMajorVerts()){
            Node n = app.getRentersProblem().nodeMap.get(v);
           Agent a = n.getAnchoredAgent();
           Resource r = n.getChoice();
           ownershipMap.put(r, a);
        }

        for(Resource r : app.getRentersProblem().resources){
            JPanel card = new JPanel();
            card.setLayout(new GridLayout(0,1));
            card.setBorder(new TitledBorder(new EtchedBorder(), r.getName()));

            //Person
            card.add(new JLabel(ownershipMap.get(r).getName()));

            //Price
            card.add(new JLabel("$" + Math.round(solution.get(r) * 100) / 100));

            add(card);
        }

    }

}
