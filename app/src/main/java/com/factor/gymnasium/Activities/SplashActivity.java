package com.factor.gymnasium.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    SharedPreferenceUtils preferances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferances = SharedPreferenceUtils.getInstance(this);

        final ImageView im1 = (ImageView)findViewById(R.id.im1);
        final Animation zoomanimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.animated_logo);
        im1.startAnimation(zoomanimation);
        zoomanimation.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationStart(Animation animation){
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if(!preferances.getStringValue("MEMBER_ID","").equalsIgnoreCase("")){
                            Intent intent =new Intent(SplashActivity.this,UserDashboardActivity.class);
                            startActivity(intent);
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                        }else{
                            Intent intent =new Intent(SplashActivity.this,SliderActivity.class);
                            startActivity(intent);
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                        }

//                                handler.removeCallbacks(this);

                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask,3000);

            }

            public void onAnimationEnd(Animation animation){


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });
    }
}