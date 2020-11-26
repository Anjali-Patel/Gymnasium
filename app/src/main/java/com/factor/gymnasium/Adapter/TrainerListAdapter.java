package com.factor.gymnasium.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.factor.gymnasium.Activities.TrainerDetailActivity;
import com.factor.gymnasium.Modal.TrainerListModel;
import com.factor.gymnasium.R;

import java.util.ArrayList;

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.SubCategoryViewHolder> {
    private String user_id;
    private Context context;

    private ArrayList<TrainerListModel> arrayList;
    ViewGroup viewGroup;
    public TrainerListAdapter(Context context, ArrayList<TrainerListModel> arrayList) {
        this.user_id=user_id;
        this.arrayList = arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public TrainerListAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.trainer_list_layout, parent, false);
        return new TrainerListAdapter.SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerListAdapter.SubCategoryViewHolder holder, int position) {
        final TrainerListModel item = arrayList.get(position);
        holder.trainer_name.setText(item.getName());
        holder.trainer_gender.setText(item.getGender());
        holder.view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent= new Intent(context, TrainerDetailActivity.class);
             context.startActivity(intent);
            }
        });
        holder.selectTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You have select trainer",Toast.LENGTH_LONG).show();
            }
        });
        Glide.with(context).load(item.getProfile_pic()).into(holder.trainer_logo);

//    Picasso.with(context).load("https://castercrew.com/media/pics/9_5d00d494c6776.jpg").into(holder.profile_pic);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder  {
        TextView trainer_name,trainer_gender;
        ImageView trainer_logo;
        Button view_profile,selectTrainer;

        SubCategoryViewHolder(View itemView) {
            super(itemView);
            view_profile=itemView.findViewById(R.id.view_profile);
            trainer_gender=itemView.findViewById(R.id.trainer_gender);
            trainer_name=itemView.findViewById(R.id.trainer_name);
            trainer_logo = itemView.findViewById(R.id.trainer_logo);
            selectTrainer=itemView.findViewById(R.id.selectTrainer);

        }


    }
}
