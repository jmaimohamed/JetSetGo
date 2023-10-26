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
    UserDatabase appDatabase = UserDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText usernameEditText = findViewById(R.id.Username);
        EditText EmailEditText = findViewById(R.id.Email);
        EditText TelephoneEditText = findViewById(R.id.Telephone);
        EditText passwordEditText = findViewById(R.id.Password);
        EditText PaysEditText = findViewById(R.id.Pays);
        Button registerButton = findViewById(R.id.RegisterButton);
        UserDatabase userDatabase = UserDatabase.getInstance(this);
        final UserDao userDao = userDatabase.userDao();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String Email = EmailEditText.getText().toString();
                String Telephone = TelephoneEditText.getText().toString();
                String Pays = PaysEditText.getText().toString();
                if(Username.isEmpty()||password.isEmpty()||Email.isEmpty()||Telephone.isEmpty()||Pays.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Check Your field !!! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User existingUser = appDatabase.userDao().getUserByEmail(Email);
                    if(existingUser!= null)
                        {
                            Toast.makeText(RegisterActivity.this, "Email already taken, please Choose Another One ", Toast.LENGTH_SHORT).show();
                        }
                    else{
                        User newUser = new User(Username, password,Email,Telephone,Pays);
                        userDao.addUser(newUser);

                        // Show a message indicating successful registration
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
