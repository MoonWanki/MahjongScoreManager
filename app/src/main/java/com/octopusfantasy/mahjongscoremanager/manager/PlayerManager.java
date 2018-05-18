package com.octopusfantasy.mahjongscoremanager.manager;

import com.octopusfantasy.mahjongscoremanager.model.Player;

public class PlayerManager {

    private static PlayerManager instance;

    private Player player1, player2, player3, player4;

    public static synchronized PlayerManager getinstance() {
        if(instance==null)
            instance = new PlayerManager();
        return instance;
    }

    private PlayerManager() {
        player1 = new Player("동가");
        player2 = new Player("남가");
        player3 = new Player("서가");
        player4 = new Player("북가");
    }

    public void initPlayers(int mode) {
        if(mode == 1) setScoreAll(20000);
        else if(mode == 2) setScoreAll(25000);

        player1.setDealer(true); player1.setWind(1);
        player2.setDealer(false); player2.setWind(2);
        player3.setDealer(false); player3.setWind(3);
        player4.setDealer(false); player4.setWind(4);

        initPlayersStatus();
    }

    public void initPlayersStatus() {
        player1.setClosed(true); player1.setRiched(false);
        player2.setClosed(true); player2.setRiched(false);
        player3.setClosed(true); player3.setRiched(false);
        player4.setClosed(true); player4.setRiched(false);
    }

    public void changeDealer() {
        if(player1.isDealer()) {
            player1.setDealer(false);
            player2.setDealer(true);
            player2.setWind(1); player3.setWind(2); player4.setWind(3); player1.setWind(4);
        } else if(player2.isDealer()) {
            player2.setDealer(false);
            player3.setDealer(true);
            player3.setWind(1); player4.setWind(2); player1.setWind(3); player2.setWind(4);
        } else if(player3.isDealer()) {
            player3.setDealer(false);
            player4.setDealer(true);
            player4.setWind(1); player1.setWind(2); player2.setWind(3); player3.setWind(4);
        } else {
            player4.setDealer(false);
            player1.setDealer(true);
            player1.setWind(1); player2.setWind(2); player3.setWind(3); player4.setWind(4);
        }
    }

    public Player getPlayer(int no) {
        switch (no) {
            case 1: return player1;
            case 2: return player2;
            case 3: return player3;
            case 4: return player4;
            default: return null;
        }
    }

    public void setScoreAll(int score) {
        player1.setScore(score);
        player2.setScore(score);
        player3.setScore(score);
        player4.setScore(score);
    }

    public int getScore(int player) {
        switch (player) {
            case 1: return player1.getScore();
            case 2: return player2.getScore();
            case 3: return player3.getScore();
            case 4: return player4.getScore();
            default: return -1;
        }
    }

}
