package id.ac.umn.team_up.ui.ProjectUtensil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import id.ac.umn.team_up.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodolistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodolistFragment extends Fragment {

    RecyclerView rvTdlList;
    TodoListAdapter tdlAdapter;
    LinkedList<TodoListExample> tdlList = new LinkedList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodolistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodolistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodolistFragment newInstance(String param1, String param2) {
        TodolistFragment fragment = new TodolistFragment();
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
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);
        isiTdlList();
        rvTdlList = (RecyclerView) view.findViewById(R.id.rvTodoList);
        tdlAdapter = new TodoListAdapter(view.getContext(), tdlList);
        rvTdlList.setAdapter(tdlAdapter);
        rvTdlList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    public void isiTdlList(){
        tdlList.add(new TodoListExample("Tugas 1", "Mengerjakan Website UMN ECO", Boolean.TRUE));
        tdlList.add(new TodoListExample("Tugas 2", "Mengerjakan Website Rencang", Boolean.FALSE));
    }
}