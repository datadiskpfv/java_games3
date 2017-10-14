package uk.co.datadisk.speedwords;

import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class SpeedWords extends JFrame {

    private static final long serialVersionUID = -8291874313657515783L;

    public static final Color TAN = new Color(222,191,168);
    private static final Font LIST_FONT = new Font(Font.DIALOG, Font.BOLD, 14);

    private ScorePanel scorePanel = new ScorePanel(0, TAN);
    private SpeedWordsTimerPanel swTimerPanel = new SpeedWordsTimerPanel(this, 60);

    private JTextArea textArea = new JTextArea();
    private GamePanel gamePanel = new GamePanel(this);

    public SpeedWords() {
        initGUI();

        setTitle("Speed Words");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {

        TitleLabel titleLabel = new TitleLabel("Speed Words");
        add(titleLabel, BorderLayout.PAGE_START);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(TAN);
        add(mainPanel, BorderLayout.CENTER);

        // left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(TAN);
        mainPanel.add(leftPanel);

        // score panel
        leftPanel.add(scorePanel);

        // timer panel
        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.red);
        leftPanel.add(timerPanel);

        swTimerPanel.setBackground(Color.red);
        timerPanel.add(swTimerPanel);

        // game panel
        leftPanel.add(gamePanel);

        // text area
        Insets insets = new Insets(4, 10, 10, 4);
        textArea.setMargin(insets);
        textArea.setEditable(false);
        textArea.setFont(LIST_FONT);

        JScrollPane scrollPanel = new JScrollPane(textArea);
        Dimension size  = new Dimension(100, 0);
        scrollPanel.setPreferredSize(size);
        mainPanel.add(scrollPanel);
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(SpeedWords::new);
    }
}