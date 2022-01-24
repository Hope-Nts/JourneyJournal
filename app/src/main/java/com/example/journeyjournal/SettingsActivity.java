package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logOutBtn = findViewById(R.id.log_out_btn);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signOut();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_settings);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent( SettingsActivity.this, MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_settings:

                        break;
                    case R.id.nav_add:

                        startActivity(new Intent( SettingsActivity.this, AddEntryActivity.class));
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}