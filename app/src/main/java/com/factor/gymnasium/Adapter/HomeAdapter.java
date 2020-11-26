package com.factor.gymnasium.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.factor.gymnasium.R;

import java.util.ArrayList;

public class HomeAdapter extends PagerAdapter {

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public HomeAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = (View) inflater.inflate(R.layout.my_adapter ,view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        myImage.setImageResource(images.get(position));
//        TextView tvDesc=(TextView)myImageLayout.findViewById(R.id.tvDesc);
//        if(position==0){
////            tvDesc.setText("Protect Your Child");
//        }
//        else if(position==1){
////            tvDesc.setText("Safe Your Parents");
//        }else if(position==2){
////            tvDesc.setText("Track Your Location");
//        }else if(position==3){
////            tvDesc.setText("Connect To Family");
//        }
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}

