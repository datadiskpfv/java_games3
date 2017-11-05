package uk.co.datadisk.blitz.model;

public class Player {
    private static final int NUMBER_OF_TOKEN = 3;

    private int id;
    private String name;
    private boolean human = false;
    private boolean show = true;
    private int tokens = NUMBER_OF_TOKEN;
    private boolean rapped = false;

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
}
