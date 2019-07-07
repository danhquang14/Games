package com.example.dell.as2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class PlayActivity extends AppCompatActivity implements Runnable, SensorEventListener  {

    public static final String FONT_PATH="font/VNI-Diudang.ttf";
    public static final String SCORE = "SCORE";
    private TextView tv_score;
    private TextView timer;
    private ImageView iv_11,iv_12,iv_13,iv_14,iv_21,iv_22,iv_23,iv_24,iv_31,iv_32,iv_33,iv_34;
    private CountDownTimer mcountDownTimer;
    //array for the images
    private Integer[] cardsArray=   {101,102,103,104,105,106,201,202,203,204,205,206};
    //actual images
    private int image101,image102,image103,image104,image105,image106,image201,image202,image203,image204,image205,image206;

    private int firstCard, secondCard;
    private int clickedFirst, clickedSecond;
    private int cardNumber=1;

    private int turn=1;
    private int mbest;
    private int playerPoint=0;
    private SharedPreferences sharedPreferences;
    //Swipe variable
    float x1,x2,y1,y2;
    SensorManager sensorManager;
    Sensor sensor;
    float acceleratorValue, acceleratorLast, shakeAmout;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);



        timer=findViewById(R.id.time_bar_text);
        tv_score=findViewById(R.id.tv_score);

        relativeLayout = (RelativeLayout) findViewById(R.id.Play_page) ;
        iv_11=findViewById(R.id.iv_11);
        iv_12=findViewById(R.id.iv_12);
        iv_13=findViewById(R.id.iv_13);
        iv_14=findViewById(R.id.iv_14);
        iv_21=findViewById(R.id.iv_21);
        iv_22=findViewById(R.id.iv_22);
        iv_23=findViewById(R.id.iv_23);
        iv_24=findViewById(R.id.iv_24);
        iv_31=findViewById(R.id.iv_31);
        iv_32=findViewById(R.id.iv_32);
        iv_33=findViewById(R.id.iv_33);
        iv_34=findViewById(R.id.iv_34);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acceleratorValue = SensorManager.GRAVITY_EARTH;
        acceleratorLast = SensorManager.GRAVITY_EARTH;
        shakeAmout = 0.00f;

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");
        //load the card images
        setTypeface();
        setGameScore();
        frontOfCardsResource();


        //shuffle the images
        Collections.shuffle(Arrays.asList(cardsArray));
        //time countdown
        startTimer();
        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_11,theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_12,theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_13,theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_14,theCard);
            }
        });
        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_21,theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_22,theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_23,theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_24,theCard);
            }
        });
        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_31,theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_32,theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_33,theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard=Integer.parseInt((String) view.getTag());
                doStuff(iv_34,theCard);
            }
        });
        }
    private void setTypeface() {
        Typeface typeface=Typeface.createFromAsset(getAssets(), FONT_PATH);
        tv_score.setTypeface(typeface);


    }

        private void doStuff(ImageView iv,int card){
        //set the correct image to the imageview
         if (cardsArray[card]==101){
             iv.setImageResource(image101);
         } else if (cardsArray[card]==102){
             iv.setImageResource(image102);
         }
         else if (cardsArray[card]==103 ){
             iv.setImageResource(image103);
         }
         else if (cardsArray[card]==104){
             iv.setImageResource(image104);
         }
         else if (cardsArray[card]==105){
             iv.setImageResource(image105);
         }
         else if (cardsArray[card]==106){
             iv.setImageResource(image106);
         }
         else if (cardsArray[card]==201){
             iv.setImageResource(image201);
         }
         else if (cardsArray[card]==202){
             iv.setImageResource(image202);
         }
         else if (cardsArray[card]==203){
             iv.setImageResource(image203);
         }
         else if (cardsArray[card]==204){
             iv.setImageResource(image204);
         }
         else if (cardsArray[card]==205){
             iv.setImageResource(image205);
         }
         else if (cardsArray[card]==206){
             iv.setImageResource(image206);
         }
         //checkwhich image is selected amd save it to temporary varaible
         if (cardNumber == 1){
             firstCard=cardsArray[card];
             if (firstCard>200){
                 firstCard=firstCard-100;
             }
             cardNumber=2;
             clickedFirst=card;

             iv.setEnabled(false);
         }else if (cardNumber==2){
             secondCard=cardsArray[card];
             if (secondCard>200){
                 secondCard=secondCard-100;
             }
             cardNumber=1;
             clickedSecond=card;

             iv_11.setEnabled(false);
             iv_12.setEnabled(false);
             iv_13.setEnabled(false);
             iv_14.setEnabled(false);
             iv_21.setEnabled(false);
             iv_22.setEnabled(false);
             iv_23.setEnabled(false);
             iv_24.setEnabled(false);
             iv_31.setEnabled(false);
             iv_32.setEnabled(false);
             iv_33.setEnabled(false);
             iv_34.setEnabled(false);
             Handler handler=new Handler();
             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     //check if the selected images are equal
                    calculate();
                 }
             },1000);
         }
        }

        private void calculate() {
            // if  images are equal remove item and add point
            if (firstCard == secondCard) {
                if (clickedFirst == 0) {
                    iv_11.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 1) {
                    iv_12.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 2) {
                    iv_13.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 3) {
                    iv_14.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 4) {
                    iv_21.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 5) {
                    iv_22.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 6) {
                    iv_23.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 7) {
                    iv_24.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 8) {
                    iv_31.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 9) {
                    iv_32.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 10) {
                    iv_33.setVisibility(View.INVISIBLE);
                } else if (clickedFirst == 11) {
                    iv_34.setVisibility(View.INVISIBLE);
                }


                if (clickedSecond == 0) {
                    iv_11.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 1) {
                    iv_12.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 2) {
                    iv_13.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 3) {
                    iv_14.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 4) {
                    iv_21.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 5) {
                    iv_22.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 6) {
                    iv_23.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 7) {
                    iv_24.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 8) {
                    iv_31.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 9) {
                    iv_32.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 10) {
                    iv_33.setVisibility(View.INVISIBLE);
                } else if (clickedSecond == 11) {
                    iv_34.setVisibility(View.INVISIBLE);
                }
                if (turn == 1) {
                    playerPoint++;
                    tv_score.setText("Score: " + playerPoint);
                   // SharedPreferences preferences=getSharedPreferences("PREFS",0);
                   // SharedPreferences.Editor editor=preferences.edit();
                   // editor.putInt("lastScore",playerPoint);
                   // editor.apply();

                }
            } else {
                iv_11.setImageResource(R.drawable.am1);
                iv_12.setImageResource(R.drawable.am1);
                iv_13.setImageResource(R.drawable.am1);
                iv_14.setImageResource(R.drawable.am1);
                iv_21.setImageResource(R.drawable.am1);
                iv_22.setImageResource(R.drawable.am1);
                iv_23.setImageResource(R.drawable.am1);
                iv_24.setImageResource(R.drawable.am1);
                iv_31.setImageResource(R.drawable.am1);
                iv_32.setImageResource(R.drawable.am1);
                iv_33.setImageResource(R.drawable.am1);
                iv_34.setImageResource(R.drawable.am1);
            }
            iv_11.setEnabled(true);
            iv_12.setEnabled(true);
            iv_13.setEnabled(true);
            iv_14.setEnabled(true);
            iv_21.setEnabled(true);
            iv_22.setEnabled(true);
            iv_23.setEnabled(true);
            iv_24.setEnabled(true);
            iv_31.setEnabled(true);
            iv_32.setEnabled(true);
            iv_33.setEnabled(true);
            iv_34.setEnabled(true);

            //check if the game is over
            checkEnd();
        }

    private void startTimer() {
        timer.setText("60");
        mcountDownTimer=new CountDownTimer(60*1000,1000){
            @Override
            public void onTick(long millisUntilFinished){
                timer.setText("" + millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                ChangetoanotherPage();
            }
        }.start();
    }
    private void setGameScore() {
        @SuppressLint("StringFormatMatches") String score = getString(R.string.score, playerPoint);
        tv_score.setText(score);

        sharedPreferences = getSharedPreferences(SCORE, Context.MODE_PRIVATE);
        mbest = sharedPreferences.getInt(SCORE, 0);
        @SuppressLint({"StringFormatMatches", "StringFormatInvalid", "LocalSuppress"}) String best = getString(R.string.best, mbest);
    }
    private void checkEnd(){
         if (iv_11.getVisibility()==View.INVISIBLE&&
                 iv_12.getVisibility()==View.INVISIBLE&&
                 iv_13.getVisibility()==View.INVISIBLE&&
                 iv_14.getVisibility()==View.INVISIBLE&&
                 iv_21.getVisibility()==View.INVISIBLE&&
                 iv_22.getVisibility()==View.INVISIBLE&&
                 iv_23.getVisibility()==View.INVISIBLE&&
                 iv_24.getVisibility()==View.INVISIBLE&&
                 iv_31.getVisibility()==View.INVISIBLE&&
                 iv_32.getVisibility()==View.INVISIBLE&&
                 iv_33.getVisibility()==View.INVISIBLE&&
                 iv_34.getVisibility()==View.INVISIBLE
                 ) { if (playerPoint>mbest){
             sharedPreferences.edit().putInt(SCORE, playerPoint).apply();

         }
            ChangetoanotherPage();

         }

    }

    private void ChangetoanotherPage() {
        mcountDownTimer.cancel();
        Intent intent = new Intent (PlayActivity.this, GameOver.class);
        intent.putExtra(SCORE,playerPoint);
        startActivity(intent);
        finish();
    }



    private void frontOfCardsResource() {
        image101=R.drawable.ic_image101;
        image102=R.drawable.ic_image102;
        image103=R.drawable.ic_image103;
        image104=R.drawable.ic_image104;
        image105=R.drawable.ic_image105;
        image106=R.drawable.ic_image106;
        image201=R.drawable.ic_image201;
        image202=R.drawable.ic_image202;
        image203=R.drawable.ic_image203;
        image204=R.drawable.ic_image204;
        image205=R.drawable.ic_image205;
        image206=R.drawable.ic_image206;

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
                if(x1<x2){
                    Intent intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        acceleratorLast = acceleratorValue;
        acceleratorValue = (float) Math.sqrt((double) (x*x+y*y+z*z));
        float delta = acceleratorValue - acceleratorLast;
        shakeAmout = shakeAmout * 0.9f + delta;

        if (shakeAmout < 2){
            relativeLayout.setBackgroundResource(R.drawable.snowman2);

        }
        else if (shakeAmout < 4) {
            relativeLayout.setBackgroundResource(R.drawable.ko1);
        }

        else {
            relativeLayout.setBackgroundResource(R.drawable.ko3);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected  void onStart() {
        super.onStart();
        sensorManager.registerListener((SensorEventListener) PlayActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener((SensorEventListener) PlayActivity.this);
    }

    @Override
    public void run() {

    }
}

