package id.ac.umn.team_up.ui.activity.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.ac.umn.team_up.R;
import id.ac.umn.team_up.models.Project;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private Context mContext;
    private List<Project> mProjects;


    public PostAdapter(Context context, List<Project> projects){
        mContext = context;
        mProjects = projects;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Project project = mProjects.get(position);
        // Insert user profile
//        String fullname = project.getMembers().get(0).getFirstName() + " " + project.getMembers().get(0).getLastName();
//        holder.profile_name.setText(fullname);
//        Picasso.get().load(project.getMembers().get(0).getProfilePicture()).placeholder(R.mipmap.ic_launcher).fit().into(holder.profile_image);
        // Insert project
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        holder.post_time.setText(formatter.format(project.getCreatedAt()));
        holder.project_title.setText(project.getTitle());
        holder.project_description.setText(project.getDescription());
        // Insert images
        for(int i = 0; i < project.getImages().size(); i++){
            if(i == 0){
                holder.project_image1.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(0)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image1);
            }
            if(i == 1){
                holder.project_image2.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(1)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image2);
            }
            if(i == 2){
                holder.project_image3.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(2)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image3);
            }
            if(i == 3){
                holder.project_image4.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(3)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image4);
            }
            if(i == 4){
                holder.project_image5.getLayoutParams().height = 300;
                Picasso.get().load(project.getImages().get(4)).placeholder(R.mipmap.ic_launcher).fit().into(holder.project_image5);
            }
        }
        // Hide horizontal scroll view
        if(project.getImages().size() == 0){
            holder.horizontal_scroll.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        public TextView profile_name;
        public CircleImageView profile_image;
        public TextView post_time;
        public TextView project_title;
        public TextView project_description;
        public ImageView project_image1;
        public ImageView project_image2;
        public ImageView project_image3;
        public ImageView project_image4;
        public ImageView project_image5;
        public HorizontalScrollView horizontal_scroll;

        public PostHolder(View itemView) {
            super(itemView);

            profile_name = itemView.findViewById(R.id.profile_name);
            profile_image = itemView.findViewById(R.id.profile_image);
            post_time = itemView.findViewById(R.id.post_time);
            project_title = itemView.findViewById(R.id.project_title);
            project_description = itemView.findViewById(R.id.project_description);
            project_image1 = itemView.findViewById(R.id.project_image1);
            project_image2 = itemView.findViewById(R.id.project_image2);
            project_image3 = itemView.findViewById(R.id.project_image3);
            project_image4 = itemView.findViewById(R.id.project_image4);
            project_image5 = itemView.findViewById(R.id.project_image5);
            horizontal_scroll = itemView.findViewById(R.id.horizontal_scroll);
        }
    }
}
