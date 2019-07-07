package com.example.dell.as2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class GameOver extends AppCompatActivity implements View.OnClickListener{
    public static final String FONT_PATH = "font/VNI-Diudang.ttf";
    private static final String PREF_FILE = "SCORE_PREF_FILE";
    private database database;
    private TextView playerPoint;
    private Button button_home;
    private Button button_again;
    private Button button_enter;
    private int score;
    private EditText edtName;
    private String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViewById();
        setTypeface();
        score = getIntent().getExtras().getInt(PlayActivity.SCORE);
        @SuppressLint("StringFormatMatches") String strScore = getString(R.string.your_score, score);
        playerPoint.setText(strScore);

    }

        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.bt_again:
                    clickTryAgain();
                    break;

                case R.id.bt_home:
                    clickHome();
                    break;

                case R.id.btn_enter:
                    clickEnter();
                    break;

                default:
                    break;
            }
        }
        private void clickEnter(){
                playerName=edtName.getText().toString();
                playerName = database.capitalizeString(playerName);

                //Check the existence of score and name
                boolean existed = database.isRecordExisted(playerName);
                //Add score depending on specified conditions
                if (existed) { //Check if record existed
                    Score recordedScore = database.getScoreRecord(playerName);
                    if (recordedScore.getScore() != score) {
                        database.addScoreRecord(new Score(1, playerName, score));
                    }
                } else {
                    database.addScoreRecord(new Score(1, playerName, score));
                }

                Toast.makeText(getApplicationContext(), "Your score have been saved to the leaderboard", Toast.LENGTH_SHORT).show();
            }
       private void clickHome(){
           Intent homeButton = new Intent(GameOver.this, MainActivity.class);
           startActivity(homeButton);
           finish();
       }
       private void clickTryAgain(){
           Intent buttonReset = new Intent(GameOver.this, PlayActivity.class);
           startActivity(buttonReset);
           finish();
       }

    private void findViewById() {
        database= new database(this);
        playerPoint=findViewById(R.id.tvPlayerScore);
        button_home=findViewById(R.id.bt_home);
        button_again=findViewById(R.id.bt_again);
        button_enter=findViewById(R.id.btn_enter);
        edtName=findViewById(R.id.playerName);
        button_enter.setOnClickListener(this);
        button_again.setOnClickListener(this);
        button_home.setOnClickListener(this);
    }

    private void setTypeface() {
        Typeface typeface=Typeface.createFromAsset(getAssets(), FONT_PATH);
        playerPoint.setTypeface(typeface);

    }


}

