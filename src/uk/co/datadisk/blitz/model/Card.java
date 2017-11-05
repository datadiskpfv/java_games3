package uk.co.datadisk.blitz.model;

public class Card {
    public static final String RANKS[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    public static final int VALUES[] = {2, 3, 4 ,5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

    public static final String SUITS = "HDSC";

    private int id;
    private int value;
    private int suit;

    public Card(int id) {
        this.id = id;
        int index = id % 13;
        value = VALUES[index];
        suit = id / 13;

    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    public boolean isGreaterThan(Card compareCard){
        return id > compareCard.getId();
    }

    public boolean equals(Card compareCard){
        return id == compareCard.getId();
    }
}
