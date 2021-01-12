package com.factor.gymnasium.Fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Adapter.AttandanceAdapter;
import com.factor.gymnasium.Adapter.BookingHistoryAdapter;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.Modal.AttandanceModel;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AttandanceFragment extends Fragment {
    String member_id,strDateofMonth="";
    SharedPreferenceUtils preferances;
    FrameLayout progressBarHolder;
    Button view_attandance;
    RecyclerView attandanceList;
    TextView selectDate,in_time,out_time;
    private int mYear, mMonth, mDay;
    private ArrayList<AttandanceModel> attandanceModelList;
    @Override
    public void onResume() {
        super.onResume();
//        preferances.setValue("PrefInTime","");
        preferances.setValue("PrefOutTime","");
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AttandanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttandanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttandanceFragment newInstance(String param1, String param2) {
        AttandanceFragment fragment = new AttandanceFragment();
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
        View view= inflater.inflate(R.layout.fragment_attandance, container, false);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id=preferances.getStringValue("MEMBER_ID","");
        attandanceModelList=new ArrayList<>();
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        view_attandance=view.findViewById(R.id.view_attandance);
        attandanceList=view.findViewById(R.id.attandanceList);
        selectDate=view.findViewById(R.id.selectDate);
        in_time=view.findViewById(R.id.in_time);
        out_time=view.findViewById(R.id.out_time);
        in_time.setText(preferances.getStringValue("PrefInTime",""));
        out_time.setText(preferances.getStringValue("PrefOutTime",""));
        selectDate.setOnClickListener(view1 -> {
            selectAttandanceDate();
        });
        view_attandance.setOnClickListener(view1 -> {
            if(strDateofMonth.equalsIgnoreCase("")){
                selectDate.setError("Please select Date");
                selectDate.requestFocus();
                Toast.makeText(getContext(),"Please Select Date",Toast.LENGTH_SHORT).show();
            } else if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
                Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
            }else{
                getAttandanceInformation();
            }
        });
        return view;
    }
    private void selectAttandanceDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        strDateofMonth = year + "-" +(monthOfYear + 1)+"-"+dayOfMonth;
                            selectDate.setText(strDateofMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getAttandanceInformation() {
        attandanceModelList.clear();
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/attendance/read_one.php?user_id="+member_id+"&"+"attendance_date="+strDateofMonth;
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            AttandanceModel   attandanceModel= new AttandanceModel();
                          /*  JSONArray jsonArray=jsonObject.getJSONArray("records");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1= jsonArray.getJSONObject(i);
                                if(jsonObject1.getString("status").equalsIgnoreCase("0")){
                                    attandanceModel.setInTime(preferances.getStringValue("GYM_NAME",""));
                                    attandanceModel.setOutTime(jsonObject1.getString("session_date"));
                                    attandanceModelList.add(attandanceModel);
                                }else{
                                    Toast.makeText(getContext(),"No Cancelled booking found",Toast.LENGTH_LONG).show();
                                } }*/
                            attandanceModel.setInTime(jsonObject.getString("attendance_date")+" " +jsonObject.getString("session_time"));
                            attandanceModel.setOutTime(jsonObject.getString("attendance_date")+" " +jsonObject.getString("out_time"));
                            attandanceModelList.add(attandanceModel);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            attandanceList.setLayoutManager(linearLayoutManager);
                            AttandanceAdapter attandanceAdapter = new AttandanceAdapter(getContext(),attandanceModelList);
                            attandanceList.setAdapter(attandanceAdapter);
                            attandanceAdapter.notifyDataSetChanged();
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

    }
}