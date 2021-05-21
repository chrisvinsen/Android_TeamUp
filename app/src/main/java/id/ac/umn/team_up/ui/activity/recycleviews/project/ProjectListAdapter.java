package id.ac.umn.team_up.ui.activity.recycleviews.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;

public class ProjectListAdapter extends FirestoreRecyclerAdapter<Project, ProjectsViewHolder> {
//    private final Context c;
    private List<Project> projects;
    private List<Message> recentMessages;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProjectListAdapter(@NonNull FirestoreRecyclerOptions<Project> options) {
        super(options);
    }

    interface itemClickListener{
        void onItemClick(int pos);
    }

//    public ProjectListAdapter(Context context, List<Project> projects){
//        //constructor
//        this.c = context;
//        this.projects = projects;
////        this.recentMessages = recentMessages;
//    }

    @NonNull
    @Override
    public ProjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_layout,parent,false);
        return new ProjectsViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ProjectsViewHolder holder, int position) {
//        //final Project p = projects.get(position);
//        holder.setTvProjectListName(this.projects.get(position).getProjectName());
//        holder.setTvProjectListRecentChatText(this.recentMessages.get(position).getMessage());
//        holder.setTvProjectListChatTime(String.valueOf(this.recentMessages.get(position).getSentAt().toDate().getTime()));
//    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectsViewHolder holder, int position, @NonNull Project model) {
        holder.setTvProjectListName(model.getTitle());
//        holder.setTvProjectListRecentChatText(model.getRecentMessage());
//        holder.setTvProjectListChatTime(String.valueOf(model.getSentAt().toDate().getTime()));
    }

//    @Override
//    public int getItemCount() {
//        return this.projects.size();
//    }
}
