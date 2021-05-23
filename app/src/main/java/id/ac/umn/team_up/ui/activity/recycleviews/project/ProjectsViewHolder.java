package id.ac.umn.team_up.ui.activity.recycleviews.project;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.ui.activity.ChatActivity;

public class ProjectsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvProjectListName, tvProjectListChatTime, tvProjectListRecentChatText;
    private ImageView ivProjectGroupIcon;
    private ProjectListAdapter.itemClickListener itemClickListener;
    private String groupID;
    private AppCompatActivity app;

    public ProjectsViewHolder(@NonNull View itemView,AppCompatActivity app) {
        super(itemView);
        this.tvProjectListName = itemView.findViewById(R.id.project_list_name);
        this.tvProjectListChatTime = itemView.findViewById(R.id.project_list_chat_time);
        this.tvProjectListRecentChatText = itemView.findViewById(R.id.project_list_chat_text);
        this.app = app;
        this.ivProjectGroupIcon = itemView.findViewById(R.id.project_list_group_icon_box);
        itemView.setOnClickListener(this);
    }

    public void setTvProjectListName(String tvProjectListName) {
        this.tvProjectListName.setText( tvProjectListName);
    }

    public String getGroupID(){
        return groupID;
    }

    public void setGroupID(String id){
        this.groupID = id;
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
//        Log.d("rvItem","clicked");
//        Log.d("data", groupID);

        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        //sending project id
        intent.putExtra("groupID",this.groupID);
        intent.putExtra("curretUser", UserController.getCurrentUser(v.getContext()).getFullName());
        app.startActivity(intent);
    }

    public ImageView getIvProjectGroupIcon() {
        return ivProjectGroupIcon;
    }

    public void setIvProjectGroupIcon(ImageView ivProjectGroupIcon) {
        this.ivProjectGroupIcon = ivProjectGroupIcon;
    }
}
