package com.factor.gymnasium.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Adapter.HomeAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SliderActivity extends AppCompatActivity {
    CircleIndicator indicator;
    ViewPager mPager;
    String str_mobile_number,random_number;
    private static int currentPage = 0;
EditText mobile_number;
FrameLayout progressBarHolder;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Integer> XMENArray ;
    private static final Integer[] XMEN = {R.drawable.gym_image, R.drawable.gym1, R.drawable.gym2,R.drawable.gym3,R.drawable.gym4,R.drawable.gym5};
String member_id="21";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        mPager = findViewById(R.id.pager);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        mobile_number=findViewById(R.id.mobile_number);
        indicator = findViewById(R.id.indicator);
        XMENArray = new ArrayList<Integer>();
        imageSlider();
    }

    public void Login(View view) {
         str_mobile_number=mobile_number.getText().toString().trim();
        if(str_mobile_number.equalsIgnoreCase("")){
            mobile_number.setError("Please enter mobile number");
        }else if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(SliderActivity.this))){
            Toast.makeText(SliderActivity.this,"Please Check your internet Connection",Toast.LENGTH_LONG).show();
        } else{
            mobileVerification();
        }

    }

    private void mobileVerification() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/user/mobileexists.php";

        RequestQueue requestQueue = Volley.newRequestQueue(SliderActivity.this);
        JSONObject object = new JSONObject();
        try {
            object.put("mobile_number",str_mobile_number);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(SliderActivity.this,"Mobile number verified successfully",Toast.LENGTH_LONG).show();
                            getSms();
                        } catch (Exception e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(SliderActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(SliderActivity.this,"Mobile number does not exist",Toast.LENGTH_LONG).show();

//                Toast.makeText(SliderActivity.this,  error.toString(),Toast.LENGTH_LONG).show();
//getSms();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void getSms() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        random_number=String.valueOf(n);
        Log.i("Random Number", String.valueOf(n));
        String url = "http://2factor.in/API/V1/b541f5dd-23d8-11e9-9ee8-0200cd936042/ADDON_SERVICES/SEND/TSMS";

        RequestQueue requestQueue = Volley.newRequestQueue(SliderActivity.this);
        JSONObject object = new JSONObject();try {
            object.put("From","ETCOTP");
            object.put("To",str_mobile_number);
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
                            Toast.makeText(SliderActivity.this,"Mobile number verified successfully",Toast.LENGTH_LONG).show();
                          Intent intent= new Intent(SliderActivity.this,OtpActivity.class);
                         intent.putExtra("otp",random_number);
                         intent.putExtra("mobile_number",str_mobile_number);
                                  startActivity(intent);
                        } catch (Exception e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(SliderActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(SliderActivity.this,"Mobile number does not exist",Toast.LENGTH_LONG).show();

//                Toast.makeText(SliderActivity.this,  error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

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