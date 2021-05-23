package id.ac.umn.team_up.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.MessageController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.ui.activity.ChatActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private static EditText edChat;
    private static Button btnSendMessage;
    private RecyclerView rvMessage;
    private static String userId;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messageRef = db.collection("MessageDetails");



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1; //GroupID
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        edChat = view.findViewById(R.id.edtSendMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);
        rvMessage =view.findViewById(R.id.rvMessage);
        userId = UserController.getUserId();


        MessageController.getMessage(rvMessage,view,userId,mParam1);

        //setOnClick
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Message message = new Message(mParam1, userId,edChat.getText(),  )
                MessageController.sentMessage(mParam1,mParam2, userId, edChat.getText().toString(),null);//groupid, userId, message
                edChat.setText("");

            }
        });

        //tvChat = (TextView) view.findViewById(R.id.tvChat);


        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        messageRef.whereEqualTo("groupId",mParam1).addSnapshotListener((Executor) this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null){
//                    Log.d("DATACHECK", "error");
//                }
//                if(!value.isEmpty()){
//                    Log.d("DATACHECK", "updated messages");
//                }
//            }
//        });
//    }
}