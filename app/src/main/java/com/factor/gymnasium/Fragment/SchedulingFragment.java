package com.factor.gymnasium.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Activities.BookingConfirmation;
import com.factor.gymnasium.Adapter.TimeSlotAdapter;
import com.factor.gymnasium.Adapter.TrainerListAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.Modal.TimeSlotModal;
import com.factor.gymnasium.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.factor.gymnasium.Activities.UserDashboardActivity.toolbar_title;
import static com.factor.gymnasium.Globals.GlobalItems.MEMBER_BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulingFragment extends Fragment {
    private boolean firstExecution = true;
    String booking_slot1,booking_slot2,booking_slot3,booking_slot4,booking_slot5,booking_slot6,booking_slot7,booking_slot8,booking_slot9,booking_slot10,booking_slot11,booking_slot12,booking_slot13;
    ImageView booked1,booked2,booked3,booked4,booked5,booked6,booked7,booked8,booked9,booked10,booked11,booked12,booked13,booked14,booked15,booked16,booked17,booked18,booked19,booked20,booked21,booked22,booked23,booked24,booked25,booked26,booked27,booked28,booked29,booked30,booked31,booked32,booked33,booked34,booked35,booked36,booked37,booked38,booked39,booked40,booked41,booked42,booked43,booked44,booked45,booked46,booked47,booked48,booked49,booked50,booked51,booked52,booked53,booked54,booked55,booked56,booked57,booked58,booked59,booked60,booked61,booked62,booked63,booked64,booked65,booked66,booked67,booked68,booked69,booked70;
   ImageView available1,available2,available3,available4,available5,available6,available7,available8,available9,available10,available11,available12,available13,available14,available15,available16,available17,available18,available19,available20,available21,available22,available23,available24,available25,available26,available27,available28,available29,available30,available31,available32,available33,available34,available35,available36,available37,available38,available39,available40,available41,available42,available43,available44,available45,available46,available47,available48,available49,available50,available51,available52,available53,available54,available55,available56,available57,available58,available59,available60,available61,available62,available63,available64,available65,available66,available67,available68,available69,available70;
    String timeSlot,sessionDuration,bookedPeople="",currentTime1,currentTime4,currentTimeSlot;
    int currentTime,currentTime3;
    private int mYear, mMonth, mDay;
    Dialog dialog;
    String  day,j;
    boolean dateSelect=false;
Spinner time_slot_spinner;
    SharedPreferenceUtils preferances;
HorizontalScrollView horizontal_scroll;
    FrameLayout progressBarHolder;
    ArrayList<TimeSlotModal> timeSlotModalArrayList;
    TimeSlotModal timeSlotModal;
    TextView view_history;
    String am_pm = "",str_inTime="",strDate="",str_sessionTime="",compareStringOnespliTh1="";
    Button scheduled;
    int hour,  minute;
    Calendar calendar;
    TextView time_slot1,time_slot2,time_slot3,time_slot4,time_slot5,time_slot6,time_slot7;
    TextView session_duration1,session_duration2,session_duration3,session_duration4,session_duration5,session_duration6,session_duration7;
    CalendarView calendarView;
    View view;
    TextView select_time,selectDate;

    Fragment fragment = null;
//    RecyclerView time_slotList;
    LinearLayout time_slotList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TimeSlotAdapter.IOnItemClickListener iOnItemClickListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SchedulingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SchedulingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchedulingFragment newInstance(String param1, String param2) {
        SchedulingFragment fragment = new SchedulingFragment();
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
          view= inflater.inflate(R.layout.fragment_scheduling, container, false);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        timeSlot=preferances.getStringValue("TIMESLOT","");
        sessionDuration=preferances.getStringValue("SESSION_DURATION","");
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        SimpleDateFormat t1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        currentTime1 = t1.format(new Date());
        SimpleDateFormat t2 = new SimpleDateFormat("HH", Locale.getDefault());
        currentTime = Integer.parseInt(t2.format(new Date()));
       /* SimpleDateFormat t3 = new SimpleDateFormat("mm", Locale.getDefault());
        currentTime3 = Integer.parseInt(t3.format(new Date()));
        currentTime2=currentTime2+1;
        currentTime4=currentTime2+":"+currentTime3;
        currentTimeSlot=currentTime1+"-"+currentTime4;*/
        initViews();
        view_history.setOnClickListener(view1 -> {
            loadBookingFragment();
        });
       /* iOnItemClickListener = new TimeSlotAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(String text) {
                select_time.setText(text);
                str_inTime=text;
//                update_group_comment_id=text1;
            }
        };*/
       /* Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        strDate = df.format(c);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                strDate = i + "-" + (i1 + 1) + "-" + i2;
            }
        });*/
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strDate.equalsIgnoreCase("")){
                    selectDate.setError("Please select date");
                    Toast.makeText(getContext(),"Please select date",Toast.LENGTH_LONG).show();
                }else{
                    tiemPickerSlot();
                } }});
        scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(strDate.equalsIgnoreCase("")){
                    selectDate.requestFocus();
                    selectDate.setError("Please Select Date");
                    Toast.makeText(getContext(),"Please Select Date",Toast.LENGTH_LONG).show();
                } else if(str_inTime.equalsIgnoreCase("")){
                    select_time.requestFocus();
                    select_time.setError("Please Select Time");
                    Toast.makeText(getContext(),"Please Select Time",Toast.LENGTH_LONG).show();
                }else if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
                    Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                }else if(time_slot_spinner.getSelectedItem().toString().trim().equalsIgnoreCase("Select Time Slot")){
                    select_time.requestFocus();
                    select_time.setError("Please select Time Slot");
                     Toast.makeText(getContext(),"Please Select Time Slot",Toast.LENGTH_LONG).show();
                 } else{
                    Intent intent= new Intent(getActivity(), BookingConfirmation.class);
                    intent.putExtra("date",strDate);
                    intent.putExtra("time",str_inTime);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private void selectDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if((dayOfMonth>=mDay||year>=mYear)&&((monthOfYear+1)>=(mMonth+1)||year>=mYear)){
                            strDate = year + "-" +(monthOfYear + 1)+"-"+dayOfMonth;
                            selectDate.setText(strDate);

                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
                                Date myDate = dateFormat.parse(strDate);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                                 day=simpleDateFormat.format(myDate);
                            } catch (ParseException | java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            //                            Log.i("DayName:",String.valueOf(day));
                            getSchedule();

                        }else{
                            selectDate.setText("");
                            selectDate.setError("Booking not allowed in Past Date");
                            selectDate.requestFocus();
                            Toast.makeText(getContext(),"Booking not allowed in Past Date",Toast.LENGTH_LONG).show();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getSchedule() {
        String url = MEMBER_BASE_URL+"schedule/getschedule.php?session_date="+strDate;
                StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("records");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                if(!jsonObject1.getString("booked_people").equalsIgnoreCase("")&&!jsonObject1.getString("session_time").equalsIgnoreCase("")){
                                    bookedPeople=jsonObject1.getString("booked_people");
                                    str_sessionTime=jsonObject1.getString("session_time");
                                }else{
                                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressBarHolder.setVisibility(View.GONE);
                        Log.i("bookedPeople",bookedPeople);
                        Log.i("sessionTime",str_sessionTime);

                        Toast.makeText(getContext(), "session does not exist", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonRequest);

    }

    private void initViews() {
        booked1=view.findViewById(R.id.booked1);
        booked2=view.findViewById(R.id.booked2);
        booked3=view.findViewById(R.id.booked3);
        booked4=view.findViewById(R.id.booked4);
        booked5=view.findViewById(R.id.booked5);
        booked6=view.findViewById(R.id.booked6);
        booked7=view.findViewById(R.id.booked7);
        booked8=view.findViewById(R.id.booked8);
        booked9=view.findViewById(R.id.booked9);
        booked10=view.findViewById(R.id.booked10);
        booked11=view.findViewById(R.id.booked11);
        booked12=view.findViewById(R.id.booked12);
        booked13=view.findViewById(R.id.booked13);
        booked14=view.findViewById(R.id.booked14);
        booked15=view.findViewById(R.id.booked15);
        booked16=view.findViewById(R.id.booked16);
        booked17=view.findViewById(R.id.booked17);
        booked18=view.findViewById(R.id.booked18);
        booked19=view.findViewById(R.id.booked19);
        booked20=view.findViewById(R.id.booked20);
        booked21=view.findViewById(R.id.booked21);
        booked22=view.findViewById(R.id.booked22);
        booked23=view.findViewById(R.id.booked23);
        booked24=view.findViewById(R.id.booked24);
        booked25=view.findViewById(R.id.booked25);
        booked26=view.findViewById(R.id.booked26);
        booked27=view.findViewById(R.id.booked27);
        booked28=view.findViewById(R.id.booked28);
        booked29=view.findViewById(R.id.booked29);
        booked30=view.findViewById(R.id.booked30);
        booked31=view.findViewById(R.id.booked31);
        booked32=view.findViewById(R.id.booked32);
        booked33=view.findViewById(R.id.booked33);
        booked34=view.findViewById(R.id.booked34);
        booked35=view.findViewById(R.id.booked35);
        booked36=view.findViewById(R.id.booked36);
        booked37=view.findViewById(R.id.booked37);
        booked38=view.findViewById(R.id.booked38);
        booked39=view.findViewById(R.id.booked39);
        booked40=view.findViewById(R.id.booked40);
        booked41=view.findViewById(R.id.booked41);
        booked42=view.findViewById(R.id.booked42);
        booked43=view.findViewById(R.id.booked43);
        booked44=view.findViewById(R.id.booked44);
        booked45=view.findViewById(R.id.booked45);
        booked46=view.findViewById(R.id.booked46);
        booked47=view.findViewById(R.id.booked47);
        booked48=view.findViewById(R.id.booked48);
        booked49=view.findViewById(R.id.booked49);
        booked50=view.findViewById(R.id.booked50);
        booked51=view.findViewById(R.id.booked51);
        booked52=view.findViewById(R.id.booked52);
        booked53=view.findViewById(R.id.booked53);
        booked54=view.findViewById(R.id.booked54);
        booked55=view.findViewById(R.id.booked55);
        booked56=view.findViewById(R.id.booked56);
        booked57=view.findViewById(R.id.booked57);
        booked58=view.findViewById(R.id.booked58);
        booked59=view.findViewById(R.id.booked59);
        booked60=view.findViewById(R.id.booked60);
        booked61=view.findViewById(R.id.booked61);
        booked62=view.findViewById(R.id.booked62);
        booked63=view.findViewById(R.id.booked63);
        booked64=view.findViewById(R.id.booked64);
        booked65=view.findViewById(R.id.booked65);
        booked66=view.findViewById(R.id.booked66);
        booked67=view.findViewById(R.id.booked67);
        booked68=view.findViewById(R.id.booked68);
        booked69=view.findViewById(R.id.booked69);
        booked70=view.findViewById(R.id.booked70);
        available1=view.findViewById(R.id.available1);
        available2=view.findViewById(R.id.available2);
        available3=view.findViewById(R.id.available3);
        available4=view.findViewById(R.id.available4);
        available5=view.findViewById(R.id.available5);
        available6=view.findViewById(R.id.available6);
        available7=view.findViewById(R.id.available7);
        available8=view.findViewById(R.id.available8);
        available9=view.findViewById(R.id.available9);
        available10=view.findViewById(R.id.available10);
        available11=view.findViewById(R.id.available11);
        available12=view.findViewById(R.id.available12);
        available13=view.findViewById(R.id.available13);
        available14=view.findViewById(R.id.available14);
        available15=view.findViewById(R.id.available15);
        available16=view.findViewById(R.id.available16);
        available17=view.findViewById(R.id.available17);
        available18=view.findViewById(R.id.available18);
        available19=view.findViewById(R.id.available19);
        available20=view.findViewById(R.id.available20);
        available21=view.findViewById(R.id.available21);
        available22=view.findViewById(R.id.available22);
        available23=view.findViewById(R.id.available23);
        available24=view.findViewById(R.id.available24);
        available25=view.findViewById(R.id.available25);
        available26=view.findViewById(R.id.available26);
        available27=view.findViewById(R.id.available27);
        available28=view.findViewById(R.id.available28);
        available29=view.findViewById(R.id.available29);
        available30=view.findViewById(R.id.available30);
        available31=view.findViewById(R.id.available31);
        available32=view.findViewById(R.id.available32);
        available33=view.findViewById(R.id.available33);
        available34=view.findViewById(R.id.available34);
        available35=view.findViewById(R.id.available35);
        available36=view.findViewById(R.id.available36);
        available37=view.findViewById(R.id.available37);
        available38=view.findViewById(R.id.available38);
        available39=view.findViewById(R.id.available39);
        available40=view.findViewById(R.id.available40);
        available41=view.findViewById(R.id.available41);
        available42=view.findViewById(R.id.available42);
        available43=view.findViewById(R.id.available43);
        available44=view.findViewById(R.id.available44);
        available45=view.findViewById(R.id.available45);
        available46=view.findViewById(R.id.available46);
        available47=view.findViewById(R.id.available47);
        available48=view.findViewById(R.id.available48);
        available49=view.findViewById(R.id.available49);
        available50=view.findViewById(R.id.available50);
        available51=view.findViewById(R.id.available51);
        available52=view.findViewById(R.id.available52);
        available53=view.findViewById(R.id.available53);
        available54=view.findViewById(R.id.available54);
        available55=view.findViewById(R.id.available55);
        available56=view.findViewById(R.id.available56);
        available57=view.findViewById(R.id.available57);
        available58=view.findViewById(R.id.available58);
        available59=view.findViewById(R.id.available59);
        available60=view.findViewById(R.id.available60);
        available61=view.findViewById(R.id.available61);
        available62=view.findViewById(R.id.available62);
        available63=view.findViewById(R.id.available63);
        available64=view.findViewById(R.id.available64);
        available65=view.findViewById(R.id.available65);
        available66=view.findViewById(R.id.available66);
        available67=view.findViewById(R.id.available67);
        available68=view.findViewById(R.id.available68);
        available69=view.findViewById(R.id.available69);
        available70=view.findViewById(R.id.available70);

        horizontal_scroll=view.findViewById(R.id.horizontal_scroll);
        time_slot1=view.findViewById(R.id.time_slot1);
        time_slot2=view.findViewById(R.id.time_slot2);
        time_slot3=view.findViewById(R.id.time_slot3);
        time_slot4=view.findViewById(R.id.time_slot4);
        time_slot5=view.findViewById(R.id.time_slot5);
        time_slot6=view.findViewById(R.id.time_slot6);
        time_slot7=view.findViewById(R.id.time_slot7);
        session_duration1=view.findViewById(R.id.session_duration1);
        session_duration2=view.findViewById(R.id.session_duration2);
        session_duration3=view.findViewById(R.id.session_duration3);
        session_duration4=view.findViewById(R.id.session_duration4);
        session_duration5=view.findViewById(R.id.session_duration5);
        session_duration6=view.findViewById(R.id.session_duration6);
        session_duration7=view.findViewById(R.id.session_duration7);
        selectDate=view.findViewById(R.id.selectDate);
        calendarView = view.findViewById(R.id.calendarView);
        time_slotList=view.findViewById(R.id.time_slotList);
        scheduled=view.findViewById(R.id.scheduled);
        view_history=view.findViewById(R.id.view_history);
        select_time=view.findViewById(R.id.select_time);
        selectDate.setOnClickListener(view1 -> {
            selectDate();

        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @SuppressLint("ResourceAsColor")
    private void tiemPickerSlot() {
        Log.i("Session Time:",str_sessionTime);
        horizontal_scroll.setVisibility(View.VISIBLE);
        time_slotList.setVisibility(View.VISIBLE);
        String[] TimeList = timeSlot.split(",");
        String time1 = TimeList [0];
        String time2 = TimeList [1];
        String time3 = TimeList [2];
        String time4 = TimeList [3];
        String time5 = TimeList [4];
        String time6 = TimeList [5];
        String time7 = TimeList [6];
        session_duration1.setText(sessionDuration);
        session_duration2.setText(sessionDuration);
        session_duration3.setText(sessionDuration);
        session_duration4.setText(sessionDuration);
        session_duration5.setText(sessionDuration);
        session_duration6.setText(sessionDuration);
        session_duration7.setText(sessionDuration);
        time_slot1.setText(time1);
        time_slot2.setText(time2);
        time_slot3.setText(time3);
        time_slot4.setText(time4);
        time_slot5.setText(time5);
        time_slot6.setText(time6);
        time_slot7.setText(time7);

        if(day.equalsIgnoreCase("Monday")){
            dateSelect=true;
            time_slot1.setBackgroundColor(Color.RED);
            time_slot1.setTextColor(Color.WHITE);
            time_slot1.setOnClickListener(view1 -> {
                timeSlotDialog();
            });

            time_slot2.setTextColor(Color.BLACK);
            time_slot2.setBackgroundColor(Color.WHITE);

            time_slot7.setBackgroundColor(Color.WHITE);
            time_slot7.setTextColor(Color.BLACK);
            time_slot3.setBackgroundColor(Color.WHITE);
            time_slot3.setTextColor(Color.BLACK);
            time_slot4.setBackgroundColor(Color.WHITE);
            time_slot4.setTextColor(Color.BLACK);
            time_slot5.setBackgroundColor(Color.WHITE);
            time_slot5.setTextColor(Color.BLACK);
            time_slot6.setBackgroundColor(Color.WHITE);
            time_slot6.setTextColor(Color.BLACK);
        } else{
            dateSelect=false;
            time_slot1.setClickable(false);
        }
        if(day.equalsIgnoreCase("Tuesday")){
            dateSelect=true;
            time_slot2.setBackgroundColor(Color.RED);
            time_slot2.setTextColor(Color.WHITE);
            time_slot2.setOnClickListener(view1 -> {
                timeSlotDialog();
            });
            time_slot1.setTextColor(Color.BLACK);
            time_slot1.setBackgroundColor(Color.WHITE);

            time_slot7.setBackgroundColor(Color.WHITE);
            time_slot7.setTextColor(Color.BLACK);
            time_slot3.setBackgroundColor(Color.WHITE);
            time_slot3.setTextColor(Color.BLACK);
            time_slot4.setBackgroundColor(Color.WHITE);
            time_slot4.setTextColor(Color.BLACK);
            time_slot5.setBackgroundColor(Color.WHITE);
            time_slot5.setTextColor(Color.BLACK);
            time_slot6.setBackgroundColor(Color.WHITE);
            time_slot6.setTextColor(Color.BLACK);
        }else{
                 dateSelect=false;
                 time_slot2.setClickable(false);
             }
        if(day.equalsIgnoreCase("Wednesday")){
            dateSelect=true;
            time_slot3.setBackgroundColor(Color.RED);
            time_slot3.setTextColor(Color.WHITE);
            time_slot3.setOnClickListener(view1 -> {
                timeSlotDialog();
            });
            time_slot1.setTextColor(Color.BLACK);
            time_slot2.setBackgroundColor(Color.WHITE);
            time_slot2.setTextColor(Color.BLACK);
            time_slot7.setBackgroundColor(Color.WHITE);
            time_slot7.setTextColor(Color.BLACK);
            time_slot4.setBackgroundColor(Color.WHITE);
            time_slot4.setTextColor(Color.BLACK);
            time_slot5.setBackgroundColor(Color.WHITE);
            time_slot5.setTextColor(Color.BLACK);
            time_slot6.setBackgroundColor(Color.WHITE);
            time_slot6.setTextColor(Color.BLACK);
        }else{
                  dateSelect=false;
                  time_slot3.setClickable(false);
              }
        if(day.equalsIgnoreCase("Thursday")){
            dateSelect=true;
            time_slot4.setBackgroundColor(Color.RED);
            time_slot4.setTextColor(Color.WHITE);
            time_slot4.setOnClickListener(view1 -> {
                timeSlotDialog();
            });
            time_slot1.setTextColor(Color.BLACK);
            time_slot1.setBackgroundColor(Color.WHITE);

            time_slot2.setBackgroundColor(Color.WHITE);
            time_slot2.setTextColor(Color.BLACK);
            time_slot3.setBackgroundColor(Color.WHITE);
            time_slot3.setTextColor(Color.BLACK);
            time_slot7.setBackgroundColor(Color.WHITE);
            time_slot7.setTextColor(Color.BLACK);
            time_slot5.setBackgroundColor(Color.WHITE);
            time_slot5.setTextColor(Color.BLACK);
            time_slot6.setBackgroundColor(Color.WHITE);
            time_slot6.setTextColor(Color.BLACK);
        }else{
                   dateSelect=false;
                   time_slot4.setClickable(false);
               }
        if(day.equalsIgnoreCase("Friday")){
            dateSelect=true;
            time_slot5.setBackgroundColor(Color.RED);
            time_slot5.setTextColor(Color.WHITE);
            time_slot5.setOnClickListener(view1 -> {
                timeSlotDialog();
            });
            time_slot1.setTextColor(Color.BLACK);
            time_slot1.setBackgroundColor(Color.WHITE);

            time_slot2.setBackgroundColor(Color.WHITE);
            time_slot2.setTextColor(Color.BLACK);
            time_slot3.setBackgroundColor(Color.WHITE);
            time_slot3.setTextColor(Color.BLACK);
            time_slot4.setBackgroundColor(Color.WHITE);
            time_slot4.setTextColor(Color.BLACK);
            time_slot7.setBackgroundColor(Color.WHITE);
            time_slot7.setTextColor(Color.BLACK);
            time_slot6.setBackgroundColor(Color.WHITE);
            time_slot6.setTextColor(Color.BLACK);
        }else{
                    dateSelect=false;
                    time_slot5.setClickable(false);
                }
        if(day.equalsIgnoreCase("Saturday")){
            dateSelect=true;
            time_slot6.setBackgroundColor(Color.RED);
            time_slot6.setTextColor(Color.WHITE);
            time_slot6.setOnClickListener(view1 -> {
                timeSlotDialog();
            });

            time_slot1.setTextColor(Color.BLACK);
            time_slot1.setBackgroundColor(Color.WHITE);

            time_slot2.setBackgroundColor(Color.WHITE);
            time_slot2.setTextColor(Color.BLACK);
            time_slot3.setBackgroundColor(Color.WHITE);
            time_slot3.setTextColor(Color.BLACK);
            time_slot4.setBackgroundColor(Color.WHITE);
            time_slot4.setTextColor(Color.BLACK);
            time_slot5.setBackgroundColor(Color.WHITE);
            time_slot5.setTextColor(Color.BLACK);
            time_slot7.setBackgroundColor(Color.WHITE);
            time_slot7.setTextColor(Color.BLACK);
        }else{
                     dateSelect=false;
                     time_slot6.setClickable(false);
                 }
                 if(day.equalsIgnoreCase("Sunday")){
            dateSelect=true;
            time_slot7.setBackgroundColor(Color.RED);
            time_slot7.setTextColor(Color.WHITE);
            time_slot7.setOnClickListener(view1 -> {
                timeSlotDialog();
            });
            time_slot1.setTextColor(Color.BLACK);
            time_slot1.setBackgroundColor(Color.WHITE);

            time_slot2.setBackgroundColor(Color.WHITE);
            time_slot2.setTextColor(Color.BLACK);
            time_slot3.setBackgroundColor(Color.WHITE);
            time_slot3.setTextColor(Color.BLACK);
            time_slot4.setBackgroundColor(Color.WHITE);
            time_slot4.setTextColor(Color.BLACK);
            time_slot5.setBackgroundColor(Color.WHITE);
            time_slot5.setTextColor(Color.BLACK);
            time_slot6.setBackgroundColor(Color.WHITE);
            time_slot6.setTextColor(Color.BLACK);
                 }else{
                      dateSelect=false;
                      time_slot7.setClickable(false);
                  }
        for(int i=0;i<7;i++){
            if(i==0){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.GONE);
                    booked3.setVisibility(View.GONE);
                    booked4.setVisibility(View.GONE);
                    booked5.setVisibility(View.GONE);
                    booked6.setVisibility(View.GONE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.VISIBLE);
                    available6.setVisibility(View.VISIBLE);
                    available7.setVisibility(View.VISIBLE);
                    available8.setVisibility(View.VISIBLE);
                    available9.setVisibility(View.VISIBLE);
                    available10.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.GONE);
                    booked4.setVisibility(View.GONE);
                    booked5.setVisibility(View.GONE);
                    booked6.setVisibility(View.GONE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.VISIBLE);
                    available6.setVisibility(View.VISIBLE);
                    available7.setVisibility(View.VISIBLE);
                    available8.setVisibility(View.VISIBLE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.GONE);
                    booked5.setVisibility(View.GONE);
                    booked6.setVisibility(View.GONE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.VISIBLE);
                    available6.setVisibility(View.VISIBLE);
                    available7.setVisibility(View.VISIBLE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.GONE);
                    booked6.setVisibility(View.GONE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.VISIBLE);
                    available6.setVisibility(View.VISIBLE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);


                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.VISIBLE);
                    booked6.setVisibility(View.GONE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.VISIBLE);
                    available6.setVisibility(View.GONE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.VISIBLE);
                    booked6.setVisibility(View.VISIBLE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.GONE);
                    available6.setVisibility(View.GONE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.VISIBLE);
                    booked6.setVisibility(View.VISIBLE);
                    booked7.setVisibility(View.VISIBLE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.GONE);
                    available5.setVisibility(View.GONE);
                    available6.setVisibility(View.GONE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.VISIBLE);
                    booked6.setVisibility(View.VISIBLE);
                    booked7.setVisibility(View.VISIBLE);
                    booked8.setVisibility(View.VISIBLE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.GONE);
                    available4.setVisibility(View.GONE);
                    available5.setVisibility(View.GONE);
                    available6.setVisibility(View.GONE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.VISIBLE);
                    booked6.setVisibility(View.VISIBLE);
                    booked7.setVisibility(View.VISIBLE);
                    booked8.setVisibility(View.VISIBLE);
                    booked9.setVisibility(View.VISIBLE);
                    booked10.setVisibility(View.GONE);

                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.GONE);
                    available3.setVisibility(View.GONE);
                    available4.setVisibility(View.GONE);
                    available5.setVisibility(View.GONE);
                    available6.setVisibility(View.GONE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked1.setVisibility(View.VISIBLE);
                    booked2.setVisibility(View.VISIBLE);
                    booked3.setVisibility(View.VISIBLE);
                    booked4.setVisibility(View.VISIBLE);
                    booked5.setVisibility(View.VISIBLE);
                    booked6.setVisibility(View.VISIBLE);
                    booked7.setVisibility(View.VISIBLE);
                    booked8.setVisibility(View.VISIBLE);
                    booked9.setVisibility(View.VISIBLE);
                    booked10.setVisibility(View.VISIBLE);

                    available1.setVisibility(View.GONE);
                    available2.setVisibility(View.GONE);
                    available3.setVisibility(View.GONE);
                    available4.setVisibility(View.GONE);
                    available5.setVisibility(View.GONE);
                    available6.setVisibility(View.GONE);
                    available7.setVisibility(View.GONE);
                    available8.setVisibility(View.GONE);
                    available9.setVisibility(View.GONE);
                    available10.setVisibility(View.GONE);
                }else{
                    booked1.setVisibility(View.GONE);
                    booked2.setVisibility(View.GONE);
                    booked3.setVisibility(View.GONE);
                    booked4.setVisibility(View.GONE);
                    booked5.setVisibility(View.GONE);
                    booked6.setVisibility(View.GONE);
                    booked7.setVisibility(View.GONE);
                    booked8.setVisibility(View.GONE);
                    booked9.setVisibility(View.GONE);
                    booked10.setVisibility(View.GONE);
                    available1.setVisibility(View.VISIBLE);
                    available2.setVisibility(View.VISIBLE);
                    available3.setVisibility(View.VISIBLE);
                    available4.setVisibility(View.VISIBLE);
                    available5.setVisibility(View.VISIBLE);
                    available6.setVisibility(View.VISIBLE);
                    available7.setVisibility(View.VISIBLE);
                    available8.setVisibility(View.VISIBLE);
                    available9.setVisibility(View.VISIBLE);
                    available10.setVisibility(View.VISIBLE);
                }
            }else if(i==1){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.GONE);
                    booked13.setVisibility(View.GONE);
                    booked14.setVisibility(View.GONE);
                    booked15.setVisibility(View.GONE);
                    booked16.setVisibility(View.GONE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.VISIBLE);
                    available16.setVisibility(View.VISIBLE);
                    available17.setVisibility(View.VISIBLE);
                    available18.setVisibility(View.VISIBLE);
                    available19.setVisibility(View.VISIBLE);
                    available20.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.GONE);
                    booked14.setVisibility(View.GONE);
                    booked15.setVisibility(View.GONE);
                    booked16.setVisibility(View.GONE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.VISIBLE);
                    available16.setVisibility(View.VISIBLE);
                    available17.setVisibility(View.VISIBLE);
                    available18.setVisibility(View.VISIBLE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.GONE);
                    booked15.setVisibility(View.GONE);
                    booked16.setVisibility(View.GONE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.VISIBLE);
                    available16.setVisibility(View.VISIBLE);
                    available17.setVisibility(View.VISIBLE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.GONE);
                    booked16.setVisibility(View.GONE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.VISIBLE);
                    available16.setVisibility(View.VISIBLE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.VISIBLE);
                    booked16.setVisibility(View.GONE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.VISIBLE);
                    available16.setVisibility(View.GONE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.VISIBLE);
                    booked16.setVisibility(View.VISIBLE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.GONE);
                    available16.setVisibility(View.GONE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.VISIBLE);
                    booked16.setVisibility(View.VISIBLE);
                    booked17.setVisibility(View.VISIBLE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.GONE);
                    available15.setVisibility(View.GONE);
                    available16.setVisibility(View.GONE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.VISIBLE);
                    booked16.setVisibility(View.VISIBLE);
                    booked17.setVisibility(View.VISIBLE);
                    booked18.setVisibility(View.VISIBLE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.GONE);
                    available14.setVisibility(View.GONE);
                    available15.setVisibility(View.GONE);
                    available16.setVisibility(View.GONE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.VISIBLE);
                    booked16.setVisibility(View.VISIBLE);
                    booked17.setVisibility(View.VISIBLE);
                    booked18.setVisibility(View.VISIBLE);
                    booked19.setVisibility(View.VISIBLE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.GONE);
                    available13.setVisibility(View.GONE);
                    available14.setVisibility(View.GONE);
                    available15.setVisibility(View.GONE);
                    available16.setVisibility(View.GONE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked11.setVisibility(View.VISIBLE);
                    booked12.setVisibility(View.VISIBLE);
                    booked13.setVisibility(View.VISIBLE);
                    booked14.setVisibility(View.VISIBLE);
                    booked15.setVisibility(View.VISIBLE);
                    booked16.setVisibility(View.VISIBLE);
                    booked17.setVisibility(View.VISIBLE);
                    booked18.setVisibility(View.VISIBLE);
                    booked19.setVisibility(View.VISIBLE);
                    booked20.setVisibility(View.VISIBLE);

                    available11.setVisibility(View.GONE);
                    available12.setVisibility(View.GONE);
                    available13.setVisibility(View.GONE);
                    available14.setVisibility(View.GONE);
                    available15.setVisibility(View.GONE);
                    available16.setVisibility(View.GONE);
                    available17.setVisibility(View.GONE);
                    available18.setVisibility(View.GONE);
                    available19.setVisibility(View.GONE);
                    available20.setVisibility(View.GONE);
                }else{
                    booked11.setVisibility(View.GONE);
                    booked12.setVisibility(View.GONE);
                    booked13.setVisibility(View.GONE);
                    booked14.setVisibility(View.GONE);
                    booked15.setVisibility(View.GONE);
                    booked16.setVisibility(View.GONE);
                    booked17.setVisibility(View.GONE);
                    booked18.setVisibility(View.GONE);
                    booked19.setVisibility(View.GONE);
                    booked20.setVisibility(View.GONE);

                    available11.setVisibility(View.VISIBLE);
                    available12.setVisibility(View.VISIBLE);
                    available13.setVisibility(View.VISIBLE);
                    available14.setVisibility(View.VISIBLE);
                    available15.setVisibility(View.VISIBLE);
                    available16.setVisibility(View.VISIBLE);
                    available17.setVisibility(View.VISIBLE);
                    available18.setVisibility(View.VISIBLE);
                    available19.setVisibility(View.VISIBLE);
                    available20.setVisibility(View.VISIBLE);
                }
            }else if(i==2){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.GONE);
                    booked23.setVisibility(View.GONE);
                    booked24.setVisibility(View.GONE);
                    booked25.setVisibility(View.GONE);
                    booked26.setVisibility(View.GONE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.VISIBLE);
                    available26.setVisibility(View.VISIBLE);
                    available27.setVisibility(View.VISIBLE);
                    available28.setVisibility(View.VISIBLE);
                    available29.setVisibility(View.VISIBLE);
                    available30.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.GONE);
                    booked24.setVisibility(View.GONE);
                    booked25.setVisibility(View.GONE);
                    booked26.setVisibility(View.GONE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.VISIBLE);
                    available26.setVisibility(View.VISIBLE);
                    available27.setVisibility(View.VISIBLE);
                    available28.setVisibility(View.VISIBLE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.GONE);
                    booked25.setVisibility(View.GONE);
                    booked26.setVisibility(View.GONE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.VISIBLE);
                    available26.setVisibility(View.VISIBLE);
                    available27.setVisibility(View.VISIBLE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.GONE);
                    booked26.setVisibility(View.GONE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.VISIBLE);
                    available26.setVisibility(View.VISIBLE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.VISIBLE);
                    booked26.setVisibility(View.GONE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.VISIBLE);
                    available26.setVisibility(View.GONE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.VISIBLE);
                    booked26.setVisibility(View.VISIBLE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.GONE);
                    available26.setVisibility(View.GONE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.VISIBLE);
                    booked26.setVisibility(View.VISIBLE);
                    booked27.setVisibility(View.VISIBLE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.GONE);
                    available25.setVisibility(View.GONE);
                    available26.setVisibility(View.GONE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.VISIBLE);
                    booked26.setVisibility(View.VISIBLE);
                    booked27.setVisibility(View.VISIBLE);
                    booked28.setVisibility(View.VISIBLE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.GONE);
                    available24.setVisibility(View.GONE);
                    available25.setVisibility(View.GONE);
                    available26.setVisibility(View.GONE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.VISIBLE);
                    booked26.setVisibility(View.VISIBLE);
                    booked27.setVisibility(View.VISIBLE);
                    booked28.setVisibility(View.VISIBLE);
                    booked29.setVisibility(View.VISIBLE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.GONE);
                    available23.setVisibility(View.GONE);
                    available24.setVisibility(View.GONE);
                    available25.setVisibility(View.GONE);
                    available26.setVisibility(View.GONE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked21.setVisibility(View.VISIBLE);
                    booked22.setVisibility(View.VISIBLE);
                    booked23.setVisibility(View.VISIBLE);
                    booked24.setVisibility(View.VISIBLE);
                    booked25.setVisibility(View.VISIBLE);
                    booked26.setVisibility(View.VISIBLE);
                    booked27.setVisibility(View.VISIBLE);
                    booked28.setVisibility(View.VISIBLE);
                    booked29.setVisibility(View.VISIBLE);
                    booked30.setVisibility(View.VISIBLE);

                    available21.setVisibility(View.GONE);
                    available22.setVisibility(View.GONE);
                    available23.setVisibility(View.GONE);
                    available24.setVisibility(View.GONE);
                    available25.setVisibility(View.GONE);
                    available26.setVisibility(View.GONE);
                    available27.setVisibility(View.GONE);
                    available28.setVisibility(View.GONE);
                    available29.setVisibility(View.GONE);
                    available30.setVisibility(View.GONE);
                }else{
                    booked21.setVisibility(View.GONE);
                    booked22.setVisibility(View.GONE);
                    booked23.setVisibility(View.GONE);
                    booked24.setVisibility(View.GONE);
                    booked25.setVisibility(View.GONE);
                    booked26.setVisibility(View.GONE);
                    booked27.setVisibility(View.GONE);
                    booked28.setVisibility(View.GONE);
                    booked29.setVisibility(View.GONE);
                    booked30.setVisibility(View.GONE);

                    available21.setVisibility(View.VISIBLE);
                    available22.setVisibility(View.VISIBLE);
                    available23.setVisibility(View.VISIBLE);
                    available24.setVisibility(View.VISIBLE);
                    available25.setVisibility(View.VISIBLE);
                    available26.setVisibility(View.VISIBLE);
                    available27.setVisibility(View.VISIBLE);
                    available28.setVisibility(View.VISIBLE);
                    available29.setVisibility(View.VISIBLE);
                    available30.setVisibility(View.VISIBLE);
                }
            }else if(i==3){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.GONE);
                    booked33.setVisibility(View.GONE);
                    booked34.setVisibility(View.GONE);
                    booked35.setVisibility(View.GONE);
                    booked36.setVisibility(View.GONE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.VISIBLE);
                    available36.setVisibility(View.VISIBLE);
                    available37.setVisibility(View.VISIBLE);
                    available38.setVisibility(View.VISIBLE);
                    available39.setVisibility(View.VISIBLE);
                    available40.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.GONE);
                    booked34.setVisibility(View.GONE);
                    booked35.setVisibility(View.GONE);
                    booked36.setVisibility(View.GONE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.VISIBLE);
                    available36.setVisibility(View.VISIBLE);
                    available37.setVisibility(View.VISIBLE);
                    available38.setVisibility(View.VISIBLE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.GONE);
                    booked35.setVisibility(View.GONE);
                    booked36.setVisibility(View.GONE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.VISIBLE);
                    available36.setVisibility(View.VISIBLE);
                    available37.setVisibility(View.VISIBLE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.GONE);
                    booked36.setVisibility(View.GONE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.VISIBLE);
                    available36.setVisibility(View.VISIBLE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.VISIBLE);
                    booked36.setVisibility(View.GONE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.VISIBLE);
                    available36.setVisibility(View.GONE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.VISIBLE);
                    booked36.setVisibility(View.VISIBLE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.GONE);
                    available36.setVisibility(View.GONE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.VISIBLE);
                    booked36.setVisibility(View.VISIBLE);
                    booked37.setVisibility(View.VISIBLE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.GONE);
                    available35.setVisibility(View.GONE);
                    available36.setVisibility(View.GONE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.VISIBLE);
                    booked36.setVisibility(View.VISIBLE);
                    booked37.setVisibility(View.VISIBLE);
                    booked38.setVisibility(View.VISIBLE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.GONE);
                    available34.setVisibility(View.GONE);
                    available35.setVisibility(View.GONE);
                    available36.setVisibility(View.GONE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.VISIBLE);
                    booked36.setVisibility(View.VISIBLE);
                    booked37.setVisibility(View.VISIBLE);
                    booked38.setVisibility(View.VISIBLE);
                    booked39.setVisibility(View.VISIBLE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.GONE);
                    available33.setVisibility(View.GONE);
                    available34.setVisibility(View.GONE);
                    available35.setVisibility(View.GONE);
                    available36.setVisibility(View.GONE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked31.setVisibility(View.VISIBLE);
                    booked32.setVisibility(View.VISIBLE);
                    booked33.setVisibility(View.VISIBLE);
                    booked34.setVisibility(View.VISIBLE);
                    booked35.setVisibility(View.VISIBLE);
                    booked36.setVisibility(View.VISIBLE);
                    booked37.setVisibility(View.VISIBLE);
                    booked38.setVisibility(View.VISIBLE);
                    booked39.setVisibility(View.VISIBLE);
                    booked40.setVisibility(View.VISIBLE);

                    available31.setVisibility(View.GONE);
                    available32.setVisibility(View.GONE);
                    available33.setVisibility(View.GONE);
                    available34.setVisibility(View.GONE);
                    available35.setVisibility(View.GONE);
                    available36.setVisibility(View.GONE);
                    available37.setVisibility(View.GONE);
                    available38.setVisibility(View.GONE);
                    available39.setVisibility(View.GONE);
                    available40.setVisibility(View.GONE);
                }else{
                    booked31.setVisibility(View.GONE);
                    booked32.setVisibility(View.GONE);
                    booked33.setVisibility(View.GONE);
                    booked34.setVisibility(View.GONE);
                    booked35.setVisibility(View.GONE);
                    booked36.setVisibility(View.GONE);
                    booked37.setVisibility(View.GONE);
                    booked38.setVisibility(View.GONE);
                    booked39.setVisibility(View.GONE);
                    booked40.setVisibility(View.GONE);

                    available31.setVisibility(View.VISIBLE);
                    available32.setVisibility(View.VISIBLE);
                    available33.setVisibility(View.VISIBLE);
                    available34.setVisibility(View.VISIBLE);
                    available35.setVisibility(View.VISIBLE);
                    available36.setVisibility(View.VISIBLE);
                    available37.setVisibility(View.VISIBLE);
                    available38.setVisibility(View.VISIBLE);
                    available39.setVisibility(View.VISIBLE);
                    available40.setVisibility(View.VISIBLE);
                }
            }else if(i==4){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.GONE);
                    booked43.setVisibility(View.GONE);
                    booked44.setVisibility(View.GONE);
                    booked45.setVisibility(View.GONE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.VISIBLE);
                    available48.setVisibility(View.VISIBLE);
                    available49.setVisibility(View.VISIBLE);
                    available50.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.GONE);
                    booked44.setVisibility(View.GONE);
                    booked45.setVisibility(View.GONE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.VISIBLE);
                    available48.setVisibility(View.VISIBLE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.VISIBLE);
                    booked44.setVisibility(View.GONE);
                    booked45.setVisibility(View.GONE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.VISIBLE);
                    available48.setVisibility(View.GONE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.VISIBLE);
                    booked44.setVisibility(View.VISIBLE);
                    booked45.setVisibility(View.GONE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.GONE);
                    available48.setVisibility(View.GONE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.VISIBLE);
                    booked44.setVisibility(View.VISIBLE);
                    booked45.setVisibility(View.VISIBLE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.GONE);
                    available47.setVisibility(View.GONE);
                    available48.setVisibility(View.GONE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.VISIBLE);
                    booked44.setVisibility(View.VISIBLE);
                    booked45.setVisibility(View.VISIBLE);
                    booked46.setVisibility(View.VISIBLE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.GONE);
                    available46.setVisibility(View.GONE);
                    available47.setVisibility(View.GONE);
                    available48.setVisibility(View.GONE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.VISIBLE);
                    booked44.setVisibility(View.VISIBLE);
                    booked45.setVisibility(View.VISIBLE);
                    booked46.setVisibility(View.VISIBLE);
                    booked47.setVisibility(View.VISIBLE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.GONE);
                    available45.setVisibility(View.GONE);
                    available46.setVisibility(View.GONE);
                    available47.setVisibility(View.GONE);
                    available48.setVisibility(View.GONE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.GONE);
                    booked44.setVisibility(View.GONE);
                    booked45.setVisibility(View.GONE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.VISIBLE);
                    available48.setVisibility(View.VISIBLE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.VISIBLE);
                    available48.setVisibility(View.VISIBLE);
                    available49.setVisibility(View.VISIBLE);
                    available50.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked41.setVisibility(View.VISIBLE);
                    booked42.setVisibility(View.VISIBLE);
                    booked43.setVisibility(View.VISIBLE);
                    booked44.setVisibility(View.VISIBLE);
                    booked45.setVisibility(View.VISIBLE);
                    booked46.setVisibility(View.VISIBLE);
                    booked47.setVisibility(View.VISIBLE);
                    booked48.setVisibility(View.VISIBLE);
                    booked49.setVisibility(View.VISIBLE);
                    booked50.setVisibility(View.VISIBLE);

                    available41.setVisibility(View.GONE);
                    available42.setVisibility(View.GONE);
                    available43.setVisibility(View.GONE);
                    available44.setVisibility(View.GONE);
                    available45.setVisibility(View.GONE);
                    available46.setVisibility(View.GONE);
                    available47.setVisibility(View.GONE);
                    available48.setVisibility(View.GONE);
                    available49.setVisibility(View.GONE);
                    available50.setVisibility(View.GONE);
                }else{
                    booked41.setVisibility(View.GONE);
                    booked42.setVisibility(View.GONE);
                    booked43.setVisibility(View.GONE);
                    booked44.setVisibility(View.GONE);
                    booked45.setVisibility(View.GONE);
                    booked46.setVisibility(View.GONE);
                    booked47.setVisibility(View.GONE);
                    booked48.setVisibility(View.GONE);
                    booked49.setVisibility(View.GONE);
                    booked50.setVisibility(View.GONE);

                    available41.setVisibility(View.VISIBLE);
                    available42.setVisibility(View.VISIBLE);
                    available43.setVisibility(View.VISIBLE);
                    available44.setVisibility(View.VISIBLE);
                    available45.setVisibility(View.VISIBLE);
                    available46.setVisibility(View.VISIBLE);
                    available47.setVisibility(View.VISIBLE);
                    available48.setVisibility(View.VISIBLE);
                    available49.setVisibility(View.VISIBLE);
                    available50.setVisibility(View.VISIBLE);
                }
            }else if(i==5){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.GONE);
                    booked53.setVisibility(View.GONE);
                    booked54.setVisibility(View.GONE);
                    booked55.setVisibility(View.GONE);
                    booked56.setVisibility(View.GONE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.VISIBLE);
                    available56.setVisibility(View.VISIBLE);
                    available57.setVisibility(View.VISIBLE);
                    available58.setVisibility(View.VISIBLE);
                    available59.setVisibility(View.VISIBLE);
                    available60.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.GONE);
                    booked54.setVisibility(View.GONE);
                    booked55.setVisibility(View.GONE);
                    booked56.setVisibility(View.GONE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.VISIBLE);
                    available56.setVisibility(View.VISIBLE);
                    available57.setVisibility(View.VISIBLE);
                    available58.setVisibility(View.VISIBLE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.GONE);
                    booked55.setVisibility(View.GONE);
                    booked56.setVisibility(View.GONE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.VISIBLE);
                    available56.setVisibility(View.VISIBLE);
                    available57.setVisibility(View.VISIBLE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.GONE);
                    booked56.setVisibility(View.GONE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.VISIBLE);
                    available56.setVisibility(View.VISIBLE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.VISIBLE);
                    booked56.setVisibility(View.GONE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.VISIBLE);
                    available56.setVisibility(View.GONE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.VISIBLE);
                    booked56.setVisibility(View.VISIBLE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.GONE);
                    available56.setVisibility(View.GONE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.VISIBLE);
                    booked56.setVisibility(View.VISIBLE);
                    booked57.setVisibility(View.VISIBLE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.GONE);
                    available55.setVisibility(View.GONE);
                    available56.setVisibility(View.GONE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.VISIBLE);
                    booked56.setVisibility(View.VISIBLE);
                    booked57.setVisibility(View.VISIBLE);
                    booked58.setVisibility(View.VISIBLE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.GONE);
                    available54.setVisibility(View.GONE);
                    available55.setVisibility(View.GONE);
                    available56.setVisibility(View.GONE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.VISIBLE);
                    booked56.setVisibility(View.VISIBLE);
                    booked57.setVisibility(View.VISIBLE);
                    booked58.setVisibility(View.VISIBLE);
                    booked59.setVisibility(View.VISIBLE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.GONE);
                    available53.setVisibility(View.GONE);
                    available54.setVisibility(View.GONE);
                    available55.setVisibility(View.GONE);
                    available56.setVisibility(View.GONE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked51.setVisibility(View.VISIBLE);
                    booked52.setVisibility(View.VISIBLE);
                    booked53.setVisibility(View.VISIBLE);
                    booked54.setVisibility(View.VISIBLE);
                    booked55.setVisibility(View.VISIBLE);
                    booked56.setVisibility(View.VISIBLE);
                    booked57.setVisibility(View.VISIBLE);
                    booked58.setVisibility(View.VISIBLE);
                    booked59.setVisibility(View.VISIBLE);
                    booked60.setVisibility(View.VISIBLE);

                    available51.setVisibility(View.GONE);
                    available52.setVisibility(View.GONE);
                    available53.setVisibility(View.GONE);
                    available54.setVisibility(View.GONE);
                    available55.setVisibility(View.GONE);
                    available56.setVisibility(View.GONE);
                    available57.setVisibility(View.GONE);
                    available58.setVisibility(View.GONE);
                    available59.setVisibility(View.GONE);
                    available60.setVisibility(View.GONE);
                }else{
                    booked51.setVisibility(View.GONE);
                    booked52.setVisibility(View.GONE);
                    booked53.setVisibility(View.GONE);
                    booked54.setVisibility(View.GONE);
                    booked55.setVisibility(View.GONE);
                    booked56.setVisibility(View.GONE);
                    booked57.setVisibility(View.GONE);
                    booked58.setVisibility(View.GONE);
                    booked59.setVisibility(View.GONE);
                    booked60.setVisibility(View.GONE);

                    available51.setVisibility(View.VISIBLE);
                    available52.setVisibility(View.VISIBLE);
                    available53.setVisibility(View.VISIBLE);
                    available54.setVisibility(View.VISIBLE);
                    available55.setVisibility(View.VISIBLE);
                    available56.setVisibility(View.VISIBLE);
                    available57.setVisibility(View.VISIBLE);
                    available58.setVisibility(View.VISIBLE);
                    available59.setVisibility(View.VISIBLE);
                    available60.setVisibility(View.VISIBLE);
                }
            }else if(i==6){
                if(bookedPeople.equalsIgnoreCase("1")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.GONE);
                    booked63.setVisibility(View.GONE);
                    booked64.setVisibility(View.GONE);
                    booked65.setVisibility(View.GONE);
                    booked66.setVisibility(View.GONE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.VISIBLE);
                    available66.setVisibility(View.VISIBLE);
                    available67.setVisibility(View.VISIBLE);
                    available68.setVisibility(View.VISIBLE);
                    available69.setVisibility(View.VISIBLE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("2")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.GONE);
                    booked64.setVisibility(View.GONE);
                    booked65.setVisibility(View.GONE);
                    booked66.setVisibility(View.GONE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.VISIBLE);
                    available66.setVisibility(View.VISIBLE);
                    available67.setVisibility(View.VISIBLE);
                    available68.setVisibility(View.VISIBLE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("3")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.GONE);
                    booked65.setVisibility(View.GONE);
                    booked66.setVisibility(View.GONE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.VISIBLE);
                    available66.setVisibility(View.VISIBLE);
                    available67.setVisibility(View.VISIBLE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("4")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.GONE);
                    booked66.setVisibility(View.GONE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.VISIBLE);
                    available66.setVisibility(View.VISIBLE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("5")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.VISIBLE);
                    booked66.setVisibility(View.GONE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.VISIBLE);
                    available66.setVisibility(View.GONE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("6")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.VISIBLE);
                    booked66.setVisibility(View.VISIBLE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.GONE);
                    available66.setVisibility(View.GONE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("7")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.VISIBLE);
                    booked66.setVisibility(View.VISIBLE);
                    booked67.setVisibility(View.VISIBLE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.GONE);
                    available65.setVisibility(View.GONE);
                    available66.setVisibility(View.GONE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("8")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.VISIBLE);
                    booked66.setVisibility(View.VISIBLE);
                    booked67.setVisibility(View.VISIBLE);
                    booked68.setVisibility(View.VISIBLE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.GONE);
                    available64.setVisibility(View.GONE);
                    available65.setVisibility(View.GONE);
                    available66.setVisibility(View.GONE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else if(bookedPeople.equalsIgnoreCase("9")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.VISIBLE);
                    booked66.setVisibility(View.VISIBLE);
                    booked67.setVisibility(View.VISIBLE);
                    booked68.setVisibility(View.VISIBLE);
                    booked69.setVisibility(View.VISIBLE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.GONE);
                    available63.setVisibility(View.GONE);
                    available64.setVisibility(View.GONE);
                    available65.setVisibility(View.GONE);
                    available66.setVisibility(View.GONE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);
                }else if(bookedPeople.equalsIgnoreCase("10")){
                    booked61.setVisibility(View.VISIBLE);
                    booked62.setVisibility(View.VISIBLE);
                    booked63.setVisibility(View.VISIBLE);
                    booked64.setVisibility(View.VISIBLE);
                    booked65.setVisibility(View.VISIBLE);
                    booked66.setVisibility(View.VISIBLE);
                    booked67.setVisibility(View.VISIBLE);
                    booked68.setVisibility(View.VISIBLE);
                    booked69.setVisibility(View.VISIBLE);
                    booked70.setVisibility(View.VISIBLE);

                    available61.setVisibility(View.GONE);
                    available62.setVisibility(View.GONE);
                    available63.setVisibility(View.GONE);
                    available64.setVisibility(View.GONE);
                    available65.setVisibility(View.GONE);
                    available66.setVisibility(View.GONE);
                    available67.setVisibility(View.GONE);
                    available68.setVisibility(View.GONE);
                    available69.setVisibility(View.GONE);
                    available70.setVisibility(View.GONE);

                }else {
                    booked61.setVisibility(View.GONE);
                    booked62.setVisibility(View.GONE);
                    booked63.setVisibility(View.GONE);
                    booked64.setVisibility(View.GONE);
                    booked65.setVisibility(View.GONE);
                    booked66.setVisibility(View.GONE);
                    booked67.setVisibility(View.GONE);
                    booked68.setVisibility(View.GONE);
                    booked69.setVisibility(View.GONE);
                    booked70.setVisibility(View.GONE);

                    available61.setVisibility(View.VISIBLE);
                    available62.setVisibility(View.VISIBLE);
                    available63.setVisibility(View.VISIBLE);
                    available64.setVisibility(View.VISIBLE);
                    available65.setVisibility(View.VISIBLE);
                    available66.setVisibility(View.VISIBLE);
                    available67.setVisibility(View.VISIBLE);
                    available68.setVisibility(View.VISIBLE);
                    available69.setVisibility(View.VISIBLE);
                    available70.setVisibility(View.VISIBLE);
                }
            }

        }

