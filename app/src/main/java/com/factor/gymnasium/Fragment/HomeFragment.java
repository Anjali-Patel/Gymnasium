package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factor.gymnasium.Adapter.HomeAdapter;
import com.factor.gymnasium.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    CircleIndicator indicator;
    ViewPager mPager;
    private static int currentPage = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Integer> XMENArray ;
    private static final Integer[] XMEN = {R.drawable.gym_image, R.drawable.gym1, R.drawable.gym2,R.drawable.gym3,R.drawable.gym4,R.drawable.gym5};
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
        XMENArray = new ArrayList<Integer>();
        imageSlider();
        return view;
    }
    private void imageSlider() {
        for (int i = 0; i < XMEN.length; i++)
            XMENArray.add(XMEN[i]);
        mPager.setAdapter(new HomeAdapter(getContext(), XMENArray));
        indicator.setViewPager(mPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

    }

}