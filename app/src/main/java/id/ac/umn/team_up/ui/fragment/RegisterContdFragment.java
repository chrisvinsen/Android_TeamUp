package id.ac.umn.team_up.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.RegisterCommunicator;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.RegisterActivity;

public class RegisterContdFragment extends Fragment {
    final String FIRSTNAME = "first_name";
    final String LASTNAME = "last_name";

    EditText etEmail, etPassword, etCPassword;
    Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_contd, container, false);

        Bundle arguments = getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            final String first_name = arguments.getString(FIRSTNAME);
            final String last_name = arguments.getString(LASTNAME);

            etEmail = view.findViewById(R.id.etEmail);
            etPassword = view.findViewById(R.id.etPassword);
            etCPassword = view.findViewById(R.id.etCPassword);
            btnRegister = view.findViewById(R.id.btnRegister);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isAllFilled = true;
                    final String email = etEmail.getText().toString();
                    final String password = etPassword.getText().toString();
                    final String cpassword = etCPassword.getText().toString();

                    if (email.isEmpty()) {
                        etEmail.setError("Email must be filled");
                        isAllFilled = false;
                    }
                    if (password.isEmpty()) {
                        etPassword.setError("Password must be filled");
                        isAllFilled = false;
                    }
                    if (cpassword.isEmpty()) {
                        etCPassword.setError("Confirmation password must be filled");
                        isAllFilled = false;
                    }
                    if (!password.equalsIgnoreCase(cpassword)) {
                        etCPassword.setError("Confirmation password doesn't match");
                        isAllFilled = false;
                    }

                    if (isAllFilled) {
                        final Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        Log.d("data", "fname: " + first_name);
                        Log.d("data", "lname: " + last_name);
                        Log.d("data", "email: " + email);
                        Log.d("data", "pass: " + password);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Please fill all required data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
        }
            

        return view;
    }
}