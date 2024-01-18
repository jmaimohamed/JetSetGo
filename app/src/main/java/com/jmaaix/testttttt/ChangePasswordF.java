package com.jmaaix.testttttt;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordF extends Fragment {
    private UserDatabase appDatabase;

    Long ID;
    public ChangePasswordF() {
        // Required empty public constructor
    }
    public ChangePasswordF(Long ID) {
        this.ID= ID;    }
    public static ChangePasswordF newInstance(Long userId) {
        ChangePasswordF fragment = new ChangePasswordF();
        fragment.ID= userId;
        return fragment;
    }
    private Button Update ;
    private UserDatabase userDatabase;
    private UserDao userDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        UserDatabase userDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        userDao = userDatabase.userDao();
        EditText Password = view.findViewById(R.id.Password);
        EditText NewPassword = view.findViewById(R.id.NewPassword);
        String Pwd = Password.getText().toString();
        String NP = NewPassword.getText().toString();
        Update = view.findViewById(R.id.Update);
        int logRounds = 10;
        User user = userDao.getUserById(this.ID);
        Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String Pwd = Password.getText().toString();
                String NP = NewPassword.getText().toString();

                if (user != null) {
                    String hashedPassword = user.getPassword();
                    if (BCrypt.verifyer().verify(Pwd.toCharArray(), hashedPassword).verified )
                        {
                            if(!NP.isEmpty()) {
                                String NewhashedPassword = BCrypt.withDefaults().hashToString(logRounds, NP.toCharArray());
                                userDao.UpdatePassword(user.getId(), NewhashedPassword);
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
                                 }
                            else {
                                Toast.makeText(getActivity(), "enter your new password !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    else
                    {
                        Toast.makeText(getActivity(), "incorrect check please your field ", Toast.LENGTH_SHORT).show();
                    }
                }
            }});

        return view;
    }
}