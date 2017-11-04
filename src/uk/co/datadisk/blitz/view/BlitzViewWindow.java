package uk.co.datadisk.blitz.view;

import uk.co.datadisk.blitz.controller.BlitzController;
import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class BlitzViewWindow extends JFrame {
    private static final long serialVersionUID = 5738888870738203100L;

    private BlitzController controller;

    public BlitzViewWindow(BlitzController controller) {
        this.controller = controller;

        initGUI();

        setTitle("Cat and Mouse");
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


        // button panel
    }
}
