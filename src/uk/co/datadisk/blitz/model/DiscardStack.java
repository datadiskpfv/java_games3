package uk.co.datadisk.blitz.model;

import java.util.ArrayList;

public class DiscardStack {

    private ArrayList<Card> cards = new ArrayList<>();

    public void reset() {
        cards.clear();
    }

    public void add(Card card){
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    public Card getTopCard() {
        Card topCard = null;
        int size = cards.size();
        if(size > 0){
            int top = size - 1;
            topCard = cards.get(top);
        }
        return topCard;
    }

    public Card removeTopCard() {
        Card topCard = null;
        int size = cards.size();
        if(size > 0){
            int top = size - 1;
            topCard = cards.remove(top);
        }
        return topCard;
    }
}
