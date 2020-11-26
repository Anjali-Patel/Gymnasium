package com.factor.gymnasium.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.factor.gymnasium.Activities.BookingConfirmation;
import com.factor.gymnasium.Adapter.TimeSlotAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Modal.TimeSlotModal;
import com.factor.gymnasium.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulingFragment extends Fragment {
    ArrayList<TimeSlotModal> timeSlotModalArrayList;
    TimeSlotModal timeSlotModal;
    String am_pm = "",str_inTime="",strDate="";
    Button scheduled;
    int hour,  minute;
    Calendar calendar;
    CalendarView calendarView;
    View view;
    TextView select_time;
    Fragment fragment = null;
    RecyclerView time_slotList;
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
        calendarView = view.findViewById(R.id.calendarView);
        time_slotList=view.findViewById(R.id.time_slotList);
        scheduled=view.findViewById(R.id.scheduled);
        select_time=view.findViewById(R.id.select_time);
        iOnItemClickListener = new TimeSlotAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(String text,String text1) {
                select_time.setText(text);
                str_inTime=text;
//                update_group_comment_id=text1;
            }
        };
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
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
                strDate = i2 + "/" + (i1 + 1) + "/" + i;
            }
        });
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiemPickerSlot();


            }
        });
        scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str_inTime.equalsIgnoreCase("")){
                    select_time.requestFocus();
                    select_time.setError("Please select Time");
                    Toast.makeText(getContext(),"Please select Time",Toast.LENGTH_LONG).show();
                }else if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
                    Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent= new Intent(getActivity(), BookingConfirmation.class);
                    startActivity(intent);
                   /* fragment = new TrainerAvailabilityFragment();
                    loadFragment(fragment);
*/
                }
            }
        });






        return view;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void tiemPickerSlot(){
        time_slotList.setVisibility(View.VISIBLE);
        timeSlotModal=new TimeSlotModal();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        time_slotList.setLayoutManager(linearLayoutManager);
//        upcoming_list.setHasFixedSize(true);
        timeSlotModalArrayList = new ArrayList<>();
        for(int i=0;i<10;i++){
            timeSlotModal.setTime_slot("11:30 am");
            timeSlotModal.setAvailable_icon(R.drawable.green_icon);
            timeSlotModal.setBooked_icon(R.drawable.red_icon);
            timeSlotModalArrayList.add(timeSlotModal);
        }
        TimeSlotAdapter timeSlotAdapter =new TimeSlotAdapter(getContext(),timeSlotModalArrayList,iOnItemClickListener);
        time_slotList.setAdapter(timeSlotAdapter);
    }

}