//        progressBarHolder.setVisibility(View.VISIBLE);
       /* timeSlotModal = new TimeSlotModal();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        time_slotList.setLayoutManager(linearLayoutManager);
//        upcoming_list.setHasFixedSize(true);
        timeSlotModalArrayList = new ArrayList<>();*/

      /*  String url = "http://printacheque.com/gymapp/api/schedule/getschedule.php?session_date="+strDate;

        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("records");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                timeSlotModal.setTime_slot(jsonObject1.getString("session_time"));
                                timeSlotModal.setAvailable_icon(R.drawable.green_icon);
                                timeSlotModal.setBooked_icon(R.drawable.red_icon);
                                timeSlotModalArrayList.add(timeSlotModal);
                            }
                            TimeSlotAdapter timeSlotAdapter =new TimeSlotAdapter(getContext(),timeSlotModalArrayList,iOnItemClickListener);
                            time_slotList.setAdapter(timeSlotAdapter);
                            timeSlotAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonRequest);
*/
/*  for(int i=0;i<7;i++){
      timeSlotModal.setTime_slot("11:30");
      timeSlotModal.setAvailable_icon(R.drawable.green_icon);
      timeSlotModal.setBooked_icon(R.drawable.red_icon);
      timeSlotModalArrayList.add(timeSlotModal);
  }
        TimeSlotAdapter timeSlotAdapter =new TimeSlotAdapter(getContext(),timeSlotModalArrayList,iOnItemClickListener);
        time_slotList.setAdapter(timeSlotAdapter);
        timeSlotAdapter.notifyDataSetChanged()*/;
    }
    private void loadBookingFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, new BookingFragment());
        transaction.addToBackStack(null);
        toolbar_title.setText(R.string.booking);
        transaction.commit();
    }
