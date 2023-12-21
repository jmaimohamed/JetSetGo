package com.jmaaix.testttttt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jmaaix.testttttt.DAO.BudgetDao;
import com.jmaaix.testttttt.database.UserDatabase;


public class fragment2 extends Fragment {

  private  String Email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        String loggedInUsername = requireActivity().getIntent().getStringExtra("Email");

        //BUTTONS
        Button ButFood = view.findViewById(R.id.IdFood1);
        Button ButAccomodation = view.findViewById(R.id.IdAccomodation1);
        Button ButTransport = view.findViewById(R.id.IdTransport1);
        Button ButShopping = view.findViewById(R.id.IdShopping1);
        Button ButLoisir = view.findViewById(R.id.IdLoisir1);
        Button capture = view.findViewById(R.id.capturefacture);



        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            Email = loggedInUsername;
            EditText editText = view.findViewById(R.id.Id_depenses);
            UserDatabase userDatabase = UserDatabase.getInstance(requireContext());
            BudgetDao budgetDao = userDatabase.budgetDao();

            ButFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String BText = editText.getText().toString();

                    if (!BText.isEmpty()) {
                        long userId = budgetDao.getUserIDByEmail(Email);
                        double BValue = Double.parseDouble(BText);

                        budgetDao.updateBFood(userId, BValue);

                        AfficheDepensesFragment afficheDepensesFragment = new AfficheDepensesFragment();
                        replaceFragment(afficheDepensesFragment);
                    }
                }
            });


            ButAccomodation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String BText = editText.getText().toString();

                    if (!BText.isEmpty()) {
                        long userId = budgetDao.getUserIDByEmail(Email);
                        double BValue = Double.parseDouble(BText);

                        budgetDao.updateBAccomodation(userId, BValue);

                        AfficheDepensesFragment afficheDepensesFragment = new AfficheDepensesFragment();
                        replaceFragment(afficheDepensesFragment);
                    }
                }
            });

            ButTransport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String BText = editText.getText().toString();

                    if (!BText.isEmpty()) {
                        long userId = budgetDao.getUserIDByEmail(Email);
                        double BValue = Double.parseDouble(BText);
                        budgetDao.updateBTransport(userId, BValue);
                        AfficheDepensesFragment afficheDepensesFragment = new AfficheDepensesFragment();
                        replaceFragment(afficheDepensesFragment);
                    }
                }
            });

            ButShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String BText = editText.getText().toString();

                    if (!BText.isEmpty()) {
                        long userId = budgetDao.getUserIDByEmail(Email);
                        double BValue = Double.parseDouble(BText);
                        budgetDao.updateBShopping(userId, BValue);
                        AfficheDepensesFragment afficheDepensesFragment = new AfficheDepensesFragment();
                        replaceFragment(afficheDepensesFragment);
                    }
                }
            });
            ButLoisir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String BText = editText.getText().toString();

                    if (!BText.isEmpty()) {
                        long userId = budgetDao.getUserIDByEmail(Email);
                        double BValue = Double.parseDouble(BText);
                        budgetDao.updateBLoisir(userId, BValue);
                        AfficheDepensesFragment afficheDepensesFragment = new AfficheDepensesFragment();
                        replaceFragment(afficheDepensesFragment);
                    }
                }
            });

            capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraFragment cameraFragment = new CameraFragment();
                    replaceFragment(cameraFragment);
                }
            });

        }
            return view;
        }



        // Retrieve the text from the argument
        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameH, fragment);
            fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
            fragmentTransaction.commit();
        }

}
