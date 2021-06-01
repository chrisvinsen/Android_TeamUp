package id.ac.umn.team_up.ui.activity.recycleviews.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.umn.team_up.R;
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
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    public class TodolistHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvDesc;
        public TodolistHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textviewTitle);
            tvDesc = itemView.findViewById(R.id.textviewDesc);
        }

        public void setTvTitle(String tvTitle) {
            this.tvTitle.setText(tvTitle);
        }

        public void setTvDesc(String tvDesc) {
            this.tvDesc.setText(tvDesc);
        }
    }
}
