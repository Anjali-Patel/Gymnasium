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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImportantInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImportantInformationFragment extends Fragment {
FrameLayout progressBarHolder;
    View view;
TextView Emergency_Contact,strname,strn_bloodgroup,medical_history;
    String member_id;
    SharedPreferenceUtils preferances;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ImportantInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImportantInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImportantInformationFragment newInstance(String param1, String param2) {
        ImportantInformationFragment fragment = new ImportantInformationFragment();
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
         view= inflater.inflate(R.layout.fragment_important_information, container, false);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id= preferances.getStringValue("MEMBER_ID","");
        initValues();
        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
            Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            getImportantInformation();
        }
        return view;
    }

    private void initValues() {
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        Emergency_Contact=view.findViewById(R.id.Emergency_Contact);
        strname=view.findViewById(R.id.strname);
        strn_bloodgroup=view.findViewById(R.id.strn_bloodgroup);
        medical_history=view.findViewById(R.id.medical_history);
    }

    private void getImportantInformation() {
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
                            Emergency_Contact .setText(jsonObject.getString("emergency_contact_no").replace("null",""));
                            strname.setText(jsonObject.getString("full_name").replace("null",""));
                            strn_bloodgroup.setText(jsonObject.getString("bloodgroup").replace("null",""));
                            medical_history.setText(jsonObject.getString("medical_history").replace("null",""));
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