package com.octopusfantasy.mahjongscoremanager.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.octopusfantasy.mahjongscoremanager.R;
import com.octopusfantasy.mahjongscoremanager.dialog.AgariDialog;
import com.octopusfantasy.mahjongscoremanager.manager.GameManager;
import com.octopusfantasy.mahjongscoremanager.model.Dice;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {

    GameManager gameManager;
    Dice dice;
    Timer timer;
    boolean isDiceRolling;

    @BindView(R.id.scoreText1) TextView scoreText1;
    @BindView(R.id.scoreText2) TextView scoreText2;
    @BindView(R.id.scoreText3) TextView scoreText3;
    @BindView(R.id.scoreText4) TextView scoreText4;

    @BindView(R.id.agariChoiceButton1) Button agariChoiceButton1;
    @BindView(R.id.agariChoiceButton2) Button agariChoiceButton2;
    @BindView(R.id.agariChoiceButton3) Button agariChoiceButton3;
    @BindView(R.id.agariChoiceButton4) Button agariChoiceButton4;

    @BindView(R.id.richButton1) ImageView richButton1;
    @BindView(R.id.richButton2) ImageView richButton2;
    @BindView(R.id.richButton3) ImageView richButton3;
    @BindView(R.id.richButton4) ImageView richButton4;

    @BindView(R.id.nakiButton1) ImageView nakiButton1;
    @BindView(R.id.nakiButton2) ImageView nakiButton2;
    @BindView(R.id.nakiButton3) ImageView nakiButton3;
    @BindView(R.id.nakiButton4) ImageView nakiButton4;

    @BindView(R.id.diceLayout) RelativeLayout diceLayout;

    @BindView(R.id.diceView1) View diceView1;
    @BindView(R.id.diceView2) View diceView2;

    @BindView(R.id.diceView1_1) ImageView diceView1_1;
    @BindView(R.id.diceView1_2) ImageView diceView1_2;
    @BindView(R.id.diceView1_3) ImageView diceView1_3;
    @BindView(R.id.diceView1_4) ImageView diceView1_4;
    @BindView(R.id.diceView1_5) ImageView diceView1_5;
    @BindView(R.id.diceView1_6) ImageView diceView1_6;

    @BindView(R.id.diceView2_1) ImageView diceView2_1;
    @BindView(R.id.diceView2_2) ImageView diceView2_2;
    @BindView(R.id.diceView2_3) ImageView diceView2_3;
    @BindView(R.id.diceView2_4) ImageView diceView2_4;
    @BindView(R.id.diceView2_5) ImageView diceView2_5;
    @BindView(R.id.diceView2_6) ImageView diceView2_6;

    @BindView(R.id.diceButton1) ImageView diceButton1;
    @BindView(R.id.diceButton2) ImageView diceButton2;
    @BindView(R.id.diceButton3) ImageView diceButton3;
    @BindView(R.id.diceButton4) ImageView diceButton4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        gameManager = new GameManager();

        // 게임 세팅
        gameManager.initGame(getIntent().getIntExtra("mode", 2));


        dice = new Dice();
        timer = new Timer();
        isDiceRolling = false;
        setNextDiceView();

        /*
        diceButton1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: diceOn(); break;
                    case MotionEvent.ACTION_UP: diceOff(); break;
                    default: break;
                }
                return true;
            }

        });
        */

        // 뷰 세팅
        initViews();
    }

    @OnTouch({R.id.diceButton1, R.id.diceButton2, R.id.diceButton3, R.id.diceButton4})
    boolean onDiceButtonClick(View v, MotionEvent e) {
        if(!isDiceRolling && e.getAction()==MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.diceButton1:
                    if (gameManager.getCurrentRotation() == 1) rollDice();
                    break;
                case R.id.diceButton2:
                    if (gameManager.getCurrentRotation() == 2) rollDice();
                    break;
                case R.id.diceButton3:
                    if (gameManager.getCurrentRotation() == 3) rollDice();
                    break;
                case R.id.diceButton4:
                    if (gameManager.getCurrentRotation() == 4) rollDice();
                    break;
            }
        }
        return true;
    }

    private static int count;

    private void rollDice() {
        isDiceRolling = true;
        timer = new Timer();
        count = 0;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                if(count<40 || count==41 || count==43 || count==45 || count==48 || count==51 || count==55 || count==60 || count==65 || count==71)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setNextDiceView();
                        }
                    });
                else if(count>80) {
                    isDiceRolling = false;
                    timer.cancel();
                }
            }
        }, 0, 1000/60);
    }
    private void setNextDiceView() {
        diceLayout.setRotation(dice.getNextLayoutAngle());
        diceView1.setRotation(dice.getNextDice1Angle());
        diceView2.setRotation(dice.getNextDice2Angle());

        showDiceView(dice.getDice1Num(), dice.getNextDice1Num(), dice.getDice2Num(), dice.getNextDice2Num());
    }

    private void showDiceView(int cur1, int next1, int cur2, int next2) {
        switch (cur1) {
            case 1: diceView1_1.setVisibility(View.INVISIBLE); break;
            case 2: diceView1_2.setVisibility(View.INVISIBLE); break;
            case 3: diceView1_3.setVisibility(View.INVISIBLE); break;
            case 4: diceView1_4.setVisibility(View.INVISIBLE); break;
            case 5: diceView1_5.setVisibility(View.INVISIBLE); break;
            case 6: diceView1_6.setVisibility(View.INVISIBLE); break;
            default: break;
        }
        switch (next1) {
            case 1: diceView1_1.setVisibility(View.VISIBLE); break;
            case 2: diceView1_2.setVisibility(View.VISIBLE); break;
            case 3: diceView1_3.setVisibility(View.VISIBLE); break;
            case 4: diceView1_4.setVisibility(View.VISIBLE); break;
            case 5: diceView1_5.setVisibility(View.VISIBLE); break;
            case 6: diceView1_6.setVisibility(View.VISIBLE); break;
        }
        switch (cur2) {
            case 1: diceView2_1.setVisibility(View.INVISIBLE); break;
            case 2: diceView2_2.setVisibility(View.INVISIBLE); break;
            case 3: diceView2_3.setVisibility(View.INVISIBLE); break;
            case 4: diceView2_4.setVisibility(View.INVISIBLE); break;
            case 5: diceView2_5.setVisibility(View.INVISIBLE); break;
            case 6: diceView2_6.setVisibility(View.INVISIBLE); break;
            default: break;
        }
        switch (next2) {
            case 1: diceView2_1.setVisibility(View.VISIBLE); break;
            case 2: diceView2_2.setVisibility(View.VISIBLE); break;
            case 3: diceView2_3.setVisibility(View.VISIBLE); break;
            case 4: diceView2_4.setVisibility(View.VISIBLE); break;
            case 5: diceView2_5.setVisibility(View.VISIBLE); break;
            case 6: diceView2_6.setVisibility(View.VISIBLE); break;
        }


    }

    private void initViews() {
        setAgariChoiceButtonsVisibility(View.GONE);
        refreshScoreViews();
        initImageViews();
    }

    // 플레이어 점수판을 실제 점수로 갱신
    private void refreshScoreViews() {
        scoreText1.setText(String.valueOf(gameManager.getScore(1)));
        scoreText2.setText(String.valueOf(gameManager.getScore(2)));
        scoreText3.setText(String.valueOf(gameManager.getScore(3)));
        scoreText4.setText(String.valueOf(gameManager.getScore(4)));
    }

    // 아가리 버튼 클릭 시
    @OnClick({R.id.agariButton1, R.id.agariButton2, R.id.agariButton3, R.id.agariButton4})
    void onAgariButtonClick(View view){

        if(agariChoiceButton1.getVisibility() == View.VISIBLE) {

            gameManager.setCurrentWinner(0);
            setAgariChoiceButtonsVisibility(View.GONE);

        } else {
            switch (view.getId()) {
                case R.id.agariButton1:
                    gameManager.setCurrentWinner(1);
                    agariChoiceButton1.setRotation(0); agariChoiceButton1.setText("쯔모");
                    agariChoiceButton2.setRotation(0); agariChoiceButton2.setText("론");
                    agariChoiceButton3.setRotation(0); agariChoiceButton3.setText("론");
                    agariChoiceButton4.setRotation(0); agariChoiceButton4.setText("론");
                    break;
                case R.id.agariButton2:
                    gameManager.setCurrentWinner(2);
                    agariChoiceButton1.setRotation(-90); agariChoiceButton1.setText("론");
                    agariChoiceButton2.setRotation(-90); agariChoiceButton2.setText("쯔모");
                    agariChoiceButton3.setRotation(-90); agariChoiceButton3.setText("론");
                    agariChoiceButton4.setRotation(-90); agariChoiceButton4.setText("론");
                    break;
                case R.id.agariButton3:
                    gameManager.setCurrentWinner(3);
                    agariChoiceButton1.setRotation(180); agariChoiceButton1.setText("론");
                    agariChoiceButton2.setRotation(180); agariChoiceButton2.setText("론");
                    agariChoiceButton3.setRotation(180); agariChoiceButton3.setText("쯔모");
                    agariChoiceButton4.setRotation(180); agariChoiceButton4.setText("론");
                    break;
                case R.id.agariButton4:
                    gameManager.setCurrentWinner(4);
                    agariChoiceButton1.setRotation(90); agariChoiceButton1.setText("론");
                    agariChoiceButton2.setRotation(90); agariChoiceButton2.setText("론");
                    agariChoiceButton3.setRotation(90); agariChoiceButton3.setText("론");
                    agariChoiceButton4.setRotation(90); agariChoiceButton4.setText("쯔모");
                    break;
                default:
                    break;
            }

            setAgariChoiceButtonsVisibility(View.VISIBLE);

        }
    }

    // 론 or 쯔모 버튼 클릭 시
    @OnClick({R.id.agariChoiceButton1, R.id.agariChoiceButton2, R.id.agariChoiceButton3, R.id.agariChoiceButton4})
    void onAgariChoiceButtonClick(Button btn) {

        setAgariChoiceButtonsVisibility(View.GONE);
        int ronedPlayerNo;
        switch (btn.getId()) {
            case R.id.agariChoiceButton1: gameManager.setRonedPlayerNo(1); break;
            case R.id.agariChoiceButton2: gameManager.setRonedPlayerNo(2); break;
            case R.id.agariChoiceButton3: gameManager.setRonedPlayerNo(3); break;
            case R.id.agariChoiceButton4: gameManager.setRonedPlayerNo(4); break;
        }

        AgariDialog agariDialog = new AgariDialog(this, gameManager, btn.getText().equals("쯔모"));
        agariDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(gameManager.isRoundChanged()) {
                    gameManager.setRoundChanged(false);
                    initViews();
                }
            }
        });
        agariDialog.show();

    }

    // 론 or 쯔모 버튼 보이기 설정
    private void setAgariChoiceButtonsVisibility(int visibility) {
        agariChoiceButton1.setVisibility(visibility);
        agariChoiceButton2.setVisibility(visibility);
        agariChoiceButton3.setVisibility(visibility);
        agariChoiceButton4.setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {

        if(agariChoiceButton1.getVisibility()==View.VISIBLE) {
            setAgariChoiceButtonsVisibility(View.GONE);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("정말 끝내시겠습니까? 저장되지 않습니다.");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("아니오", null).create().show();
    }

    // 울기 버튼 클릭 시
    @OnClick({R.id.nakiButton1, R.id.nakiButton2, R.id.nakiButton3, R.id.nakiButton4})
    public void onNakiButtonClick(ImageView view) {

        switch (view.getId()) {
            case R.id.nakiButton1:
                if(gameManager.getPlayer(1).isRiched()) break;
                if(gameManager.getPlayer(1).isClosed()) {
                    gameManager.getPlayer(1).setClosed(false);
                    view.setImageResource(R.drawable.nakionimg);
                } else {
                    gameManager.getPlayer(1).setClosed(true);
                    view.setImageResource(R.drawable.nakioffimg);
                }
                break;
            case R.id.nakiButton2:
                if(gameManager.getPlayer(2).isRiched()) break;
                if(gameManager.getPlayer(2).isClosed()) {
                    gameManager.getPlayer(2).setClosed(false);
                    view.setImageResource(R.drawable.nakionimg);
                } else {
                    gameManager.getPlayer(2).setClosed(true);
                    view.setImageResource(R.drawable.nakioffimg);
                }
                break;
            case R.id.nakiButton3:
                if(gameManager.getPlayer(3).isRiched()) break;
                if(gameManager.getPlayer(3).isClosed()) {
                    gameManager.getPlayer(3).setClosed(false);
                    view.setImageResource(R.drawable.nakionimg);
                } else {
                    gameManager.getPlayer(3).setClosed(true);
                    view.setImageResource(R.drawable.nakioffimg);
                }
                break;
            case R.id.nakiButton4:
                if(gameManager.getPlayer(4).isRiched()) break;
                if(gameManager.getPlayer(4).isClosed()) {
                    gameManager.getPlayer(4).setClosed(false);
                    view.setImageResource(R.drawable.nakionimg);
                } else {
                    gameManager.getPlayer(4).setClosed(true);
                    view.setImageResource(R.drawable.nakioffimg);
                }
                break;
            default: break;
        }
    }

    // 리치봉 클릭시
    @OnClick({R.id.richButton1, R.id.richButton2, R.id.richButton3, R.id.richButton4})
    public void onRichButtonClick(ImageView view) {
        switch (view.getId()) {
            case R.id.richButton1:
                if(!gameManager.getPlayer(1).isClosed()) break;
                if(gameManager.getPlayer(1).isRiched()) {
                    gameManager.cancelRich(1);
                    view.setImageResource(R.drawable.riichibongoff);
                } else {
                    if(gameManager.doRich(1))
                        view.setImageResource(R.drawable.riichibongon);
                }
                break;
            case R.id.richButton2:
                if(!gameManager.getPlayer(2).isClosed()) break;
                if(gameManager.getPlayer(2).isRiched()) {
                    gameManager.cancelRich(2);
                    view.setImageResource(R.drawable.riichibongoff);
                } else {
                    if(gameManager.doRich(2))
                        view.setImageResource(R.drawable.riichibongon);
                }
                break;
            case R.id.richButton3:
                if(!gameManager.getPlayer(3).isClosed()) break;
                if(gameManager.getPlayer(3).isRiched()) {
                    gameManager.cancelRich(3);
                    view.setImageResource(R.drawable.riichibongoff);
                } else {
                    if(gameManager.doRich(3))
                        view.setImageResource(R.drawable.riichibongon);
                }
                break;
            case R.id.richButton4:
                if(!gameManager.getPlayer(4).isClosed()) break;
                if(gameManager.getPlayer(4).isRiched()) {
                    gameManager.cancelRich(4);
                    view.setImageResource(R.drawable.riichibongoff);
                } else {
                    if(gameManager.doRich(4))
                        view.setImageResource(R.drawable.riichibongon);
                }
                break;
        }

        refreshScoreViews();
    }

    private void initImageViews() {

        richButton1.setImageResource(R.drawable.riichibongoff);
        richButton2.setImageResource(R.drawable.riichibongoff);
        richButton3.setImageResource(R.drawable.riichibongoff);
        richButton4.setImageResource(R.drawable.riichibongoff);

        nakiButton1.setImageResource(R.drawable.nakioffimg);
        nakiButton2.setImageResource(R.drawable.nakioffimg);
        nakiButton3.setImageResource(R.drawable.nakioffimg);
        nakiButton4.setImageResource(R.drawable.nakioffimg);

        switch (gameManager.getCurrentRotation()) {
            case 1:
                diceButton4.setImageResource(R.drawable.diceoff);
                diceButton1.setImageResource(R.drawable.diceon);
                break;
            case 2:
                diceButton1.setImageResource(R.drawable.diceoff);
                diceButton2.setImageResource(R.drawable.diceon);
                break;
            case 3:
                diceButton2.setImageResource(R.drawable.diceoff);
                diceButton3.setImageResource(R.drawable.diceon);
                break;
            case 4:
                diceButton3.setImageResource(R.drawable.diceoff);
                diceButton4.setImageResource(R.drawable.diceon);
                break;
        }

    }

    private void changeRotation()  {

    }

}
