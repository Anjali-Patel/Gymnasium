package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.factor.gymnasium.Fragment.HomeFragment;
import com.factor.gymnasium.Fragment.SchedulingFragment;
import com.factor.gymnasium.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.factor.gymnasium.Activities.UserDashboardActivity.toolbar_title;

public class BookingDetailsActivity extends AppCompatActivity {
TextView gym_name,gym_datetime, gym_name1,gym_datetime1,book_again,share;
File imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        Intent intent=getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initValue();

        //  toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.booking_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        gym_name.setText(intent.getStringExtra("gym_name"));
        gym_datetime.setText(intent.getStringExtra("date_timme"));
        gym_name1.setText(intent.getStringExtra("gym_name"));
        gym_datetime1.setText(intent.getStringExtra("date_timme"));
        book_again.setOnClickListener(view -> {
//            loadSchedulingFragment();
     Intent myIntent= new Intent(BookingDetailsActivity.this,UserDashboardActivity.class);
     startActivity(myIntent);
        });
        share.setOnClickListener(view -> {
           /* Bitmap bitmap = takeScreenshot();
            saveBitmap(bitmap);
            shareIt();*/
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
            startActivity(Intent.createChooser(intent2, "Share via"));
        });
    }

    private void initValue() {
        gym_name=findViewById(R.id.gym_name);
        gym_datetime=findViewById(R.id.gym_datetime);
        gym_name1=findViewById(R.id.gym_name1);
        book_again=findViewById(R.id.book_again);
        share=findViewById(R.id.share);
        gym_datetime1=findViewById(R.id.gym_datetime1);
    }

    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "My highest score with screen shot";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Catch score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
         imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
//            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
//            Log.e("GREC", e.getMessage(), e);
        }
    }
    private void loadSchedulingFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        FragmentTransaction transaction = getSupportParentActivityIntent().beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, new SchedulingFragment());
        transaction.addToBackStack(null);
        toolbar_title.setText(R.string.new_booking);

        transaction.commit();
    }

}