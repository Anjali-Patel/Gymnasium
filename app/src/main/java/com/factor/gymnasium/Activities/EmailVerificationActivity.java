package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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

import static com.factor.gymnasium.Globals.GlobalItems.MEMBER_BASE_URL;

public class EmailVerificationActivity extends AppCompatActivity {
 EditText enter_email;
 FrameLayout progressBarHolder;
 String str_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        enter_email=findViewById(R.id.enter_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.forgot_password_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } }
    public void submit(View view) {
        str_email=enter_email.getText().toString().trim();
        if(str_email.equalsIgnoreCase("")){
            enter_email.setError("Please enter your email address");
            enter_email.requestFocus();
        } else if(!GlobalItems.isInternetAvailable(EmailVerificationActivity.this)){
            Toast.makeText(EmailVerificationActivity.this,R.string.check_internetConnection,Toast.LENGTH_LONG).show();
        } else{
                emailVerification();
        }
    }
    private void emailVerification() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url =MEMBER_BASE_URL+"user/emailexists.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("email",str_email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(EmailVerificationActivity.this, "Email verified successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent (EmailVerificationActivity.this,ForgotActivity.class);
                            intent.putExtra("registered_email",response.getString("email"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(EmailVerificationActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(EmailVerificationActivity.this,  error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}