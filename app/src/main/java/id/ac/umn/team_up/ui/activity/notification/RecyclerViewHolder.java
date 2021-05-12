package id.ac.umn.team_up.ui.activity.notification;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView tvNameFrom, tvNotifHead, tvNotifTime;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        tvNameFrom = itemView.findViewById(R.id.tvNameFrom);
        tvNotifHead = itemView.findViewById(R.id.tvNotifHead);
        tvNotifTime = itemView.findViewById(R.id.tvNotifTime);
    }

    public TextView getNameFrom(){
        return tvNameFrom;
    }

    public TextView getNotifHead(){
        return tvNotifHead;
    }

    public TextView getNotifTime(){
        return tvNotifTime;
    }

}
