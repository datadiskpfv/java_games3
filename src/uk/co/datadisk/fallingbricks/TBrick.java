package uk.co.datadisk.fallingbricks;

import uk.co.datadisk.mycommonmethods.FileIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TBrick extends Brick {
    private static final String BRICK_FILE = "/images/whiteBrick.jpg";
    private static final boolean TILES[][][] =
            {
             {{true, true, true},{false, true, false}},
             {{false,true},{true,true},{false,true}},
             {{false, true, false},{true, true, true}},
             {{true, false}, {true, true}, {true, false}}
            };

    private int state = 0;
    public static BufferedImage image;

    public TBrick(int row, int col) {
        super(row, col);
        if(image == null){
            image = FileIO.readImageFile(this, BRICK_FILE);
        }
    }

    public void draw(Graphics g){
        int rows = TILES[state].length;
        int cols = TILES[state][0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(TILES[state][row][col]){
                    int tileX = x + (TILE_SIZE * col);
                    int tileY = y + (TILE_SIZE * row);
                    g.drawImage(image, tileX, tileY, null);
                }
            }
        }
    }

    public int getNumberOfRows() {
        return TILES[state].length;
    }
    public int getNumberOfCols() {
        return TILES[state][0].length;
    }

    public void rotateLeft() {
        state--;
        if(state < 0){
            state = TILES.length - 1;
        }
    }

    public void rotateRight() {
        state++;
        if(state >= TILES.length){
            state = 0;
        }
    }

    public boolean hasTileAt(int row, int col){
        return TILES[state][row][col];
    }

    public BufferedImage getTileImage(){
        return image;
    }
}
