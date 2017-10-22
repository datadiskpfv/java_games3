package uk.co.datadisk.fallingbricks;

import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class FallingBricks extends JFrame {
    private static final long serialVersionUID = -1181206630042541237L;

    ScorePanel scorePanel = new ScorePanel(0, Color.CYAN);
    BricksPanel bricksPanel = new BricksPanel();

    public FallingBricks() {
        initGUI();

        setTitle("Baby Bird");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("Falling Bricks");
        add(titleLabel, BorderLayout.PAGE_START);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.CYAN);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // score panel
        mainPanel.add(scorePanel);

        // bricks panel
        mainPanel.add(bricksPanel);
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(FallingBricks::new);
    }

}
