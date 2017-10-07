package uk.co.datadisk.speedwords;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = -8199907012506480613L;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    private SpeedWords speedWords;

    public GamePanel(SpeedWords speedWords) {
        this.speedWords = speedWords;
    }

    public void paintComponent(Graphics g) {
        // draw background
        g.setColor(SpeedWords.TAN);
        g.drawRect(0,0, WIDTH, HEIGHT);

        LetterTile tile = new LetterTile("P");
        tile.draw(g, 100, 100);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        return size;
    }
}