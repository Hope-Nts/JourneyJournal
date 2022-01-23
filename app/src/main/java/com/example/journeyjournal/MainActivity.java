package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.journeyjournal.Adapter.JournalEntryRecViewAdapter;
import com.example.journeyjournal.Interface.EntryRecyclerViewInterface;
import com.example.journeyjournal.Model.JournalEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EntryRecyclerViewInterface {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView entryRecyclerView;
    private ArrayList<JournalEntry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);



        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));


        JournalEntryRecViewAdapter adapter = new JournalEntryRecViewAdapter(this);
        adapter.setEntries(entries);


        entryRecyclerView = findViewById(R.id.entry_recyclerview);
        entryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        entryRecyclerView.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent( MainActivity.this, SettingsActivity.class));
                        finish();
                        break;
                    case R.id.nav_add:
                        startActivity(new Intent( MainActivity.this, AddEntryActivity.class));
                        finish();
                        break;
                }

                return true;
            }
        });
    }

    //overriding interface method to open the entry details in the EntryFragment
    @Override
    public void onEntryClick(int position) {
        Toast.makeText(this, ""+ position +entries.get(position).toString(), Toast.LENGTH_SHORT).show();
    }
}