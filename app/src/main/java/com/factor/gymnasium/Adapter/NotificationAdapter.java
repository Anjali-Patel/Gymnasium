package com.factor.gymnasium.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.factor.gymnasium.Activities.BookingDetailsActivity;
import com.factor.gymnasium.Activities.NotificationDetailActivity;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.Modal.NotificationModel;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.SubCategoryViewHolder> {
    private String user_id;
    private Context context;

    private ArrayList<NotificationModel> arrayList;
    ViewGroup viewGroup;
    public NotificationAdapter(Context context, ArrayList<NotificationModel> arrayList) {
        this.user_id=user_id;
        this.arrayList = arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public NotificationAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.booking_history_layout, parent, false);
        return new NotificationAdapter.SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.SubCategoryViewHolder holder, int position) {
        final NotificationModel item = arrayList.get(position);
        holder.gym_name.setText(item.getMessage());
        holder.gym_date.setText(item.getDate_time());
        Glide.with(context).load(item.getGym_logo()).into(holder.gym_logo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, NotificationDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder  {
        TextView gym_name,gym_date;
        ImageView gym_logo;

        SubCategoryViewHolder(View itemView) {
            super(itemView);
            gym_name=itemView.findViewById(R.id.gym_name);
            gym_date=itemView.findViewById(R.id.gym_date);
            gym_logo = itemView.findViewById(R.id.gym_logo);


        }


    }
}
