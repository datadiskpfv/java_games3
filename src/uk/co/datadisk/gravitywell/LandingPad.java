package uk.co.datadisk.gravitywell;

import java.awt.*;

public class LandingPad {

    private static final int OFFSET_LABEL_X = 20;
    private static final int OFFSET_LABEL_Y = 30;

    private int x1 = 0;
    private int x2 = 0;

    private int y = 0;
    private int multiplier = 1;
    private String label = "x ";
    private int labelX;
    private int labelY;

    public LandingPad(int x1, int x2, int y, int multiplier) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
        this.multiplier = multiplier;

        label += multiplier;
        labelX = x1 + OFFSET_LABEL_X;
        labelY = y + OFFSET_LABEL_Y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(x1,y,x2,y);
        g.setColor(Color.BLACK);
        g.drawString(label, labelX, labelY);
    }

    public boolean contains(Lander lander){
        boolean contains = false;
        Rectangle landerBounds = lander.getBounds();
        int landerX1 = landerBounds.x;
        int landerX2 = landerX1 + landerBounds.width;
        int landerBottom = landerBounds.y + landerBounds.height;
        if(landerX1 >= x1 && landerX2 <= x2 && landerBottom >= y) {
            contains = true;
        }
        return contains;
    }

    public int getMultiplier(){
        return multiplier;
    }
}
