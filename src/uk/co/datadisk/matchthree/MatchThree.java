package uk.co.datadisk.matchthree;

import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchThree extends JFrame {

    private static final long serialVersionUID = 1960646579536307638L;

    ScorePanel scorePanel = new ScorePanel(0, Color.GREEN);

    private BallPanel ballPanel = new BallPanel(this);

    private MatchThree() {
        initGUI();

        setTitle("Match Three");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("Match Three");
        add(titleLabel, BorderLayout.PAGE_START);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.GREEN);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // score panel
        mainPanel.add(scorePanel);


        // ball panel
        mainPanel.add(ballPanel);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        mainPanel.add(buttonPanel);

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHint();
            }
        });
        buttonPanel.add(hintButton);
    }

    private void showHint() {
        ballPanel.showHint();
    }

    public void addToScore(int newPoints) {
        scorePanel.addToScore(newPoints);
    }

    public void restart() {
        scorePanel.reset();
        ballPanel.setInitialBalls();
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(MatchThree::new);
    }
}
