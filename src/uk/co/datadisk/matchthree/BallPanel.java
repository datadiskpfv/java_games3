package uk.co.datadisk.matchthree;

import javax.swing.*;
import java.awt.*;

public class BallPanel extends JPanel {

    private static final long serialVersionUID = -245035603746622888L;

    private static final int ROWS = 10;
    private static final int COLS = 6;
    private static final int WIDTH = COLS * Cell.SIZE;
    private static final int HEIGHT = ROWS * Cell.SIZE;

    private MatchThree game;

    private Cell cells[][] = new Cell[ROWS][COLS];

    public BallPanel(MatchThree game) {
        this.game = game;
        setLayout(new GridLayout(ROWS, COLS));
        setInitialBalls();
    }

    private void setInitialBalls() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell();
            }
        }
        repaint();
    }

    public Dimension getPreferredSize(){
        Dimension size = new Dimension(WIDTH, HEIGHT);
        return size;
    }

    @Override
    public void paintComponent(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH, HEIGHT);

        // cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * Cell.SIZE;
                int y = row * Cell.SIZE;
                cells[row][col].draw(g, x, y);
            }
        }
    }
}
