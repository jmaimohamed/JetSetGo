package com.jmaaix.testttttt;

import static android.text.style.TtsSpan.ARG_USERNAME;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.concurrent.Executor;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
    private Button Information;
    private Button delete;
    private Button magic;
    private Button All_Users;
    private Button FingerPrint;
    private UserDatabase userDatabase;
    private UserDao userDao;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private String fingerprintData;
    private SessionManager sessionManager; // Initialize SessionManager

    private BiometricPrompt.PromptInfo promptInfo;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        // Initialize Room database and UserDao
        userDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        userDao = userDatabase.userDao();
        Information = view.findViewById(R.id.Info);
        delete = view.findViewById(R.id.Delete);
    magic = view.findViewById(R.id.Spes);
    Button Logout = view.findViewById(R.id.LogOut);
        All_Users=view.findViewById(R.id.All_User);
        User user = userDao.getUserByEmail(this.Email);
        String userRole = user.getRole();
        boolean isAdmin = "Admin".equals(userRole);

        // Observe user login status
        boolean isLoggedIn = user != null;
        if (isAdmin) {
            All_Users.setVisibility(View.VISIBLE);
            All_Users.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    All_UserF allUsersFragment = All_UserF.newInstance();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameH, allUsersFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }
        else {
                 All_Users.setVisibility(View.GONE);
            }


        Log.d("UserAccountF", "User found: " + user);
        if (user != null) {

            Log.d("UserAccountF", "User found: " + user);

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
                            getActivity().finish();
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


            magic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve the user's ID (assuming you have the user object)
                    Long userId = user.getId();

                    // Create a new instance of MagicFragment and pass the user ID as an argument
                    MagicFragment magicFragment = MagicFragment.newInstance(userId);

                    // Use a FragmentTransaction to navigate to the MagicFragment
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameH, magicFragment);
                    transaction.addToBackStack(null); // Add to back stack for navigation
                    transaction.commit();


                }
            });


            Information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Emailuser = user.getEmail();
                    InformationF infoFragment = InformationF.newInstance(Emailuser);
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameH, infoFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        } else {
            delete.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
            magic.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
            Information.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            startActivity(loginIntent);
            Log.d("UserAccountF", "User not found for username: " + this.Email);
            Toast.makeText(getActivity().getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
        }


        return view;
    }


}