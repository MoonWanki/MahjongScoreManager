package com.octopusfantasy.mahjongscoremanager.manager;

import com.octopusfantasy.mahjongscoremanager.model.Yaku;
import com.octopusfantasy.mahjongscoremanager.util.Scoreboard;

import java.util.ArrayList;

public class AgariManager {

    private int fu;
    private boolean isFuChangeable;
    private boolean tsumo;
    private Yaku dora;
    private int doraNum;

    private int ronScore, tsumoSmallScore, tsumoBigScore;
    private int basicScore, totalScore;
    private String detailScoreStr;
    private boolean isWinnerDealer, isWinnerClosed;
    private boolean isPeace, isSevenPairs;
    private boolean agariApplied;

    private GameManager gameManager;

    private ArrayList<Yaku> satisfiedYakuList;

    public AgariManager(GameManager gameManager, boolean tsumo) {
        this.gameManager = gameManager;
        this.tsumo = tsumo;

        doraNum = 0;
        ronScore = 0; tsumoBigScore = 0; tsumoSmallScore = 0;
        isPeace = false; isSevenPairs = false;

        agariApplied = false;

        isWinnerDealer = gameManager.getCurrentWinner().isDealer();
        isWinnerClosed = gameManager.getCurrentWinner().isClosed();

        setDefaultFu();
        isFuChangeable = true;

        satisfiedYakuList = new ArrayList<>();

        if(gameManager.getCurrentWinner().isRiched())
            selectYaku(new Yaku(1, "리치", true));
        if(isWinnerClosed && tsumo)
            selectYaku(new Yaku(1, "멘젠쯔모", true));
    }

    private void setDefaultFu() {
        if(isWinnerClosed && !tsumo) fu = 40;
        else fu = 30;
    }

    public void selectYaku(Yaku yaku) {

        if(satisfiedYakuList.contains(yaku)) return;

        if(yaku.getName().equals("치또이쯔")) { // 치또이쯔 부수 처리
            isSevenPairs = true;
            fu = 25;
            isFuChangeable = false;
        } else if(yaku.getName().equals("핑후")) { // 핑후 부수 처리
            isPeace = true;
            if(tsumo) fu = 20; else fu = 30;
            isFuChangeable = false;
        }
        satisfiedYakuList.add(yaku);
        calculateScore();
    }

    public void deselectYaku(Yaku yaku) {
        if(yaku.getName().equals("치또이쯔")) {
            isSevenPairs = false;
            setDefaultFu();
            isFuChangeable = true;
        } else if(yaku.getName().equals("핑후")) {
            isPeace = false;
            setDefaultFu();
            isFuChangeable = true;
        }
        satisfiedYakuList.remove(yaku);
        calculateScore();
    }

    public ArrayList<Yaku> getSatisfiedYakuList() {
        return satisfiedYakuList;
    }

    public int getHanSum() {
        int sum = 0;
        for (Yaku yaku : satisfiedYakuList) {
            if(isWinnerClosed) sum += yaku.getHan();
            else sum += yaku.getOpenedHan();
        }
        return sum;
    }

    private void calculateScore() {

        int unitScore = Scoreboard.getUnitScore(getHanSum(), fu);
        int counter = gameManager.getCurrentCounter();

        if(isWinnerDealer) {
            basicScore = roundUp(unitScore * 6);
            if(tsumo) {
                tsumoBigScore = roundUp(unitScore * 2) + (counter * 100); // 오야 쯔모
                detailScoreStr = String.valueOf(tsumoBigScore) + " ALL";
                totalScore = tsumoBigScore * 3;
            }
            else {
                ronScore = basicScore + (counter * 300); // 오야 론
                detailScoreStr = String.valueOf(ronScore);
                totalScore = ronScore;
            }
        } else {
            basicScore = roundUp(unitScore * 4);
            if(tsumo) {
                tsumoSmallScore = roundUp(unitScore) + (counter * 100); // 자 쯔모
                tsumoBigScore = roundUp(unitScore * 2) + (counter * 100);
                detailScoreStr = String.valueOf(tsumoSmallScore) + "-" + String.valueOf(tsumoBigScore);
                totalScore = (tsumoSmallScore * 2) + tsumoBigScore;
            } else {
                ronScore = basicScore + (counter * 300); // 자 론
                detailScoreStr = String.valueOf(ronScore);
                totalScore = ronScore;
            }
        }

        totalScore += gameManager.getRichDeposit() * 1000;

    }

    public int getRonScore() {
        return ronScore;
    }

    public int getTsumoSmallScore() {
        return tsumoSmallScore;
    }

    public int getTsumoBigScore() {
        return tsumoBigScore;
    }

    public int getFu() {
        return fu;
    }

    public int getBasicScore() {
        return basicScore;
    }

    public boolean applyScoreChange() {

        if(tsumo) {

            if(isWinnerDealer) { // 오야 쯔모

                for(int i=1 ; i<=4 ; i++) {
                    if(i == gameManager.getCurrentWinnerNo())
                        gameManager.getPlayer(i).scoreUpBy(totalScore);
                    else
                        gameManager.getPlayer(i).scoreDownBy(tsumoBigScore);
                }

            } else { // 자 쯔모

                for (int i=1 ; i<=4 ; i++) {
                    if(i == gameManager.getCurrentWinnerNo())
                        gameManager.getPlayer(i).scoreUpBy(totalScore);
                    else if (gameManager.getPlayer(i).isDealer()) {
                        gameManager.getPlayer(i).scoreDownBy(tsumoBigScore);
                    } else
                        gameManager.getPlayer(i).scoreDownBy(tsumoSmallScore);

                }
            }

        } else { // 론

            gameManager.getCurrentWinner().scoreUpBy(totalScore);
            gameManager.getRonedPlayer().scoreDownBy(basicScore);

        }


        agariApplied = true;
        return true;
    }

    public boolean isAgariApplied() {
        return agariApplied;
    }

    private int roundUp(int num) {
        return (int) (Math.ceil(num/100d) * 100d);
    }

    public void addDora() {
        doraNum++;
        if(satisfiedYakuList.contains(dora)) satisfiedYakuList.remove(dora);
        addYakuAsDora();
    }

    public void removeDora() {
        if(doraNum == 0) return;
        doraNum--;
        satisfiedYakuList.remove(dora);
        if(doraNum > 0) addYakuAsDora();
    }

    private void addYakuAsDora() {
        String doraText = "도라";
        if(doraNum == 2) doraText += "도라";
        else if(doraNum > 2) doraText += String.valueOf(doraNum);

        dora = new Yaku(doraNum, doraText, false, doraNum);
        satisfiedYakuList.add(dora);
        calculateScore();
    }

    public void fuUp() {
        if(isFuChangeable && fu < 120) fu += 10;
        calculateScore();
    }

    public void fuDown() {
        if(isFuChangeable && fu > 20) fu -= 10;
        calculateScore();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getDetailScoreStr() {
        return detailScoreStr;
    }

    public boolean isAgariable() {
        if(satisfiedYakuList.isEmpty()) return false;
        else if(satisfiedYakuList.size()==1 && satisfiedYakuList.contains(dora)) return false;
        else return true;
    }
}
