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

    private int hintRow = -1;
    private int hintCol = -1;
    private boolean showHint = false;

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

    public void setInitialBalls() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell();
                while(twoMatchToLeft(row,col) || twoMatchAbove(row,col)){
                    cells[row][col] = new Cell();
                }
            }
        }

        if(!validMovesRemaining()){
            setInitialBalls();
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

        // hint
        if(showHint){
            g.setColor(Color.WHITE);
            int x = hintCol * Cell.SIZE;
            int y = hintRow * Cell.SIZE;
            g.drawRect(x,y,Cell.SIZE, Cell.SIZE);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean validSwap = false;
                Cell temp = new Cell();
                temp.copy(cells[row1][col1]);
                cells[row1][col1].copy(cells[row2][col2]);
                cells[row2][col2].copy(temp);

                int points = 0;
                boolean repeat = true;
                while(repeat) {
                    points = 0;

                    for (int row = 0; row < ROWS; row++) {
                        int newPoints = markChainsAndGetPointsInRow(row);
                        points += newPoints;
                    }

                    for (int col = 0; col < COLS; col++) {
                        int newPoints = markChainsAndGetPointsInCol(col);
                        points += newPoints;
                    }

                    if(showHint){
                        showHint = false;
                        if((row1 == hintRow && col1 == hintCol) || (row2 == hintRow && col2 == hintCol)){
                            int loosePoints = points -2;
                            game.addToScore(loosePoints);
                        }
                    }

                    repaint();

                    if (points > 0) {
                        game.addToScore(points);
                        removeMarkChains();
                        validSwap = true;
                        endGameIfDone();
                    } else {
                        repeat = false;
                    }
                }
                // if not a valid swap, put swapped balls back
                if (!validSwap){
                    cells[row2][col2].copy(cells[row1][col1]);
                    cells[row1][col1].copy(temp);
                }
            }
        }).start();
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
                points += calculatePoints(count);
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

        points += calculatePoints(count);
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
                points += calculatePoints(count);
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
        points += calculatePoints(count);
        return points;
    }

    private int calculatePoints(int count){
        int points = 0;
        if (count == 3){
            points = 10;
        } else if (count == 4){
            points = 15;
        } else if (count >= 5){
            points = 20;
        }

        return points;
    }

    private void removeMarkChains(){
        // pause before clearing the marked chains
        pause(1000);

        // remove marked chains
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if(cells[row][col].isInChain()){
                    cells[row][col].setEmpty();
                }
            }
        }
        repaint();
        pause(500);

        // loop through all rows and columns,
        // starting with the last row
        for (int row = ROWS - 1; row >= 0; row--) {
            boolean foundEmptyInCol = false;
            for (int col = 0; col < COLS; col++) {
                // if a cell is empty, copy the first non-empty
                // cell above it
                if(cells[row][col].isEmpty()){
                    foundEmptyInCol = true;
                    boolean foundBall = false;
                    for (int r = row-1; r >= 0 && !foundBall; r--) {
                        if(!cells[r][col].isEmpty()){
                            cells[row][col].copy(cells[r][col]);
                            cells[r][col].setEmpty();
                            foundBall = true;
                        }
                    }
                    // if no ball was found above it,
                    // fill the cell with a new ball
                    if (!foundBall){
                        cells[row][col] = new Cell();
                    }
                }
            }
            // repaint and pause after each row
            // so the user can see one row drop
            // at a time
            repaint();
            if (foundEmptyInCol){
                pause(500);
            }
        }
    }

    private void pause(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e){}
    }

    private boolean inChain(int row, int col){
        boolean inChain = twoMatchAbove(row, col)
                || twoMatchAbove(row+1, col)
                || twoMatchAbove(row+2, col)
                || twoMatchToLeft(row, col)
                || twoMatchToLeft(row, col+1)
                || twoMatchToLeft(row, col+2);
        return inChain;
    }

    private boolean isValidSwap(int row1, int col1, int direction){
        boolean valid = false;

        // set the rows and columns of the cells to swap
        int row2 = row1;
        int col2 = col1;

        switch (direction){
            case DIRECTION_LEFT:
                col2 = col1 - 1;
                break;
            case DIRECTION_RIGHT:
                col2 = col1 + 1;
                break;
            case DIRECTION_UP:
                row2 = row1 - 1;
                break;
            case DIRECTION_DOWN:
                row2 = row1 + 1;
                break;
        }

        // swap the two cells
        Cell temp = new Cell();
        temp.copy(cells[row1][col1]);
        cells[row1][col1].copy(cells[row2][col2]);
        cells[row2][col2].copy(temp);

        // if either of the swapped cells is now in a chain
        // it is a valid swap
        if (inChain(row1, col1)||inChain(row2,col2)){
            valid = true;
            hintRow = row1;
            hintCol = col1;
        }
        // swap the two cells back
        temp.copy(cells[row2][col2]);
        cells[row2][col2].copy(cells[row1][col1]);
        cells[row1][col1].copy(temp);

        return valid;
    }

    private boolean validMovesRemaining() {
        boolean movesRemaining = false;

        for (int row = 0; row < ROWS && !movesRemaining; row++) {
            for (int col = 0; col < COLS && !movesRemaining; col++) {
                if ( row < ROWS - 1){
                    movesRemaining = isValidSwap(row, col, DIRECTION_DOWN);
                }
                if(!movesRemaining && col < COLS -1){
                    movesRemaining = isValidSwap(row, col, DIRECTION_RIGHT);
                }
            }
        }
        return movesRemaining;
    }

    private void endGameIfDone() {
        boolean gameOver = !validMovesRemaining();
        if(gameOver){
            String message = "There are no more moves, game over....., Do you want to play again?";
            int option = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION){
                game.restart();
            } else {
                System.exit(0);
            }
        }
    }

    public void showHint() {
        showHint = true;
        repaint();
    }
}
