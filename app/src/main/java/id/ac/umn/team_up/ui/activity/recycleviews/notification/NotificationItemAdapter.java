package id.ac.umn.team_up.ui.activity.recycleviews.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class  NotificationItemAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private String notifFrom, notifHead, notifDate;
    private LayoutInflater mInflater;


    public NotificationItemAdapter(Context context){
        this.notifFrom = "Felix juniarto";
        this.notifHead = "you got new message";
        this.notifDate = "Oct 9, 2020";
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.notification_item;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(viewType, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.getNameFrom().setText(this.notifFrom);
        holder.getNotifHead().setText(this.notifHead);
        holder.getNotifTime().setText(this.notifDate);
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
