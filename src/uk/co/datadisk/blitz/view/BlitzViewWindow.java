package uk.co.datadisk.blitz.view;

import uk.co.datadisk.blitz.controller.BlitzController;
import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BlitzViewWindow extends JFrame {
    private static final long serialVersionUID = 5738888870738203100L;

    private BlitzController controller;
    private GamePanel gamePanel;

    public BlitzViewWindow(BlitzController controller, BufferedImage cardBackImage) {
        this.controller = controller;
        gamePanel = new GamePanel(controller, cardBackImage);

        initGUI();

        setTitle("Blitz");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("Blitz");
        add(titleLabel, BorderLayout.PAGE_START);

        // game panel
        add(gamePanel, BorderLayout.CENTER);

        // button panel
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
