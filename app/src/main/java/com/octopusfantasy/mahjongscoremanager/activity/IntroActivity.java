package com.octopusfantasy.mahjongscoremanager.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.octopusfantasy.mahjongscoremanager.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {

    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.startButton1)
    void startGame1() {
        startMainActivity(1);
    }

    @OnClick(R.id.startButton2)
    void startGame2() {
       startMainActivity(2);
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Snackbar.make(getWindow().getDecorView().getRootView(), "종료하려면 뒤로 버튼을 한번 더 눌러주세요.", Snackbar.LENGTH_SHORT).show();
            return;
        } else {
            finish();
        }
    }

    private void startMainActivity(int mode) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }

}
