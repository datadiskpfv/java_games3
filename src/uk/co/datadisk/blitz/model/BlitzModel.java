package uk.co.datadisk.blitz.model;

import uk.co.datadisk.blitz.controller.BlitzController;

import java.util.Random;

public class BlitzModel {
    public static final int STATE_WAIT_FOR_INPUT = 0;
    public static final int STATE_NEW_HAND = 1;
    public static final int STATE_DEAL = 2;
    public static final int STATE_COMPUTER_DRAW = 3;
    public static final int STATE_COMPUTER_DISCARD = 4;
    public static final int STATE_NEXT_PLAYER = 5;
    public static final int STATE_MY_TURN_DRAW = 6;
    public static final int STATE_MY_TURN_DISCARD = 7;
    public static final int STATE_SETTLE_RAP = 8;
    public static final int STATE_SETTLE_BLITZ = 9;
    public static final int STATE_END_OF_HAND = 10;

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
        // find lowest points
        int lowestPoints = 31;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (!players[i].isOut()) {
                int points = players[i].getPointsInHand();
                if (points < lowestPoints){
                    lowestPoints = points;
                }
            }
        }

        // if current player has lowest points
        // he loses 2 tokens and no one else loses any
        int rappedPlayersPoints = player.getPointsInHand();
        if(rappedPlayersPoints == lowestPoints){
            player.loseTokens(2);
            controller.showLoseTokens(player 2);
        } else {
            // every player with lowest points
            // loses one token
            for (int i = 0; i < numberOfPlayers; i++) {
                Player p = players[i];
                if (i != active){
                    if(!p.isOut() && p.getPointsInHand() == lowestPoints){
                        p.loseTokens(1);
                        controller.showLoseToken(p,1);
                    }
                }
            }
        }

        dealerId = getNextDealerId();
        Player dealer = players[dealerId];
        controller.showDealer(dealer);
        state = STATE_END_OF_HAND;
        play();
    }

    private void computerDiscard() {
        int lowestCardIndex = player.getLowestCardIndex();
        Card discard = player.removeCardAt(lowestCardIndex);
        playerDiscard(discard, lowestCardIndex);
    }

    private void computerDraw() {
        boolean shouldTakeDiscard = false;

        // if the discard stack is not empty
        if(!discardStack.isEmpty()) {
            // check the points in the hand without the discard
            int priorPoints = player.getPointsInHand();

            // add the discard to the hand
            Card discard = discardStack.getTopCard();
            player.addCard(discard);

            // remove the lowest card in the hand
            int lowestCardIndex = player.getLowestCardIndex();
            Card removedCard = player.removeCardAt(lowestCardIndex);

            // check the points in the hand again
            int afterPoints = player.getPointsInHand();

            // if there are now more points, it improved the hand
            if(priorPoints > afterPoints){
                shouldTakeDiscard = true;
            }

            // put the cards back as they were
            player.addCard(removedCard);
            player.removeCard(discard);
        }

        if (shouldTakeDiscard) {
            Card discard = discardStack.removeTopCard();
            player.addCard(discard);
            Card nextDiscard = discardStack.getTopCard();
            controller.showTakeDiscard(player, discard, nextDiscard);
            state = STATE_COMPUTER_DISCARD;
        } else if (!someoneRapped && player.shouldRap()){
            someoneRapped = true;
            player.rap();
            controller.showRap(player);
            state = STATE_NEXT_PLAYER;
        } else {
            Card card = deck.deal();
            player.addCard(card);
            controller.showDrawCard(player, card);
            state = STATE_COMPUTER_DISCARD;
        }
        play();
    }

    private void nextPlayer() {
        active = (active + 1) % numberOfPlayers;
        player = players[active];
        if(player.isOut()){
            state = STATE_NEXT_PLAYER;
        } else if (player.hasRapped()){
            state = STATE_SETTLE_RAP;
        } else if (player.isHuman()){
            state = STATE_MY_TURN_DRAW;
            controller.showMyTurn();
        } else {
            state = STATE_COMPUTER_DRAW;
        }
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

    public boolean hasSomeoneRapped() {
        return someoneRapped;
    }

    private void playerDiscard(Card discard, int removeCardIndex){
        discardStack.add(discard);
        controller.showDiscard(player, discard, removeCardIndex);
        if (player.getPointsInHand() == 31){
            controller.showBlitz(player);
            state = STATE_SETTLE_BLITZ;
        } else {
            state = STATE_NEXT_PLAYER;
        }
        play();
    }

    public void myPlayerDrewCard() {
        Card card = deck.deal();
        player.addCard(card);
        controller.showDrawCard(player, card);
        state = STATE_MY_TURN_DISCARD;
        play();
    }

    public void myPlayerTookDiscard() {
        Card discard = discardStack.removeTopCard();
        player.addCard(discard);
        Card nextDiscard = discardStack.getTopCard();
        controller.showTakeDiscard(player, discard, nextDiscard);
        state = STATE_MY_TURN_DISCARD;
        play();
    }

    public void myPlayerDiscard(int index){
        Card discard = player.removeCardAt(index);
        playerDiscard(discard, index);
    }

    public int getState() {
        return state;
    }

    public int getNextDealerId() {
        boolean done = false;
        int nextDealerId = (dealerId + 1) % numberOfPlayers;

        while(!done) {
            if(!players[nextDealerId].isOut()){
                done = true;
            } else {
                nextDealerId = (nextDealerId + 1) % numberOfPlayers;
            }
        }
        return nextDealerId;
    }
}