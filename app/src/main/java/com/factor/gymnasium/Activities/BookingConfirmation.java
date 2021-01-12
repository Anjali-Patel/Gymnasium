package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Adapter.TrainerListAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.Modal.TrainerListModel;
import com.factor.gymnasium.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BookingConfirmation extends AppCompatActivity {
    RecyclerView trainerList;
    ArrayList<TrainerListModel> trainerModelsList;
    TrainerListModel trainerListModel;
    Dialog dialog;
    FrameLayout progressBarHolder;
    String date,time,member_id,gym_id;
    SharedPreferenceUtils preferances;
TextView booking_date,booking_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        booking_date=findViewById(R.id.booking_date);
        booking_time=findViewById(R.id.booking_time);
        preferances = SharedPreferenceUtils.getInstance(BookingConfirmation.this);
        member_id= preferances.getStringValue("MEMBER_ID","");
        gym_id="5";
        date=getIntent().getStringExtra("date");
        time=getIntent().getStringExtra("time");
        booking_date.setText(date);
        booking_time.setText(time);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        trainerList=findViewById(R.id.trainerList);
        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(BookingConfirmation.this))){
            Toast.makeText(BookingConfirmation.this,R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            trainerList();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.booking_confirmation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void trainerList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        trainerList.setLayoutManager(linearLayoutManager);
//        cancelled_bookingList.setHasFixedSize(true);
        trainerModelsList = new ArrayList<>();
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/trainer/read.php";

        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("records");
                            for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                trainerListModel=new TrainerListModel();
                                trainerListModel.setTrainer_id(jsonObject1.getString("trainer_id"));
                                trainerListModel.setName(jsonObject1.getString("full_name"));
                                trainerListModel.setContact_number(jsonObject1.getString("mobile_number"));
                                trainerListModel.setGender(jsonObject1.getString("gender"));
                                trainerListModel.setAge(jsonObject1.getString("age"));
                                trainerListModel.setAdress(jsonObject1.getString("address"));
                                trainerListModel.setQualification(jsonObject1.getString("certification"));
                                trainerListModel.setEmailaddress(jsonObject1.getString("email"));
                                trainerListModel.setProfile_pic(R.drawable.green_icon);
                                trainerModelsList.add(trainerListModel);
                            }
                            TrainerListAdapter trainerListAdapter =new TrainerListAdapter(BookingConfirmation.this,trainerModelsList);
                            trainerList.setAdapter(trainerListAdapter);
                            trainerListAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(BookingConfirmation.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(BookingConfirmation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookingConfirmation.this);
        requestQueue.add(jsonRequest);
    }
    public void select(View view) {
        bookingConfirmationDialog();
    }
    private void bookingConfirmation() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url ="http://printacheque.com/gymapp/api/schedule/create.php";
        RequestQueue requestQueue = Volley.newRequestQueue(BookingConfirmation.this);
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id",gym_id);
            object.put("session_date",date);
            object.put("session_time",time);
            object.put("member_id", member_id);
            object.put("status","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(BookingConfirmation.this,response.getString("message"),Toast.LENGTH_LONG).show();
                            preferances.setValue("sessionDateTime",date+" "+time);
                            preferances.setValue("sessionDate",date );
                            preferances.setValue("sessionTime", time);
                            preferances.setValue("status",  "1");
                            Intent intent= new Intent(BookingConfirmation.this,UserDashboardActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(BookingConfirmation.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(BookingConfirmation.this, "Same day more than one booking not possible for same member",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void bookingConfirmationDialog() {
        dialog = new Dialog(BookingConfirmation.this, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(getString(R.string.yes));
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setText(getString(R.string.no));
        tv1.setText(R.string.confirm_booking);
        b1.setOnClickListener(v -> {
            dialog.dismiss();
            bookingConfirmation();

        });
        b2.setOnClickListener(v -> {
            dialog.dismiss();
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}