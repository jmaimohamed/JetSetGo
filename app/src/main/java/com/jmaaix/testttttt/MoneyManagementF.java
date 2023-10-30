package com.jmaaix.testttttt;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MoneyManagementF extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_management, container, false);
        Button suivant = view.findViewById(R.id.IDButton_Suivant);

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText
                EditText editText = view.findViewById(R.id.IdBudget); // Replace with your EditText's ID
                String dataToSend = editText.getText().toString() + " $";

                // Create a bundle to pass the text to Fragment2 (AfficheDepensesFragment)
                Bundle args = new Bundle();
                args.putString("dataKey", dataToSend);
                Fragment Affich = new AfficheDepensesFragment(); // Replace with the actual Fragment2 class
                Affich.setArguments(args);

                replaceFragment(Affich);
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameH, fragment);
        fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        fragmentTransaction.commit();
    }
}




