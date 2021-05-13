package id.ac.umn.team_up.ui.activity.project;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvProjectListName, tvProjectListTime, tvProjectListRecent;
    private ProjectListAdapter.itemClickListener itemClickListener;

    public ProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tvProjectListName = itemView.findViewById(R.id.tvProjectListName);
        this.tvProjectListTime = itemView.findViewById(R.id.tvProjectListTime);
        this.tvProjectListRecent = itemView.findViewById(R.id.tvProjectListRecent);
        itemView.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        //this.itemClickListener.onItemClick(this.getLayoutPosition());
        Log.d("rvItem","clicked");

    }
}
