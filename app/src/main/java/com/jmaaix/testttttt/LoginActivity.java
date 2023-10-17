package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Rigester = findViewById(R.id.Register);
        Button Forget = findViewById(R.id.ForgotPassword);

        Rigester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                            }
        });
        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });
        EditText editUsername = findViewById(R.id.EditUsername);
        EditText editPassword = findViewById(R.id.EditPassword);
        Button loginButton = findViewById(R.id.LoginButton);
        Button FingerPrint = findViewById(R.id.FingerPrint);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve stored username and password from SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                String storedUsername = preferences.getString("username", "");
                String storedPassword = preferences.getString("password", "");

                // Get the entered values
                String enteredUsername = editUsername.getText().toString();
                String enteredPassword = editPassword.getText().toString();

                // Check if entered values match the stored values
                if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword) && enteredPassword!=null && enteredUsername!=null ) {
                    // Authentication successful
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", enteredUsername);
                    startActivity(intent);

                    Toast.makeText(LoginActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                    // You can navigate to the main screen or perform other actions
                } else {
                    // Authentication failed
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FingerPrint.setOnClickListener(view -> authenticateWithBiometric());
    }
    private void authenticateWithBiometric() {
        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                showBiometricPrompt();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // No biometric features available on this device
                Toast.makeText(this, "No biometric features available", Toast.LENGTH_SHORT).show();
                // You might provide an alternative login method here
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // Biometric features are currently unavailable
                Toast.makeText(this, "Biometric features are currently unavailable", Toast.LENGTH_SHORT).show();
                // You might provide an alternative login method here
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // The user hasn't enrolled any biometric credentials
                Toast.makeText(this, "No biometric credentials enrolled", Toast.LENGTH_SHORT).show();
                // You might guide the user to enroll biometric credentials
                break;
        }
    }

    private void showBiometricPrompt() {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Place your finger on the sensor")
                .setNegativeButtonText("Cancel")
                .build();

        BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                if (isFingerprintRegistered()) {
                    // Proceed with login logic
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Biometric authentication successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Fingerprint not registered", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, authenticationCallback);

        biometricPrompt.authenticate(promptInfo);
    }

    private boolean isFingerprintRegistered() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("fingerprint_registered", false);
    }
}