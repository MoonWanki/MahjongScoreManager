package com.octopusfantasy.mahjongscoremanager.model;

public class Dice {

    private long layoutAngle, dice1Angle, dice2Angle;
    private int dice1Num, dice2Num;

    public Dice() {
        layoutAngle = getRandom(0, 359, 0);
        dice1Angle = getRandom(0, 359, 0);
        dice2Angle = getRandom(0, 359, 0);
        dice1Num = 0;
        dice2Num = 0;
    }

    public int getNextLayoutAngle() {
        layoutAngle += 22;
        return (int) layoutAngle % 360;
    }

    public int getNextDice1Angle() {
        dice1Angle -= 17;
        return (int) (-dice1Angle) % 360;
    }

    public int getNextDice2Angle() {
        dice2Angle -= 17;
        return (int) (-dice2Angle) % 360;
    }

    public int getNextDice1Num() {
        dice1Num = getRandom(1, 6, dice1Num);
        return dice1Num;
    }

    public int getNextDice2Num() {
        dice2Num = getRandom(1, 6, dice2Num);
        return dice2Num;
    }

    public int getDice1Num() {
        return dice1Num;
    }

    public int getDice2Num() {
        return dice2Num;
    }

    private int getRandom(int min, int max, int except) {
        int num = (int) Math.floor(Math.random() * (max-min+1) + min);
        if(num == except) return getRandom(min, max, except);
        else if(num == max+1) return getRandom(min, max, except);
        else return num;
    }

}
