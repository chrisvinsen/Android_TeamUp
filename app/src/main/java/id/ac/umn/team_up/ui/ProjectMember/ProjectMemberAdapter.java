package id.ac.umn.team_up.ui.ProjectMember;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import id.ac.umn.team_up.R;


public class ProjectMemberAdapter extends RecyclerView.Adapter<ProjectMemberAdapter.ItemPMViewHolder> {
    private LinkedList<ProjectMemberExample> mPMLists;
    private LayoutInflater mInflater;
    private Context mContext;
    public ProjectMemberAdapter(Context context, LinkedList<ProjectMemberExample> pmList){
        this.mContext = context;
        this.mPMLists = pmList;
        this.mInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProjectMemberAdapter.ItemPMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.project_member, parent, false);
        return new ItemPMViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectMemberAdapter.ItemPMViewHolder holder, int position) {
        ProjectMemberExample mPMExample = mPMLists.get(position);
        holder.memberName.setText(mPMExample.getMemberName());
        holder.memberPosition.setText(mPMExample.getPosition());
    }

    @Override
    public int getItemCount() {
        return mPMLists.size();
    }

    public class ItemPMViewHolder extends RecyclerView.ViewHolder {
        private TextView memberName, memberPosition;
        private LinearLayout pmUtensil;
        private ProjectMemberAdapter madapter;
        private Button accBtn, decBtn;
        private int mPosisi;
        private ProjectMemberExample mPM;
        public ItemPMViewHolder(@NonNull View itemView, ProjectMemberAdapter adapter) {
            super(itemView);
            madapter = adapter;
            memberName = (TextView) itemView.findViewById(R.id.memName);
            memberPosition = (TextView) itemView.findViewById(R.id.memPosition);
            pmUtensil = (LinearLayout) itemView.findViewById(R.id.pmUtensil);
            accBtn = (Button) itemView.findViewById(R.id.accButton);
            decBtn = (Button) itemView.findViewById(R.id.decButton);

            accBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosisi = getLayoutPosition();
                    mPM = mPMLists.get(mPosisi);
                    Log.d("AccButton", mPM.getMemberName());
                }
            });
            decBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosisi = getLayoutPosition();
                    mPM = mPMLists.get(mPosisi);
                    Log.d("decButton", mPM.getMemberName());
                }
            });
        }
    }
}
