package uk.co.datadisk.babybird;

import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class BabyBird extends JFrame {

    private static final long serialVersionUID = -3506304889404490460L;

    ScorePanel scorePanel = new ScorePanel(0, Color.GREEN);
    FlightPanel flightpanel = new FlightPanel(this);

    public BabyBird() {
        initGUI();

        setTitle("Baby Bird");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("Baby Bird");
        add(titleLabel, BorderLayout.PAGE_START);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.GREEN);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // score panel
        mainPanel.add(scorePanel);

        // flight panel
        mainPanel.add(flightpanel);

        // bottom panel

        // bird nest panel

    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(BabyBird::new);
    }
}
