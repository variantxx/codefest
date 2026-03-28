package com.variantxx.codefest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {
    DBHandler dbHandler;
    EditText usernameInput, emailInput, passwordInput;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(this);
        usernameInput = findViewById(R.id.signup_username_input);
        emailInput = findViewById(R.id.signup_email_input);
        passwordInput = findViewById(R.id.signup_password_input);
        signupBtn = findViewById(R.id.signup_btn);

        initialize();
    }

    private void initialize() {
        signupBtn.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }else {
                try {
                    dbHandler.addUser(username, email, password);
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception err) {
                    Toast.makeText(this, "Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}