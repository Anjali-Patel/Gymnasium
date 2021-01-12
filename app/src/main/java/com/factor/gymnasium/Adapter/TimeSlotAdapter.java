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
import com.factor.gymnasium.Modal.TimeSlotModal;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.SubCategoryViewHolder> {
    private String user_id;
    private Context context;
    String time_slot;
    private ArrayList<TimeSlotModal> arrayList;
    ViewGroup viewGroup;
    private IOnItemClickListener iClickListener;
    public interface IOnItemClickListener {
        void onItemClick(String text);
    }
    public TimeSlotAdapter(Context context, ArrayList<TimeSlotModal> arrayList,IOnItemClickListener iClickListener) {
        this.user_id=user_id;
        this.iClickListener=iClickListener;
        this.arrayList = arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public TimeSlotAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.time_slot_layout, parent, false);
        return new TimeSlotAdapter.SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.SubCategoryViewHolder holder, int position) {
        final TimeSlotModal item = arrayList.get(position);
        holder.time_slot.setText(item.getTime_slot());
        Glide.with(context).load(item.getBooked_icon()).into(holder.booked);
        Glide.with(context).load(item.getAvailable_icon()).into(holder.available);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int total_seats=10,booked_seats=1;
  if(total_seats>booked_seats){
      iClickListener.onItemClick(item.getTime_slot());
      Toast.makeText(context,"You are eligible for booking",Toast.LENGTH_LONG).show();
  }else{
      Toast.makeText(context,"This slot already booked",Toast.LENGTH_LONG).show();
  }
            }
        });
//    Picasso.with(context).load("https://castercrew.com/media/pics/9_5d00d494c6776.jpg").into(holder.profile_pic);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class SubCategoryViewHolder extends RecyclerView.ViewHolder  {
        TextView time_slot;
        ImageView booked,available;
        SubCategoryViewHolder(View itemView) {
            super(itemView);
            time_slot=itemView.findViewById(R.id.time_slot);
            booked=itemView.findViewById(R.id.booked);
            available = itemView.findViewById(R.id.available);


        }


    }
}
