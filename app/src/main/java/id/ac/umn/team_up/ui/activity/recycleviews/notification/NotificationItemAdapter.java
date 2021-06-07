package id.ac.umn.team_up.ui.activity.recycleviews.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.ProjectActivity;

public class  NotificationItemAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private String notifFrom, notifHead, notifDate;
    private Integer size;
    private LayoutInflater mInflater;
    private List<Project> arrProject = new ArrayList<Project>();
    private SimpleDateFormat formatter =  new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private Context context;

    public NotificationItemAdapter(Context context, List<Project> project){
        this.context = context;
        this.arrProject = project;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.notification_item;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(viewType, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.getNameFrom().setText(this.arrProject.get(position).getRecentMessageSender());
        holder.getNotifHead().setText(this.arrProject.get(position).getRecentMessage());
        holder.getNotifTime().setText(Utils.dateToString(this.arrProject.get(position).getSentAt()));

        //set onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProjectActivity.class);
                //sending project id
                intent.putExtra("groupID",arrProject.get(position).getId());
                intent.putExtra("curretUser", UserController.getCurrentUser(v.getContext()).getFullName());
                intent.putExtra("projectTitle", arrProject.get(position).getTitle());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProject.size();
    }
}
