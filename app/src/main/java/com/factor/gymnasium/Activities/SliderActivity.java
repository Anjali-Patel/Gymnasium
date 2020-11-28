package com.factor.gymnasium.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.factor.gymnasium.Adapter.HomeAdapter;
import com.factor.gymnasium.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SliderActivity extends AppCompatActivity {
    CircleIndicator indicator;
    ViewPager mPager;
    private static int currentPage = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Integer> XMENArray ;
    private static final Integer[] XMEN = {R.drawable.gym_image, R.drawable.gym1, R.drawable.gym2,R.drawable.gym3,R.drawable.gym4,R.drawable.gym5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        XMENArray = new ArrayList<Integer>();
        imageSlider();
    }

    public void Login(View view) {
        Intent intent= new Intent(SliderActivity.this,OtpActivity.class);
        startActivity(intent);
    }
    private void imageSlider() {
        for (int i = 0; i < XMEN.length; i++)
            XMENArray.add(XMEN[i]);
        mPager.setAdapter(new HomeAdapter(SliderActivity.this, XMENArray));
        indicator.setViewPager(mPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

    }

}