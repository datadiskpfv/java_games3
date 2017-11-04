package uk.co.datadisk.gravitywell;

import java.awt.*;

public class SpeedoMeter {

    private static final int X = 600;
    private static final int Y = 80;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 200;
    private static final int MARKER_X = X - 6;
    private static final int MARKER_WIDTH = WIDTH + 12;
    private static final int MARKER_HEIGHT = 4;
    private static final int HALF_MARKER_HEIGHT = MARKER_HEIGHT / 2;

    private int markerY = HEIGHT;
    private int safeSpeedHeight;
    private int safeSpeedY;

    public SpeedoMeter(double maxSafeLandingSpeed) {
        safeSpeedHeight = (int) (HEIGHT * maxSafeLandingSpeed);
        safeSpeedY = (Y + HEIGHT) - safeSpeedHeight;
    }

    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(X, Y, WIDTH, HEIGHT);

        // safe speed
        g.setColor(Color.YELLOW);
        g.fillRect(X, safeSpeedY, WIDTH, safeSpeedHeight);

        // current speed
        g.setColor(Color.BLACK);
        g.fillRect(MARKER_X, Y + markerY, MARKER_WIDTH, MARKER_HEIGHT);
    }

    public void setSpeed(double speed){
        int speedHeight = (int) (HEIGHT * Math.abs(speed));
        markerY = HEIGHT - speedHeight;

        // keep marker within speedometer
        if(markerY < 0) {
            markerY = 0;
        } else if (markerY > HEIGHT) {
            markerY = HEIGHT;
        }
    }
}
