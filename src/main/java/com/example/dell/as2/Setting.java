package com.example.dell.as2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.FaceDetector;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

public class Setting extends AppCompatActivity {
    public static final String FONT_PATH="font/VNI-Diudang.ttf";
    public static final String MUSIC_ENABLE = "MUSIC";
    public static final String PREFS = "PREFS";
    public static final String MUSIC_MAIN = "MUSIC MAIN";

    //View
    private TextView tvMusic;
    private TextView tvLanguage;

    //Button
    private Button btnVi;
    private Button btnEng;
    private Button btnJa;
    private ToggleButton tgMusic;

    private Button Google;
    private Button FaceBook;
    private Button Twitter;

    //SharedPreferences
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        findViewById();
        setTypeFace();
    }
    private void findViewById(){
        tvMusic = findViewById(R.id.tvMusic);
        tvLanguage = findViewById(R.id.tvLanguageChoose);
        btnVi = findViewById(R.id.btn_vi);
        btnEng = findViewById(R.id.btn_eng);
        btnJa = findViewById(R.id.btn_ja);
        tgMusic = findViewById(R.id.tgMusic);
        Google= findViewById(R.id.btnGG);
        FaceBook=findViewById(R.id.btnFB);
        Twitter=findViewById(R.id.btnTW);


        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/signin/v2/identifier?flowName=GlifWebSignIn&flowEntry=ServiceLogin"));
                startActivity(browserIntent);
            }
        });
        FaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(browserIntent);
            }
        });
        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));
                startActivity(browserIntent);
            }
        });
        btnVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changelanguage("vi");
                sharedPreferences.edit().putBoolean(MUSIC_MAIN, false).apply();
                Intent intent = new Intent( Setting.this, MainActivity.class);
                startActivity(intent);

            }
        });
        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changelanguage("eng");
                sharedPreferences.edit().putBoolean(MUSIC_MAIN, false).apply();
                Intent intent = new Intent(Setting.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnJa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changelanguage("ja");
                sharedPreferences.edit().putBoolean(MUSIC_MAIN, false).apply();
                Intent intent = new Intent(Setting.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void Changelanguage(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Set the music button status
        tgMusic.setChecked(sharedPreferences.getBoolean(MUSIC_ENABLE, true));
        tgMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Toast.makeText(Setting.this, "The music is pause", Toast.LENGTH_SHORT).show();
                    stopService(new Intent(Setting.this, MediaServicce.class));
                    sharedPreferences.edit().putBoolean(MUSIC_ENABLE, false).apply();
                    sharedPreferences.edit().putBoolean(MUSIC_MAIN, false).apply();

                } else {
                    Toast.makeText(Setting.this, "The music is continue", Toast.LENGTH_SHORT).show();
                    startService(new Intent(Setting.this, MediaServicce.class));
                    sharedPreferences.edit().putBoolean(MUSIC_ENABLE, true).apply();
                    sharedPreferences.edit().putBoolean(MUSIC_MAIN, false).apply();
                }
            }
        });
    }


    private void setTypeFace() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), FONT_PATH);
        tvMusic.setTypeface(typeface);
        tvLanguage.setTypeface(typeface);
        btnVi.setTypeface(typeface);
        btnEng.setTypeface(typeface);
        btnJa.setTypeface(typeface);
        tgMusic.setTypeface(typeface);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Set Sharepreferences for MainActivity
        if (sharedPreferences.getBoolean(MUSIC_ENABLE, true)) {
            sharedPreferences.edit().putBoolean(MUSIC_MAIN, true).apply();
        }
    }
}

