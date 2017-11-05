package uk.co.datadisk.blitz.controller;

import uk.co.datadisk.blitz.view.BlitzViewWindow;
import uk.co.datadisk.blitz.view.GamePanel;
import uk.co.datadisk.mycommonmethods.FileIO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BlitzController {

    private static final String CARDS_FILE = "/images/cards.png";
    private static final String CARD_BACK_FILE = "/images/cardBack.png";
    public static final int CARD_WIDTH = 30;
    public static final int CARD_HEIGHT = 50;
    private static final int SUITS = 4;
    private static final int RANKS = 13;
    private static final int CARD_BACK_INDEX = SUITS * RANKS;
    private static final int NUMBER_OF_PLAYERS = 3;
    private static final int NUMBER_OF_IMAGES = SUITS * RANKS + 1;
    private BufferedImage cardImages[] = new BufferedImage[NUMBER_OF_IMAGES];
    private GamePanel gamePanel;

    private BlitzViewWindow window;

    private BlitzController() {

        readCardImages();
        window = new BlitzViewWindow(this, cardImages[CARD_BACK_INDEX]);

        gamePanel = window.getGamePanel();

    }

    private void readCardImages() {
        BufferedImage cardsImage = FileIO.readImageFile(this, CARDS_FILE);
        int i = 0;
        for (int suit = 0; suit < SUITS; suit++) {
            for (int rank = 0; rank < RANKS; rank++) {
                int x = rank * CARD_WIDTH;
                int y  = suit *CARD_HEIGHT;
                cardImages[i] = cardsImage.getSubimage(x,y,CARD_WIDTH,CARD_HEIGHT);
                i++;
            }
        }
        cardImages[CARD_BACK_INDEX] = FileIO.readImageFile(this, CARD_BACK_FILE);
    }

    public ActionListener getDealListener() {
        ActionListener dealListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.enableDealButton(false);
            }
        };

        return dealListener;
    }

    public ActionListener getRapListener() {
        ActionListener rapListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.enableRapButton(false);
            }
        };
        return rapListener;
    }

    public MouseAdapter getMouseAdapter() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                clicked(x,y);
            }
        };
        return adapter;
    }

    private void clicked(int x, int y) {
        if(gamePanel.hasDeckAt(x,y)){
            System.out.println("The deck was clicked");
        }
        if(gamePanel.hasDiscardAt(x, y)){
            System.out.println("The discard was clicked");
        }

        int clickedCard = gamePanel.getCardIndexForPlayerAt(2, x, y);
        if(clickedCard > -1){
            System.out.println("Clicked card: " + clickedCard);
        }
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        SwingUtilities.invokeLater(BlitzController::new);
    }
}
