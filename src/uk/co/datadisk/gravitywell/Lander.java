package uk.co.datadisk.gravitywell;

import jdk.nashorn.internal.scripts.JO;
import uk.co.datadisk.mycommonmethods.FileIO;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Lander {

    private static final String IMAGE_DRIFT = "/images/lander.gif";
    private static final String IMAGE_LEFT = "/images/landerLeft.gif";
    private static final String IMAGE_RIGHT = "/images/landerRight.gif";
    private static final String IMAGE_UP = "/images/landerUP.gif";

    private static final int MOVE_DRIFT = 0;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_RIGHT = 2;
    private static final int MOVE_UP = 3;

    private static final int SIZE = 48;
    private static final double START_X = 50d;
    private static final double START_Y = 10d;

    private static final double MAX_LANDING_SPEED = .25d;
    private static final double GRAVITY = .003d;
    private static final double ACCELERATION = .015d;
    private static final String TORCH_SOUND = "/sounds/torch.wav";

    private BufferedImage image[] = new BufferedImage[4];
    private int imageIndex = MOVE_DRIFT;
    private double x = START_X;
    private double y = START_Y;

    private FuelGauge fuelGauge = new FuelGauge();
    private SpeedoMeter speedoMeter = new SpeedoMeter(MAX_LANDING_SPEED);

    private double moveX = 0;
    private double moveY = 0;
    private double accelerateX = 0;
    private double accelerateY = 0;
    private int landingPoints = 0;
    private Clip audioClip;

    public Lander() {
        image[MOVE_DRIFT] = FileIO.readImageFile(this, IMAGE_DRIFT);
        image[MOVE_LEFT] = FileIO.readImageFile(this, IMAGE_LEFT);
        image[MOVE_RIGHT] = FileIO.readImageFile(this, IMAGE_RIGHT);
        image[MOVE_UP] = FileIO.readImageFile(this, IMAGE_UP);

        try {
            URL url = getClass().getResource(TORCH_SOUND);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
        } catch(IOException e){
            String message = "Audio file " + TORCH_SOUND + " could not be opened";
            JOptionPane.showMessageDialog(null, message);
        } catch(UnsupportedAudioFileException e) {
            String message = "File " + TORCH_SOUND + " is not a valid audio file";
            JOptionPane.showMessageDialog(null, message);
        } catch (LineUnavailableException e ){
            String message = "Resources not available to open file " + TORCH_SOUND + " at thie time";
            JOptionPane.showMessageDialog(null, message);
        }
    }

    public void draw(Graphics g) {
        // lander
        int xPos = (int) x;
        int yPos = (int) y;

        // allow for extra thruster blast for right-moving
        if(imageIndex == MOVE_RIGHT){
            xPos -= 4;
        }
        g.drawImage(image[imageIndex], xPos, yPos, null);

        // fuel gauge
        fuelGauge.draw(g);

        // speedometer
        speedoMeter.draw(g);
    }

    public void move() {
        if(accelerateX != 0){
            moveX += accelerateX;
            fuelGauge.burn(1);
        }

        if( accelerateY != 0){
            moveY += accelerateY;
            fuelGauge.burn(1);
        }

        moveY += GRAVITY;
        x += moveX;
        y += moveY;

        speedoMeter.setSpeed(moveY);
    }

    public void accelerateLeft() {
        accelerateX = -ACCELERATION;
        imageIndex = MOVE_LEFT;
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void accelerateRight() {
        accelerateX = ACCELERATION;
        imageIndex = MOVE_RIGHT;
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void accelerateUp() {
        accelerateY = -ACCELERATION;
        imageIndex = MOVE_UP;
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopAcceleration() {
        accelerateX = 0;
        accelerateY = 0;
        imageIndex = MOVE_DRIFT;
        audioClip.stop();
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle((int)x, (int)y, SIZE, SIZE);
        return bounds;
    }

    public boolean landOn(LandingPad landingPad){
        boolean safeLanding = false;
        if(moveY <= MAX_LANDING_SPEED){
            safeLanding = true;
            double underLimit = MAX_LANDING_SPEED - moveY;
            int multiplier = landingPad.getMultiplier();
            landingPoints = (int) (underLimit * 1000 * multiplier);
        }
        return safeLanding;
    }

    public void addFuel(double amount){
        fuelGauge.addFuel(amount);
    }

    public int getLandingPoints() {
        return landingPoints;
    }

    public void nextLander() {
        x = START_X;
        y = START_Y;
        accelerateX = 0;
        accelerateY = 0;
        moveX = 0;
        moveY = 0;
        imageIndex = MOVE_DRIFT;
        landingPoints = 0;
    }

    public boolean isOutOfFuel() {
        return fuelGauge.isEmpty();
    }

    public void reset() {
        fuelGauge.refill();
        nextLander();
    }

    public void stopSound() {
        audioClip.stop();
    }
}
