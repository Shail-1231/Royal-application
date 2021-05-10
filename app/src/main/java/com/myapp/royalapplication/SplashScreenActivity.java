package com.myapp.royalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    private int time = 3000;
    String email, password, name;
    int credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_APP", MODE_PRIVATE);
        name = sharedPreferences.getString("KEY_NAME", "");
        email = sharedPreferences.getString("KEY_EMAIL", "");
        password = sharedPreferences.getString("KEY_PASSWORD", "");
        credits = sharedPreferences.getInt("KEY_CREDITS", 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, SignInActivity.class);
                startActivity(i);

                if(email.equals("") && password.equals(""))
                {
                    Toast.makeText(SplashScreenActivity.this, "No such user exits", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(intent);
                }

                else {
                    Intent intent = new Intent(SplashScreenActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                }
            }
        }, time);
    }
}