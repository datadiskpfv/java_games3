package uk.co.datadisk.catandmouse;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Cat extends MazeRunner {

    private static final String CAT_UP = "/images/catUp.png";
    private static final String CAT_DOWN = "/images/catDown.png";
    private static final String CAT_LEFT = "/images/catLeft.png";
    private static final String CAT_RIGHT = "/images/catRight.png";

    public static final int SPEED_WANDER = 5;
    public static final int SPEED_HUNT = 10;

    public final CatWanderState STATE_WANDER = new CatWanderState(this);
    public final CatHuntState STATE_HUNT = new CatHuntState(this);

    private Mouse mouse;
    private Random rand = new Random();

    public Cat(Mouse mouse, Maze maze) {
        this.mouse = mouse;
        this.maze = maze;

        x = maze.getCatX();
        y = maze.getCatY();
        direction = DIRECTION_LEFT;
        changeX = -1;
        changeY = 0;
        speed = SPEED_WANDER;
        state = STATE_WANDER;

        try {
            setImage(DIRECTION_UP, CAT_UP);
            setImage(DIRECTION_DOWN, CAT_DOWN);
            setImage(DIRECTION_LEFT, CAT_LEFT);
            setImage(DIRECTION_RIGHT, CAT_RIGHT);
        } catch (IOException e){
            String message = "Could not read the mouse image file";
            JOptionPane.showMessageDialog(null, message);
            System.exit(5);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void followPath() {
        if(!wallInDirection(direction)){
            // continue in current direction
            x += changeX * speed;
            y += changeY * speed;
        } else {
            moveIntoCell();
            // randomly choose a left or right turn
            int right = (direction + 1) % 4;
            int left = (direction + 3) % 4;
            int pick = rand.nextInt(2);
            if(pick == 0){
                // try a right turn first
                if (!wallInDirection(right)){
                    turn(right);
                } else {
                    turn(left);
                }
            } else {
                // try a left turn first
                if (!wallInDirection(left)){
                    turn(left);
                } else {
                    turn(right);
                }
            }
        }
    }

    public void hunt() {
        if (withinCell()) {
            // in which direction is the mouse?
            int nextDirection = direction;
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            int xDistance = mouseX - x;
            int yDistance = mouseY - y;
            int absXDistance = Math.abs(xDistance);
            int absYDistance = Math.abs(yDistance);

            if (absXDistance > absYDistance) {
                if (xDistance < 0) {
                    nextDirection = DIRECTION_LEFT;
                } else {
                    nextDirection = DIRECTION_RIGHT;
                }
            } else {
                if (yDistance < 0) {
                    nextDirection = DIRECTION_UP;
                } else {
                    nextDirection = DIRECTION_DOWN;
                }
            }

            if (!wallInDirection(nextDirection)) {
                turn(nextDirection);
            }
        }
        followPath();
    }

    private boolean withinCell() {
        Rectangle mazeBounds = maze.getBounds(x, y);
        Rectangle catBounds = getBounds();
        boolean within = mazeBounds.contains(catBounds);
        return within;
    }
}
