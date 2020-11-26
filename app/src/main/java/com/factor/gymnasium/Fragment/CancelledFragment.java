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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelledFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledFragment extends Fragment {
    RecyclerView cancelled_bookingList;

    private ArrayList<Booking_HistoryModel> booking_historyModels;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  Booking_HistoryModel booking_historyModel;

    public CancelledFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CancelledFragment newInstance(String param1, String param2) {
        CancelledFragment fragment = new CancelledFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cancelled, container, false);
        cancelled_bookingList=view.findViewById(R.id.cancelled_bookingList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cancelled_bookingList.setLayoutManager(linearLayoutManager);
//        cancelled_bookingList.setHasFixedSize(true);
        booking_historyModels = new ArrayList<>();
        booking_historyModel=new Booking_HistoryModel();
        for(int i=0;i<6;i++){
            booking_historyModel.setName("Fun & Fit Gym Pvt Ltd");
            booking_historyModel.setDate_time("12 November,2020 5:pm");
            booking_historyModel.setLogo(R.drawable.booking_logo);
            booking_historyModels.add(booking_historyModel);
        }

        BookingHistoryAdapter bookingHistoryAdapter =new BookingHistoryAdapter(getContext(),booking_historyModels);
        cancelled_bookingList.setAdapter(bookingHistoryAdapter);
        return view;
    }
}