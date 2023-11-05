package com.jmaaix.testttttt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import at.favre.lib.crypto.bcrypt.BCrypt;
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
                String plainPassword = password;
                int logRounds = 10;
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
                        String hashedPassword = BCrypt.withDefaults().hashToString(logRounds, plainPassword.toCharArray());
                        Log.e("EmailSender", "Error sending email: " + hashedPassword);
                        User newUser = new User(Username, hashedPassword ,Email,Telephone,Pays);
                        userDao.addUser(newUser);
                        boolean registrationSuccess = true; // Replace with your registration logic
                        if (registrationSuccess) {

                            new SendEmailTask().execute(Email,Username);
                            Toast.makeText(RegisterActivity.this, "Registration successful. Welcome email sent.", Toast.LENGTH_SHORT).show();
                            }
                        else
                        {
                                Toast.makeText(RegisterActivity.this, "Registration successful, but failed to send the welcome email.", Toast.LENGTH_SHORT).show();
                        }



                        // Show a message indicating successful registration
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private class SendEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                if (params.length > 0) {
                    String recipientEmail = params[0];
                    String username = params[1];
                    EmailSender.sendEmail(recipientEmail,username); // Pass the recipient's email address to the email sending method
                }
            } catch (Exception e) {
                Log.e("EmailSender", "Error sending email: " + e.getMessage(), e);
            }
            return null;
        }
    }

}
