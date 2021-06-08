package id.ac.umn.team_up.ui.activity.recycleviews.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.rpc.Help;

import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ToDoList;

public class TodoListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ToDoList> mTodoList;

    public TodoListAdapter(Context context, List<ToDoList> todoList){
        this.context = context;
        this.mTodoList = todoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todolist_item,parent, false);
        return new TodolistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TodolistHolder) holder).setTvTitle(mTodoList.get(position).getTitle());
        ((TodolistHolder) holder).setTvDesc(mTodoList.get(position).getDescription());
        if(mTodoList.get(position).getStatus().equals("true")){
            ((TodolistHolder) holder).lybg.setBackgroundResource(R.drawable.selectedtdl);
            ((TodolistHolder) holder).checkboxImg.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
            ((TodolistHolder) holder).tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            ((TodolistHolder) holder).tvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
        }else{
            ((TodolistHolder) holder).lybg.setBackgroundResource(R.drawable.unselectedtdl);
            ((TodolistHolder) holder).checkboxImg.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);
            ((TodolistHolder) holder).tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            ((TodolistHolder) holder).tvDesc.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        ((TodolistHolder) holder).setStatus(mTodoList.get(position).getStatus());
        ((TodolistHolder) holder).setTodolistId(mTodoList.get(position).getTodolistId());
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    public class TodolistHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvTitle, tvDesc;
        private LinearLayout lybg;
        private ImageButton checkboxImg;
        private String status, todolistId;
        private int mposisi;
        private ToDoList mTdlList;
        public TodolistHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.textviewTitle);
            this.tvDesc = itemView.findViewById(R.id.textviewDesc);
            this.lybg = itemView.findViewById(R.id.bgTdl);
            this.checkboxImg = itemView.findViewById(R.id.checkboxStatus);
            itemView.setOnClickListener(this);
        }


        public void setTvTitle(String tvTitle) {
            this.tvTitle.setText(tvTitle);
        }


        public void setTvDesc(String tvDesc) {
            this.tvDesc.setText(tvDesc);
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTodolistId(String todolistId){
            this.todolistId = todolistId;
        }

        public String getTodolistId() {
            return todolistId;
        }
        @Override
        public void onClick(View v) {
            Log.e("tdlrv", "Clicked");
            mposisi = getLayoutPosition();
            Log.e("pos", String.valueOf(mposisi));
            mTdlList = mTodoList.get(mposisi);
            Log.e("tdlrv", mTdlList.getStatus());
            ProjectController.updateStatusTodolist(this.todolistId,this.status, mposisi);
        }
    }
}
