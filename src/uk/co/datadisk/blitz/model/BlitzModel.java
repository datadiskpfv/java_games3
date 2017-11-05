package uk.co.datadisk.blitz.model;

import uk.co.datadisk.blitz.controller.BlitzController;

import java.util.Random;

public class BlitzModel {
    private static final int STATE_WAIT_FOR_INPUT = 0;
    private static final int STATE_NEW_HAND = 1;
    private static final int STATE_DEAL = 2;
    private static final int STATE_COMPUTER_DRAW = 3;
    private static final int STATE_COMPUTER_DISCARD = 4;
    private static final int STATE_NEXT_PLAYER = 5;
    private static final int STATE_MY_TURN_DRAW = 6;
    private static final int STATE_MY_TURN_DISCARD = 7;
    private static final int STATE_SETTLE_RAP = 8;
    private static final int STATE_SETTLE_BLITZ = 9;
    private static final int STATE_END_OF_HAND = 10;

    private BlitzController controller;
    private int numberOfPlayers;
    private Player[] players;
    private int myPlayerId;
    private Random rand = new Random();
    private int dealerId;
    private int state = STATE_WAIT_FOR_INPUT;
    private int active = 0;
    private Player player;
    private boolean someoneRapped = false;
    private Deck deck = new Deck();
    private DiscardStack discardStack = new DiscardStack();

    public BlitzModel(BlitzController controller, int numberOfPlayers) {
        this.controller = controller;
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        myPlayerId = numberOfPlayers - 1;

        // the last player is human
        int computerPlayers = numberOfPlayers - 1;
        for (int id = 0; id < computerPlayers; id++) {
            String name = "Player " + (id + 1);
            players[id] = new Player(id, name, false, true);
        }
        players[myPlayerId] = new Player(myPlayerId, "You", true, true);
        dealerId = rand.nextInt(numberOfPlayers);
    }

    public Player getPlayer(int index){
        return players[index];
    }

    public int getMyPlayerId() {
        return myPlayerId;
    }

    public Player getDealer() {
        return players[dealerId];
    }

    public void play() {
        switch(state){
            case STATE_NEW_HAND:
                newHand();
                break;
            case STATE_DEAL:
                deal();
            case STATE_NEXT_PLAYER:
                nextPlayer();
                break;
            case STATE_COMPUTER_DRAW:
                computerDraw();
                break;
            case STATE_COMPUTER_DISCARD:
                computerDiscard();
                break;
            case STATE_SETTLE_RAP:
                settleRap();
                break;
            case STATE_SETTLE_BLITZ:
                settleBlitz();
                break;
            case STATE_END_OF_HAND:
                endOfHand();
                break;

        }
    }

    public void start(){
        state = STATE_NEW_HAND;
        play();
    }

    private void endOfHand() {

    }

    private void settleBlitz() {

    }

    private void settleRap() {

    }

    private void computerDiscard() {

    }

    private void computerDraw() {

    }

    private void nextPlayer() {

    }

    private void deal() {
        // deal 3 cards to each player
        for (int c = 0; c < 3; c++) {
            for (int p = 0; p < numberOfPlayers; p++) {
                int dealTo = (dealerId + p + 1) % numberOfPlayers;
                if(!players[dealTo].isOut()){
                    Card card = deck.deal();
                    players[dealTo].addCard(card);
                    controller.showDrawCard(players[dealTo], card);
                }
            }
        }

        // deal a discard
        Card card = deck.deal();
        discardStack.add(card);
        controller.showDealDiscard(card);
        state = STATE_NEXT_PLAYER;
        play();
    }

    private void newHand() {
        active = dealerId;
        player = players[active];
        someoneRapped = false;
        deck.reset();
        discardStack.reset();
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i].startNewHand();
        }
        controller.showNewHand();
        state = STATE_DEAL;
        play();
    }
}