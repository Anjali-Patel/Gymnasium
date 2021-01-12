package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.factor.gymnasium.R;

import java.util.Objects;

public class PersonalProfileActivity extends AppCompatActivity {
String strName,str_mobile_number,strdob,str_address,str_Email;
TextView name,address,email;
EditText mobile,dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        dob=findViewById(R.id.dob);
        strName=getIntent().getStringExtra("name");
        str_mobile_number=getIntent().getStringExtra("mobile_number");
        str_address=getIntent().getStringExtra("address");
        str_Email=getIntent().getStringExtra("email");
        name.setText(strName);
        address.setText(str_address);
        email.setText(str_Email);
        mobile.setText(str_mobile_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.personal_information);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public void skip(View view) {
        Intent intent= new Intent(PersonalProfileActivity.this,UserDashboardActivity.class);
        startActivity(intent);
    }
}