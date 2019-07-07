package com.example.dell.as2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String FONT_PATH="font/VNI-Diudang.ttf";


    private TextView mtvBestScore;
    private Button mbtPlay;
    private Button mbtSetting;
    private Button mbtInformation;


    //Swipe variable
    float x1,x2,y1,y2;
    private SharedPreferences mSharePreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setTypeface();
    }
    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(PlayActivity.SCORE, Context.MODE_PRIVATE);
        int bestScore = sharedPreferences.getInt(PlayActivity.SCORE, 0);
        @SuppressLint({"StringFormatMatches", "StringFormatInvalid", "LocalSuppress"}) String best = getString(R.string.best_score, bestScore);
        mtvBestScore.setText(best);

        //Get music status from setting acvitity
        SharedPreferences preferences = getSharedPreferences(Setting.PREFS, Context.MODE_PRIVATE);
        if (preferences.getBoolean(Setting.MUSIC_MAIN, true)) {
            stopService(new Intent(this, MediaServicce.class));
            startService(new Intent(this, MediaServicce.class));
        }
    }

    private void setTypeface() {
        Typeface typeface=Typeface.createFromAsset(getAssets(), FONT_PATH);
        mtvBestScore.setTypeface(typeface);
        mbtPlay.setTypeface(typeface);
        mbtSetting.setTypeface(typeface);
        mbtInformation.setTypeface(typeface);
    }
    private void findViewById() {
        mtvBestScore=findViewById(R.id.tv_best_score);
        mbtPlay=findViewById(R.id.bt_play);
        mbtSetting=findViewById(R.id.bt_setting);
        mbtInformation=findViewById(R.id.information);
        mbtPlay.setOnClickListener(this);
        mbtSetting.setOnClickListener(this);
        mbtInformation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_play:
                clickPlay();
                break;
            case R.id.bt_setting:
                clickSetting();
                break;
            case R.id.information:
                clickInformation();
                break;
            default:
                break;
        }
    }



    private void clickPlay() {
        Intent intent= new Intent(this,PlayActivity.class);
        startActivity(intent);
    }

    private void clickSetting() {
        Intent intent= new Intent(this,Setting.class);
        startActivity(intent);
    }
    private void clickInformation() {
        Intent intent=new Intent(this,Information.class);
        startActivity(intent);

    }
    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=touchevent.getX();
                y1=touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=touchevent.getX();
                y2=touchevent.getY();
                if(x1>x2){
                    Intent intent=new Intent(this,PlayActivity.class);
                    startActivity(intent);
                }
        }
        return false;
    }
}

