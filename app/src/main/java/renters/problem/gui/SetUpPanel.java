package renters.problem.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import renters.problem.app.App;

public class SetUpPanel extends JPanel{
private SetUpPanel setUpPanel;

    private JPanel moneyBar;
    private JTextField totalMoney;

    private JPanel playerBar;
    private JTextField playerCount;

    private JButton submitButton;

    private App app;
    
    public SetUpPanel(App app){
       this.app = app;
       this.setUpPanel = this; //reference for later

        JPanel upperPanel = new JPanel();

        upperPanel.setLayout(new GridLayout(1,3));
        upperPanel.add(createMoneyBar());
        upperPanel.add(createPlayerBar());

        submitButton = new JButton("Submit");
        submitButton.setSize(new Dimension(40, 80));
        submitButton.addActionListener(new SubmitButtonListener());

        setLayout(new GridLayout(2,1));
        add(upperPanel);
        add(submitButton);
    }

    private JPanel createMoneyBar(){
        moneyBar = new JPanel();
        moneyBar.setBorder(new TitledBorder(new EtchedBorder(), "Money"));
        moneyBar.setLayout(new GridLayout(2, 1));
        moneyBar.add(new JLabel("What is the total money?"));
        totalMoney = new JTextField("1000");
        moneyBar.add(totalMoney, BorderLayout.CENTER);

        return moneyBar;
    }

    private JPanel createPlayerBar(){
        playerBar = new JPanel();
        playerBar.setBorder(new TitledBorder(new EtchedBorder(), "PLayers"));
        playerBar.setLayout(new GridLayout(2,1));
        playerBar.add(new JLabel("How many players?"));
        playerCount = new JTextField("3");
        playerBar.add(playerCount);
        //TODO this is permenant right now
        playerCount.setEditable(false);


        return playerBar;
    }

    private class SubmitButtonListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            try{
                double total = Double.parseDouble(totalMoney.getText());
                int players = Integer.parseInt(playerCount.getText());
                app.createRentersProblem(total, players);
                setUpPanel.setVisible(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "There was an error in the formatting.", "",
                        JOptionPane.ERROR_MESSAGE);
            }
            

            
        }

    }
}
