package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String username = getIntent().getStringExtra("username");
        TextView textViewUsername = findViewById(R.id.Username_recup);
            textViewUsername.setText("Welcome, " + username);
    }
}