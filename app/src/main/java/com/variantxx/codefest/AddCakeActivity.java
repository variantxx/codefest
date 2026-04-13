package com.variantxx.codefest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCakeActivity extends AppCompatActivity {
    DBHandler dbHandler;
    ImageView preview;
    EditText name, description;
    Button saveBtn;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_cake);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DBHandler(this);
        preview = findViewById(R.id.cake_preview_select);
        name = findViewById(R.id.cake_name_input);
        description = findViewById(R.id.cake_desc_input);
        saveBtn = findViewById(R.id.cake_save_btn);

        initialize();
    }

    private void initialize() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        preview.setImageURI(imageUri);
                    }
                });

        preview.setOnClickListener(v -> openImagePicker());

        saveBtn.setOnClickListener(v -> {
            String cakeName = name.getText().toString();
            String cakeDesc = description.getText().toString();
            if (cakeName.isEmpty() || cakeDesc.isEmpty() || imageUri == null) {
                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
                return;
            }
            dbHandler.addCake(imageUri.toString(), cakeName, cakeDesc);
            finish();
        });

    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        imagePickerLauncher.launch(intent);
    }
}