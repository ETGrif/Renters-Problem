package renters.problem.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import renters.problem.app.Agent;
import renters.problem.app.App;
import renters.problem.app.Resource;

public class AgentsAndResourcesPanel extends JPanel {

    private AgentsAndResourcesPanel frame;
    private App app;

    ArrayList<Card> cards = new ArrayList<Card>();

    private JPanel agentsPanel;
    private JPanel resourcesPanel;
    private JButton submitButton;

    public AgentsAndResourcesPanel(App app) {
        this.frame = this;
        this.app = app;

        setLayout(new GridLayout(3, 1));
        add(createAgentsPanel());
        add(createResourcesPanel());

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton);

    }

    private JPanel createAgentsPanel() {
        agentsPanel = new JPanel();
        agentsPanel.setLayout(new GridLayout(1, 0));
        agentsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Agents"));
        for(Agent a : app.getRentersProblem().agents){
            agentsPanel.add(new Card(a));
        }

        return agentsPanel;
    }

    private JPanel createResourcesPanel() {
        resourcesPanel = new JPanel();
        resourcesPanel.setLayout(new GridLayout(1, 0));
        resourcesPanel.setBorder(new TitledBorder(new EtchedBorder(), "Resources"));
        for(Resource r : app.rp.resources){
            resourcesPanel.add(new Card(r));
        }

        return resourcesPanel;
    }

    private class Card extends JPanel {

        Nameable n;
        JTextField name;


        public Card(Nameable n) {
            this.n = n;
            cards.add(this);
            setLayout(new GridLayout(0, 1));

            // color label
            JPanel colorStripe = new JPanel();
            colorStripe.setBackground(n.getColor());
            colorStripe.setSize(new Dimension(40, 10));
            add(colorStripe);

            //make the name
            name = new JTextField(n.getName());
            name.setPreferredSize(new Dimension(40, 10));
            add(name);


        }

        public Nameable getN(){
            return n;
        }

        public String getName(){
            return name.getText();
        }
    }

    private class SubmitButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            for(Card c : cards){
                Nameable n = c.getN();
                String newName = c.getName();
                n.setName(newName);
            }
            frame.setVisible(false);
            app.startQuestionsPane();
            
        }

        //after all the updating, call the the app to move to the next stage

    }
}
