package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private String loggedInUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        frameLayout = findViewById(R.id.frameH);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loggedInUsername = getIntent().getStringExtra("Email");
        
        // Set up initial fragment
        replaceFragment(new StepCounterF());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.podometry) {
                replaceFragment(new StepCounterF());


            } else if (itemId == R.id.myMoneyManagement) {
                replaceFragment(new AfficheDepensesFragment());
            }

         else if (itemId == R.id.myNote) {
             replaceFragment(MyNoteF.newInstance(loggedInUsername));
        }


         else if (itemId == R.id.Rec) {
            replaceFragment( MyReclamationF.newInstance(loggedInUsername));
        }


            else if (itemId == R.id.User) {


                replaceFragment(UserAccountF.newInstance(loggedInUsername));
            }
            



            return true;
        });

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameH, fragment);
        fragmentTransaction.commit();
    }


}
