package com.factor.gymnasium.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;
import com.goodiebag.pinview.Pinview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.factor.gymnasium.Globals.GlobalItems.MEMBER_BASE_URL;
import static com.factor.gymnasium.Globals.GlobalItems.openAct;

public class MainActivity extends AppCompatActivity {
EditText mobile_number,otp,email,password;
String str_mobile,str_otp,str_email,str_password;
FrameLayout progressBarHolder;
String member_id,gym_id="5";

    @Override
    protected void onStart() {
        super.onStart();

    }

    Pinview pinview;
    SharedPreferenceUtils preferances;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferances = SharedPreferenceUtils.getInstance(this);
        pinview = findViewById(R.id.pinview);
        member_id=preferances.getStringValue("MEMBER_ID","");
        progressBarHolder=findViewById(R.id.progressBarHolder);
        mobile_number=findViewById(R.id.mobile_number);
//        otp=findViewById(R.id.otp);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }

        }
//   otpView.setKeyListener();


    }
    public void registerUser(View view) {
     Intent intent= new Intent(MainActivity.this,RegistrationActivity.class);
     startActivity(intent);
    }
    public void Login(View view) {
        str_email=email.getText().toString().trim();
        str_password=password.getText().toString().trim();
        if(str_email.equalsIgnoreCase("")){
            email.requestFocus();
            email.setError("Please enter email address");
        }else if(str_password.equalsIgnoreCase("")){
            password.requestFocus();
            password.setError("Please enter password");
        }/*else if(str_password.length()<5){
            password.requestFocus();
            password.setError("Minimum length of password should be 5");
         }*/else if(!isValidEmail(str_email)){
            email.requestFocus();
            email.setError("Please enter valid email address");
        }else if(!GlobalItems.isInternetAvailable(MainActivity.this)){
            Toast.makeText(MainActivity.this,R.string.check_internetConnection,Toast.LENGTH_LONG).show();
        } else{
            loginMember();
        }
    }

    private void loginMember() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = MEMBER_BASE_URL+"user/login.php?email="+str_email+"&"+"password="+str_password;
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                  @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            member_id=jsonObject.getString("user_id");
                            preferances.setValue("MEMBER_ID",member_id);
                            preferances.setValue(GlobalItems.FULL_NAME,jsonObject.getString("full_name"));
                            preferances.setValue(GlobalItems.EMAIL_ID,jsonObject.getString("email"));
                            Toast.makeText(MainActivity.this, jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            Intent intent= new Intent(MainActivity.this,UserDashboardActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }

                  }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Wrong Email Id or Password", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonRequest);
    }

    public void forgotPassword(View view) {
        Intent intent= new Intent(MainActivity.this,EmailVerificationActivity.class);
        startActivity(intent);
    }
    public void submit(View view) {
        str_mobile=mobile_number.getText().toString().trim();
        if(str_mobile.equalsIgnoreCase("")){
            mobile_number.requestFocus();
            mobile_number.setError("Please enter mobile number");
        } else if(str_mobile.length()!=10){
            mobile_number.requestFocus();
            mobile_number.setError("Mobile number length should be 10");
        } else if(!GlobalItems.isInternetAvailable(MainActivity.this)){
            Toast.makeText(MainActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
        } else {

        }
//        Intent intent= new Intent(MainActivity.this,ForgotActivity.class);
//        startActivity(intent);
    }
/*
    public void Verify(View view) {
        str_otp=otp.getText().toString().trim();
        if(str_otp.equalsIgnoreCase("")){
            otp.requestFocus();
            otp.setError("Please enter OTP");
        }else if(str_otp.length()!=4){
            otp.requestFocus();
            otp.setError("OTP Length should be 4 Digit");
        }else if(!GlobalItems.isInternetAvailable(MainActivity.this)){
            Toast.makeText(MainActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
        } else {
            Intent intent= new Intent(MainActivity.this,UserDashboardActivity.class);
            startActivity(intent);
        }


    }
*/
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}