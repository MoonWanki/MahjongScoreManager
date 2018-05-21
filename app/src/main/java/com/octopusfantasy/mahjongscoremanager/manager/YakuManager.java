package com.octopusfantasy.mahjongscoremanager.manager;

import com.octopusfantasy.mahjongscoremanager.model.Yaku;

import java.util.ArrayList;

public class YakuManager {

    private ArrayList<Yaku> yakuList = new ArrayList<>();

    private static YakuManager instance;

    public static synchronized YakuManager getInstance() {

        if(instance==null)
            instance = new YakuManager();
        return instance;
    }

    private YakuManager() {

        // 1판 역
        yakuList.add(new Yaku(1, "백", false, 1));
        yakuList.add(new Yaku(1, "발", false, 1));
        yakuList.add(new Yaku(1, "중", false, 1));
        yakuList.add(new Yaku(1, "이페코", true));
        yakuList.add(new Yaku(1, "해저로월", false, 1));
        yakuList.add(new Yaku(1, "하저로어", false, 1));
        yakuList.add(new Yaku(1, "영상개화", false, 1));
        yakuList.add(new Yaku(1, "창깡", false, 1));

        // 2판 역
        yakuList.add(new Yaku(2, "또이또이", false, 2));
        yakuList.add(new Yaku(2, "삼색동순", false, 1));
        yakuList.add(new Yaku(2, "치또이쯔", true));
        yakuList.add(new Yaku(2, "일기통관", false, 1));
        yakuList.add(new Yaku(2, "챤타", false, 1));
        yakuList.add(new Yaku(2, "상앙꼬", true));
        yakuList.add(new Yaku(2, "삼색동각", false, 2));
        yakuList.add(new Yaku(2, "소삼원", false, 2));
        yakuList.add(new Yaku(2, "혼노두", false, 2));
        yakuList.add(new Yaku(2, "산깡쯔", false, 2));

        // 3판 역
        yakuList.add(new Yaku(3, "혼일색", false, 2));
        yakuList.add(new Yaku(3, "쥰짱", false, 2));
        yakuList.add(new Yaku(3, "량페코", true));

        // 6판 역
        yakuList.add(new Yaku(6, "청일색", false, 5));

        // 역만
        yakuList.add(new Yaku(13, "국사무쌍", true));
        yakuList.add(new Yaku(13, "스앙꼬", true));
        yakuList.add(new Yaku(13, "대삼원", false, 13));
        yakuList.add(new Yaku(13, "자일색", false, 13));
        yakuList.add(new Yaku(13, "녹일색", false, 13));
        yakuList.add(new Yaku(13, "청노두", false, 13));
        yakuList.add(new Yaku(13, "대사희", false, 13));
        yakuList.add(new Yaku(13, "소사희", false, 13));
        yakuList.add(new Yaku(13, "스깡쯔", false, 13));
        yakuList.add(new Yaku(13, "구련보등", true));
        yakuList.add(new Yaku(13, "천화", true));
        yakuList.add(new Yaku(13, "지화", true));
        yakuList.add(new Yaku(13, "인화", true));

    }

    public ArrayList<Yaku> get1stYakuList(boolean closed, int prevailingWind, int playerWind, boolean isWinnerRiched) {
        ArrayList<Yaku> newYakuList = new ArrayList<>();
        if(isWinnerRiched) newYakuList.add(new Yaku(1, "일발", true));
        newYakuList.add(new Yaku(1, "탕야오", false, 1));
        if(closed) newYakuList.add(new Yaku(1, "핑후", true));
        if(prevailingWind == playerWind) {
            switch (prevailingWind) {
                case 1: newYakuList.add(new Yaku(2, "더블 동", false, 2)); break;
                case 2: newYakuList.add(new Yaku(2, "더블 남", false, 2)); break;
                case 3: newYakuList.add(new Yaku(2, "더블 서", false, 2)); break;
                case 4: newYakuList.add(new Yaku(2, "더블 북", false, 2)); break;
            }
        } else {
            switch (prevailingWind) {
                case 1: newYakuList.add(new Yaku(1, "장풍 동", false, 1)); break;
                case 2: newYakuList.add(new Yaku(1, "장풍 남", false, 1)); break;
                case 3: newYakuList.add(new Yaku(1, "장풍 서", false, 1)); break;
                case 4: newYakuList.add(new Yaku(1, "장풍 북", false, 1)); break;
            }
            switch (playerWind) {
                case 1: newYakuList.add(new Yaku(1, "자풍 동", false, 1)); break;
                case 2: newYakuList.add(new Yaku(1, "자풍 남", false, 1)); break;
                case 3: newYakuList.add(new Yaku(1, "자풍 서", false, 1)); break;
                case 4: newYakuList.add(new Yaku(1, "자풍 북", false, 1)); break;
            }
        }
        for (Yaku curYaku : yakuList) {
            if(curYaku.getHan() == 1) {
                if (!closed && curYaku.getClosedOnly()) continue; // 울었을 경우 비멘젠역은 패스
                newYakuList.add(curYaku);
            }
        }
        return newYakuList;
    }

    public ArrayList<Yaku> get2ndYakuList(boolean closed, boolean isWinnerRiched) {
        ArrayList<Yaku> newYakuList = new ArrayList<>();
        for (Yaku curYaku : yakuList) {
            if(curYaku.getHan() == 2) {
                if (!closed && curYaku.getClosedOnly()) continue; // 울었을 경우 비멘젠역은 패스
                newYakuList.add(curYaku);
            }
        }
        if(isWinnerRiched) newYakuList.add(new Yaku(2, "더블리치", true));

        return newYakuList;
    }

    public ArrayList<Yaku> get3rdYakuList(boolean closed) {
        ArrayList<Yaku> newYakuList = new ArrayList<>();
        for (Yaku curYaku : yakuList) {
            if(curYaku.getHan() >= 3) {
                if (!closed && curYaku.getClosedOnly()) continue; // 울었을 경우 비멘젠역은 패스
                newYakuList.add(curYaku);
            }
        }
        return newYakuList;
    }
}
