package id.ac.umn.team_up.ui.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;

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
                    } else if (password.length() < 6) {
                        etPassword.setError("Password must be at least 6 characters long");
                        isAllFilled = false;
                    }
                    if (cpassword.isEmpty()) {
                        etCPassword.setError("Confirmation password must be filled");
                        isAllFilled = false;
                    } else if (cpassword.length() < 6) {
                        etCPassword.setError("Password confirmation must be at least 6 characters long");
                        isAllFilled = false;
                    }

                    if (isAllFilled) {
                        if (password.equalsIgnoreCase(cpassword)) {
                            User newUser = new User(first_name, last_name, email);
                            UserController.register((AppCompatActivity) getActivity(), newUser, password);
                        } else {
                            etCPassword.setError("Confirmation password doesn't match");
                        }
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