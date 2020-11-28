package com.factor.gymnasium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factor.gymnasium.Adapter.BannerAdapter;
import com.factor.gymnasium.Adapter.HomeAdapter;
import com.factor.gymnasium.Adapter.TestimonialAdapter;
import com.factor.gymnasium.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    CircleIndicator indicator,indicator1,indicator2;
    ViewPager mPager,mPager1,mPager2;
    private static int currentPage = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Integer> XMENArray ;
    private ArrayList<Integer> XMENArray1 ;
    private ArrayList<Integer> XMENArray2 ;
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
        mPager1 = view.findViewById(R.id.pager1);
        indicator1 = view.findViewById(R.id.indicator1);
        mPager2 = view.findViewById(R.id.pager2);
        indicator2 = view.findViewById(R.id.indicator2);
        imageSlider();
        imageSlider1();
        imageSlider2();

        return view;
    }
    private void imageSlider() {
        XMENArray = new ArrayList<Integer>();

        XMENArray.addAll(Arrays.asList(XMEN));
        mPager.setAdapter(new HomeAdapter(getContext(), XMENArray));
//        mPager.notify();
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
        }, 2000, 2000);

    }


    private void imageSlider1() {
        XMENArray1 = new ArrayList<Integer>();
        XMENArray1.addAll(Arrays.asList(XMEN));
        mPager1.setAdapter(new BannerAdapter(getContext(), XMENArray1));
        indicator1.setViewPager(mPager1);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                mPager1.setCurrentItem(currentPage++, true);
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


    private void imageSlider2() {
        XMENArray2 = new ArrayList<Integer>();
        XMENArray2.addAll(Arrays.asList(XMEN));
        mPager2.setAdapter(new TestimonialAdapter(getContext(), XMENArray2));
        indicator2.setViewPager(mPager2);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                mPager2.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

    }



}