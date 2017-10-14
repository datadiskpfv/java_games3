package uk.co.datadisk.speedwords;

import java.awt.*;
import java.util.ArrayList;

public class TileSet {

    public static final int HIGHLIGHT_WIDTH = 2;
    private ArrayList<LetterTile> tiles = new ArrayList<>();

    private int x = 0;
    private int y = 0;

    public TileSet(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i+1);
            LetterTile tile = new LetterTile(letter);
            tiles.add(tile);
            this.x = x;
            this.y = y;
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            LetterTile tile = tiles.get(i);
            int xPos = x + (LetterTile.SIZE  * i);
            tile.draw(g, xPos, y);
        }
    }

    public void changeXY(int changeX, int changeY){
        x += changeX;
        y += changeY;
    }

    public int getWidth() {
        int width = tiles.size() * LetterTile.SIZE;
        return width;
    }

    public boolean contains(int pointX, int pointY) {
        boolean contains = false;
        int width = getWidth();
        int height = LetterTile.SIZE;
        if( pointX >= x && pointX < x + width && pointY >= y && pointY < y + height) {
            contains = true;
        }
        return contains;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < tiles.size(); i++) {
            LetterTile tile = tiles.get(i);
            String s2 =  tile.getLetter();
            s += s2;
        }
        return s;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumberOfTiles() {
        return tiles.size();
    }

    public LetterTile getTile(int i) {
        return tiles.get(i);
    }

    public TileSet removeAndReturn1TileAt(int clickedX, int clickedY) {
        TileSet tileSet = null;
        for (int i = 0; i < tiles.size() && tileSet == null; i++) {
            int tileLeftEdge = x + (LetterTile.SIZE * i);
            int tileRightEdge = tileLeftEdge + LetterTile.SIZE;
            int tileTopEdge = y;
            int tileBottomEdge = y + LetterTile.SIZE;
            if (clickedX >= tileLeftEdge && clickedX <= tileRightEdge && clickedY >= tileTopEdge && clickedY <= tileBottomEdge ) {
                LetterTile tile = tiles.get(i);
                String letter = tile.getLetter();
                tileSet = new TileSet(letter, tileLeftEdge, tileTopEdge);
                tiles.remove(i);
                if (i == 0) {
                    x += LetterTile.SIZE;
                }
            }
        }
        return tileSet;
    }

    public boolean insertTiles(TileSet droppedTiles) {
        boolean inserted = false;

        int droppedTilesWidth = droppedTiles.getWidth();
        int droppedTilesHeight = LetterTile.SIZE;
        int droppedTilesLeft = droppedTiles.getX();
        int droppedTilesRight = droppedTilesLeft + droppedTilesWidth;
        int droppedTilesTop = droppedTiles.getY();
        int droppedTilesBottom = droppedTilesTop + droppedTilesHeight;

        // if the dropped tile overlaps the row
        if (droppedTilesBottom > y && droppedTilesTop < y + droppedTilesHeight) {
            // if the dropped tile overlaps the left edge of the first tile

            if (droppedTilesRight >= x && droppedTilesRight <= x + LetterTile.SIZE) {
                // insert the dropped tiles before the tiles onto which they were dropped
                for (int i = 0; i < droppedTiles.getNumberOfTiles(); i++) {
                    LetterTile tile = droppedTiles.getTile(i);
                    tiles.add(i, tile);
                    inserted = true;
                }
                x -= droppedTilesWidth;
            } else {
                // otherwise, if the dropped tiles overlap any other tile
                for (int i = 0; i < tiles.size() && !inserted; i++) {
                    int compareX = x + i * LetterTile.SIZE;
                    if (droppedTilesLeft >= compareX && droppedTilesLeft <= compareX + LetterTile.SIZE) {
                        // insert the dropped tiles after the first overlapped tiles
                        for (int j = 0; j < droppedTiles.getNumberOfTiles(); j++) {
                            LetterTile tile = droppedTiles.getTile(j);
                            tiles.add(i + 1 + j, tile);
                            inserted = true;
                        }
                    }
                }
            }
        }
        return inserted;
    }
}