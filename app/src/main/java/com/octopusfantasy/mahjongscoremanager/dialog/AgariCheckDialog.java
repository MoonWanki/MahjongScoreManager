package com.octopusfantasy.mahjongscoremanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.octopusfantasy.mahjongscoremanager.R;
import com.octopusfantasy.mahjongscoremanager.manager.AgariManager;
import com.octopusfantasy.mahjongscoremanager.util.Scoreboard;

public class AgariCheckDialog extends Dialog {

    private Context context;
    AgariManager agariManager;
    private int score;
    private int winnerNo;

    public AgariCheckDialog(@NonNull Context context, int winnerNo, int score, AgariManager agariManager) {
        super(context);
        this.context = context;
        this.score  = score;
        this.winnerNo = winnerNo;
        this.agariManager = agariManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_agari_check);

        TextView text1 = findViewById(R.id.agariFinalCheckText1);
        TextView text2 = findViewById(R.id.agariFinalCheckText2);
        TextView text3 = findViewById(R.id.agariFinalCheckText3);

        String str1 = Scoreboard.getNamedScore(agariManager.getHanSum(), agariManager.getFu()) + " (으)로 화료하여";
        String str2 = String.valueOf(score) + "점";
        String str3 = "을 얻습니다.";

        text1.setText(str1);
        text2.setText(str2);
        text3.setText(str3);

        ImageView agariFinalCheckOkButton = findViewById(R.id.agariFinalCheckOkButton);
        agariFinalCheckOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agariManager.applyScoreChange();
                dismiss();
            }
        });

        ImageView agariFinalCheckCancelButton = findViewById(R.id.agariFinalCheckCancelButton);
        agariFinalCheckCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        LinearLayout linearLayout = findViewById(R.id.agariCheckDialogParent);
        linearLayout.setRotation(getDegree(winnerNo));
    }

    private int getDegree(int playerNo) {
        switch (playerNo) {
            case 1: return 0;
            case 2: return -90;
            case 3: return 180;
            case 4: return 90;
            default: return 0;
        }
    }


}
