package id.ac.umn.team_up.ui.activity.recycleviews.message;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.models.Message;

public class MessageListAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder{
        private TextView tvMessageText, tvMessageTime, tvMessageDate;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
            tvMessageDate = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
            tvMessageTime = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
        }


        public void setTvMessageText(String Message){
            this.tvMessageText.setText(Message);
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
