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
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    DBHandler dbHandler;
    SharedPreferences sharedPref;
    ArrayList<Note> arrayList;
    NoteViewAdapter arrayAdapter;
    ListView listView;
    Button addNoteBtn;

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
        arrayList = new ArrayList<>();
        arrayAdapter = new NoteViewAdapter(this, arrayList);
        addNoteBtn = findViewById(R.id.home_add_note_btn);
        listView = findViewById(R.id.listView);
        sharedPref = getSharedPreferences("CodeFestPref", MODE_PRIVATE);

        initialize();
    }

    private void initialize() {
        // Display data to list view.
        listView.setAdapter(arrayAdapter);
        int userId = sharedPref.getInt("userId", -1);
        List<Note> notes = dbHandler.getNotesByUser(userId);
        arrayList.clear();
        arrayList.addAll(notes);

        addNoteBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, NoteAddScreenActivity.class);
            startActivity(i);
        });
    }

}