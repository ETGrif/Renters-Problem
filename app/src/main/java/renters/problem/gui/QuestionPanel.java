package renters.problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import renters.problem.app.Agent;
import renters.problem.app.App;
import renters.problem.app.Node;
import renters.problem.app.Resource;

public class QuestionPanel extends JPanel {

    List<Agent> remainingAgents;
    QuestionPanel questionPanel;
    App app;

    public QuestionPanel(App app) {
        this.app = app;
        questionPanel = this;
        remainingAgents = new ArrayList<Agent>(Arrays.asList(app.getRentersProblem().getAgents()));
        add(new ChooseAgentPanel(app));

    }

    private class ChooseAgentPanel extends JPanel {

        public ChooseAgentPanel(App app) {
            setLayout(new GridLayout(0, 1));
            add(new JLabel("Who are you?"));

            JPanel agents = new JPanel();
            agents.setLayout(new GridLayout(1, 0));
            for (Agent a : remainingAgents) {
                JButton button = new JButton(a.getName());
                button.setBackground(a.getColor());
                button.addActionListener(new ChooseAgentButtonListener(a));
                agents.add(button);
            }
            add(agents);

        }

    }

    private class ChooseAgentButtonListener implements ActionListener {
        Agent agent;

        public ChooseAgentButtonListener(Agent agent) {
            this.agent = agent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            questionPanel.removeAll();
            questionPanel.revalidate();
            questionPanel.repaint();
            questionAgent(agent);
        }

        // after all the updating, call the the app to move to the next stage

    }

    private void questionAgent(Agent agent) {

        setLayout(new GridLayout(0, 1));
        add(new DivisionDisplay(agent));

    }

    private class DivisionDisplay extends JPanel {

        Agent agent;
        Iterator<Node> nodeItt;
        Node node;
        Resource[] resources;

        public DivisionDisplay(Agent agent) {
            this.agent = agent;
            nodeItt = app.getRentersProblem().getAnchoredSets().get(agent).iterator();
            resources = app.getRentersProblem().resources;

            // next
            node = nodeItt.next();
            addMouseListener(new ChoiceClickListener());

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // find the things (divide into fifths vvertically,, and 1 + n horizontally)
            int colorStripeHeight = 20;
            int boxWidth = (int) (getWidth() / (1.0 + (2 * resources.length)));
            int heightSpacing = (int) ((getHeight() - colorStripeHeight) / 8.0);
            int boxHeight = (getHeight() - colorStripeHeight) - 3 * heightSpacing;
            int textOffset = 5;
            Map<Resource, Double> division = node.getDivision();

            // ColorStripe
            g.setColor(agent.getColor());
            g.fillRect(0, 0, getWidth(), colorStripeHeight);

            // Agent Name
            g.setColor(Color.BLACK);
            g.drawString(agent.getName(), 0, colorStripeHeight + 15);

            for (int i = 0; i < resources.length; i++) {

                // box
                g.setColor(resources[i].getColor());
                double share = division.get(resources[i]);
                double ratio = share / app.getRentersProblem().getTotal();
                int offset = (int) (boxHeight * (1 - ratio));
                g.fillRect(boxWidth * (1 + 2 * i), colorStripeHeight + 2 * heightSpacing + offset, boxWidth,
                        boxHeight - offset);

                g.setColor(Color.BLACK);
                double price = Math.round(division.get(resources[i]) * 100) / 100;
                // price
                g.drawString("$" + price, boxWidth * (1 + 2 * i),
                        colorStripeHeight + 2 * heightSpacing + offset - textOffset);
                // label
                g.drawString(resources[i].getName(), boxWidth * (1 + 2 * i),
                        colorStripeHeight + heightSpacing + offset - 2 * textOffset);

            }

        }

        private class ChoiceClickListener implements MouseInputListener {

            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int boxWidth = (int) (getWidth() / (1.0 + (2 * resources.length)));
                int column = x / boxWidth; // collumn is now betweel 0 - 2n+1

                if (column % 2 == 0) {
                    // if even, then it was inbetween the resources
                    return;
                }

                column = (column - 1) / 2; // column should now be the index of the resource

                Resource clickedResource = resources[column];

                node.setChoice(clickedResource);

                // go to next node
                if (nodeItt.hasNext()) {
                    node = nodeItt.next();
                    repaint();
                } else {
                    questionPanel.removeAll();
                    remainingAgents.remove(agent);

                    if (remainingAgents.size() > 0) {
                        questionPanel.add(new ChooseAgentPanel(app));
                        questionPanel.revalidate();
                        questionPanel.repaint();
                    }else{
                        System.out.println("Finished Asking Questions");
                        app.afterQuestions();
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
            };

            @Override
            public void mouseReleased(MouseEvent e) {
            };

            @Override
            public void mouseEntered(MouseEvent e) {
            };

            @Override
            public void mouseExited(MouseEvent e) {
            };

            @Override
            public void mouseDragged(MouseEvent e) {
            };

            @Override
            public void mouseMoved(MouseEvent e) {
            };

        }
    }

}
