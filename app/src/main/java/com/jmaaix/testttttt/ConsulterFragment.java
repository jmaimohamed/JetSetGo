package com.jmaaix.testttttt;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jmaaix.testttttt.DAO.FactureDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.Facture;

import java.util.List;

public class ConsulterFragment extends Fragment {

    private RecyclerView recyclerView;
    private String Email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulter, container, false);
        String loggedInUsername = requireActivity().getIntent().getStringExtra("Email");
        Email = loggedInUsername;
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        UserDatabase userDatabase = UserDatabase.getInstance(requireContext());
        FactureDao factureDao = userDatabase.factureDao();
        long userId = factureDao.getUserIDByEmail(Email);

        List<Facture> factureList = factureDao.getAllFacturesById(userId);

        ImageAdapter imageAdapter = new ImageAdapter();
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setFactureList(factureList);

        return view;
    }
}



