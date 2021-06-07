package id.ac.umn.team_up.ui.activity.recycleviews.notification;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.team_up.R;

public class NotificationAddMember extends RecyclerView.ViewHolder {
    public TextView tvName, tvStatus;
    public ImageButton btnAcc, btnReject;

    public NotificationAddMember(@NonNull View itemView) {
        super(itemView);
        this.tvName = itemView.findViewById(R.id.memName);
        this.tvStatus = itemView.findViewById(R.id.memPosition);
        this.btnAcc = itemView.findViewById(R.id.accButton);
        this.btnReject = itemView.findViewById(R.id.decButton);
    }


}
