package uk.co.datadisk.blitz.view;

import uk.co.datadisk.blitz.controller.BlitzController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 530354890307724023L;

    public static final int SPACING = 4;
    public static final int GAP = 20;
    public static final int MARGIN = 10;
    public static final int FONT_SIZE = 20;
    private static final int CARD_WIDTH = BlitzController.CARD_WIDTH;
    private static final int CARD_HEIGHT = BlitzController.CARD_HEIGHT;
    private static final int PLAYER_WIDTH = CARD_WIDTH * 4 + SPACING * 3;
    private static final int PLAYER_HEIGHT = CARD_HEIGHT + FONT_SIZE * 3 + SPACING * 2;
    private static final int WIDTH = PLAYER_WIDTH * 2 + GAP + MARGIN * 2;
    private static final int HEIGHT = PLAYER_HEIGHT * 2 + CARD_HEIGHT + GAP * 2 + MARGIN * 2;

    private static final int DECK_X = WIDTH / 2 - CARD_WIDTH - SPACING / 2;
    private static final int DECK_Y = MARGIN + PLAYER_HEIGHT + GAP;
    private static final int DISCARD_X = DECK_X + CARD_WIDTH + SPACING;
    private static final int DISCARD_Y = DECK_Y;

    private BufferedImage deck;
    private BufferedImage discard;

    public GamePanel(BlitzController controller, BufferedImage cardBackImage) {
        Font font = new Font(Font.DIALOG, Font.BOLD, FONT_SIZE);
        setFont(font);
        deck = cardBackImage;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size  = new Dimension(WIDTH, HEIGHT);
        return size;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(0,0, WIDTH, HEIGHT);

        // players


        // deck
        g.drawImage(deck, DECK_X, DECK_Y, null);

        // discards
        g.drawImage(discard, DISCARD_X, DISCARD_Y, null);

        // moving cards
    }

    public void setDiscard(BufferedImage discard) {
        this.discard = discard;
        repaint();
    }
}

