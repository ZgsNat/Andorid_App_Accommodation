package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQUEST_CODE_FOR_DOING_SOMETHING = 200;
    private Button btnGoToLoginPage;
    private Button btnGoToRegisterPage;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    private void bindingView() {
        btnGoToLoginPage = findViewById(R.id.btnLoginPage);
        btnGoToRegisterPage = findViewById(R.id.btnSignupPage);
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void bindingAction() {
        btnGoToLoginPage.setOnClickListener(this::onBtnGoToLoginPageClick);
        btnGoToRegisterPage.setOnClickListener(this::onBtnGoToRegisterPageClick);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void onBtnGoToRegisterPageClick(View view) {
        Intent i = new Intent(this, Signup.class);
        startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
    }


    private void onBtnGoToLoginPageClick(View view) {
        Intent i = new Intent(this, Login.class);
        startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_profile){
            Toast.makeText(this, "Go to Profile", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, UserProfile.class);
            startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
        }
        if(id == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}