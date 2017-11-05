package uk.co.datadisk.blitz.view;

import uk.co.datadisk.blitz.controller.BlitzController;
import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class BlitzViewWindow extends JFrame {
    private static final long serialVersionUID = 5738888870738203100L;

    private BlitzController controller;
    private GamePanel gamePanel;
    private JButton dealButton = new JButton("Deal");
    private JButton rapButton = new JButton("Rap");

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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.PAGE_END);

        ActionListener dealListener = controller.getDealListener();
        dealButton.addActionListener(dealListener);
        buttonPanel.add(dealButton);

        ActionListener rapListener = controller.getRapListener();
        rapButton.addActionListener(rapListener);
        rapButton.setEnabled(false);
        buttonPanel.add(rapButton);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void enableDealButton(boolean enable){
        dealButton.setEnabled(enable);
    }

    public void setDealButtonText(String text){
        dealButton.setText(text);
    }

    public void enableRapButton(boolean enable){
        rapButton.setEnabled(enable);
    }
}
