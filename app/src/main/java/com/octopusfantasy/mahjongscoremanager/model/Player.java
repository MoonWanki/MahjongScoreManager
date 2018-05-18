package com.octopusfantasy.mahjongscoremanager.model;

public class Player {

    private String name;
    private int score;
    private boolean isDealer;
    private boolean isClosed;
    private boolean isRiched;
    private int wind;

    public Player(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public boolean isRiched() {
        return isRiched;
    }

    public void setRiched(boolean isRiched) {
        this.isRiched = isRiched;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public int getWind() {
        return wind;
    }

    public void scoreUpBy(int num) {
        score += num;
    }

    public void scoreDownBy(int num) {
        score -= num;
    }
}
