package id.ac.umn.team_up.ui.activity.recycleviews.projectmember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.ui.CircleTransform;
import id.ac.umn.team_up.ui.activity.recycleviews.project.ProjectsViewHolder;

public class ProjectMemberAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ProjectMember> lProjectMembers;

    public ProjectMemberAdapter(Context context, List<ProjectMember> projectMembers){
        this.context = context;
        this.lProjectMembers = projectMembers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_project_member, parent, false);

        return new ProjectMemberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ProjectMemberHolder) holder).setMemberName(lProjectMembers.get(position).getFullName());
        ((ProjectMemberHolder) holder).setMemberPosition(lProjectMembers.get(position).getRole());

        if(lProjectMembers.get(position).getPicture().compareTo("") != 0){
            Picasso.get()
                    .load(lProjectMembers.get(position).getPicture())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .transform(new CircleTransform())
                    .into(((ProjectMemberHolder) holder).getProjectMemberIcon());
        }else{
            Picasso.get()
                    .load(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .transform(new CircleTransform())
                    .into(((ProjectMemberHolder) holder).getProjectMemberIcon());
        }
    }

    @Override
    public int getItemCount() {
        return lProjectMembers.size();
    }

    public class ProjectMemberHolder extends RecyclerView.ViewHolder{
        private TextView memberName, memberPosition;
        private ImageView projectMemberIcon;
        public ProjectMemberHolder(@NonNull View itemView) {
            super(itemView);
            this.memberName = itemView.findViewById(R.id.memName);
            this.memberPosition = itemView.findViewById(R.id.memPosition);
            this.projectMemberIcon = itemView.findViewById(R.id.project_member_icon);
        }
        public void setMemberName(String memberName){
            this.memberName.setText(memberName);
        }

        public void setMemberPosition(String memberPosition){
            this.memberPosition.setText(memberPosition);
        }

        public ImageView getProjectMemberIcon() {
            return projectMemberIcon;
        }
    }
}
