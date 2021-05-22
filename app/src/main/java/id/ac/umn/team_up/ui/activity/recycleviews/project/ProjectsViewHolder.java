package id.ac.umn.team_up.ui.activity.recycleviews.project;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class ProjectsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvProjectListName, tvProjectListChatTime, tvProjectListRecentChatText;
    private ImageView ivProjectGroupIcon;
    private ProjectListAdapter.itemClickListener itemClickListener;

    public ProjectsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tvProjectListName = itemView.findViewById(R.id.project_list_name);
        this.tvProjectListChatTime = itemView.findViewById(R.id.project_list_chat_time);
        this.tvProjectListRecentChatText = itemView.findViewById(R.id.project_list_chat_text);
        this.ivProjectGroupIcon = itemView.findViewById(R.id.project_list_group_icon_box);
        itemView.setOnClickListener(this);
    }

    public void setTvProjectListName(String tvProjectListName) {
        this.tvProjectListName.setText( tvProjectListName);
    }

    public void setTvProjectListChatTime(String tvProjectListChatTime) {
        this.tvProjectListChatTime.setText(tvProjectListChatTime);
    }

    public void setTvProjectListRecentChatText(String tvProjectListRecentChatText) {
        this.tvProjectListRecentChatText.setText(tvProjectListRecentChatText);
    }

    public TextView getTvProjectListName() {
        return tvProjectListName;
    }

    public TextView getTvProjectListChatTime() {
        return tvProjectListChatTime;
    }

    public TextView getTvProjectListRecentChatText() {
        return tvProjectListRecentChatText;
    }

    @Override
    public void onClick(View v) {
        //this.itemClickListener.onItemClick(this.getLayoutPosition());
        Log.d("rvItem","clicked");

    }

    public ImageView getIvProjectGroupIcon() {
        return ivProjectGroupIcon;
    }

    public void setIvProjectGroupIcon(ImageView ivProjectGroupIcon) {
        this.ivProjectGroupIcon = ivProjectGroupIcon;
    }
}
