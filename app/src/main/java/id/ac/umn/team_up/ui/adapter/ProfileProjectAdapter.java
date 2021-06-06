package id.ac.umn.team_up.ui.adapter;

import android.util.Log;
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

public class ProfileProjectAdapter extends FirestoreRecyclerAdapter<Project, ProfileProjectViewHolder> {
    //    private final Context c;
    private AppCompatActivity app;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProfileProjectAdapter(@NonNull FirestoreRecyclerOptions<Project> options, AppCompatActivity app) {
        super(options);
        this.app = app;
    }

    @NonNull
    @Override
    public ProfileProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project,parent,false);
        return new ProfileProjectViewHolder(view,app);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileProjectViewHolder holder, int position, @NonNull Project model) {
        holder.setGroupID(model.getId());
        holder.setTvProjectName(model.getTitle());
        holder.setTvProjectDate(model.getDuration());

        Log.e("bgsd", model.getTitle());

        if(model.getGroupIcon().compareTo("") != 0){
            Picasso.get()
                    .load(model.getGroupIcon())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .transform(new CircleTransform())
                    .into(holder.getIvProjectIcon());
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .transform(new CircleTransform())
                    .into(holder.getIvProjectIcon());
        }

    }
}
