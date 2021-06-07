package id.ac.umn.team_up.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.RegisterCommunicator;

public class RegisterFragment extends Fragment {

    Button btnContinue;
    EditText etFirstname, etLastname;
    RegisterCommunicator registerCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etFirstname = view.findViewById(R.id.etFirstname);
        etLastname = view.findViewById(R.id.etLastname);

        btnContinue = view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etFirstname.getText().toString().isEmpty()) {
                    etFirstname.setError("First name must be filled");
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill all required data", Toast.LENGTH_SHORT).show();
                } else {
                    if (registerCommunicator != null) {
                        registerCommunicator.nextRegistration(etFirstname.getText().toString(), etLastname.getText().toString());
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    public void setRegisterCommunicator(RegisterCommunicator registerCommunicator) {
        this.registerCommunicator = registerCommunicator;
    }
}