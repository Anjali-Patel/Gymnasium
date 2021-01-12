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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class ForgotActivity extends AppCompatActivity {
  EditText password,confirm_password;
   String str_paswword,str_confirm_password,str_email;
   FrameLayout progressBarHolder;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        str_email=getIntent().getStringExtra("registered_email");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.forgot_password_activity);
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
          resetPassword();
        }
    }

    private void resetPassword() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url ="http://printacheque.com/gymapp/api/user/resetpassword.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("email",str_email);
            object.put("password",str_paswword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(ForgotActivity.this, response.getString("message"),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent (ForgotActivity.this, MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(ForgotActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(ForgotActivity.this,  error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{5,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}