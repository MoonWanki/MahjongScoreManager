package com.octopusfantasy.mahjongscoremanager.manager;

import com.octopusfantasy.mahjongscoremanager.model.Player;

public class GameManager {

    private static GameManager instance;
    private YakuManager yakuManager;
    private PlayerManager playerManager;

    private int currentWinnerNo;
    private int ronedPlayerNo;

    private int currentRound, currentRotation, currentCounter;

    private int mode;
    private int richDeposit;

    private boolean roundChanged;

    public GameManager() {
        yakuManager = YakuManager.getInstance();
        playerManager = PlayerManager.getinstance();
    }

    public void initGame(int mode) {

        currentWinnerNo = 0;
        ronedPlayerNo = 0;

        currentRound = 1;
        currentRotation = 1;
        currentCounter = 0;

        richDeposit = 0;
        playerManager.initPlayers(mode);

        roundChanged = false;

        this.mode = mode;
    }

    public int getScore(int player) {
        return playerManager.getScore(player);

    }

    public boolean isRoundChanged() {
        return roundChanged;
    }

    public void setRoundChanged(boolean roundChanged) {
        this.roundChanged = roundChanged;
    }

    public boolean doRich(int playerNo) {
        if(getScore(playerNo) < 1000) return false;
        getPlayer(playerNo).setRiched(true);
        richDepositUp();
        getPlayer(playerNo).scoreDownBy(1000);
        return true;
    }

    public void cancelRich(int playerNo) {
        getPlayer(playerNo).setRiched(false);
        richDepositDown();
        getPlayer(playerNo).scoreUpBy(1000);
    }

    public YakuManager getYakuManager() {
        return yakuManager;
    }

    public Player getCurrentWinner() {
        return playerManager.getPlayer(currentWinnerNo);
    }

    public int getCurrentWinnerNo() {
        return currentWinnerNo;
    }

    public void setCurrentWinner(int currentWinnerNo) {
        this.currentWinnerNo = currentWinnerNo;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentRotation() {
        return currentRotation;
    }

    public int getCurrentCounter() {
        return currentCounter;
    }

    public Player getPlayer(int no) {
        return playerManager.getPlayer(no);
    }

    public int getRichDeposit() {
        return richDeposit;
    }

    public void richDepositUp() {
        richDeposit++;
    }

    public void richDepositDown() {
        if(richDeposit > 0) richDeposit--;
    }

    // 화료 시 국 전환
    public void toNextRotation() {

        if(getCurrentWinner().isDealer()) { // 친의 화료 시

            currentCounter++; // 본장 증가

        } else { // 자의 화료 시

            currentRotation++; // 국 전환
            playerManager.changeDealer(); // 오야 넘어감

            if(currentRotation > 4) {

                if(currentRound == mode) {
                    // 게임 종료

                    return;
                }

                currentRound++;
                currentRotation = 1;
            }
        }

        playerManager.initPlayersStatus();
        richDeposit = 0;
        currentWinnerNo = 0;
        ronedPlayerNo = 0;


    }

    public void setRonedPlayerNo(int no) {
        ronedPlayerNo = no;
    }

    public Player getRonedPlayer() {
        if(ronedPlayerNo == 0) return null;
        else return getPlayer(ronedPlayerNo);
    }
}
