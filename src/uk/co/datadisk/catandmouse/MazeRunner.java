package uk.co.datadisk.catandmouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class MazeRunner {

    // direction in clockwise rotation order
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
    public static final int DIRECTION_NONE = 4;

    protected Maze maze;
    protected int x;
    protected int y;
    protected int direction;
    protected BufferedImage[] image = new BufferedImage[4];
    protected int[] offsetX = new int[4];
    protected int[] offsetY = new int[4];
    protected int changeX = 0;
    protected int changeY = 0;
    protected int speed;
    protected State state;

    protected void setImage(int direction, String fileName) throws IOException {
        InputStream input = getClass().getResourceAsStream(fileName);
        image[direction] = ImageIO.read(input);
        int imageWidth = image[direction].getWidth();
        offsetX[direction] = (Maze.CELL_SIZE - imageWidth) / 2;
        int imageHeight = image[direction].getHeight();
        offsetY[direction] = (Maze.CELL_SIZE - imageHeight) / 2;
    }

    public void draw(Graphics g){
        int xPos = x + offsetX[direction];
        int yPos = y + offsetY[direction];
        g.drawImage(image[direction], xPos, yPos, null);
    }

    public void turn(int direction){
        this.direction = direction;
        switch(direction) {
            case DIRECTION_UP:
                changeX = 0;
                changeY = -1;
                break;
            case DIRECTION_DOWN:
                changeX = 0;
                changeY = 1;
                break;
            case DIRECTION_LEFT:
                changeX = -1;
                changeY = 0;
                break;
            case DIRECTION_RIGHT:
                changeX = 1;
                changeY = 0;
                break;
        }
    }

    public void move() {
        state.performAction();
    }

    public void setState(State newState) {
        if(newState != state){
            // exit the last state before
            // switching to new state
            state.exit();
            state = newState;
            state.enter();
        }
    }

    protected boolean wallInDirection(int direction){
        boolean wall = false;
        int checkX = x;
        int checkY = y;

        switch(direction){
            case DIRECTION_UP:
                checkY = y - speed;
                break;
            case DIRECTION_DOWN:
                int imageHeight = image[direction].getHeight();
                checkY = y + speed + imageHeight + 1;
                break;
            case DIRECTION_LEFT:
                checkX = x - speed;
                break;
            case DIRECTION_RIGHT:
                int imageWidth = image[direction].getWidth();
                checkX = x + speed + imageWidth + 1;
                break;
        }

        if (maze.wallAt(checkX, checkY)) {
            wall = true;
        }
        return wall;
    }

    public void moveIntoCell() {
        int intoX = x % Maze.CELL_SIZE;
        int intoY = y % Maze.CELL_SIZE;

        switch(direction){
            case DIRECTION_UP:
                if (intoY > offsetY[direction]){
                    // center in this cell
                    y -= intoY;
                }
                break;
            case DIRECTION_DOWN:
                if (intoY > offsetY[direction]){
                    // center in next cell
                    y = y - intoY + Maze.CELL_SIZE;
                }
                break;
            case DIRECTION_LEFT:
                if (intoX > offsetX[direction]){
                    // center in this cell
                    x -= intoX;
                }
                break;
            case DIRECTION_RIGHT:
                if (intoX > offsetX[direction]){
                    // center in next cell
                    x = x - intoX + Maze.CELL_SIZE;
                }
                break;
        }
    }

    public Rectangle getBounds() {
        int width = image[direction].getWidth();
        int height = image[direction].getHeight();
        Rectangle bounds = new Rectangle(x, y, width, height);
        return bounds;
    }
}