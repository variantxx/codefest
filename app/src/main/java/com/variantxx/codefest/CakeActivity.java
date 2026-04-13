package com.variantxx.codefest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CakeActivity extends AppCompatActivity {

    DBHandler dbHandler;
    GridView gridView;
    ArrayList<Cake> cakes;
    CakeViewAdapter cakeAdapter;
    Button addCakeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cake);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(this);
        gridView = findViewById(R.id.grid_view);

        initialize();
    }

    private void initialize() {
        displayGridView();
        addCakeButton.setOnClickListener(v ->{
            Intent intent = new Intent(this, AddCakeActivity.class);
            startActivity(intent);
        });
    }

     @Override
    protected void onResume() {
        super.onResume();
    }

    private void displayGridView() {
       cakes = new ArrayList<>();
       cakes = dbHandler.getAllCakes();
       cakeAdapter = new CakeViewAdapter(this, cakes);
       gridView.setAdapter(cakeAdapter);
    }
}