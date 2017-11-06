package uk.co.datadisk.blitz.model;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private static final int NUMBER_OF_TOKEN = 3;

    private int id;
    private String name;
    private boolean human = false;
    private boolean show = true;
    private int tokens = NUMBER_OF_TOKEN;

    private boolean rapped = false;
    private ArrayList<Card> cards = new ArrayList<>();
    private Random rand = new Random();

    public Player(int id, String name, boolean human, boolean show) {
        this.id = id;
        this.name = name;
        this.human = human;
        this.show = show;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getTokens() {
        return tokens;
    }

    public void loseTokens(int count){
        tokens -= count;
    }

    public boolean isHuman() {
        return human;
    }

    public boolean shouldShow() {
        return show;
    }

    public void setShow(boolean show){
        this.show = show;
    }

    public void rap() {
        rapped = true;
    }

    public boolean hasRapped() {
        return rapped;
    }

    public boolean isOut() {
        return tokens < 0;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void startNewHand() {
        rapped = false;
        cards.clear();
    }

    public int addCard(Card newCard){
        int i = 0;
        int numberOfCards = cards.size();
        boolean done = false;
        while(i < numberOfCards && !done){
            Card card = cards.get(i);
            if(newCard.isGreaterThan(card)){
                i++;
            } else {
                done = true;
            }
        }
        cards.add(i, newCard);
        return i;
    }

    public int getCardPosition(Card checkCard){
        int position = -1;
        int checkId = checkCard.getId();
        for (int i = 0; i < cards.size() && position == -1; i++) {
            Card card = cards.get(i);
            int id = card.getId();
            if(id == checkId){
                position = i;
            }
        }
        return position;
    }

    public int[] getPointsInSuits() {
        int[] pointsInSuits = new int[4];

        // add the points in each suit
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            int suit = card.getSuit();
            pointsInSuits[suit] += card.getValue();
        }
        return pointsInSuits;
    }

    public int getPointsInHand() {
        int[] pointsInSuits = getPointsInSuits();

        // get points for the suit that has most points
        int highestPoints = pointsInSuits[0];
        for (int i = 1; i < pointsInSuits.length; i++) {
            if (pointsInSuits[i] > highestPoints){
                highestPoints = pointsInSuits[i];
            }
        }
        return highestPoints;
    }

    public int getLowestCardIndex() {
        // get the points in each suit
        int[] pointsInSuits = getPointsInSuits();

        // get the suit with the least points
        int worstSuit = 0;

        // worst points should start with a nuber
        // higher than the maximum possible value
        // a hand may hold up to 4 cards, worst
        // points must be less than 42
        int worstPoints = 42;
        for (int i = 0; i < pointsInSuits.length; i++) {
            if(pointsInSuits[i] > 0  && pointsInSuits[i] < worstPoints){
                worstPoints = pointsInSuits[i];
                worstSuit = i;
            }
        }

        // since the cards are arranged by suit and
        // value, the lowest card in the worst suit
        // should be the first card in the worst suit
        int i = 0;
        Card checkCard = cards.get(0);
        int checkSuit = checkCard.getSuit();
        while( checkSuit != worstSuit){
            i++;
            checkCard = cards.get(i);
            checkSuit = checkCard.getSuit();
        }

        return i;
    }

    public Card removeCardAt(int index){
        Card card = cards.remove(index);
        return card;
    }

    public void removeCard(Card removeCard){
        boolean removed = false;
        int compareId = removeCard.getId();
        for (int i = 0; i < cards.size() && !removed; i++) {
            Card card = cards.get(i);
            int id = card.getId();
            if(id == compareId){
                cards.remove(i);
                removed = true;
            }
        }
    }

    public boolean shouldRap() {
        boolean shouldRap = false;
        int pointsInHand = getPointsInHand();
        if (pointsInHand > 16){
            int choice = rand.nextInt(3);
            if(choice == 0){
                shouldRap = true;
            }
        }
        return shouldRap;
    }
}
