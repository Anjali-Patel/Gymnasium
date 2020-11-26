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
import com.factor.gymnasium.Activities.BookingConfirmation;
import com.factor.gymnasium.Activities.BookingDetailsActivity;
import com.factor.gymnasium.Modal.Booking_HistoryModel;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.SubCategoryViewHolder> {
private String user_id;
private Context context;

private ArrayList<Booking_HistoryModel> arrayList;
ViewGroup viewGroup;
 public BookingHistoryAdapter(Context context, ArrayList<Booking_HistoryModel> arrayList) {
        this.user_id=user_id;
        this.arrayList = arrayList;
        this.context=context;
        }
@NonNull
@Override
public BookingHistoryAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.booking_history_layout, parent, false);
        return new BookingHistoryAdapter.SubCategoryViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull BookingHistoryAdapter.SubCategoryViewHolder holder, int position) {
    final Booking_HistoryModel item = arrayList.get(position);
    holder.gym_name.setText(item.getName());
    holder.gym_date.setText(item.getDate_time());
    Glide.with(context).load(item.getLogo()).into(holder.gym_logo);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(context, BookingDetailsActivity.class);
            context.startActivity(intent);
        }
    });
//    Picasso.with(context).load("https://castercrew.com/media/pics/9_5d00d494c6776.jpg").into(holder.profile_pic);
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
