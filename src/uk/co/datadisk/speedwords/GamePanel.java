package uk.co.datadisk.speedwords;

import jdk.nashorn.internal.scripts.JO;
import uk.co.datadisk.mycommonmethods.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = -8199907012506480613L;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    private static final int START_X = WIDTH / 2 - 7 * LetterTile.SIZE / 2;
    private static final int START_Y = HEIGHT / 2 - LetterTile.SIZE / 2;
    private static final String FILE_NAME = "/enable1_7.txt";

    private SpeedWords speedWords;
    private ArrayList<TileSet> tileSets = new ArrayList<>();
    private ArrayList<String> sevenLetterWords = new ArrayList<>();
    private Random rand = new Random();

    private TileSet movingTiles;
    private int mouseX;
    private int mouseY;

    private Dictionary dictionary = new Dictionary();
    private ArrayList<String> formedWords = new ArrayList<>();
    private boolean outOfTime = false;

    public GamePanel(SpeedWords speedWords) {

        this.speedWords = speedWords;

        sevenLetterWords = FileIO.readTextFile(this, FILE_NAME);
        restart();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int mouseButton = e.getButton();
                boolean leftClicked = MouseEvent.BUTTON1 == mouseButton;
                clicked(x,y, leftClicked);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                dragged(x, y);
            }
        });
    }

    private void dragged(int x, int y) {
        if( movingTiles != null) {
            int changeX = x - mouseX;
            int changeY = y - mouseY;
            movingTiles.changeXY(changeX, changeY);
            mouseX = x;
            mouseY = y;
            repaint();
        }
    }

    private void released() {
        // if dropped on other tiles, connect it to the tiles
        if (movingTiles != null) {
            boolean addedToTiles = false;
            for (int i = 0; i < tileSets.size() && !addedToTiles; i++) {
                TileSet tileSet = tileSets.get(i);
                addedToTiles = tileSet.insertTiles(movingTiles);
                if (addedToTiles) {
                    movingTiles = null;
                    checkWord(tileSet);
                }
            }
        }
        if (movingTiles != null) {
            String s = movingTiles.toString();
            int x = movingTiles.getX();
            int y = movingTiles.getY();
            TileSet newTileSet = new TileSet(s, x, y);
            tileSets.add(0,newTileSet);
            tileSets.add(newTileSet);
            movingTiles = null;
            checkWord(newTileSet);
        }
        repaint();
    }

    private void clicked(int x, int y, boolean leftClicked) {
        if (movingTiles == null && !outOfTime) {
            mouseX = x;
            mouseY = y;

            for (int i = 0; i < tileSets.size() && movingTiles == null; i++) {
                TileSet tileSet = tileSets.get(i);

                if (tileSet.contains(x,y)){
                    if (leftClicked) {
                        movingTiles = tileSet.removeAndReturn1TileAt(x, y);
                        if (tileSet.getNumberOfTiles() == 0) {
                            tileSets.remove(i);
                        }
                        else {
                            checkWord(tileSet);
                        }
                    } else {
                        movingTiles = tileSet;
                        tileSets.remove(i);
                    }
                }
            }
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        // draw background
        g.setColor(SpeedWords.TAN);
        g.fillRect(0,0, WIDTH, HEIGHT);

        // draw all current tile sets
        for (int i = tileSets.size()-1; i >= 0 ; i--) {
            TileSet tileSet = tileSets.get(i);
            tileSet.draw(g);
        }

        // draw moving tiles
        if (movingTiles != null) {
            movingTiles.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        return size;
    }

    public void restart() {
        tileSets.clear();
        formedWords.clear();
        int range = sevenLetterWords.size();
        int choose = rand.nextInt(range);
        String s = sevenLetterWords.get(choose);
        TileSet tileSet = new TileSet(s, START_X, START_Y);
        tileSets.add(tileSet);
        checkWord(tileSet);
        outOfTime = false;
        movingTiles = null;
        repaint();
    }

    private void checkWord(TileSet tileSet) {
        String s = tileSet.toString();
        boolean isAWord = dictionary.isAWord(s);
        boolean foundBefore = formedWords.contains(s);
        if(isAWord && !foundBefore) {
            tileSet.setValid(true);
            int points = tileSet.getPoints();
            speedWords.addToScore(points);

            // if this is the first word found
            // add it to the list
            if (formedWords.size() == 0) {
                formedWords.add(s);
            } else {
                // otherwise, insert word before the
                // first word it is alphabetically less than
                boolean added = false;
                for (int i = 0; i < formedWords.size() && !added; i++) {
                    String formedWord = formedWords.get(i);
                    if (s.compareTo(formedWord) < 0){
                        formedWords.add(i, s);
                        added = true;
                    }
                }

                // if the word is not less than any of the words
                // add it to the end of the list
                if (!added) {
                    formedWords.add(s);
                }
            }

            speedWords.setWordList(formedWords);
        } else {
            tileSet.setValid(false);
        }
    }

    public void setOutOfTime(boolean outOfTime){
        this.outOfTime = outOfTime;
    }
}