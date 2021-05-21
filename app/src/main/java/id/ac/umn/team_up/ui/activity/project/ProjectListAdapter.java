package id.ac.umn.team_up.ui.activity.project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Project;
//RecyclerView.Adapter<ProjectViewHolder>
public class ProjectListAdapter extends FirestoreRecyclerAdapter<Project, ProjectViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    //constructor, add app parameter
    private AppCompatActivity app;
    private Project project;
    public ProjectListAdapter(@NonNull FirestoreRecyclerOptions<Project> options, AppCompatActivity app) {
        super(options);
        this.app= app;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectViewHolder holder, int position, @NonNull Project model) {
        //Log.d("user id", UserController.getUserId());
        //Log.d("project id",model.get_id());
//        String docCol = model.get_id().replaceAll("\\s","");
//        ArrayList<DocumentSnapshot> testing = ProjectController.queryMember("ProjectNael",docCol, "members");
//
//        Log.d("docsnap member", testing.toString());
        Log.d("name", model.get_id());
        holder.setTvProjectListName(model.getTitle());
        holder.setIvProjectIcon(model.getGroupIcon());
//        Log.d("projectId", model.get_id());
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("ProjectNael").document(model.get_id()).collection("members").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.d("", "Error : " + error.getMessage());
//                }
//                Log.d("member",value.getDocuments().toString());
//
//            }
//        });
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);


        return new ProjectViewHolder(v,app);
    }
}
