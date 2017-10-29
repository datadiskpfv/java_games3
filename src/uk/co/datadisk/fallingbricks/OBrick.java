package uk.co.datadisk.fallingbricks;

import uk.co.datadisk.mycommonmethods.FileIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBrick extends Brick {
    private static final String BRICK_FILE = "/images/yellowBrick.jpg";
    private static final boolean TILES[][] =
            {
                    {true, true},
                    {true, true}
            };

    public static BufferedImage image;

    public OBrick(int row, int col) {
        super(row, col);

        if(image == null){
            image = FileIO.readImageFile(this, BRICK_FILE);
        }
    }

    public void draw(Graphics g){
        int rows = TILES.length;
        int cols = TILES[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(TILES[row][col]){
                    int tileX = x + (TILE_SIZE * col);
                    int tileY = y + (TILE_SIZE * row);
                    g.drawImage(image, tileX, tileY, null);
                }
            }
        }
    }

    public int getNumberOfRows() {
        return TILES.length;
    }
    public int getNumberOfCols() {
        return TILES[0].length;
    }

    public void rotateLeft() {}

    public void rotateRight() {}

    public boolean hasTileAt(int row, int col){
        return TILES[row][col];
    }

    public BufferedImage getTileImage(){
        return image;
    }
}
