package com.jmaaix.testttttt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);

        // Find the ImageView
        ImageView ringImageView = findViewById(R.id.imageView4);

        // Start the animation on the ImageView
        ringImageView.startAnimation(rotateAnimation);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the LoginActivity after 2 seconds
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);

                // Close the MainActivity (optional)
                finish();
            }
        }, 2000);
    }
}