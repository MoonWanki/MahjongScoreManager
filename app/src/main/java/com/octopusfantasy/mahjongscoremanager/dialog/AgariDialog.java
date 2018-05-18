package com.octopusfantasy.mahjongscoremanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.octopusfantasy.mahjongscoremanager.R;
import com.octopusfantasy.mahjongscoremanager.adapter.SatisfiedYakuRecyclerAdapter;
import com.octopusfantasy.mahjongscoremanager.adapter.YakuRecyclerViewAdapter;
import com.octopusfantasy.mahjongscoremanager.manager.AgariManager;
import com.octopusfantasy.mahjongscoremanager.manager.GameManager;
import com.octopusfantasy.mahjongscoremanager.model.Yaku;
import com.octopusfantasy.mahjongscoremanager.util.Scoreboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgariDialog extends Dialog {

    private Context context;
    private GameManager gameManager;
    private AgariManager agariManager;
    private boolean tsumo;
    private String namedScore;

    private LinearLayout upperDialog, lowerDialog;
    private RecyclerView satisfiedYakuRecycler;

    @BindView(R.id.basicScoreText) TextView basicScoreText;
    @BindView(R.id.bonusByRichDepositText) TextView bonusByRichDepositText;
    @BindView(R.id.bonusByRichDepositNumText) TextView bonusByRichDepositNumText;
    @BindView(R.id.bonusByCounterText) TextView bonusByCounterText;
    @BindView(R.id.bonusByCounterNumText) TextView bonusByCounterNumText;
    @BindView(R.id.mainScoreTitleText) TextView mainScoreTitleText;
    @BindView(R.id.mainScoreNumberText) TextView mainScoreNumberText;
    @BindView(R.id.mainScoreDetailText) TextView mainScoreDetailText;
    @BindView(R.id.agariCheckButton) ImageButton agariCheckButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_agari);

        upperDialog = findViewById(R.id.upperDialog);
        lowerDialog = findViewById(R.id.lowerDialog);
        // 레이아웃 세팅
        inflateLayoutToDialog(gameManager.getCurrentWinnerNo());

        ButterKnife.bind(this);

        // AgariManager에 플레이어 정보, 역 정보 전달
        agariManager = new AgariManager(gameManager, tsumo);

        // 역 리스트 표시
        setYakuLists();

        initScoringView();

    }

    public AgariDialog(Context context, GameManager gameManager, boolean tsumo) {
        super(context);
        this.context = context;
        this.gameManager = gameManager;
        this.tsumo = tsumo;
    }

    private void inflateLayoutToDialog(int currentWinner) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (currentWinner) {

            case 1:
                inflater.inflate(R.layout.layout_scoring, upperDialog, true);
                inflater.inflate(R.layout.layout_yaku_select, lowerDialog, true);
                upperDialog.setRotation(0);
                lowerDialog.setRotation(0);
                break;
            case 2:
                inflater.inflate(R.layout.layout_scoring, lowerDialog, true);
                inflater.inflate(R.layout.layout_yaku_select, upperDialog, true);
                upperDialog.setRotation(-90);
                lowerDialog.setRotation(-90);
                break;
            case 3:
                inflater.inflate(R.layout.layout_scoring, lowerDialog, true);
                inflater.inflate(R.layout.layout_yaku_select, upperDialog, true);
                upperDialog.setRotation(180);
                lowerDialog.setRotation(180);
                break;
            case 4:
                inflater.inflate(R.layout.layout_scoring, upperDialog, true);
                inflater.inflate(R.layout.layout_yaku_select, lowerDialog, true);
                upperDialog.setRotation(90);
                lowerDialog.setRotation(90);
                break;

            default: break;
        }
    }


    @OnClick(R.id.agariCheckButton)
    public void onAgariCheckButtonClick() {

        if(agariManager.isAgariable()) {

            AgariCheckDialog agariCheckDialog = new AgariCheckDialog(context, gameManager.getCurrentWinnerNo(), agariManager.getTotalScore(), agariManager);
            agariCheckDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if(agariManager.isAgariApplied()) {
                        gameManager.setRoundChanged(true);
                        gameManager.toNextRotation();
                        dismiss();
                    }
                }
            });
            agariCheckDialog.show();
        }

    }

    @OnClick(R.id.doraUpButton)
    public void onDoraUpButtonClick() {
        agariManager.addDora();
        satisfiedYakuRecycler.getAdapter().notifyDataSetChanged();
        refreshScoringView();
    }

    @OnClick(R.id.doraDownButton)
    public void onDoraDownButtonClick() {
        agariManager.removeDora();
        satisfiedYakuRecycler.getAdapter().notifyDataSetChanged();
        refreshScoringView();
    }

    @OnClick(R.id.fuUpButton)
    public void onFuUpButtonClick() {
        agariManager.fuUp();
        refreshScoringView();
    }

    @OnClick(R.id.fuDownButton)
    public void onFuDownButtonClick() {
        agariManager.fuDown();
        refreshScoringView();
    }

    private void setYakuLists() {

        boolean isWinnerClosed = gameManager.getCurrentWinner().isClosed();

        RecyclerView yakuRecycler1 = findViewById(R.id.yakuRecycler1);
        RecyclerView yakuRecycler2 = findViewById(R.id.yakuRecycler2);
        RecyclerView yakuRecycler3 = findViewById(R.id.yakuRecycler3);

        yakuRecycler1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        yakuRecycler2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        yakuRecycler3.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        yakuRecycler1.setAdapter(new YakuRecyclerViewAdapter(gameManager.getYakuManager().get1stYakuList(
                isWinnerClosed,
                gameManager.getCurrentRound(),
                gameManager.getCurrentWinner().getWind(),
                gameManager.getCurrentWinner().isRiched()
        ), this));
        yakuRecycler2.setAdapter(new YakuRecyclerViewAdapter(gameManager.getYakuManager().get2ndYakuList(isWinnerClosed), this));
        yakuRecycler3.setAdapter(new YakuRecyclerViewAdapter(gameManager.getYakuManager().get3rdYakuList(isWinnerClosed), this));

        yakuRecycler1.setHasFixedSize(true);
        yakuRecycler2.setHasFixedSize(true);
        yakuRecycler3.setHasFixedSize(true);

    }

    public void onYakuItemClick(Yaku yaku, boolean select) {
        if(select) agariManager.selectYaku(yaku);
        else agariManager.deselectYaku(yaku);

        satisfiedYakuRecycler.getAdapter().notifyDataSetChanged();
        refreshScoringView();
    }

    public void initScoringView() {

        // 타이틀
        setResultTitleText();

        // 만족하는 역 리스트
        satisfiedYakuRecycler = findViewById(R.id.satisfiedYakuList);
        satisfiedYakuRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        satisfiedYakuRecycler.setAdapter(new SatisfiedYakuRecyclerAdapter(agariManager.getSatisfiedYakuList(), gameManager.getCurrentWinner().isClosed()));

        refreshScoringView();
    }

    private void setResultTitleText() {
        String str = "";
        int round = gameManager.getCurrentRound();
        int rotation = gameManager.getCurrentRotation();
        int counter = gameManager.getCurrentCounter();

        if(round == 1) str += "동";
        else if(round == 2) str += "남";
        str += String.valueOf(rotation) + "국 ";

        if(counter > 0) str += String.valueOf(counter) + "본장 ";

        str += gameManager.getCurrentWinner().getName() + " ";
        str += (tsumo ? "쯔모 " : "론 ");

        TextView scoreHeaderText = findViewById(R.id.scoreHeaderText);
        scoreHeaderText.setText(str);
    }

    private void refreshScoringView() {

        int hanSum = agariManager.getHanSum();
        int fu = agariManager.getFu();
        namedScore = Scoreboard.getNamedScore(hanSum, fu);

        String basicScoreStr = "";
        String bonusByRichDepositNumStr = "×";
        String bonusByRichDepositStr = "";
        String bonusByCounterNumStr = "×";
        String bonusByCounterStr = "";
        String mainScoreNumberStr = "";
        String mainScoreDetailStr = "";

        // 기본 점수 표시
        if(hanSum > 0) {
            basicScoreStr += String.valueOf(agariManager.getHanSum()) + "판 ";
            if (hanSum < 5) basicScoreStr += String.valueOf(fu) + "부 ";
            basicScoreStr += agariManager.getBasicScore() + "점";
            basicScoreText.setText(basicScoreStr);
        } else basicScoreText.setText("-");

        // 리치봉 개수 표시
        bonusByRichDepositNumStr += String.valueOf(gameManager.getRichDeposit()) + " :";
        bonusByRichDepositNumText.setText(bonusByRichDepositNumStr);

        // 공탁금 보너스 점수 표시
        bonusByRichDepositStr += String.valueOf(gameManager.getRichDeposit() * 1000) + "점";
        bonusByRichDepositText.setText(bonusByRichDepositStr);

        // 리치봉 개수 표시
        bonusByCounterNumStr += String.valueOf(gameManager.getCurrentCounter()) + " :";
        bonusByCounterNumText.setText(bonusByCounterNumStr);

        // 본장 보너스 점수 표시
        bonusByCounterStr += String.valueOf(gameManager.getCurrentCounter() * 300) + "점";
        bonusByCounterText.setText(bonusByCounterStr);

        if(hanSum > 0) {
            // 최종 판 수 표시
            mainScoreTitleText.setText(namedScore);

            // 최종 점수 표시
            mainScoreNumberStr += String.valueOf(agariManager.getTotalScore()) + "점";
            mainScoreNumberText.setText(mainScoreNumberStr);

            // 세부 점수 표시
            mainScoreDetailStr += "(" + agariManager.getDetailScoreStr() + ")";
            mainScoreDetailText.setText(mainScoreDetailStr);

        } else {
            mainScoreTitleText.setText("");
            mainScoreNumberText.setText("");
            mainScoreDetailText.setText("");
        }

        if(agariManager.isAgariable()) agariCheckButton.setBackgroundColor(context.getResources().getColor(R.color.colorCheckable));
        else agariCheckButton.setBackgroundColor(context.getResources().getColor(R.color.colorNotCheckable));

    }

}
