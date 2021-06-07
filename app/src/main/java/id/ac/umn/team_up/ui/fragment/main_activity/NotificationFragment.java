package id.ac.umn.team_up.ui.fragment.main_activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.NotificationController;
import id.ac.umn.team_up.ui.activity.recycleviews.notification.NotificationItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // RecyclerView
    private RecyclerView rvNotification;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        //return inflater.inflate(R.layout.fragment_notification, container, false);



        //inflate NotificationFragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        //init recyclerview
        rvNotification = view.findViewById(R.id.rvNotificationItem);

        //adapter
        NotificationItemAdapter adapter = new NotificationItemAdapter(view.getContext(), NotificationController.reqMember);

        //setting the recyclerview
        NotificationController.setRecyclerview(rvNotification, view.getContext(), adapter);

        //NotificationController.getRecentMessage(rvNotification, view.getContext());

        Log.d("PROJECTREQ", String.valueOf(NotificationController.reqMember.size()));

        rvNotification.setHasFixedSize(true);
        rvNotification.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvNotification.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //NotificationController.loadProjectMemberRequestNotification();

    }
}