package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = findViewById(R.id.frameH);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);



      //  String username = getIntent().getStringExtra("username");
     //   TextView textViewUsername = findViewById(R.id.Username_recup);
    //    textViewUsername.setText("Welcome, " + username);




        // Set up initial fragment
        replaceFragment(new StepCounterF());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.podometry) {
                replaceFragment(new StepCounterF());


            } else if (itemId == R.id.myMoneyManagement) {
                replaceFragment(new MoneyManagementF());
            }

         else if (itemId == R.id.myNote) {
            replaceFragment(new MyNoteF());
        }


         else if (itemId == R.id.Rec) {
            replaceFragment(new MyReclamationF());
        }


            else if (itemId == R.id.User) {
                replaceFragment(new UserAccountF());
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