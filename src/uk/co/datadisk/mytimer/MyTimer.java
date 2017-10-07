package uk.co.datadisk.mytimer;

import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class MyTimer extends JFrame {

    private static final long serialVersionUID = 6658663244818335767L;

    Font font = new Font(Font.DIALOG, Font.BOLD, 36);

    private TimerPanel timerPanel = new TimerPanel(10, font);

    public MyTimer() {
        initGUI();

        setTitle("My Timer");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("My Timer");
        add(titleLabel, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(timerPanel);
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(MyTimer::new);
    }
}
