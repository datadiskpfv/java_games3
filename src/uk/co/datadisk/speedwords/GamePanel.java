package uk.co.datadisk.speedwords;

import uk.co.datadisk.mycommonmethods.FileIO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = -8199907012506480613L;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    public static final int START_X = WIDTH / 2 - 7 * LetterTile.SIZE / 2;
    public static final int START_Y = HEIGHT / 2 - LetterTile.SIZE / 2;
    public static final String FILE_NAME = "/enable1_7.txt";

    private SpeedWords speedWords;
    private ArrayList<TileSet> tileSets = new ArrayList<>();
    private ArrayList<String> sevenLetterWords = new ArrayList<>();
    private Random rand = new Random();

    public GamePanel(SpeedWords speedWords) {

        this.speedWords = speedWords;

        sevenLetterWords = FileIO.readTextFile(this, FILE_NAME);
        restart();
    }

    public void paintComponent(Graphics g) {
        // draw background
        g.setColor(SpeedWords.TAN);
        g.drawRect(0,0, WIDTH, HEIGHT);

        // draw all current tile sets
        for (int i = tileSets.size()-1; i >= 0 ; i--) {
            TileSet tileSet = tileSets.get(i);
            tileSet.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        return size;
    }

    private void restart() {
        tileSets.clear();
        int range = sevenLetterWords.size();
        int choose = rand.nextInt(range);
        String s = sevenLetterWords.get(choose);
        TileSet tileSet = new TileSet(s, START_X, START_Y);
        tileSets.add(tileSet);
        repaint();
    }
}