package com.jmaaix.testttttt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;



public class fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        // Retrieve the text from the argument
        Bundle args = getArguments();
        if (args != null) {
            String text = args.getString("dataKey");
            if (text != null) {
                // Display the text in TextView or any other view
                TextView textView = view.findViewById(R.id.affiche_budget);
                textView.setText(text);
            }
        }

        return view;
    }
}
