package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.R;

import java.util.regex.Pattern;

public class ForgotActivity extends AppCompatActivity {
EditText password,confirm_password;
String str_paswword,str_confirm_password;
FrameLayout progressBarHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.forgot_password);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBarHolder=findViewById(R.id.progressBarHolder);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
    }
    public void submit(View view) {
        str_paswword=password.getText().toString().trim();
        str_confirm_password=confirm_password.getText().toString().trim();
        if(str_paswword.equalsIgnoreCase("")){
            password.requestFocus();
            password.setError("Please enter password");
        }else if(str_confirm_password.equalsIgnoreCase("")){
            confirm_password.requestFocus();
            confirm_password.setError("Please enter confirm password");
        }else if(!str_confirm_password.equalsIgnoreCase(str_paswword)){
            confirm_password.requestFocus();
            confirm_password.setError("password not matching");
        }else if(str_paswword.length()<5){
            password.requestFocus();
            password.setError("Minimum length of password should be 5");
        } else if(!isValidPassword(str_paswword)){
            password.requestFocus();
            password.setError("Please enter valid password");
        }else if(!GlobalItems.isInternetAvailable(ForgotActivity.this)){
            Toast.makeText(ForgotActivity.this,R.string.check_internetConnection,Toast.LENGTH_LONG).show();
        } else{
            Intent intent= new Intent(ForgotActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{5,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}