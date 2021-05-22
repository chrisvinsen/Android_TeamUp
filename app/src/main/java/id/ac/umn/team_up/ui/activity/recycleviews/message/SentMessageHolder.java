package id.ac.umn.team_up.ui.activity.recycleviews.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class SentMessageHolder extends RecyclerView.ViewHolder{
    private TextView tvMessageText, tvMessageTime, tvMessageDate;

    public SentMessageHolder(@NonNull View itemView) {
        super(itemView);
        tvMessageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
        tvMessageDate = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
        tvMessageTime = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
    }


}
