package id.ac.umn.team_up.ui.ProjectUtensil;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.util.LinkedList;

import id.ac.umn.team_up.R;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ItemTdlViewHolder> {
    private LinkedList<TodoListExample> mTdlLists;
    private LayoutInflater mInflater;
    private Context mContext;
    public TodoListAdapter(Context context, LinkedList<TodoListExample> tdlList){
        this.mContext = context;
        this.mTdlLists = tdlList;
        this.mInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoListAdapter.ItemTdlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todolistexample, parent, false);
        return new ItemTdlViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.ItemTdlViewHolder holder, int position) {
        TodoListExample mTodoListExample = mTdlLists.get(position);
        holder.tvTitle.setText(mTodoListExample.getTitle());
        holder.tvDesc.setText(mTodoListExample.getDesc());
        holder.cbStatus.setChecked(mTodoListExample.getStatus());
        if(mTodoListExample.getStatus()) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.bgTdl.setBackgroundResource(R.drawable.selectedtodolist);
        }else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            holder.tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            holder.bgTdl.setBackgroundResource(R.drawable.unselectedtodolist);
        }
    }

    @Override
    public int getItemCount() {
        return mTdlLists.size();
    }

    public class ItemTdlViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvDesc;
        private CheckBox cbStatus;
        private TodoListAdapter mAdapter;
        private LinearLayout bgTdl;
        private int mPosisi;
        private TodoListExample mTdlList;

        public ItemTdlViewHolder(@NonNull View itemView, TodoListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvTitle = (TextView) itemView.findViewById(R.id.textviewTitle);
            tvDesc = (TextView) itemView.findViewById(R.id.textviewDesc);
            cbStatus = (CheckBox) itemView.findViewById(R.id.checkboxStatus);
            bgTdl = (LinearLayout) itemView.findViewById(R.id.bgTdl);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            mPosisi = getLayoutPosition();
            mTdlList = mTdlLists.get(mPosisi);
            Log.d("Stats", "Click");
            tvTitle = (TextView) v.findViewById(R.id.textviewTitle);
            tvDesc = (TextView) v.findViewById(R.id.textviewDesc);
            bgTdl = (LinearLayout) v.findViewById(R.id.bgTdl);
            cbStatus = (CheckBox) itemView.findViewById(R.id.checkboxStatus);

            if(mTdlList.getStatus()) {
                Boolean test = cbStatus.isChecked();
                if(test) {
                    Log.d("Stats", "TRUE");
                }
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                bgTdl.setBackgroundResource(R.drawable.unselectedtodolist);
                cbStatus.setChecked(false);
                Log.d("Stats", "True");
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                bgTdl.setBackgroundResource(R.drawable.selectedtodolist);
                cbStatus.setChecked(true);
                Log.d("Stats", "False");
            }
        }

    }

}
