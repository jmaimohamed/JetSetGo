package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

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
    }

}