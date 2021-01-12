package com.factor.gymnasium.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.factor.gymnasium.Adapter.BookingHistoryAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UpcomingFragment extends Fragment {
    public static final String inputFormat = "HH:mm";
//    public static String upcomimhgTime="";
String upcomimhgTime="";
    private Date date;
    private String dateCompareOne;
    private String dateCompareTwo;

    private String compareStringOne = "";
    private String compareStringTwo = "",compareStringOne1,compareStringTwo2,currentcompareStringOne1,currentcompareStringOne2;

    SimpleDateFormat inputParser;

    RecyclerView upcoming_list;
    Dialog dialog;
    String currentDate,currentTime1,currentTime4;
    int currentTime2,currentTime3;
    CardView cardView;
    TextView gym_name,gym_date,cancel_booking,no_booking;
    SharedPreferenceUtils preferances;

  String str_gyma_name,bookingDate,strCurrentTime,strCurrentDate;
 FrameLayout progressBarHolder;
    ArrayList<Booking_HistoryModel> booking_historyModelList;
    Booking_HistoryModel booking_historyModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public UpcomingFragment() {

    }
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
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
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_upcoming, container, false);
        gym_name=view.findViewById(R.id.gym_name);
        gym_date=view.findViewById(R.id.gym_date);
        cardView=view.findViewById(R.id.cardView);
        cancel_booking=view.findViewById(R.id.cancel_booking);
        no_booking=view.findViewById(R.id.no_booking);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        preferances = SharedPreferenceUtils.getInstance(getContext());
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        currentDate = sdf.format(new Date());*/
        SimpleDateFormat t1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        currentTime1 = t1.format(new Date());
        SimpleDateFormat t2 = new SimpleDateFormat("HH", Locale.getDefault());
        currentTime2 = Integer.parseInt(t2.format(new Date()));
        SimpleDateFormat t3 = new SimpleDateFormat("mm", Locale.getDefault());
        currentTime3 = Integer.parseInt(t3.format(new Date()));
//        currentTime2=currentTime2+1;
        currentTime4=currentTime2+":"+currentTime3;
        String a=currentTime1+"-"+currentTime4;
        Log.i("CurrentDateStatus",a);
//         String upcomimhgTime=preferances.getStringValue("sessionTime","");
//        String[] spliTtimeslot = upcomimhgTime.split("-");
//        compareStringOne= spliTtimeslot[0];
       /*  upcomimhgTime=preferances.getStringValue("sessionTime","");
        String[] spliTtimeslot = upcomimhgTime.split("-");
         compareStringOne= spliTtimeslot[0];
         compareStringTwo= spliTtimeslot[1];*/
        inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        dateCompareOne = sdf.format(new Date());
        dateCompareTwo=preferances.getStringValue("sessionDate","");
        try{
            upcomimhgTime=preferances.getStringValue("sessionTime","");
            String[] spliTtimeslot = upcomimhgTime.split("-");
            compareStringOne= spliTtimeslot[0];
            compareStringTwo= spliTtimeslot[1];
            String[] spliTtimeslot1 = compareStringTwo.split(":");
            compareStringOne1= spliTtimeslot1[0];
            compareStringTwo2= spliTtimeslot1[1];
          /*  String[] spliTtimeslot2 = currentTime4.split(":");
            currentcompareStringOne1= spliTtimeslot2[0];
            currentcompareStringOne2= spliTtimeslot2[1];*/

    if(currentTime2==Integer.parseInt(compareStringOne1)){
        if(currentTime3==Integer.parseInt(compareStringTwo2)){
            getCompletedBookig();
        }
    } }catch(Exception e){
            e.printStackTrace();
            Log.i("TimeException",e.toString());

        }

//        compareDates();
       /* if(currentTime1.equalsIgnoreCase(compareStringOne)){
            getCompletedBookig();

        }*/
        if(preferances.getStringValue("status","").equalsIgnoreCase("1")){
            str_gyma_name=preferances.getStringValue("GYM_NAME","");
            bookingDate=preferances.getStringValue("sessionDateTime","");
            gym_name.setText(str_gyma_name);
            gym_date.setText(bookingDate);
        }else{
            cardView.setVisibility(View.GONE);
            no_booking.setVisibility(View.VISIBLE);
        }
        cancel_booking.setOnClickListener(view1 -> {
            getCancelInformation();
        });
        return view;
    }
    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    private void getCompletedBookig() {
        String url ="http://printacheque.com/gymapp/api/schedule/update.php";
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
                            Toast.makeText(getContext(),"Booking has been Completed",Toast.LENGTH_LONG).show();
                            gym_name.setVisibility(View.GONE);
                            gym_date.setVisibility(View.GONE);
                            cancel_booking.setVisibility(View.INVISIBLE);
                            cardView.setVisibility(View.GONE);
                            no_booking.setVisibility(View.VISIBLE);
                            preferances.setValue("status","2");
                            preferances.setValue("sessionDate","" );
                            preferances.setValue("sessionTime", "");
//                            preferances.setValue("sessionDateTime","");

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
        String url ="http://printacheque.com/gymapp/api/schedule/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id","5");
            String a=preferances.getStringValue("sessionDate","");
            String b=preferances.getStringValue("MEMBER_ID","");
            object.put("session_date",a);
            object.put("member_id", b);
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
                            Toast.makeText(getContext(),"Booking hasbeen canceled",Toast.LENGTH_LONG).show();
                            gym_name.setVisibility(View.GONE);
                            preferances.setValue("status","0");
                            gym_date.setVisibility(View.GONE);
                            cancel_booking.setVisibility(View.INVISIBLE);
                            cardView.setVisibility(View.GONE);
                            no_booking.setVisibility(View.VISIBLE);
                            preferances.setValue("sessionDate","" );
                            preferances.setValue("sessionTime", "");
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
}