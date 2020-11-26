package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.factor.gymnasium.R;

public class TrainerListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trainer List");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
}