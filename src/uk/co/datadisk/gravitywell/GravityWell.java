package uk.co.datadisk.gravitywell;

import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class GravityWell extends JFrame {

    private static final long serialVersionUID = -1131180335321200917L;

    ScorePanel scorePanel = new ScorePanel(0, Color.cyan);
    GamePanel gamePanel = new GamePanel(scorePanel);

    public GravityWell() {
        initGUI();

        setTitle("Gravity Well");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("Gravity Well");
        add(titleLabel, BorderLayout.PAGE_START);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.CYAN);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        //score panel
        mainPanel.add(scorePanel);

        // game panel
        mainPanel.add(gamePanel);
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(GravityWell::new);
    }
}
