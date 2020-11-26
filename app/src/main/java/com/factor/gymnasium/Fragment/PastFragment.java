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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastFragment extends Fragment {
RecyclerView pastbooking_list;
ArrayList<Booking_HistoryModel> booking_historyModelList;
    Booking_HistoryModel booking_historyModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastFragment newInstance(String param1, String param2) {
        PastFragment fragment = new PastFragment();
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
        View view= inflater.inflate(R.layout.fragment_past, container, false);
        pastbooking_list=view.findViewById(R.id.pastbooking_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pastbooking_list.setLayoutManager(linearLayoutManager);
//        pastbooking_list.setHasFixedSize(true);
        booking_historyModelList = new ArrayList<>();
        booking_historyModel= new Booking_HistoryModel();
        for(int i=0;i<6;i++){
            booking_historyModel.setName("Fun & Fit Gym Pvt Ltd");
            booking_historyModel.setDate_time("12 November,2020 5:pm");
            booking_historyModel.setLogo(R.drawable.booking_logo);
            booking_historyModelList.add(booking_historyModel);

        }
        BookingHistoryAdapter bookingHistoryAdapter =new BookingHistoryAdapter(getContext(),  booking_historyModelList);
        pastbooking_list.setAdapter(bookingHistoryAdapter);
        return view;
    }
}