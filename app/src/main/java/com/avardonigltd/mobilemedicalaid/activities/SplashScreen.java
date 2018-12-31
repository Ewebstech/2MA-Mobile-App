package com.avardonigltd.mobilemedicalaid.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 6000;
    Animation fromBottom,fromTop;
    TextView messageSplash;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        messageSplash = findViewById(R.id.messageSplash);
        img = findViewById(R.id.img);

        fromBottom = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.frombottom);
        fromTop = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fromtop);
        messageSplash.setAnimation(fromBottom);
        img.setAnimation(fromTop);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AppPreference.isFirstTimeLaunched()){
                    startActivity(new Intent(SplashScreen.this, WelcomePage.class));
                }
                else{
                    if(AppPreference.isLogged()){
                        startActivity(new Intent(SplashScreen.this, NavigationalDrawer.class));
                    }else{startActivity(new Intent(SplashScreen.this, Login.class));}
                }
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    }

