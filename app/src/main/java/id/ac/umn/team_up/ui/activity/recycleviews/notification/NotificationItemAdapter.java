package id.ac.umn.team_up.ui.activity.recycleviews.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import id.ac.umn.team_up.controllers.NotificationController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.ui.activity.ProjectActivity;

public class  NotificationItemAdapter extends RecyclerView.Adapter<NotificationAddMember> {
    private String notifFrom, notifHead, notifDate;
    private Integer size;
    private LayoutInflater mInflater;
    private List<String> arrProject = new ArrayList<String>();
    private List<ProjectMember> reqMember = new ArrayList<ProjectMember>();
    private SimpleDateFormat formatter =  new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private Context context;

    public NotificationItemAdapter(Context context, List<ProjectMember> project){
        Log.d("REQMEMBER",project.toString());
        this.context = context;
        this.reqMember = project;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.layout_project_member_pending;
    }

    @NonNull
    @Override
    public NotificationAddMember onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(viewType, parent, false);
        return new NotificationAddMember(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAddMember holder, int position) {
            holder.tvName.setText(this.reqMember.get(position).getFullName());
            holder.tvStatus.setText(this.reqMember.get(position).getProjectName());

            holder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificationController.onDelete(reqMember.get(position), position);
                }
            });

            holder.btnAcc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificationController.onAccept(reqMember.get(position), position);
                }
            });


//        holder.getNameFrom().setText(this.arrProject.get(position).getRecentMessageSender());
//        holder.getNotifHead().setText(this.arrProject.get(position).getRecentMessage());
//        holder.getNotifTime().setText(Utils.dateToString(this.arrProject.get(position).getSentAt()));

        //set onclick
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ProjectActivity.class);
//                //sending project id
//                intent.putExtra("groupID",arrProject.get(position).getId());
//                intent.putExtra("curretUser", UserController.getCurrentUser(v.getContext()).getFullName());
//                intent.putExtra("projectTitle", arrProject.get(position).getTitle());
//
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
//        return reqMember.size();
        return this.reqMember.size();
    }
}
