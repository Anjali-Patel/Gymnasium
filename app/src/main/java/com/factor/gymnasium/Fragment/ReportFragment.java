package com.factor.gymnasium.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.factor.gymnasium.R;


public class ReportFragment extends Fragment {
    Fragment fragment = null;
    private OnFragmentInteractionListener mListener;
TextView my_performance;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        View view= inflater.inflate(R.layout.fragment_report, container, false);
     /*   my_performance=view.findViewById(R.id.my_performance);
        my_performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AttandanceFragment();
                FragmentManager childFragMan = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = childFragMan.beginTransaction();
                transaction.replace(R.id.dashboard_fragment_container,fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        my_performance=view.findViewById(R.id.my_performance);
        my_performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AttandanceFragment();
                FragmentManager childFragMan = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = childFragMan.beginTransaction();
                transaction.replace(R.id.dashboard_fragment_container,fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                try {
                    throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void messageFromParentFragment(Uri uri);
    }
    }
