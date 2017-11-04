package uk.co.datadisk.catandmouse;

import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class CatAndMouse extends JFrame {

    private static final long serialVersionUID = 2364953475567794079L;

    ScorePanel scorePanel = new ScorePanel(0, Color.yellow);
    GamePanel gamePanel = new GamePanel(scorePanel);

    public CatAndMouse() {
        initGUI();

        setTitle("Cat and Mouse");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("Cat and Mouse");
        add(titleLabel, BorderLayout.PAGE_START);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.GREEN);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

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

        EventQueue.invokeLater(CatAndMouse::new);
    }
}
