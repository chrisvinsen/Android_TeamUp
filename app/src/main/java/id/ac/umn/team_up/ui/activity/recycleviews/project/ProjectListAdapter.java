package id.ac.umn.team_up.ui.activity.recycleviews.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.CircleTransform;

public class ProjectListAdapter extends FirestoreRecyclerAdapter<Project, ProjectsViewHolder> {
//    private final Context c;
    private AppCompatActivity app;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProjectListAdapter(@NonNull FirestoreRecyclerOptions<Project> options, AppCompatActivity app) {
        super(options);
        this.app = app;
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
        return new ProjectsViewHolder(view,app);
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
        holder.setGroupID(model.getId());
        holder.setTvProjectListName(model.getTitle());
        holder.setprojectDesc(model.getDescription());
//        Message recentMessage = MessageController.getRecentMessage(model.getId(), app.getApplicationContext()), this);
//        Log.e("RECENTMESSAGEONADAPTER", recentMessage.getMessage());
        if(model.getRecentMessage() != null){
            holder.setTvProjectListRecentChatText(model.getRecentMessage());
        }
        if(model.getSentAt() != null){
            holder.setTvProjectListChatTime(Utils.getHourAndMinute(model.getSentAt()));
        }
        if(model.getGroupIcon().compareTo("") != 0){
            Picasso.get()
                    .load(model.getGroupIcon())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .transform(new CircleTransform())
                    .into(holder.getIvProjectGroupIcon());
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .transform(new CircleTransform())
                    .into(holder.getIvProjectGroupIcon());
        }

    }

//    @Override
//    public int getItemCount() {
//        return this.projects.size();
//    }
}
