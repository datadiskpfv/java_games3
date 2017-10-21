package uk.co.datadisk.matchthree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BallPanel extends JPanel {

    private static final long serialVersionUID = -245035603746622888L;

    private static final int ROWS = 10;
    private static final int COLS = 6;
    private static final int WIDTH = COLS * Cell.SIZE;
    private static final int HEIGHT = ROWS * Cell.SIZE;

    private MatchThree game;

    private Cell cells[][] = new Cell[ROWS][COLS];

    public static final int DIRECTION_NONE = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_UP = 3;
    public static final int DIRECTION_DOWN = 4;

    private static final Cursor HORIZONTAL_ARROWS = new Cursor(Cursor.W_RESIZE_CURSOR);
    private static final Cursor VERTICAL_ARROWS = new Cursor(Cursor.N_RESIZE_CURSOR);
    private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

    public BallPanel(MatchThree game) {
        this.game = game;
        setLayout(new GridLayout(ROWS, COLS));
        setInitialBalls();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                clicked(x,y);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mouseMovedTo(x,y);
            }
        });
    }

    private void mouseMovedTo(int x, int y) {
        int direction = getSwapDirection(x,y);

        switch(direction){
            case DIRECTION_LEFT:
            case DIRECTION_RIGHT:
                setCursor(HORIZONTAL_ARROWS);
                break;
            case DIRECTION_UP:
            case DIRECTION_DOWN:
                setCursor(VERTICAL_ARROWS);
                break;
            default:
                setCursor(DEFAULT_CURSOR);
        }
    }

    private void clicked(int x, int y) {
        int row = y / Cell.SIZE;
        int col = x / Cell.SIZE;
        int direction = getSwapDirection(x,y);

        switch(direction){
            case DIRECTION_LEFT:
                swap(row, col, row, col-1);
                break;
            case DIRECTION_RIGHT:
                swap(row, col, row , col+1);
                break;
            case DIRECTION_UP:
                swap(row, col, row-1, col);
                break;
            case DIRECTION_DOWN:
                swap(row, col, row+1, col);
                break;
        }
    }

    private void setInitialBalls() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell();
                while(twoMatchToLeft(row,col) || twoMatchAbove(row,col)){
                    cells[row][col] = new Cell();
                }
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

    public int getSwapDirection(int x, int y){
        int direction = DIRECTION_NONE;

        // which cell was clicked?
        int cellSize = Cell.SIZE;
        int col = x / cellSize;
        int row = y / cellSize;

        // how far was the click from each edge
        int left = x % cellSize;
        int right = cellSize - left;
        int top = y % cellSize;
        int bottom = cellSize - top;

        // if not in the first column and left edge is closest
        if (col > 0 && left <= right && left <= top && left <= bottom){
            direction = DIRECTION_LEFT;
        }

        // if not in the last column and right edge is closest
        else if (col < COLS -1 && right <= left && right <= top && right <= bottom){
            direction = DIRECTION_RIGHT;
        }

        // if not in the first row and top edge is closest
        else if (row > 0 && top <= left && top <= right && top <= bottom){
            direction = DIRECTION_UP;
        }

        // if not in last row and bottom edge is closest
        else if (row < ROWS - 1 && bottom <= left && bottom <= right && bottom <= top){
            direction = DIRECTION_DOWN;
        }

        return direction;
    }

    private void swap(int row1, int col1, int row2, int col2){
        Cell temp = new Cell();
        temp.copy(cells[row1][col1]);
        cells[row1][col1].copy(cells[row2][col2]);
        cells[row2][col2].copy(temp);

        repaint();
    }

    private boolean twoMatchAbove(int row, int col){
        boolean match = false;

        if(row > 1 && row < ROWS && col >= 0 && col < COLS){
            int color1 = cells[row][col].getColor();
            int color2 = cells[row-1][col].getColor();
            int color3 = cells[row-2][col].getColor();

            if(color1 == color2 && color1 == color3){
                match = true;
            }
        }
        return match;
    }

    private boolean twoMatchToLeft(int row, int col){
        boolean match = false;
        if (row >= 0 && row < ROWS && col > 1 && col < COLS){
            int color1 = cells[row][col].getColor();
            int color2 = cells[row][col-1].getColor();
            int color3 = cells[row][col-2].getColor();

            if(color1 == color2 && color1 == color3){
                match = true;
            }
        }
        return match;
    }

    private int markChainsAndGetPointsInRow(int row){
        int points = 0;

        // get the color of the first ball
        int color = cells[row][0].getColor();
        int count = 1;

        // loop through each column of the row
        for (int col = 1; col < COLS; col++) {

            // count the balls in each color
            int nextColor = cells[row][col].getColor();
            if(nextColor == color){
                count++;
            } else {
                color = nextColor;
                count = 1;
            }

            // mark chains of three or more. When count
            // reaches 3, also mark previous two.
            if (count == 3){
                cells[row][col].setInChain(true);
                cells[row][col-1].setInChain(true);
                cells[row][col-2].setInChain(true);
            } else if (count > 3){
                cells[row][col].setInChain(true);
            }
        }
        return points;
    }

    private int markChainsAndGetPointsInCol(int col){
        int points = 0;

        // get the color of the first ball
        int color = cells[0][col].getColor();
        int count = 1;

        // loop through each column of the row
        for (int row = 1; row < ROWS; row++) {

            // count the balls in each color
            int nextColor = cells[row][col].getColor();
            if(nextColor == color){
                count++;
            } else {
                color = nextColor;
                count = 1;
            }

            // mark chains of three or more. When count
            // reaches 3, also mark previous two.
            if (count == 3){
                cells[row][col].setInChain(true);
                cells[row-1][col].setInChain(true);
                cells[row-2][col].setInChain(true);
            } else if (count > 3){
                cells[row][col].setInChain(true);
            }
        }
        return points;
    }
}
