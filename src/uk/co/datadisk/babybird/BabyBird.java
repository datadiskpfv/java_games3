package uk.co.datadisk.babybird;

import jdk.nashorn.internal.scripts.JO;
import uk.co.datadisk.mycomponents.TitleLabel;
import uk.co.datadisk.speedwords.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BabyBird extends JFrame {

    private static final long serialVersionUID = -3506304889404490460L;
    private static final int LIVES = 4;

    ScorePanel scorePanel = new ScorePanel(0, Color.GREEN);
    FlightPanel flightpanel = new FlightPanel(this);
    BirdNestPanel birdNestPanel;

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
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        add(bottomPanel, BorderLayout.PAGE_END);

        // bird nest panel
        Bird bird = flightpanel.getBird();
        BufferedImage birdImage = bird.getImage();
        birdNestPanel = new BirdNestPanel(birdImage, LIVES - 1);
        bottomPanel.add(birdNestPanel);
    }

    public void nextBird() {
        int birdsRemaining = birdNestPanel.removedBird();

        if(birdsRemaining < 0){
            String message = "No more birds remaining, Do you want to play again?";
            int option = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                birdNestPanel.setBirdCount(LIVES -1);
                scorePanel.reset();
                flightpanel.restart();
            } else {
                System.exit(0);
            }
        }
    }

    public void addToScore(int points){
        scorePanel.addToScore(points);
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
