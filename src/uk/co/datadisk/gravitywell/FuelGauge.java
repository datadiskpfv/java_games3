package uk.co.datadisk.gravitywell;

import java.awt.*;

public class FuelGauge {

    private static final int X = 10;
    private static final int Y = 360;
    private static final int WIDTH = 620;
    private static final int HEIGHT = 20;
    private static final double FULL_TANK = 1000d;
    private double currentAmount = FULL_TANK;
    private int currentWidth = WIDTH;

    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRect(X, Y, currentWidth, HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(X,Y, WIDTH, HEIGHT);
    }

    public void burn(int burnAmount) {
        currentAmount -= burnAmount;

        if(currentAmount < 0 ){
            currentAmount = 0;
        }

        calculateWidth();
    }

    private void calculateWidth() {
        double percent = currentAmount / FULL_TANK;
        currentWidth = (int) (percent * WIDTH);
    }

    public void addFuel(double addFuel){
        currentAmount += addFuel;
        // do not overfill
        if(currentAmount > FULL_TANK){
            currentAmount = FULL_TANK;
        }
        calculateWidth();
    }

    public boolean isEmpty() {
        return currentAmount <= 0;
    }

    public void refill(){
        currentAmount = FULL_TANK;
        calculateWidth();
    }
}
