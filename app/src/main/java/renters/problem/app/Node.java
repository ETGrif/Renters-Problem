package renters.problem.app;

import java.util.Map;

public class Node {
    public Agent anchoredAgent;
    public Map<Resource, Double> division;
    public Resource choice;

    public Node() {

    }

    public Agent getAnchoredAgent() {
        return anchoredAgent;
    }

    public void setAnchoredAgent(Agent anchoredAgent) {
        this.anchoredAgent = anchoredAgent;
    }

    public Map<Resource, Double> getDivision() {
        return division;
    }

    public void setDivision(Map<Resource, Double> division) {
        this.division = division;
    }

    public Resource getChoice() {
        return choice;
    }

    public void setChoice(Resource choice) {
        this.choice = choice;
    }

}
