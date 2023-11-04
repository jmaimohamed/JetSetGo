package com.jmaaix.testttttt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MagicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MagicFragment extends Fragment {

    private Long ID;
    EditText SpessEdit;
    private Button MagicButtin;

    public MagicFragment() {
        // Required empty public constructor

    }

    public MagicFragment(Long id) {
        // Required empty public constructor
        this.ID = id;
    }

    private UserDatabase userDatabase;
    private UserDao userDao;

    public static MagicFragment newInstance(Long userId) {
        MagicFragment fragment = new MagicFragment();
        fragment.ID= userId;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_magic, container, false);
        userDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        userDao = userDatabase.userDao();
        MagicButtin = view.findViewById(R.id.UpdateMagic);
        SpessEdit = view.findViewById(R.id.Spess);
        User user = userDao.getUserById(this.ID);
        if (user != null) {
            Log.d("UserAccountF", "User found: " + user);

            SpessEdit.setText(user.getSpes());
            MagicButtin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Spess = SpessEdit.getText().toString();
                    userDao.updateMagic(user.getId(), Spess);
                    Toast.makeText(getActivity().getApplicationContext(), "Update Done !!", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return view;
    }

}