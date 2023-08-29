package com.example.todo.service;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;

public class UserProfile extends AppCompatActivity {

    private ImageButton backButton;
    private EditText userTitle;
    private EditText userName;
    private Button cancelButton;
    private Button saveButton;
    private TextView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backButton = findViewById(R.id.backMenu);
        userTitle = findViewById(R.id.editTitle);
        userName = findViewById(R.id.editUserName);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        profileIcon = findViewById(R.id.userProfile);
        final String existingName = getIntent().getStringExtra(getString(R.string.user));
        final String existingTitle = getIntent().getStringExtra(getString(R.string.user_title));

        userName.setText(existingName);
        userName.getText().clear();
        userTitle.setText(existingTitle);
        userTitle.getText().clear();
        backButton.setOnClickListener(view -> onBackPressed());
        cancelButton.setOnClickListener(view -> onBackPressed());
        saveButton.setOnClickListener(view -> {

        });
    }
}