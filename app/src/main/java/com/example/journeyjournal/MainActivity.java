package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.journeyjournal.Adapter.EntryAdapter;
import com.example.journeyjournal.Adapter.JournalEntryRecViewAdapter;
import com.example.journeyjournal.Interface.EntryRecyclerViewInterface;
import com.example.journeyjournal.Model.JournalEntry;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
//    private RecyclerView entryRecyclerView;
//    private ArrayList<JournalEntry> entries = new ArrayList<>();


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private final CollectionReference entriesRef = db.collection("users").document(userId)
            .collection("entries");

    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);



//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));
//        entries.add(new JournalEntry("TestTitle", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. ", "27/11/2021"));


//        JournalEntryRecViewAdapter adapter = new JournalEntryRecViewAdapter(this);
//        adapter.setEntries(entries);
        setUpRecyclerView();

//        entryRecyclerView = findViewById(R.id.entry_recyclerview);
//        entryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        entryRecyclerView.setAdapter(adapter);

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

        adapter.setOnItemClickListener(new EntryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //gets the item clicked and turns it into an object
                JournalEntry journalEntry = documentSnapshot.toObject(JournalEntry.class);
                //gets the id od the document
                String id = documentSnapshot.getId();
                //document reference for updating or deleting
//                documentSnapshot.getReference();
                Toast.makeText(MainActivity.this, "Position:"+position+"ID:"+id, Toast.LENGTH_SHORT).show();
                //sending document reference
                String path = documentSnapshot.getReference().getPath();

            }
        });
    }

    private void setUpRecyclerView(){
        Query query = entriesRef;
//                orderBy("date",Query.Direction.DESCENDING);

        //this is how we get our query into the adapter
        FirestoreRecyclerOptions<JournalEntry> options = new FirestoreRecyclerOptions.Builder<JournalEntry>()
                .setQuery(query, JournalEntry.class)
                .build();

        adapter = new EntryAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.entry_recyclerview);
        //for performance
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    //listening for the changes in the data when app is in the foreground
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //stops listening for changes
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    //overriding interface method to open the entry details in the EntryFragment
//    @Override
//    public void onEntryClick(int position) {
//        Toast.makeText(this, ""+ position + entries.get(position).toString(), Toast.LENGTH_SHORT).show();
//        startActivity(new Intent( MainActivity.this, ViewEntryActivity.class));
//    }
}