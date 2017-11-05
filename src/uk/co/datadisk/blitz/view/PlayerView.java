package uk.co.datadisk.blitz.view;

import uk.co.datadisk.blitz.controller.BlitzController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.management.BufferPoolMXBean;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayerView {
    private static final int CARD_WIDTH = BlitzController.CARD_WIDTH;
    private static final int CARD_HEIGHT = BlitzController.CARD_HEIGHT;
    private static final int SPACING = GamePanel.SPACING;
    private static final int FONT_SIZE = GamePanel.FONT_SIZE;
    private static final int TOKEN_SIZE = 10;
    private static final int TOKEN_Y_OFFSET = CARD_HEIGHT + SPACING;
    private static final int TOKEN_TEXT_Y_OFFSET = TOKEN_Y_OFFSET + FONT_SIZE;
    private static final int INFO_Y_OFFSET = TOKEN_TEXT_Y_OFFSET + SPACING + FONT_SIZE;

    private String name;
    private int tokens;
    private int x;
    private int y;
    private ArrayList<BufferedImage> cards = new ArrayList<>();
    private String info = "";

    public PlayerView(String name, int tokens, int x, int y) {
        this.name = name;
        this.tokens = tokens;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        //name
        g.drawString(name, x, y);

        // cards
        for (int i = 0; i < cards.size(); i++) {
            BufferedImage cardImage = cards.get(i);
            int cardX = x + i * (CARD_WIDTH + SPACING);
            int cardY = SPACING + y;
            g.drawImage(cardImage, cardX, cardY, null);
        }

        // tokens
        if (tokens > 0){
            for (int i = 0; i < tokens; i++) {
                int tokenX = x + (TOKEN_SIZE + SPACING);
                int tokenY = TOKEN_Y_OFFSET + y;
                g.fillOval(tokenX, tokenY, TOKEN_SIZE, TOKEN_SIZE);
            }
        } else if (tokens == 0){
            int textY = y + TOKEN_TEXT_Y_OFFSET;
            g.drawString("Riding", x, textY);
        } else {
            int textY = y + TOKEN_TEXT_Y_OFFSET;
            g.drawString("Out", x, textY);
        }

        // info
        int infoY = y + INFO_Y_OFFSET;
        g.drawString(info, x, infoY);
    }

    public void updateCards(ArrayList<BufferedImage> newCards){
        cards = newCards;
    }

    public void addInfo(String additionalInfo) {
        info += additionalInfo + " ";
    }

    public void updateTokens(int newTokens){
        tokens = newTokens;
    }

    public int getCardIndexAt(int pointX, int pointY){
        int index = -1;
        for (int i = 0; i < cards.size() && index == -1; i++) {
            int cardX = x + i * (CARD_WIDTH + SPACING);
            int cardY = y + SPACING;
            Rectangle cardBounds = new Rectangle(cardX, cardY, CARD_WIDTH, CARD_HEIGHT);
            if(cardBounds.contains(pointX, pointY)){
                index = i;
            }
        }
        return index;
    }

    public void clearCardsAndInfo() {
        cards.clear();
        info = "";
    }

}
