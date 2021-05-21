package id.ac.umn.team_up.ui.activity.project;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.activity.ChatActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;


public class ProjectViewHolder extends RecyclerView.ViewHolder {
    private TextView tvProjectListName, tvProjectListTime, tvProjectListRecent;
    private ImageView ivProjectIcon;
    private AppCompatActivity app;

    public ProjectViewHolder(@NonNull View itemView, AppCompatActivity app) {
        super(itemView);
        this.tvProjectListName = itemView.findViewById(R.id.tvProjectListName);
        this.tvProjectListTime = itemView.findViewById(R.id.tvProjectListTime);
        this.tvProjectListRecent = itemView.findViewById(R.id.tvProjectListRecent);
        this.ivProjectIcon = itemView.findViewById(R.id.ivProjectListIcon);
        this.app = app;

        //set on click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatActivity.class);
                app.startActivity(intent);

            }
        });
    }

    public void setIvProjectIcon(String url){
        Picasso.get().load(url).into(this.ivProjectIcon);
    }

    public void setTvProjectListName(String tvProjectListName) {
        this.tvProjectListName.setText( tvProjectListName);
    }

    public void setTvProjectListTime(String tvProjectListTime) {
        this.tvProjectListTime.setText(tvProjectListTime);
    }

    public void setTvProjectListRecent(String tvProjectListRecent) {
        this.tvProjectListRecent.setText(tvProjectListRecent);
    }

    public TextView getTvProjectListName() {
        return tvProjectListName;
    }

    public TextView getTvProjectListTime() {
        return tvProjectListTime;
    }

    public TextView getTvProjectListRecent() {
        return tvProjectListRecent;
    }

//    @Override
//    public void onClick(View v) {
//        //this.itemClickListener.onItemClick(this.getLayoutPosition());
//        Log.d("rvItem","clicked");
//        Intent intent = new Intent(v.getContext(), ChatActivity.class);
//        this.app.startActivity(intent);
//    }
}
