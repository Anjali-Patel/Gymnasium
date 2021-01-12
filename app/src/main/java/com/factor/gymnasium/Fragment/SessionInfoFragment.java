package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionInfoFragment extends Fragment {
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
private EditText start_session,add_exercise,weight_profile,repeatation,remarks;
String str_startsession,str_addexercise,str_weight,str_repeatation,str_remark;
FrameLayout progressBarHolder;
Button save_profile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SessionInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SessionInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionInfoFragment newInstance(String param1, String param2) {
        SessionInfoFragment fragment = new SessionInfoFragment();
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
         view= inflater.inflate(R.layout.fragment_session_info, container, false);
        initViews();

        save_profile.setOnClickListener(view1 -> {
            str_startsession=start_session.getText().toString().trim();
            str_addexercise=add_exercise.getText().toString().trim();
            str_weight=weight_profile.getText().toString().trim();
            str_repeatation=repeatation.getText().toString().trim();
            str_remark= remarks.getText().toString().trim();
            if(str_startsession.equalsIgnoreCase("")){
                start_session.setError("Please enter session");
                start_session.requestFocus();
            }else if(str_addexercise.equalsIgnoreCase("")){
                add_exercise.setError("Please add exercise");
                add_exercise.requestFocus();
            }else if(str_weight.equalsIgnoreCase("")){
                weight_profile.setError("Please enter weight");
                weight_profile.requestFocus();
            }else if(str_repeatation.equalsIgnoreCase("")){
                repeatation.setError("Please enter repeatation");
                repeatation.requestFocus();
            }else if(str_remark.equalsIgnoreCase("")){
                remarks.setError("Please enter remark");
                remarks.requestFocus();
            }else if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            }else{

            }















        });
        return view;
    }

    private void initViews() {
        save_profile=view.findViewById(R.id.save_profile);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        start_session=view.findViewById(R.id.start_session);
        add_exercise=view.findViewById(R.id.add_exercise);
        weight_profile=view.findViewById(R.id.weight_profile);
        repeatation=view.findViewById(R.id.repeatation);
        remarks=view.findViewById(R.id.remarks);
    }
}