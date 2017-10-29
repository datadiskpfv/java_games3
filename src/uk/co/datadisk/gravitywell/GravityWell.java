package uk.co.datadisk.gravitywell;

import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class GravityWell extends JFrame {

    private static final long serialVersionUID = -1131180335321200917L;

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
