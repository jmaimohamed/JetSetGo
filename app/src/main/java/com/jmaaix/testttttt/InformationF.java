package com.jmaaix.testttttt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationF extends Fragment {

    private String Email;

    EditText usernameEditView ;
    EditText TelephoneEditView ;
    EditText PaysEditView;
    EditText PasswordEditView ;
    EditText EmailEditView;

    public InformationF() {
        // Required empty public constructor
    }public InformationF(String Email) {
            // Required empty public constructor
            this.Email= Email;

    }

    public static InformationF newInstance(String Email) {
        InformationF fragment = new InformationF();
        fragment.Email = Email;
        return fragment;
    }

    private Button updateUsernameButton;
    private Button magic;
    private Button Change;
    private UserDatabase userDatabase;
    private UserDao userDao;
    private Executor executor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_information, container, false);
        userDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        userDao = userDatabase.userDao();
        updateUsernameButton = view.findViewById(R.id.Update);
        Change = view.findViewById(R.id.ChangePassword);

        // Retrieve the username of the logged-in user (from the login process)
        // String loggedInUsername = getArguments().getString(ARG_USERNAME);
        //   Log.d("UserAccountF", "loggedInUsername: " + loggedInUsername);

        // Retrieve the user information using the username


        usernameEditView = view.findViewById(R.id.Username);
        EmailEditView  = view.findViewById(R.id.Email);
        TelephoneEditView  = view.findViewById(R.id.Telephone);
        PaysEditView  = view.findViewById(R.id.Pays);

        User user = userDao.getUserByEmail(this.Email);
        Log.d("UserAccountF", "User found: " + user);
        if (user != null) {
            Log.d("UserAccountF", "User found: " + user);
            usernameEditView.setText( user.getUsername());
            EmailEditView.setText(user.getEmail());
            TelephoneEditView.setText( user.getTelephone());
            PaysEditView.setText( user.getPays());
            updateUsernameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newUsername =usernameEditView.getText().toString() ;
                    String Email =EmailEditView.getText().toString() ;
                    String Telephone =TelephoneEditView.getText().toString() ;
                    String Pays =PaysEditView.getText().toString() ;
                    userDao.updateUsername(user.getId(),newUsername,Email,Telephone,Pays);
                    Toast.makeText(getActivity().getApplicationContext(), "Update Done !!", Toast.LENGTH_SHORT).show();

                }
            });
            Change.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Long userId = user.getId();
                        ChangePasswordF Pass = ChangePasswordF.newInstance(userId);
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameH, Pass);
                        transaction.addToBackStack(null);
                        transaction.commit();
                }

            });

    }
        else {
            Log.d("UserAccountF", "User not found for username: " + this.Email);
            Toast.makeText(getActivity().getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}
