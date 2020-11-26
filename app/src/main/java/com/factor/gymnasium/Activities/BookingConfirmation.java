package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.factor.gymnasium.Adapter.TrainerListAdapter;
import com.factor.gymnasium.Modal.TrainerListModel;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class BookingConfirmation extends AppCompatActivity {
    RecyclerView trainerList;
    ArrayList<TrainerListModel> trainerModelsList;
    TrainerListModel trainerListModel;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        trainerList=findViewById(R.id.trainerList);
        trainerList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.booking_details);
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
        trainerListModel=new TrainerListModel();
        for(int i=0;i<6;i++){
            trainerListModel.setName("Rahul Shetty");
            trainerListModel.setGender("Male");
            trainerListModel.setProfile_pic(R.drawable.green_icon);
            trainerModelsList.add(trainerListModel);

        }

        TrainerListAdapter trainerListAdapter =new TrainerListAdapter(BookingConfirmation.this,trainerModelsList);
        trainerList.setAdapter(trainerListAdapter);
    }

    public void select(View view) {
        bookingConfirmation();
    }
    private void bookingConfirmation() {
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