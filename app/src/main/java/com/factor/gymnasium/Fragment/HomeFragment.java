package com.factor.gymnasium.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Activities.BookingConfirmation;
import com.factor.gymnasium.Activities.UserDashboardActivity;
import com.factor.gymnasium.Adapter.BannerAdapter;
import com.factor.gymnasium.Adapter.HomeAdapter;
import com.factor.gymnasium.Adapter.TestimonialAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static com.factor.gymnasium.Globals.GlobalItems.MEMBER_BASE_URL;


public class HomeFragment extends Fragment {
    public static final String inputFormat = "HH:mm";
    private Date date;
    private String dateCompareOne;
    private String dateCompareTwo;
    private String compareStringOne = "";
    private String compareStringTwo = "",upcomimhgTime="",compareStringOne1,compareStringTwo2,currentcompareStringOne1,currentcompareStringOne2;
    SimpleDateFormat inputParser;
    SharedPreferenceUtils preferances;
    int currentTime2,currentTime3;
    Dialog dialog;
    String currentDate,currentTime1,currentTime4;
FrameLayout progressBarHolder;
    CircleIndicator indicator,indicator1,indicator2;
    ViewPager mPager,mPager1,mPager2;
    private static int currentPage = 0;
TextView gym_name,time_stamp,no_booking,cancel_booking,tv1,tv2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Integer> XMENArray ;
    private ArrayList<Integer> XMENArray1 ;
    private ArrayList<Integer> XMENArray2 ;
    private static final Integer[] XMEN = {R.drawable.gym_image, R.drawable.gym1, R.drawable.gym2,R.drawable.gym3,R.drawable.gym4,R.drawable.gym5};
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mPager = view.findViewById(R.id.pager);
        tv2=view.findViewById(R.id.tv2);
        tv1=view.findViewById(R.id.tv1);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        time_stamp=view.findViewById(R.id.time_stamp);
        cancel_booking=view.findViewById(R.id.cancel_booking);
        no_booking=view.findViewById(R.id.no_booking);
        gym_name=view.findViewById(R.id.gym_name);
        indicator = view.findViewById(R.id.indicator);
        mPager1 = view.findViewById(R.id.pager1);
        indicator1 = view.findViewById(R.id.indicator1);
        mPager2 = view.findViewById(R.id.pager2);
        indicator2 = view.findViewById(R.id.indicator2);
       /* if(!preferances.getStringValue("GYM_NAME","").equalsIgnoreCase("")&& !preferances.getStringValue("sessionDateTime","").equalsIgnoreCase("")){
            no_booking.setVisibility(View.GONE);
            gym_name.setVisibility(View.VISIBLE);
            time_stamp.setVisibility(View.VISIBLE);
            cancel_booking.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);*/

        SimpleDateFormat t1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        currentTime1 = t1.format(new Date());
        SimpleDateFormat t2 = new SimpleDateFormat("HH", Locale.getDefault());
        currentTime2 = Integer.parseInt(t2.format(new Date()));
        SimpleDateFormat t3 = new SimpleDateFormat("mm", Locale.getDefault());
        currentTime3 = Integer.parseInt(t3.format(new Date()));
//        currentTime2=currentTime2+1;
        currentTime4=currentTime2+":"+currentTime3;
        String a=currentTime1+"-"+currentTime4;
//       String upcomimhgTime=preferances.getStringValue("sessionTime","");
//        String[] spliTtimeslot = upcomimhgTime.split("-");
//        String abc=spliTtimeslot[0];

