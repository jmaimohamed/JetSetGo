package com.jmaaix.testttttt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_UserF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All_UserF extends Fragment {
    RecyclerView rv ;

    private List<String> usernames; // Change the list type to String
    private RecyclerView userRecyclerView;
    private UserListAdapter adapter;
    public All_UserF() {
        // Required empty public constructor
    }

    public static All_UserF newInstance() {
        return new All_UserF();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all__user, container, false);
        userRecyclerView = view.findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Set your preferred layout manager

        UserDao userDao = UserDatabase.getInstance(requireContext()).userDao();
        List<User> userList = userDao.getAll();

        adapter = new UserListAdapter(userList, new UserListAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Log.d("UserAccountF", "User List ttttttt: " + userList);
                 InformationF userInfoFragment = InformationF.newInstance(user.getEmail());

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameH, userInfoFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }

            @Override
            public void onDeleteClick(User user) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure you want to delete this account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        userDao.deleteUserById(user.getId());
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }

                        adapter.notifyDataSetChanged();
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

        userRecyclerView.setAdapter(adapter);

        return view ;

    }
}