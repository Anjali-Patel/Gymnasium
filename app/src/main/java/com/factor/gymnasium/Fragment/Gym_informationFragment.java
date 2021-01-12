package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Gym_informationFragment extends Fragment {
FrameLayout progressBarHolder;
String gym_id;
View view;
TextView gym_name,gym_address,gym_owner,gym_starting_date,gym_area,gym_brand_type,tagline;
    SharedPreferenceUtils preferances;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Gym_informationFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gym_informationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Gym_informationFragment newInstance(String param1, String param2) {
        Gym_informationFragment fragment = new Gym_informationFragment();
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
         view= inflater.inflate(R.layout.fragment_gym_information, container, false);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        gym_id="5";
        initValue();

        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
            Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            getGymInformation();
        }
        return view;
    }

    private void initValue() {
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        gym_name=view.findViewById(R.id.gym_name);
        gym_address=view.findViewById(R.id.gym_address);
        gym_owner=view.findViewById(R.id.gym_owner);
        gym_starting_date=view.findViewById(R.id.gym_starting_date);
        gym_area=view.findViewById(R.id.gym_area);
        gym_brand_type=view.findViewById(R.id.gym_brand_type);
        tagline=view.findViewById(R.id.tagline);
    }

    private void getGymInformation() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/Gym/read_one.php?gym_id="+gym_id;
        //Again creating the string request
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            gym_name.setText(jsonObject.getString("gym_name").replace("null",""));
                            gym_address.setText(jsonObject.getString("address").replace("null",""));
                            gym_owner.setText(jsonObject.getString("email").replace("null",""));
                            gym_starting_date.setText(jsonObject.getString("timing").replace("null",""));
                            gym_area.setText(jsonObject.getString("locality").replace("null",""));
                            gym_brand_type.setText(jsonObject.getString("gym_category").replace("null",""));
                           tagline.setText(jsonObject.getString("tagline").replace("null",""));
                           String a=jsonObject.getString("gym_name").replace("null","");
                            preferances.setValue("GYM_NAME",a);
                        String time_slot=jsonObject.getString("timing").replace("null","");
                        preferances.setValue("TIMESLOT",time_slot);
                        preferances.setValue("SESSION_DURATION",jsonObject.getString("session_duration").replace("null",""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        //Toast.makeText(LoginActivity.this, userid, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        //  isLoading(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(jsonRequest);
    }
}