package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.team_up.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                boolean isAllFilled = true;

                if (email.isEmpty()) {
                    etEmail.setError("Email must be filled");
                    isAllFilled = false;
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password must be filled");
                    isAllFilled = false;
                }

                if (isAllFilled) {
                    Log.d("data", "email: " + etEmail.getText().toString());
                    Log.d("data", "password: " + etPassword.getText().toString());

                    if (email.equalsIgnoreCase("sen") && password.equalsIgnoreCase("sen")) {
                        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        etPassword.setText("");
                        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill all required data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}