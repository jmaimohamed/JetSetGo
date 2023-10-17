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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.Executor;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ListView countriesListView = findViewById(R.id.countriesListView);

        // Get the list of country names
        ArrayList<String> countryNames = getCountryNames();

        // Sort the country names alphabetically
        Collections.sort(countryNames, new Comparator<String>() {
            @Override
            public int compare(String country1, String country2) {
                return country1.compareToIgnoreCase(country2);
            }
        });

        // Create an ArrayAdapter using the list of country names and a default layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                countryNames
        );

        // Set the adapter to the ListView
        countriesListView.setAdapter(adapter);

        // Set item click listener for the ListView
        countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click on the item at position
                String selectedCountry = countryNames.get(position);
                Toast.makeText(RegisterActivity.this, "Selected Country: " + selectedCountry, Toast.LENGTH_SHORT).show();
            }
        });
        EditText usernameEditText = findViewById(R.id.Username);
        EditText passwordEditText = findViewById(R.id.Password);
        Button registerButton = findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save username and password to SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                SharedPreferences.Editor editor = preferences.edit();

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                editor.putString("username", username);
                editor.putString("password", password);

                editor.apply();

               // BiometricManager biometricManager = BiometricManager.from(RegisterActivity.this);
               // if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
                    // Enroll biometric credentials
                  //  enrollBiometricCredentials();
               // }

                // Show a message indicating successful registration
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                // You can navigate to the login screen or perform other actions after registration
            }
        });
    }

    // Helper method to get a list of country names
    private ArrayList<String> getCountryNames() {
        String[] locales = Locale.getISOCountries();
        ArrayList<String> countryNames = new ArrayList<>();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryNames.add(obj.getDisplayCountry());
        }

        return countryNames;
    }private void enrollBiometricCredentials() {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Enroll Fingerprint")
                .setSubtitle("Place your finger on the sensor to enroll")
                .setNegativeButtonText("Cancel")
                .build();

        BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                // Store a flag indicating that biometric credentials are enrolled
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("fingerprint_registered", true);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "Biometric credentials enrolled successfully", Toast.LENGTH_SHORT).show();
            }
        };

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, authenticationCallback);

        biometricPrompt.authenticate(promptInfo);
    }


}