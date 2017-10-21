package uk.co.datadisk.babybird;

import uk.co.datadisk.mycommonmethods.FileIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {

    private static final String WALL_IMAGE_FILE = "/images/wall.jpg";

    private static BufferedImage wallImage;
    private static int width = 72;
    private static int height = 600;

    private int x = FlightPanel.WIDTH;
    private int bottomY;
    private int topHeight;
    private int bottomHeight;
    private BufferedImage topImage;
    private BufferedImage bottomImage;

    public Wall() {
        if(wallImage == null){
            wallImage = FileIO.readImageFile(this, WALL_IMAGE_FILE);
            width = wallImage.getWidth();
            height = wallImage.getHeight();
        }

        topHeight = 100;
        int gap = 150;
        bottomY = topHeight + gap;
        bottomHeight = FlightPanel.HEIGHT - bottomY;

        topImage = wallImage.getSubimage(0, height - topHeight, width, topHeight);
        bottomImage = wallImage.getSubimage(0,0, width, bottomHeight);
    }

    public void draw(Graphics g){
        if(wallImage == null){
            g.setColor(Color.CYAN);
            g.fillRect(300, 0, width, height);
            g.fillRect(300, bottomY, width, bottomHeight);
        } else {
            g.drawImage(topImage, 300, 0, null);
            g.drawImage(bottomImage, 300, bottomY, null);
        }
    }
}
