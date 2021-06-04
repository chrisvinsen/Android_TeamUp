package id.ac.umn.team_up.ui.activity.recycleviews.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Message;
import okhttp3.internal.Util;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Message> mMessageList;
    private String userId;

    public MessageListAdapter(Context context, List<Message> messageList, String userId){
        mContext = context;
        mMessageList = messageList;
        this.userId = userId;
    }



    @Override
    public int getItemViewType(int position){
        if(mMessageList.get(position).getFromId().equals(userId)){
            return VIEW_TYPE_MESSAGE_SENT;
        }else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_send,parent, false);
            return new SentMessageHolder(view);
        }else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_from,parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                //message body
                ((SentMessageHolder) holder).setTvMessageText(mMessageList.get(position).getMessage());

                //date
                ((SentMessageHolder) holder).setTvMessageDate(Utils.getDateforChat(mMessageList.get(position).getCreatedAt()));


                //time
                ((SentMessageHolder) holder).setTvMessageTime(Utils.getHourAndMinute(mMessageList.get(position).getCreatedAt()));


                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                //body
                ((ReceivedMessageHolder) holder).setTvRecieveMessage(mMessageList.get(position).getMessage());

                //name
                ((ReceivedMessageHolder) holder).setTvRecieveName(mMessageList.get(position).getFullName());

                //date
                ((ReceivedMessageHolder) holder).setTvRecieveDate(Utils.getDateforChat(mMessageList.get(position).getCreatedAt()));

                //time
                ((ReceivedMessageHolder) holder).setTvRecieveTime(Utils.getHourAndMinute(mMessageList.get(position).getCreatedAt()));


        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    //from user
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

        public void setTvMessageDate(String date){ this.tvMessageDate.setText(date);}

        public void setTvMessageTime(String time){ this.tvMessageTime.setText(time);}
    }

    //from others
    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private TextView tvRecieveDate, tvRecieveName, tvRecieveMessage, tvRecieveTime;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            tvRecieveDate = itemView.findViewById(R.id.text_gchat_date_other);
            tvRecieveMessage = itemView.findViewById(R.id.text_gchat_message_other);
            tvRecieveName = itemView.findViewById(R.id.text_gchat_user_other);
            tvRecieveTime = itemView.findViewById(R.id.text_gchat_timestamp_other);
        }

        public void setTvRecieveMessage(String message){
            this.tvRecieveMessage.setText(message);
        }

        public void setTvRecieveName(String fullname){
            this.tvRecieveName.setText(fullname);
        }

        public void setTvRecieveDate(String date){ this.tvRecieveDate.setText(date);}

        public void setTvRecieveTime(String time) {this.tvRecieveTime.setText(time);}


    }


}
