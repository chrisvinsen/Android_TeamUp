package id.ac.umn.team_up.ui.activity.recycleviews.post;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import de.hdodenhof.circleimageview.CircleImageView;
import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.CircleTransform;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> implements Filterable {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();
    private static CollectionReference memberRef = db_firestore.collection("ProjectMembers");
    private Context mContext;
    private List<Project> mProjects;
    private List<Project> mProjectsFilter;


    public PostAdapter(Context context, List<Project> projects){
        mContext = context;
        mProjects = projects;
        mProjectsFilter = projects;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Boolean check = true;
        Project project = mProjectsFilter.get(position);
        // Insert user profile
        holder.profile_name.setText(project.getAdminFullname());
        if(project.getAdminPicture() != ""){
            Picasso.get().load(project.getAdminPicture()).placeholder(R.mipmap.ic_launcher).transform(new CircleTransform()).into(holder.profile_image);
        }
        else{
            Picasso.get().load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).transform(new CircleTransform()).into(holder.profile_image);
        }
        // Insert project
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        holder.post_time.setText(formatter.format(project.getCreatedAt()));
        holder.project_title.setText(project.getTitle());
        holder.project_description.setText(project.getDescription());
        // Hide all images
        holder.project_image1.getLayoutParams().height = 0;
        holder.project_image2.getLayoutParams().height = 0;
        holder.project_image3.getLayoutParams().height = 0;
        holder.project_image4.getLayoutParams().height = 0;
        holder.project_image5.getLayoutParams().height = 0;
        holder.project_image1.setVisibility(View.INVISIBLE);
        holder.project_image2.setVisibility(View.INVISIBLE);
        holder.project_image3.setVisibility(View.INVISIBLE);
        holder.project_image4.setVisibility(View.INVISIBLE);
        holder.project_image5.setVisibility(View.INVISIBLE);
        // Insert images
        for(int i = 0; i < project.getImages().size(); i++){
            if(i == 0){
                holder.project_image1.setVisibility(View.VISIBLE);
                holder.project_image1.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(0)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image1);
            }
            if(i == 1){
                holder.project_image2.setVisibility(View.VISIBLE);
                holder.project_image2.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(1)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image2);
            }
            if(i == 2){
                holder.project_image3.setVisibility(View.VISIBLE);
                holder.project_image3.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(2)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image3);
            }
            if(i == 3){
                holder.project_image4.setVisibility(View.VISIBLE);
                holder.project_image4.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(3)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image4);
            }
            if(i == 4){
                holder.project_image5.setVisibility(View.VISIBLE);
                holder.project_image5.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(4)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image5);
            }
        }
        Log.d("filter231", project.getId());
        Log.d("filter231", String.valueOf(project.getImages().size()));
        // Hide horizontal scroll view
        if(project.getImages().size() == 0){
            holder.horizontal_scroll.setVisibility(View.INVISIBLE);
        }
        else{
            holder.horizontal_scroll.setVisibility(View.VISIBLE);
        }
        // Check for member id
        for(String ids : project.getMembers()){
            if(mAuth.getUid().equals(ids)){
                check = false;
            }
        }
        if(project.getMembersRequest() != null){
            for(String ids : project.getMembersRequest()){
                if(mAuth.getUid().equals(ids)){
                    check = false;
                }
            }
        }

        // If user already a member or already send a request
        if(check == false){
            holder.join_button.setEnabled(false);
        }
        holder.join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable button
                holder.join_button.setEnabled(false);

                // Add user to project
                project.addMemberRequest(mAuth.getUid());
                DocumentReference projectRef = db_firestore.document("ProjectDetails/" + project.getId());
                projectRef.update("membersRequest", project.getMembersRequest());

                // Get shared preference
                SharedPreferences sharedPref = Utils.getSharedPref(mContext.getApplicationContext());

                // Put member into map
                Map<String, Object> member = new HashMap<>();
                member.put("userId", mAuth.getUid());
                member.put("projectId", project.getId());

                String fullname = sharedPref.getString("ufirstname", "") + " " + sharedPref.getString("ulastname", "");
                String picture = sharedPref.getString("upicture", "");

                member.put("fullName", fullname);
                member.put("role", "Member");
                member.put("picture", picture);
                member.put("adminId", project.getAdminId());
                member.put("isMember", false);

                // Set map into collection
                memberRef.document().set(member)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Request Successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Utils.show(mContext, "Something went wrong, please try again later.");
                                holder.join_button.setEnabled(true);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjectsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Project> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                Log.d("filter", "0");
                filteredList.addAll(mProjects);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                Log.d("filtera1", "1");
                Log.d("filter", filterPattern);

                for (Project item : mProjects) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        Log.d("filtera23", filterPattern);
                        Log.d("filtera2", item.getTitle().toLowerCase());
                        filteredList.add(item);
                    }
                    else if(item.getDescription().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProjectsFilter.clear();
            if(results.values != null){
                mProjectsFilter.addAll((List) results.values);
            }
            Log.d("filter231", "start");
            notifyDataSetChanged();
        }
    };

    public class PostHolder extends RecyclerView.ViewHolder{
        public TextView profile_name;
        public ImageView profile_image;
        public TextView post_time;
        public TextView project_title;
        public TextView project_description;
        public ImageView project_image1;
        public ImageView project_image2;
        public ImageView project_image3;
        public ImageView project_image4;
        public ImageView project_image5;
        public HorizontalScrollView horizontal_scroll;
        public Button join_button;

        public PostHolder(View itemView) {
            super(itemView);

            profile_name = itemView.findViewById(R.id.profile_name);
            profile_image = itemView.findViewById(R.id.profile_image_post_item);
            post_time = itemView.findViewById(R.id.post_time);
            project_title = itemView.findViewById(R.id.project_title);
            project_description = itemView.findViewById(R.id.project_description);
            project_image1 = itemView.findViewById(R.id.project_image1);
            project_image2 = itemView.findViewById(R.id.project_image2);
            project_image3 = itemView.findViewById(R.id.project_image3);
            project_image4 = itemView.findViewById(R.id.project_image4);
            project_image5 = itemView.findViewById(R.id.project_image5);
            horizontal_scroll = itemView.findViewById(R.id.horizontal_scroll);
            join_button = itemView.findViewById(R.id.join_button);
        }
    }
}
