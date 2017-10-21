package uk.co.datadisk.babybird;

import uk.co.datadisk.mycommonmethods.FileIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bird {

    private static final String BIRD_FLAP_UP = "/images/babyBirdFlapUp.gif";
    private static final String BIRD_GLIDE = "/images/babyBirdGlide.gif";
    private static final String BIRD_FLAP_DOWN = "/images/babyBirdFlapDown.gif";

    private static final int FLAP_UP = 0;
    private static final int FLAP_GLIDE = 1;
    private static final int FLAP_DOWN = 2;
    private static final float GRAVITY = 0.5f;
    private static final int FLAP_FORCE = -8;

    BufferedImage birds[] = new BufferedImage[3];
    private int width;
    private int height;
    int x = 10;
    int y = 10;
    private int flap = FLAP_GLIDE;

    private boolean flapping = false;
    private float changeY = 0;
    private int panelHeight;

    public Bird(int panelHeight) {
        this.panelHeight = panelHeight;

        birds[FLAP_UP] = FileIO.readImageFile(this, BIRD_FLAP_UP);
        birds[FLAP_GLIDE] = FileIO.readImageFile(this, BIRD_GLIDE);
        birds[FLAP_DOWN] = FileIO.readImageFile(this, BIRD_FLAP_DOWN);

        width = birds[0].getWidth();
        height = birds[0].getHeight();
    }

    public void draw(Graphics g){
        g.drawImage(birds[flap], x, y, null);
    }

    public void startFlapping() {
        flapping = true;
        changeY = FLAP_FORCE;
    }

    public void move() {
        int changeYInt = (int) changeY;
        int distanceFromTop = y + height + changeYInt;

        if(distanceFromTop > panelHeight){
            y = panelHeight - height;
            changeY = 0;
        } else {
            y += changeY;
            changeY += GRAVITY;
            if(changeY > 0){
                flapping = false;
            }
        }

        if(flapping){
            flap++;
            flap %= 3;
        } else {
            flap = FLAP_GLIDE;
        }
    }
}
