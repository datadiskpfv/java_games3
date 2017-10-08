package uk.co.datadisk.speedwords;

import sun.awt.image.BufferedImageGraphicsConfig;
import uk.co.datadisk.mycommonmethods.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LetterTile extends JPanel {

    private static final long serialVersionUID = 1404545826538853216L;

    public static final int SIZE = 40;
    private static final String IMAGE_NAME = "/images/WoodTile.jpg";

    private static final Font BIG_FONT = new Font(Font.DIALOG, Font.BOLD, 30);
    private static final Font SMALL_FONT = new Font(Font.DIALOG, Font.BOLD, 12);

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LETTER_POINTS[] = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    private String letter;
    private BufferedImage image;
    FontMetrics bigFM = getFontMetrics(BIG_FONT);
    FontMetrics smallFM = getFontMetrics(SMALL_FONT);
    private int points;

    public LetterTile(String letter) {
        this.letter = letter;

        if (image == null) {
            image = FileIO.readImageFile(this, IMAGE_NAME);
        }

        int index = ALPHABET.indexOf(letter);
        points = LETTER_POINTS[index];
    }

    public void draw(Graphics g, int x, int y) {

        if (image == null) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, SIZE, SIZE);
        } else {
            g.drawImage(image,x,y,SIZE,SIZE,null);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, SIZE - 1, SIZE - 1);

        g.setFont(BIG_FONT);
        int letterWidth = bigFM.stringWidth(letter);
        int letterX = (SIZE - letterWidth) / 2 + x;
        int letterY =  SIZE * 3 / 4 + y;
        g.drawString(letter, letterX, letterY);

        g.setFont(SMALL_FONT);
        String pointsString = "" + points;
        int pointsWidth = smallFM.stringWidth(pointsString);
        int pointsX = SIZE - pointsWidth - 2 + x;
        int pointsY = SIZE * 34 /40 + y;
        g.drawString("" + points, pointsX, pointsY);
    }

    public Dimension getPreferredSize() {
        Dimension size = new Dimension(SIZE, SIZE);
        return size;
    }

    public String getLetter() {
        return letter;
    }

    public int getPoints() {
        return points;
    }
}
