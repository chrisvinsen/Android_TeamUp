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
        ((TodolistHolder) holder).setTvTitle(mTodoList.get(position).getTitle(), mTodoList.get(position).getStatus());
        ((TodolistHolder) holder).setTvDesc(mTodoList.get(position).getDescription(), mTodoList.get(position).getStatus());
        ((TodolistHolder) holder).setLybg(mTodoList.get(position).getStatus());
        ((TodolistHolder) holder).setCheckboxImg(mTodoList.get(position).getStatus());
        ((TodolistHolder) holder).setProjectId(mTodoList.get(position).getProjectId());
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
        private String projectId, todolistId;
        public TodolistHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.textviewTitle);
            this.tvDesc = itemView.findViewById(R.id.textviewDesc);
            this.lybg = itemView.findViewById(R.id.bgTdl);
            this.checkboxImg = itemView.findViewById(R.id.checkboxStatus);
            itemView.setOnClickListener(this);
        }


        public void setTvTitle(String tvTitle, String status) {
            this.tvTitle.setText(tvTitle);
            if(status == "true"){
                this.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            }else{
                this.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        }


        public void setTvDesc(String tvDesc, String status) {
            this.tvDesc.setText(tvDesc);
            if(status == "true"){
                this.tvDesc.setTextColor(ContextCompat.getColor(context, R.color.white));
            }else{
                this.tvDesc.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        }
        public void setLybg(String status){
            if(status == "true"){
                this.lybg.setBackgroundResource(R.drawable.selectedtdl);
            }else{
                this.lybg.setBackgroundResource(R.drawable.unselectedtdl);
            }
        }
        public void setCheckboxImg(String status){
            if(status == "true"){
                this.checkboxImg.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
            }else{
                this.checkboxImg.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);
            }
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public void setTodolistId(String todolistId){
            this.todolistId = todolistId;
        }

        public String getTodolistId() {
            return todolistId;
        }
        @Override
        public void onClick(View v) {
//
            ProjectController.updateStatusTodolist(this.todolistId,"false");
        }
    }
}
