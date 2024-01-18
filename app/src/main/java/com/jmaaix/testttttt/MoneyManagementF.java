package com.jmaaix.testttttt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jmaaix.testttttt.DAO.BudgetDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.Budget;
import com.jmaaix.testttttt.entities.User;

public class MoneyManagementF extends Fragment {
    private String Email;

    public MoneyManagementF() {
        // Required empty public constructor
    }

    public static MoneyManagementF newInstance(String email) {
        MoneyManagementF fragment = new MoneyManagementF();
        fragment.setEmail(email);
        return fragment;
    }

    private void setEmail(String email) {
        this.Email = email;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_management, container, false);

        // Retrieve the current user's email from the intent
        String loggedInUsername = requireActivity().getIntent().getStringExtra("Email");

        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            Email = loggedInUsername;
            Button suivant = view.findViewById(R.id.IDButton_Suivant);
            EditText editText = view.findViewById(R.id.IdBudget);
            UserDatabase userDatabase = UserDatabase.getInstance(requireContext());
            BudgetDao budgetDao = userDatabase.budgetDao();
            suivant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String budgetText = editText.getText().toString();
                    if (!budgetText.isEmpty()) {
                        try {
                            double budgetValue = Double.parseDouble(budgetText);
                            // Get the user ID using the retrieved email
                            long userId = budgetDao.getUserIDByEmail(Email);
                            double totalBudget = budgetDao.getTotalBudget(userId);

                            if (totalBudget == 0.0) {
                                // Budget does not exist, insert a new budget
                                Budget newBudget = new Budget(userId, budgetValue);
                                budgetDao.insertBudget(newBudget);
                            } else {
                                // Budget exists, update the existing budget
                                budgetDao.updateTotalBudget(userId, budgetValue);
                            }

                            // After inserting or updating the budget, navigate to AfficheDepensesFragment
                            AfficheDepensesFragment afficheDepensesFragment = new AfficheDepensesFragment();
                            replaceFragment(afficheDepensesFragment);

                        } catch (NumberFormatException e) {
                            Toast.makeText(requireContext(), "Please enter a valid budget", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Please enter a budget", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
        }

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
