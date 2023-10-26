package com.jmaaix.testttttt;

import static android.text.style.TtsSpan.ARG_USERNAME;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAccountF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountF extends Fragment {

    private String mParam1;
    private String mParam2;
    private String Email;

    EditText usernameEditView ;
    EditText TelephoneEditView ;
    EditText PaysEditView;
    EditText PasswordEditView ;
    EditText EmailEditView;
    private List<User> userList = new ArrayList<User>();
    public UserAccountF() {
        // Required empty public constructor

    } public UserAccountF(String Email) {
        // Required empty public constructor
        this.Email= Email;
    }



    public static UserAccountF newInstance(String Email) {
        UserAccountF fragment = new UserAccountF();
        fragment.Email = Email;
        return fragment;
    }
    private Button updateUsernameButton;
    private Button delete;

    private UserDatabase userDatabase;
    private UserDao userDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        // Initialize Room database and UserDao
        userDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        userDao = userDatabase.userDao();
        updateUsernameButton = view.findViewById(R.id.Update);
        delete = view.findViewById(R.id.Delete);

        // Retrieve the username of the logged-in user (from the login process)
       // String loggedInUsername = getArguments().getString(ARG_USERNAME);
     //   Log.d("UserAccountF", "loggedInUsername: " + loggedInUsername);

        // Retrieve the user information using the username


        usernameEditView = view.findViewById(R.id.Username);
        EmailEditView  = view.findViewById(R.id.Email);
        TelephoneEditView  = view.findViewById(R.id.Telephone);
        PaysEditView  = view.findViewById(R.id.Pays);
        PasswordEditView= view.findViewById(R.id.Password);

        User user = userDao.getUserByEmail(this.Email);
        Log.d("UserAccountF", "User found: " + user);
        if (user != null) {
            Log.d("UserAccountF", "User found: " + user);
            usernameEditView.setText( user.getUsername());
            PasswordEditView.setText( user.getPassword());
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


                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete Account");
                    builder.setMessage("Are you sure you want to delete your account?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Delete the account (call a method to delete from the database)
                            userDao.deleteUserById(user.getId());
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                fm.popBackStack();
                            }                            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(loginIntent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User canceled the account deletion
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        } else {
            Log.d("UserAccountF", "User not found for username: " + this.Email);
            Toast.makeText(getActivity().getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

}