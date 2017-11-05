package uk.co.datadisk.blitz.model;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    private Random rand = new Random();

    public Deck() {
        reset();
    }

    public void reset() {
        cards.clear();
        int numberOfSuits = Card.SUITS.length();
        int numberOfRanks = Card.RANKS.length;
        int numberOfCards = numberOfSuits * numberOfRanks;
        for (int id = 0; id < numberOfCards; id++) {
            int cardsSize = cards.size();
            int insertAt = 0;
            if(cardsSize > 0){
                insertAt = rand.nextInt(cardsSize + 1);
            }
            Card card = new Card(id);
            cards.add(insertAt, card);
        }
    }

    public Card deal() {
        Card card = cards.remove(0);
        return card;
    }
}
