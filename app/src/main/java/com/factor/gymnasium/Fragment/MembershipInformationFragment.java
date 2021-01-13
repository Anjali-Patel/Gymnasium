package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import static com.factor.gymnasium.Globals.GlobalItems.MEMBER_BASE_URL;


public class MembershipInformationFragment extends Fragment {
    TextView membership,type_membership,since,renewel_date,validity,set_by;
    FrameLayout progressBarHolder;
    String member_id;
    View view;
    SharedPreferenceUtils preferances;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public MembershipInformationFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MembershipInformationFragment newInstance(String param1, String param2) {
        MembershipInformationFragment fragment = new MembershipInformationFragment();
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
         view= inflater.inflate(R.layout.fragment_membership_information, container, false);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id= preferances.getStringValue("MEMBER_ID","");
        initValues();

        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
            Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            getMembershipInformation();
        }
        return view;
    }

    private void initValues() {
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        membership=view.findViewById(R.id.membership);
        type_membership=view.findViewById(R.id.type_membership);
        since=view.findViewById(R.id.since);
        renewel_date=view.findViewById(R.id.renewel_date);
        validity=view.findViewById(R.id.validity);
        set_by=view.findViewById(R.id.set_by);

    }

    private void getMembershipInformation() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = MEMBER_BASE_URL+"member/read_one.php?member_id="+member_id;
        //Again creating the string request
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            membership.setText(jsonObject.getString("member_id").replace("null",""));
                            type_membership.setText(jsonObject.getString("membership_type").replace("null",""));
                            since.setText(jsonObject.getString("start_date").replace("null",""));
                            renewel_date.setText(jsonObject.getString("renewal_date").replace("null",""));
                            validity.setText(jsonObject.getString("validity").replace("null",""));
                            set_by.setText(jsonObject.getString("set_by").replace("null",""));

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
                        Toast.makeText(getContext(),"Member Does not exist.Please first fill the given field's", Toast.LENGTH_LONG).show();
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