package com.avardonigltd.mobilemedicalaid.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.adapters.MyPageAdapter;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;

public class WelcomePage extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView[] dotstv;
    private int [] layouts;
    private Button btnskip;
    private Button btnnext;
    private MyPageAdapter myPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppPreference.setIsFirstTimeLaunch(false);

        setStatusBarTransparent();

        setContentView(R.layout.activity_welcome_page);

        viewPager = findViewById(R.id.vp);
        layoutDot = findViewById(R.id.linearPager);
        btnnext = findViewById(R.id.next);
        btnskip = findViewById(R.id.skip);

        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem()+1;
                if (currentPage< layouts.length){
                    viewPager.setCurrentItem(currentPage);
                }
                else {
                    startActivity();
                }
            }
        });

       layouts = new int [] {R.layout.slider_1,R.layout.slider_2,R.layout.slider_3};
        myPageAdapter = new MyPageAdapter(layouts,getApplicationContext());
        viewPager.setAdapter(myPageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == layouts.length-1){
                    btnnext.setText("GOT IT");
                    btnskip.setVisibility(View.GONE);
                } else {
                    btnnext.setText("NEXT");
                    btnskip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotStatus(0);
    }

    private void setDotStatus (int page){
        layoutDot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for (int i = 0; i <dotstv.length; i++ ){

            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#000000"));
            layoutDot.addView(dotstv[i]);
        }

        if (dotstv.length>0) {

            dotstv[page].setTextColor(Color.parseColor("#007CC2"));
        }
    }

    public void startActivity(){
        startActivity(new Intent(WelcomePage.this,Login.class));
        finish();
    }

    public void setStatusBarTransparent(){

        if (Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
