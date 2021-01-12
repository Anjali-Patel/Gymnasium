package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Adapter.BookingHistoryAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastFragment extends Fragment {
RecyclerView pastbooking_list;
TextView no_booking;
ArrayList<Booking_HistoryModel> booking_historyModelList;
    Booking_HistoryModel booking_historyModel;
    SharedPreferenceUtils preferances;
    String member_id;
    FrameLayout progressBarHolder;
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
        } }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_past, container, false);
        pastbooking_list=view.findViewById(R.id.pastbooking_list);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        no_booking=view.findViewById(R.id.no_booking);
        booking_historyModelList = new ArrayList<>();
        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id= preferances.getStringValue("MEMBER_ID","");
        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
            Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            getPastBooking();
        }
        return view;
    }

    private void getPastBooking() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/schedule/getprevschedule.php?member_id="+member_id;
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("records");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1= jsonArray.getJSONObject(i);
                                if(jsonObject1.getString("status").equalsIgnoreCase("2")){
                                    booking_historyModel=new Booking_HistoryModel();
                                    booking_historyModel.setName(preferances.getStringValue("GYM_NAME",""));
                                    booking_historyModel.setDate(jsonObject1.getString("session_date"));
                                    booking_historyModel.setTime(jsonObject1.getString("session_time"));
                                    booking_historyModel.setLogo(R.drawable.booking_logo);
                                    booking_historyModel.setStatus(jsonObject1.getString("status"));
                                    booking_historyModelList.add(booking_historyModel);
                                }
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            pastbooking_list.setLayoutManager(linearLayoutManager);
                            BookingHistoryAdapter bookingHistoryAdapter =new BookingHistoryAdapter(getContext(),booking_historyModelList);
                            pastbooking_list.setAdapter(bookingHistoryAdapter);
                            bookingHistoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "session does not exist.", Toast.LENGTH_LONG).show();
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
}