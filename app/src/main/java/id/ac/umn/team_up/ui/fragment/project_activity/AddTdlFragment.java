package id.ac.umn.team_up.ui.fragment.project_activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTdlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTdlFragment extends Fragment {
    private EditText editTextTitle, editTextDesc;
    private Button buttonAddTdl;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddTdlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTdlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTdlFragment newInstance(String param1, String param2) {
        AddTdlFragment fragment = new AddTdlFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_tdl, container, false);
        editTextTitle = view.findViewById(R.id.etTitle);
        editTextDesc = view.findViewById(R.id.etDesc);
        buttonAddTdl = view.findViewById(R.id.btnSubmitTdl);
        buttonAddTdl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ProjectController.addTodolist(mParam1, editTextTitle.getText().toString(), editTextDesc.getText().toString());
                editTextTitle.setText("");
                editTextDesc.setText("");
                Fragment fragment = new TodolistFragment().newInstance(mParam1,"");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.chatActivityContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}