private void timeSlotDialog(){
    dialog = new Dialog(getContext(), R.style.AlertDialogCustom);
    dialog.setContentView(R.layout.time_slot_spinner_layout);
//    dialog.setCancelable(false);
    time_slot_spinner=dialog.findViewById(R.id.time_slot_spinner);
    ArrayList<String> timeSlotList= new ArrayList<>();
    timeSlotList.add("Select Time Slot");
    timeSlotList.add("06:00-07:00");
    timeSlotList.add("07:15-08:15");
    timeSlotList.add("08:30-09:30");
    timeSlotList.add("09:45-10:45");
    timeSlotList.add("11:00-12:00");
    timeSlotList.add("12:15-13:15");
    timeSlotList.add("13:30-14:30");
    timeSlotList.add("14:45-15:45");
    timeSlotList.add("16:00-17:00");
    timeSlotList.add("17:15-18:15");
    timeSlotList.add("18:30-19:30");
    timeSlotList.add("19:45-20:45");
    timeSlotList.add("21:00-22:00");
    ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,timeSlotList);
    time_slot_spinner.setAdapter(arrayAdapter);
    time_slot_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(firstExecution){
                firstExecution = false;
                return;
            }
//            select_time.setText(timeSlotList.get(position));
            try{
                str_inTime = timeSlotList.get(position);
                String[] spliTtimeslot1 = str_inTime.split("-");
                String  compareStringOne= spliTtimeslot1[0];
                String[] spliTh1 = compareStringOne.split(":");
                compareStringOnespliTh1= spliTh1[0];
                if(Integer.parseInt(compareStringOnespliTh1)>=currentTime){
                    select_time.setText(timeSlotList.get(position));
                    dialog.dismiss();
                }else{
                    str_inTime="";
                    select_time.setText("");
                    Toast.makeText(getContext(),"In previous Slot, Booking not allowed",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
    try {
        dialog.show();
    } catch (Exception e) {
        e.printStackTrace();
    }

}
}