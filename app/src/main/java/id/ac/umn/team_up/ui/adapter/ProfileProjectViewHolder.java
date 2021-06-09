package id.ac.umn.team_up.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class ProfileProjectViewHolder extends RecyclerView.ViewHolder {
    private TextView tvProjectName, tvProjectDate;
    private ImageView projectImage;
    private String groupID;
    private AppCompatActivity app;

    public ProfileProjectViewHolder(@NonNull View itemView, AppCompatActivity app) {
        super(itemView);
        this.tvProjectName = itemView.findViewById(R.id.projectName);
        this.tvProjectDate = itemView.findViewById(R.id.projectDate);
        this.app = app;
        this.projectImage = itemView.findViewById(R.id.projectImage);
    }

    public void setTvProjectName(String tvProjectName) {
        this.tvProjectName.setText( tvProjectName);
    }

    public void setGroupID(String id){
        this.groupID = id;
    }

    public void setTvProjectDate(String tvProjectDate) {
        this.tvProjectDate.setText( tvProjectDate);
    }

    public ImageView getIvProjectIcon() {
        return projectImage;
    }
}
