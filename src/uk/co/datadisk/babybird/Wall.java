package uk.co.datadisk.babybird;

import uk.co.datadisk.mycommonmethods.FileIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Wall {

    private static final String WALL_IMAGE_FILE = "/images/wall.jpg";
    private static final int CHANGE_X = -10;

    private static final int TOP_MIN = 100;
    private static final int TOP_MAX = 300;
    private static final int GAP_MIN = 100;
    private static final int GAP_MAX = 240;
    private static final int POINTS_OFFSET = 80;


    private static BufferedImage wallImage;
    private static int width = 72;
    private static int height = 600;

    public int x = FlightPanel.WIDTH;
    private int bottomY;
    private int topHeight;
    private int bottomHeight;
    private BufferedImage topImage;
    private BufferedImage bottomImage;

    private Random rand = new Random();
    private int points = 1;
    private String pointsString;
    private int pointsX;

    public Wall(FontMetrics fm) {
        if(wallImage == null){
            wallImage = FileIO.readImageFile(this, WALL_IMAGE_FILE);
            width = wallImage.getWidth();
            height = wallImage.getHeight();
        }

        int range = GAP_MAX - GAP_MIN;
        int pick = rand.nextInt(range);
        int gap = pick + GAP_MIN;

        // calculate ratio of picked gap to possible range
        float ratio = (float) pick / range;
        // turn ratio into a number 1 to 10
        int intValue = (int) (ratio * 10);
        // turn value into a number 1 to 10 so the
        // smallest gap has the highest score
        points = 10 - intValue;

        pointsString = "" + points;
        int pointsWidth = fm.stringWidth(pointsString);
        pointsX = ( width / 2) - (pointsWidth / 2);

        range = TOP_MAX - TOP_MIN;
        pick = rand.nextInt(range);
        topHeight = pick + TOP_MIN;

        bottomY = topHeight + gap;
        bottomHeight = FlightPanel.HEIGHT - bottomY;

        topImage = wallImage.getSubimage(0, height - topHeight, width, topHeight);
        bottomImage = wallImage.getSubimage(0,0, width, bottomHeight);
    }

    public int getChangeX() {
        return CHANGE_X;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return 0;
    }

    public void draw(Graphics g){
        if(wallImage == null){
            g.setColor(Color.CYAN);
            g.fillRect(x, 0, width, height);
            g.fillRect(x, bottomY, width, bottomHeight);
        } else {
            g.drawImage(topImage, x, 0, null);
            g.drawImage(bottomImage, x, bottomY, null);
        }

        g.setColor(Color.BLACK);
        g.drawString(pointsString, x + pointsX, bottomY + POINTS_OFFSET);
    }

    public void move() {
        x += CHANGE_X;
    }

    public boolean isPastWindowEdge() {
        int rightEdgeX = x + width;
        return rightEdgeX < 0;
    }

    public Rectangle getTopBounds() {
        Rectangle bounds = new Rectangle(x,0,width,topHeight);
        return bounds;
    }

    public Rectangle getBottomBounds() {
        Rectangle bounds = new Rectangle(x,bottomY,width,bottomHeight);
        return bounds;
    }

    public int getPoints() {
        return points;
    }
}
