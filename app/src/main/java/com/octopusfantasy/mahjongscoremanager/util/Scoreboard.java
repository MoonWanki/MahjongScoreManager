package com.octopusfantasy.mahjongscoremanager.util;

public class Scoreboard {

    private static final int MANGAN = 2000;
    private static final int HANEMAN = 3000;
    private static final int BAIMAN = 4000;
    private static final int SANBAIMAN = 6000;
    private static final int YAKUMAN = 8000;

    public static int getUnitScore(int han, int fu) {

        if(han >= 13) return YAKUMAN;
        else if(han >= 11) return SANBAIMAN;
        else if(han >= 8) return BAIMAN;
        else if(han >= 6) return HANEMAN;
        else if(han == 5) return MANGAN;
        else if(han == 4 && fu >= 40) return MANGAN;
        else if(han == 3 && fu >= 70) return MANGAN;

        return fu * (int) Math.pow(2, han + 2);
    }

    public static String getNamedScore(int han, int fu) {

        if(han >= 13) return "역만";
        else if(han >= 11) return "삼배만";
        else if(han >= 8) return "배만";
        else if(han >= 6) return "하네만";
        else if(han == 5) return "만관";
        else if(han == 4 && fu >= 40) return "만관";
        else if(han == 3 && fu >= 70) return "만관";
        else return String.valueOf(han) + "판 " + String.valueOf(fu) + "부";
    }

}
