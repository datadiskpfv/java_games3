package uk.co.datadisk.blitz.controller;

import uk.co.datadisk.blitz.view.BlitzViewWindow;

import javax.swing.*;

public class BlitzController {

    private BlitzViewWindow window;

    private BlitzController() {
        window = new BlitzViewWindow(this);
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        SwingUtilities.invokeLater(BlitzController::new);
    }
}
