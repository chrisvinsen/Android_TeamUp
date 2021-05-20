package id.ac.umn.team_up.ui.activity.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.models.Project;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private final Context c;
    public ArrayList<Project> projects;
    private AppCompatActivity app;

    interface itemClickListener{
        void onItemClick(int pos);
    }

    public ProjectListAdapter(Context context,ArrayList<Project> projects, AppCompatActivity app){
        //constructor
        this.c = context;
        this.app = app;
        this.projects = projects;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.project_item,parent,false);
        return new ProjectViewHolder(view, this.app);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        //final Project p = projects.get(position);
        holder.setTvProjectListName(this.projects.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return this.projects.size();
    }
}
