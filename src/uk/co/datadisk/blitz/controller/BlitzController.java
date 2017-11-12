package uk.co.datadisk.blitz.controller;

import uk.co.datadisk.blitz.model.BlitzModel;
import uk.co.datadisk.blitz.model.Card;
import uk.co.datadisk.blitz.model.Player;
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
    private BlitzModel model = new BlitzModel(this, NUMBER_OF_PLAYERS);

    private BlitzViewWindow window;

    private BlitzController() {
        readCardImages();
        window = new BlitzViewWindow(this, cardImages[CARD_BACK_INDEX]);
        gamePanel = window.getGamePanel();
        start();
    }

    private void start() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Player player = model.getPlayer(i);
            String name = player.getName();
            int tokens = player.getTokens();
            gamePanel.setPlayer(i, name, tokens);
        }

        Player dealer = model.getDealer();
        String dealText = dealer.getName() + " Deal";
        window.setDealButtonText(dealText);
        window.enableDealButton(true);
        gamePanel.setDiscard(null);
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
                model.start();
            }
        };

        return dealListener;
    }

    public ActionListener getRapListener() {
        ActionListener rapListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.enableRapButton(false);
                model.myPlayerRapped();
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
        int state = model.getState();
        switch(state){
            case BlitzModel.STATE_MY_TURN_DRAW:
                if (gamePanel.hasDeckAt(x, y)){
                    model.myPlayerDrewCard();
                    window.enableRapButton(false);
                } else if (gamePanel.hasDiscardAt(x, y)){
                    model.myPlayerTookDiscard();
                    window.enableRapButton(false);
                }
                break;
            case BlitzModel.STATE_MY_TURN_DISCARD:
                int myPlayerId = model.getMyPlayerId();
                int cardIndex = gamePanel.getCardIndexForPlayerAt(myPlayerId, x, y);
                if(cardIndex > -1){
                    model.myPlayerDiscard(cardIndex);
                }
                break;
        }
    }

    public void showNewHand() {
        gamePanel.clearCardsAndInfo();
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

    private ArrayList<BufferedImage> createCardImageForPlayer(Player player){
        ArrayList<BufferedImage> playerCardImages = new ArrayList<>();
        ArrayList<Card> cards = player.getCards();
        for (int i = 0; i < cards.size(); i++) {
            BufferedImage cardImage = cardImages[CARD_BACK_INDEX];
            if (player.shouldShow()) {
                Card card = cards.get(i);
                int id = card.getId();
                cardImage = cardImages[id];
            }
            playerCardImages.add(cardImage);
        }
        return playerCardImages;
    }

    public void showDrawCard(Player player, Card card){
        int cardId = CARD_BACK_INDEX;
        if (player.shouldShow()){
            cardId = card.getId();
        }
        BufferedImage cardImage = cardImages[cardId];
        int playerId = player.getId();
        int insertAt = player.getCardPosition(card);
        ArrayList<BufferedImage> playerCards = createCardImageForPlayer(player);
        gamePanel.moveDeckToPlayer(cardImage, playerId, insertAt);
        gamePanel.updateCardsForPlayer(playerId, playerCards);
    }

    public void showDealDiscard(Card card) {
        int id = card.getId();
        BufferedImage cardImage = cardImages[id];
        gamePanel.moveDeckToDiscard(cardImage);
        gamePanel.setDiscard(cardImage);
    }

    public void showMyTurn() {
        if (!model.hasSomeoneRapped()){
            window.enableRapButton(true);
        }
    }

    public void showTakeDiscard(Player player, Card discard, Card nextDiscard) {
        BufferedImage nextDiscardImage = null;
        if (nextDiscard !=  null){
            int nextDiscardId = nextDiscard.getId();
            nextDiscardImage = cardImages[nextDiscardId];
        }
        int discardId = discard.getId();
        BufferedImage discardImage = cardImages[discardId];
        int playerId = player.getId();
        int position = player.getCardPosition(discard);
        ArrayList<BufferedImage> playerCards = createCardImageForPlayer(player);
        gamePanel.setDiscard(nextDiscardImage);
        gamePanel.moveDiscardToPlayer(discardImage, playerId, position);
        gamePanel.updateCardsForPlayer(playerId, playerCards);
    }

    public void showRap(Player player) {
        int playerId = player.getId();
        gamePanel.addInfoForPlayer(playerId, "Rapped");
    }

    public void showDiscard(Player player, Card card, int position) {
        int cardId = card.getId();
        BufferedImage cardImage = cardImages[cardId];
        int playerId = player.getId();
        ArrayList<BufferedImage> playerCardImages = createCardImageForPlayer(player);
        gamePanel.updateCardsForPlayer(playerId, playerCardImages);
        gamePanel.movePlayerToDiscard(cardImage, playerId, position);
        gamePanel.setDiscard(cardImage);
    }

    public void showBlitz(Player player) {
        int playerId = player.getId();
        gamePanel.addInfoForPlayer(playerId, "Blitz");
    }

    public void showDealer(Player dealer) {
        String name = dealer.getName();
        String dealText  = name + " Deal";
        window.setDealButtonText(dealText);
        window.enableDealButton(true);
    }

    public void showEndGame(Player winner) {
        String name = winner.getName();
        String message = name + " won!, Do you want to play again?";
        int option = JOptionPane.showConfirmDialog(null, message, "Play Again?", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION){
            model.reset();
            start();
        } else {
            System.exit(0);
        }
    }

    public void showUpdatedCards(Player player) {
        int playerId = player.getId();
        ArrayList<BufferedImage> playerCardImages = createCardImageForPlayer(player);
        gamePanel.updateCardsForPlayer(playerId, playerCardImages);
    }

    public void showLoseTokens(Player player, int count) {
        int playerId = player.getId();
        int totalTokens = player.getTokens();
        gamePanel.updateTokensForPlayer(playerId, totalTokens);
        String info = "Lost " + count;
        gamePanel.addInfoForPlayer(playerId, info);
    }
}