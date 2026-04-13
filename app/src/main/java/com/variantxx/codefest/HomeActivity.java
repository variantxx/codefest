package com.variantxx.codefest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    DBHandler dbHandler;
    SharedPreferences sharedPref;
    ArrayList<Note> notes;
    NoteViewAdapter noteAdapter;
    ListView listView;
    Button addNoteBtn;

    Button cakeBtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(this);
        addNoteBtn = findViewById(R.id.home_add_note_btn);
        cakeBtm = findViewById(R.id.homeCake_btn);
        listView = findViewById(R.id.listView);
        sharedPref = getSharedPreferences("CodeFestPref", MODE_PRIVATE);

        initialize();
    }

    private void initialize() {
        displayListView();

        addNoteBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, NoteAddScreenActivity.class);
            startActivity(i);
        });
        cakeBtm.setOnClickListener(v -> {
            Intent i = new Intent(this, CakeActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayListView();
    }

    private void displayListView() {
        int userId = sharedPref.getInt("userId", -1);
        notes = new ArrayList<>();
        notes = dbHandler.getNotesByUser(userId);
        noteAdapter = new NoteViewAdapter(this, notes);
        listView.setAdapter(noteAdapter);
    }

}