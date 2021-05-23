package id.ac.umn.team_up.ui.ProjectUtensil;

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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import id.ac.umn.team_up.R;

public class TodoListAdapter extends FirestoreRecyclerAdapter<ToDoList, TodoListAdapter.TodoListHolder> {
    private Context mContext;
    public TodoListAdapter( @NonNull FirestoreRecyclerOptions<ToDoList> options) {
        super(options);
        Log.d("INSIDEDEDE", options.toString());
//        this.mContext = context;
    }

    @NonNull
    @Override
    public TodoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolistexample, parent, false);
        Log.d("INSIDE", "todoCreate");
        return new TodoListHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull TodoListAdapter.TodoListHolder holder, int position, @NonNull ToDoList model) {
        holder.tvTitle.setText(model.getTitle());
        Log.d("modelload", model.getTitle());
        holder.tvDesc.setText(model.getDescription());
//        if(model.getStatus() == "true"){
//            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white ));
//            holder.tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.white ));
//            holder.cbStatus.setBackgroundResource(R.drawable.check_box);
//            holder.bgTdl.setBackgroundResource(R.drawable.selectedtodolist);
//        }else{
//            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black ));
//            holder.tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.black ));
//            holder.cbStatus.setBackgroundResource(R.drawable.check_box_outline);
//            holder.bgTdl.setBackgroundResource(R.drawable.unselectedtodolist);
//        }
    }

    class TodoListHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDesc;

        public TodoListHolder(View itemView) {
            super(itemView);
            Log.d("pinkey", "asdasdasdad");
            this.tvTitle = itemView.findViewById(R.id.textviewTitle);
            this.tvDesc = itemView.findViewById(R.id.textviewDesc);
        }
    }
}