     /*    upcomimhgTime=preferances.getStringValue("sessionTime","");
        String[] spliTtimeslot = upcomimhgTime.split("-");
        compareStringOne= spliTtimeslot[0];
        compareStringTwo= spliTtimeslot[1];*/
        inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        dateCompareOne = sdf.format(new Date());
        dateCompareTwo=preferances.getStringValue("sessionDate","");
//        compareDates();
        try{
            upcomimhgTime=preferances.getStringValue("sessionTime","");
            String[] spliTtimeslot = upcomimhgTime.split("-");
            compareStringOne= spliTtimeslot[0];
            compareStringTwo= spliTtimeslot[1];
            String[] spliTtimeslot1 = compareStringTwo.split(":");
            compareStringOne1= spliTtimeslot1[0];
            compareStringTwo2= spliTtimeslot1[1];
         /*   String[] spliTtimeslot2 = currentTime4.split(":");
            currentcompareStringOne1= spliTtimeslot2[0];
            currentcompareStringOne2= spliTtimeslot2[1];*/

             if(currentTime2==Integer.parseInt(compareStringOne1)){
                 if(currentTime3==Integer.parseInt(compareStringTwo2)){
                     getCompletedBooking();
                 }

         }

        }catch(Exception e){
            Log.i("TimeException",e.toString());
            e.printStackTrace();
        }
        if(preferances.getStringValue("status","").equalsIgnoreCase("1")){
      no_booking.setVisibility(View.GONE);
    gym_name.setVisibility(View.VISIBLE);
    time_stamp.setVisibility(View.VISIBLE);
    cancel_booking.setVisibility(View.VISIBLE);
    tv2.setVisibility(View.VISIBLE);
    tv1.setVisibility(View.VISIBLE);
    gym_name.setText(preferances.getStringValue("GYM_NAME",""));
    time_stamp.setText(preferances.getStringValue("sessionDateTime",""));
   } else{
      no_booking.setVisibility(View.VISIBLE);
            gym_name.setVisibility(View.GONE);
            time_stamp.setVisibility(View.GONE);
            cancel_booking.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv1.setVisibility(View.GONE);
   }
  cancel_booking.setOnClickListener(view1 -> {
            getCancelInformation();
        });
        imageSlider();
        imageSlider1();
        imageSlider2();

        return view;
    }
    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    private void getCompletedBooking() {
        String url =MEMBER_BASE_URL+"schedule/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id","5");
            String a=preferances.getStringValue("sessionDate","");
            String b=preferances.getStringValue("MEMBER_ID","");
            object.put("session_date",a);
            object.put("member_id", b);
            object.put("status","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getContext(),"Booking has been completed",Toast.LENGTH_LONG).show();
                            no_booking.setVisibility(View.VISIBLE);
                            gym_name.setVisibility(View.GONE);
                            time_stamp.setVisibility(View.GONE);
                            cancel_booking.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                            tv1.setVisibility(View.GONE);
                            preferances.setValue("sessionDate","" );
                            preferances.setValue("sessionTime", "");
//                            preferances.setValue("sessionDateTime","");
//                            preferances.setValue("GYM_NAME","");
                            preferances.setValue("status","2");
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getCancelInformation() {
        dialog = new Dialog(getContext(), R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(getString(R.string.yes));
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setText(getString(R.string.no));
        tv1.setText(R.string.strcancel_booking);
        b1.setOnClickListener(v -> {
            dialog.dismiss();
            if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
                Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
            }else{
                cancelBooking();
            }

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

    private void cancelBooking() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url =MEMBER_BASE_URL+"schedule/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id","5");
            object.put("session_date",preferances.getStringValue("sessionDate",""));
            object.put("member_id", preferances.getStringValue("MEMBER_ID",""));
            object.put("status","0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Booking has been canceled",Toast.LENGTH_LONG).show();
                            no_booking.setVisibility(View.VISIBLE);
                            gym_name.setVisibility(View.GONE);
                            time_stamp.setVisibility(View.GONE);
                            cancel_booking.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                            tv1.setVisibility(View.GONE);
                            preferances.setValue("sessionDate","" );
                            preferances.setValue("sessionTime", "");
//                            preferances.setValue("sessionDateTime","");
//                            preferances.setValue("GYM_NAME","");
                            preferances.setValue("status","0");

                        } catch (Exception e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void imageSlider() {
        XMENArray = new ArrayList<Integer>();

        XMENArray.addAll(Arrays.asList(XMEN));
        mPager.setAdapter(new HomeAdapter(getContext(), XMENArray));
//        mPager.notify();
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
        }, 2000, 2000);
    }
    private void imageSlider1() {
        XMENArray1 = new ArrayList<Integer>();
        XMENArray1.addAll(Arrays.asList(XMEN));
        mPager1.setAdapter(new BannerAdapter(getContext(), XMENArray1));
        indicator1.setViewPager(mPager1);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                mPager1.setCurrentItem(currentPage++, true);
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


    private void imageSlider2() {
        XMENArray2 = new ArrayList<Integer>();
        XMENArray2.addAll(Arrays.asList(XMEN));
        mPager2.setAdapter(new TestimonialAdapter(getContext(), XMENArray2));
        indicator2.setViewPager(mPager2);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                mPager2.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

    }



}