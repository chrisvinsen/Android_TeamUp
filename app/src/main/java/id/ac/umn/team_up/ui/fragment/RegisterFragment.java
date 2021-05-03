package id.ac.umn.team_up.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.RegisterCommunicator;

public class RegisterFragment extends Fragment {

    Button btnContinue;
    EditText etFirstname, etLastname;
    RegisterCommunicator registerCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etFirstname = view.findViewById(R.id.etFirstname);
        etLastname = view.findViewById(R.id.etLastname);

        btnContinue = view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerCommunicator != null) {
                    registerCommunicator.changeFragment();
                }
            }
        });

        return view;
    }

    public void setRegisterCommunicator(RegisterCommunicator registerCommunicator) {
        this.registerCommunicator = registerCommunicator;
    }
}