package com.myapp.royalapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.myapp.royalapplication.ui.AddGiftsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView studentProfile;
    String name, email, password;
    Integer credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        studentProfile = findViewById(R.id.nav_tv_student_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
        name = sharedPreferences.getString("KEY_NAME", "");
        email = sharedPreferences.getString("KEY_EMAIL", "");
        password = sharedPreferences.getString("KEY_PASSWORD", "");
        credits = sharedPreferences.getInt("KEY_CREDITS", 0);
        //studentProfile.setText("hey there");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_open_drawer_description);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }

    @SuppressLint("ApplySharedPref")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("KEY_NAME");
            editor.remove("KEY_EMAIL");
            editor.remove("KEY_PASSWORD");
            editor.remove("KEY_CREDITS");
            editor.commit();

            Intent i = new Intent(NavigationDrawerActivity.this, SignInActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_add_gifts) {
            fragment = new AddGiftsFragment();
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}