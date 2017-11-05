package uk.co.datadisk.blitz.model;

import java.util.ArrayList;

public class Player {
    private static final int NUMBER_OF_TOKEN = 3;

    private int id;
    private String name;
    private boolean human = false;
    private boolean show = true;
    private int tokens = NUMBER_OF_TOKEN;
    private boolean rapped = false;
    private ArrayList<Card> cards = new ArrayList<>();

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
}
