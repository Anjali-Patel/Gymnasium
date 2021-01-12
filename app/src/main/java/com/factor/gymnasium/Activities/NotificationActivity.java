package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.factor.gymnasium.Adapter.BookingHistoryAdapter;
import com.factor.gymnasium.Adapter.NotificationAdapter;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.Modal.NotificationModel;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
RecyclerView notification_list;
    private ArrayList<NotificationModel> notificationModelArrayList;
    private  NotificationModel notificationModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notification_list=findViewById(R.id.notification_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.notification);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notification_list.setLayoutManager(linearLayoutManager);
//        cancelled_bookingList.setHasFixedSize(true);
        notificationModelArrayList = new ArrayList<>();
        notificationModel=new NotificationModel();
        for(int i=0;i<6;i++){
            notificationModel.setMessage("You have booked gym");
            notificationModel.setDate_time("12 November,2020 5:pm");
            notificationModel.setGym_logo(R.drawable.booking_logo);
            notificationModelArrayList.add(notificationModel);
        }

        NotificationAdapter notificationAdapter =new NotificationAdapter(getApplicationContext(),notificationModelArrayList);
        notification_list.setAdapter(notificationAdapter);
    }
}