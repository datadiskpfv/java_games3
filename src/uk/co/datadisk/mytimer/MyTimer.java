package uk.co.datadisk.mytimer;

import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class MyTimer extends JFrame {

    private static final long serialVersionUID = 6658663244818335767L;

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
