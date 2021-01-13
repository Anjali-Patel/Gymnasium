package com.factor.gymnasium.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.R;
import com.goodiebag.pinview.Pinview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.factor.gymnasium.Globals.GlobalItems.RECEIVE_OTP;

public class OtpActivity extends AppCompatActivity {
    Pinview pinview;
    String str_otp,random_otp,str_mobilenumber,random_number;
    FrameLayout progressBarHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        pinview=findViewById(R.id.pinview);
        random_otp=getIntent().getStringExtra("otp");
        str_mobilenumber=getIntent().getStringExtra("mobile_number");
    }
    public void submit(View view) {

         str_otp=pinview.getValue().trim();
        if(str_otp.equalsIgnoreCase("")){
            Toast.makeText(OtpActivity.this,"Please enter 6 Digit  OTP",Toast.LENGTH_LONG).show();
        }else if(str_otp.length()!=6){
            Toast.makeText(OtpActivity.this,"OTP length should be 6 Digit",Toast.LENGTH_LONG).show();
        }else if(!GlobalItems.isInternetAvailable(OtpActivity.this)){
            Toast.makeText(OtpActivity.this,"Please Check your internet Connection",Toast.LENGTH_LONG).show();
        } else{
            otpVerification();

        }

    }

    private void otpVerification() {
      if(str_otp.equalsIgnoreCase(random_otp)){
          Toast.makeText(OtpActivity.this,"OTP has been verified successfully", Toast.LENGTH_LONG).show();
          Intent intent= new Intent(OtpActivity.this,MainActivity.class);
          startActivity(intent);
      }else{
          Toast.makeText(OtpActivity.this,"OTP not matching", Toast.LENGTH_LONG).show();

      }


    }

    public void changeNumber(View view) {
        Intent intent = new Intent(OtpActivity.this,SliderActivity.class);
        startActivity(intent);
    }

    public void sendAgain(View view) {
        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(OtpActivity.this))){
            Toast.makeText(OtpActivity.this,"Please Check your internet Connection",Toast.LENGTH_LONG).show();
        } else{
            mobileVerification();

        }
    }
    private void mobileVerification() {
        progressBarHolder.setVisibility(View.VISIBLE);
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        random_number=String.valueOf(n);
        Log.i("Random Number", String.valueOf(n));
        String url = RECEIVE_OTP;

        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        JSONObject object = new JSONObject();
        try {
            object.put("From","ETCOTP");
            object.put("To",str_mobilenumber);
            object.put("TemplateName","ETCSweekar_OTP");
            object.put("VAR1",random_number);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(OtpActivity.this,"OTP has been sent on entered mobile number",Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(OtpActivity.this,OtpActivity.class);
                            intent.putExtra("otp",random_number);
                            startActivity(intent);
                        } catch (Exception e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(OtpActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(OtpActivity.this,error.toString(),Toast.LENGTH_LONG).show();

//                Toast.makeText(SliderActivity.this,  error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

}