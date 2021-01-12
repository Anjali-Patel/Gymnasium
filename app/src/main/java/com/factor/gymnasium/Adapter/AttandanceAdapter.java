package com.factor.gymnasium.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.factor.gymnasium.Modal.AttandanceModel;
import com.factor.gymnasium.Modal.TimeSlotModal;
import com.factor.gymnasium.R;
import java.util.ArrayList;
public class AttandanceAdapter extends RecyclerView.Adapter<AttandanceAdapter.SubCategoryViewHolder> {
    private Context context;
    private ArrayList<AttandanceModel> arrayList;
    ViewGroup viewGroup;
    public AttandanceAdapter(Context context, ArrayList<AttandanceModel> arrayList) {
        this.arrayList = arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public AttandanceAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.attandance_layout, parent, false);
        return new AttandanceAdapter.SubCategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AttandanceAdapter.SubCategoryViewHolder holder, int position) {
        final AttandanceModel item = arrayList.get(position);
        holder.in_time.setText(item.getInTime());
        holder.out_time.setText(item.getOutTime());

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class SubCategoryViewHolder extends RecyclerView.ViewHolder  {
        TextView in_time,out_time;
        SubCategoryViewHolder(View itemView) {
            super(itemView);
            in_time=itemView.findViewById(R.id.in_time);
            out_time=itemView.findViewById(R.id.out_time);
        }
    }
}
