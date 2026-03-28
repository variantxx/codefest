package com.variantxx.codefest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NoteAddScreenActivity extends AppCompatActivity {
    DBHandler dbHandler;
    SharedPreferences sharedPref;
    EditText titleInput, contentInput;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_add_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(this);
        sharedPref = getSharedPreferences("CodeFestPref", MODE_PRIVATE);
        titleInput = findViewById(R.id.add_note_title_input);
        contentInput = findViewById(R.id.add_note_content_input);
        saveButton = findViewById(R.id.add_note_btn);

        initialize();
    }

    private void initialize() {
        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String content = contentInput.getText().toString();

            if (!title.isEmpty() && !content.isEmpty()) {
                int userId = sharedPref.getInt("userId", -1);
                if (userId != -1) {
                    dbHandler.addNote(userId, title, content);
                    Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this, "User not found. Please log in again.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }else {
                Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}