package uk.co.datadisk.babybird;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BirdNestPanel extends JPanel {
    private static final long serialVersionUID = 4989753376175383344L;

    private static final int MARGIN = 10;
    private static final int SPACING = 20;
    private BufferedImage image;
    private int count = 0;
    private int birdWidth;
    private int birdHeight;
    private int width;
    private int height;

    public BirdNestPanel(BufferedImage image, int count) {
        this.image = image;
        this.count = count;

        birdWidth = image.getWidth();
        birdHeight = image.getHeight();
        width = (birdWidth * count) + (2*MARGIN) + ((count - 1) * SPACING);
        height = birdHeight + (2*MARGIN);
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width,height);

        for (int i = 0; i < count; i++) {
            int x = MARGIN + (birdWidth + SPACING) * i;
            g.drawImage(image, x, MARGIN, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(width, height);
        return size;
    }

    public int removedBird() {
        count--;
        repaint();
        return count;
    }

    public void setBirdCount(int count){
        this.count = count;
        repaint();
    }
}
