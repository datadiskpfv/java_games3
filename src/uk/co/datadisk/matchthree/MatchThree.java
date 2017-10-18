package uk.co.datadisk.matchthree;

import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class MatchThree extends JFrame {

    private static final long serialVersionUID = 1960646579536307638L;

    ScorePanel scorePanel = new ScorePanel(0, Color.GREEN);

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


        // button panel



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
