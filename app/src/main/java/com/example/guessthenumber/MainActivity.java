package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//100-tentatives-secondes
public class MainActivity extends AppCompatActivity {
    Animation zoom;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom);
            img = findViewById(R.id.image);
            img.startAnimation(zoom);

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(),Home_Page.class);
                    startActivity(i);
                    finish();
                }
            },2500);
        }

    }