package com.example.forher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forher.authentification.EditProfileActivity;
import com.example.forher.authentification.LoginActivity;
import com.example.forher.authentification.RegisterActivity;
import com.example.forher.intro.IntroActivity;
import com.example.forher.ui.PostActivity;
import com.example.forher.ui.RaysTestActivity;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //   ProgBar progressBar = findViewById(R.id.progbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash_screen.this, IntroActivity.class));
                finish();
            }
        }, 2000);
    }
}