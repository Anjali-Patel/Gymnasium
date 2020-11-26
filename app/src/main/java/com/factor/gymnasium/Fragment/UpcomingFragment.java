package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factor.gymnasium.Adapter.BookingHistoryAdapter;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment {
    RecyclerView upcoming_list;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcoming_list=view.findViewById(R.id.upcoming_list);
        booking_historyModel=new Booking_HistoryModel();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        upcoming_list.setLayoutManager(linearLayoutManager);
//        upcoming_list.setHasFixedSize(true);
        booking_historyModelList = new ArrayList<>();
        for(int i=0;i<6;i++){
            booking_historyModel.setName("Fun & Fit Gym Pvt Ltd");
            booking_historyModel.setDate_time("12 November,2020 5:pm");
            booking_historyModel.setLogo(R.drawable.booking_logo);
            booking_historyModelList.add(booking_historyModel);

        }
        BookingHistoryAdapter bookingHistoryAdapter =new BookingHistoryAdapter(getContext(),booking_historyModelList);
        upcoming_list.setAdapter(bookingHistoryAdapter);
        return view;
    }
}