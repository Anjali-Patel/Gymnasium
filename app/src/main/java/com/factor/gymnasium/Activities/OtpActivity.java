package com.factor.gymnasium.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.factor.gymnasium.R;

public class OtpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
    }
    public void submit(View view) {
        Intent intent= new Intent(OtpActivity.this,MainActivity.class);
        startActivity(intent);
    }
}