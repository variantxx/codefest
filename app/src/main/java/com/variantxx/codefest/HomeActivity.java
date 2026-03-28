package com.variantxx.codefest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    ListView listView;
    SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            DBHandler dbHandler = new DBHandler(this);
            listView = findViewById(R.id.listView);

            ArrayList<Note> arrayList = new ArrayList<>();
            NoteViewAdapter arrayAdapter = new NoteViewAdapter(this, arrayList);
            listView.setAdapter(arrayAdapter);


            sharedPref = getSharedPreferences("CodeFestPref", MODE_PRIVATE);
            int userId = sharedPref.getInt("userId", -1);
            List<Note> notes = dbHandler.getNotesByUser(userId);
            arrayList.clear();
            arrayList.addAll(notes);











            return insets;
        });
    }
}