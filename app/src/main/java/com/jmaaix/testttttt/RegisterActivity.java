package com.jmaaix.testttttt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText usernameEditText = findViewById(R.id.Username);
        EditText passwordEditText = findViewById(R.id.Password);
        Button registerButton = findViewById(R.id.RegisterButton);

        UserDatabase userDatabase = UserDatabase.getInstance(this);
        final UserDao userDao = userDatabase.userDao();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Create a User object and insert it into the Room database
                User newUser = new User(username, password);
                userDao.addUser(newUser);

                // Show a message indicating successful registration
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
