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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Adapter.BookingHistoryAdapter;
import com.factor.gymnasium.Adapter.CancelHistoryAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.Modal.CancelHistoryModel;
import com.factor.gymnasium.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelledFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledFragment extends Fragment {
    RecyclerView cancelled_bookingList;
    SharedPreferenceUtils preferances;
String member_id;
FrameLayout progressBarHolder;
    private List<CancelHistoryModel> booking_historyModelsList;
    private  Booking_HistoryModel booking_historyModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cancelled, container, false);
        cancelled_bookingList=view.findViewById(R.id.cancelled_bookingList);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        booking_historyModelsList = new ArrayList<>();
        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id= preferances.getStringValue("MEMBER_ID","");

        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
            Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_LONG).show();
        }else{
            getCancelBooking();
        }
        return view;
    }
    private void getCancelBooking() {
//        booking_historyModelsList.clear();
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
                            if(jsonObject1.getString("status").equalsIgnoreCase("0")){
                                CancelHistoryModel cancelHistoryModel= new CancelHistoryModel();
                                cancelHistoryModel.setName( preferances.getStringValue("GYM_NAME",""));
                                cancelHistoryModel.setDate(jsonObject1.getString("session_date"));
                                cancelHistoryModel.setTime(jsonObject1.getString("session_time"));
                                cancelHistoryModel.setLogo(R.drawable.booking_logo);
                                cancelHistoryModel.setStatus(jsonObject1.getString("status"));
                                booking_historyModelsList.add(cancelHistoryModel);
                                Log.i("CancelHistoryModel:",String.valueOf(cancelHistoryModel));
                            }
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            cancelled_bookingList.setLayoutManager(linearLayoutManager);
                            CancelHistoryAdapter cancelHistoryAdapter = new CancelHistoryAdapter(getContext(),booking_historyModelsList);
                            cancelled_bookingList.setAdapter(cancelHistoryAdapter);
                            cancelHistoryAdapter.notifyDataSetChanged();
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