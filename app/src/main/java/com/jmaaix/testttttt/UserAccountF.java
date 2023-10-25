package com.jmaaix.testttttt;

import static android.text.style.TtsSpan.ARG_USERNAME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private String username;

    TextView userIdTextView ;
    TextView usernameTextView ;
    TextView passwordTextView;
    private List<User> userList = new ArrayList<User>();
    public UserAccountF() {
        // Required empty public constructor

    } public UserAccountF(String username) {
        // Required empty public constructor
        this.username= username;
    }



    public static UserAccountF newInstance(String username) {
        UserAccountF fragment = new UserAccountF();
        fragment.username = username;
        return fragment;
    }

    private UserDatabase userDatabase;
    private UserDao userDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        // Initialize Room database and UserDao
        userDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        userDao = userDatabase.userDao();

        // Retrieve the username of the logged-in user (from the login process)
       // String loggedInUsername = getArguments().getString(ARG_USERNAME);
     //   Log.d("UserAccountF", "loggedInUsername: " + loggedInUsername);

        // Retrieve the user information using the username


        userIdTextView = view.findViewById(R.id.userIdTextView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        passwordTextView = view.findViewById(R.id.passwordTextView);


        User user = userDao.getUserByUsername(this.username);
        Log.d("UserAccountF", "User found: " + user);
        if (user != null) {
            Log.d("UserAccountF", "User found: " + user);
            usernameTextView.setText("Username: " + user.getUsername());
            passwordTextView.setText("Password: " + user.getPassword());
        } else {
            Log.d("UserAccountF", "User not found for username: " + this.username);
            Toast.makeText(getActivity().getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}