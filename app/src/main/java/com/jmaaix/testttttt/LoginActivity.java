package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;

public class LoginActivity extends AppCompatActivity {
    private UserDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Room database
        appDatabase = UserDatabase.getInstance(this);

        Button RegisterButton = findViewById(R.id.Register);
        Button ForgotPasswordButton = findViewById(R.id.ForgotPassword);
        Button LoginButton = findViewById(R.id.LoginButton);
        Button FingerPrintButton = findViewById(R.id.FingerPrint);
        EditText editUsername = findViewById(R.id.EditUsername);
        EditText editPassword = findViewById(R.id.EditPassword);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        ForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the entered values
                String enteredUsername = editUsername.getText().toString();
                String enteredPassword = editPassword.getText().toString();

                // Perform database access to validate the user
                User user = appDatabase.userDao().getUserByUsername(enteredUsername);

                if (user != null && user.getPassword().equals(enteredPassword)) {
                    // Authentication successful
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", enteredUsername);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Authentication failed
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
