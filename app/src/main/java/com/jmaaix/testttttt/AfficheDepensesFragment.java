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

import com.jmaaix.testttttt.DAO.BudgetDao;
import com.jmaaix.testttttt.database.UserDatabase;


public class AfficheDepensesFragment extends Fragment {

    private String Email;

    public AfficheDepensesFragment() {
        // Required empty public constructor
    }

    public static AfficheDepensesFragment newInstance(String email) {
        AfficheDepensesFragment fragment = new AfficheDepensesFragment();
        fragment.setEmail(email);
        return fragment;
    }

    private void setEmail(String email) {
        this.Email = email;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_affiche_depenses, container, false);
        TextView totalBudgetTextView = rootView.findViewById(R.id.affiche_budget);
        TextView totalBfood=rootView.findViewById(R.id.IdTotalFood);
        TextView totalBAccomodation=rootView.findViewById(R.id.IdTotalAccomodation);
        TextView totalBTransport=rootView.findViewById(R.id.IdTotalTransport);
        TextView totalBShopping=rootView.findViewById(R.id.IdTotalShopping);
        TextView totalBLoisir=rootView.findViewById(R.id.IdTotalDivertissement);

        Button addDepenseButton = rootView.findViewById(R.id.IDbutton_addDepense);
        Button editBudget = rootView.findViewById(R.id.IDbutton_addbudget);

        UserDatabase userDatabase = UserDatabase.getInstance(requireContext());
        BudgetDao budgetDao = userDatabase.budgetDao();
        String loggedInUsername = requireActivity().getIntent().getStringExtra("Email");

        // Handle the button click to navigate to another fragment (Fragment2, for example)
        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            Email = loggedInUsername;
            long userId = budgetDao.getUserIDByEmail(Email);
            double totalBudget = budgetDao.getTotalBudget(userId);

            double Bfood= budgetDao.getBFood(userId);
            double Baccomodation = budgetDao.getBAccomodation(userId);
            double Btransport = budgetDao.getBTransport(userId);
            double Bshopping = budgetDao.getBShopping(userId);
            double BLoisir= budgetDao.getBLosir(userId);

            double BudgetRestant=totalBudget-(Bfood+ Baccomodation+Btransport+Bshopping+BLoisir);

            totalBudgetTextView.setText(String.valueOf(BudgetRestant));
            totalBfood.setText(String.valueOf(Bfood));
            totalBAccomodation.setText(String.valueOf(Baccomodation));
            totalBTransport.setText(String.valueOf(Btransport));
            totalBShopping.setText(String.valueOf(Bshopping));
            totalBLoisir.setText(String.valueOf(BLoisir));
        }
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



