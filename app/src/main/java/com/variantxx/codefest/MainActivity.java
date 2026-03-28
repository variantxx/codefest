package com.variantxx.codefest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHandler;
    SharedPreferences sharedPref;
    EditText emailInput, passwordInput;
    Button loginBtn;
    TextView newAccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHandler = new DBHandler(this);
        sharedPref = getSharedPreferences("CodeFestPref", MODE_PRIVATE);
        emailInput = findViewById(R.id.login_email_input);
        passwordInput = findViewById(R.id.login_password_input);
        loginBtn = findViewById(R.id.login_btn);
        newAccBtn = findViewById(R.id.new_account_btn);

        initialize();
    }

    private void initialize() {
        // Initialize the database handler.
        dbHandler.getWritableDatabase();

        // Handle user login
        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (!dbHandler.checkUser(email, password)) {
                Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
            }else {
                // Mark user as logged in.
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("userEmail", email);
                editor.apply();
                // Navigate to main screen.
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        newAccBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(i);
        });
    }
}