package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrainerDetailActivity extends AppCompatActivity {
TextView trainer_name,trainer_contact_number,trainer_gender,trainer_age,Trainer_Address,Trainer_Qualification,Trainer_Email_address;
FrameLayout progressBarHolder;
private String trainer_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_detail);
        trainer_id=getIntent().getStringExtra("trainer_id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.trainer_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(TrainerDetailActivity.this))){
            Toast.makeText(TrainerDetailActivity.this,R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            trainerListDetails();
        }
    }

    private void trainerListDetails() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/trainer/read_one.php?trainer_id="+trainer_id;

        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            trainer_name.setText(jsonObject.getString("full_name").replace("null",""));
                            trainer_contact_number.setText(jsonObject.getString("mobile_number").replace("null",""));
                            trainer_gender.setText(jsonObject.getString("gender").replace("null",""));
                            trainer_age.setText(jsonObject.getString("age").replace("null",""));
                            Trainer_Address.setText(jsonObject.getString("address").replace("null",""));
                            Trainer_Qualification.setText(jsonObject.getString("certification").replace("null",""));
                            Trainer_Email_address.setText(jsonObject.getString("email").replace("null",""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(TrainerDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(TrainerDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TrainerDetailActivity.this);
        requestQueue.add(jsonRequest);
    }

    private void initView() {
        progressBarHolder=findViewById(R.id.progressBarHolder);

        trainer_name=findViewById(R.id.trainer_name);
        trainer_contact_number=findViewById(R.id.trainer_contact_number);
        trainer_gender=findViewById(R.id.trainer_gender);
        trainer_age=findViewById(R.id.trainer_age);
        Trainer_Address=findViewById(R.id.Trainer_Address);
        Trainer_Qualification=findViewById(R.id.Trainer_Qualification);
        Trainer_Email_address=findViewById(R.id.Trainer_Email_address);

    }
}