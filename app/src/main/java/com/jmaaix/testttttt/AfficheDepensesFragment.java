package com.jmaaix.testttttt;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class AfficheDepensesFragment extends Fragment {

    public AfficheDepensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_affiche_depenses, container, false);

        Button addDepenseButton = rootView.findViewById(R.id.IDbutton_addDepense);
        Button editBudget = rootView.findViewById(R.id.IDbutton_addbudget);


        // Receive the data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String receivedData = args.getString("dataKey");
            if (receivedData != null) {
                // Update the TextView with the received data
                TextView textView = rootView.findViewById(R.id.affiche_budget); // Replace with your TextView's ID
                textView.setText(receivedData);
            }
        }

        // Handle the button click to navigate to another fragment (Fragment2, for example)
        addDepenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with Fragment2
                Fragment fragment2 = new fragment2(); // Replace with the actual Fragment2 class
                replaceFragment(fragment2);
            }
        });

        editBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with Fragment2
                Fragment MoneyMa = new MoneyManagementF(); // Replace with the actual Fragment2 class
                replaceFragment(MoneyMa);
            }
        });


        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameH, fragment);
        fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        fragmentTransaction.commit();
    }
}



