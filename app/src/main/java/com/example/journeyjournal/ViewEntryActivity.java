package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeyjournal.Model.JournalEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ViewEntryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView background;
    private ImageView returnBtn;
    private ImageView editBtn,deleteBtn;
    private TextView entryTitle,entryDate, entryDescription;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        returnBtn = findViewById(R.id.view_back_button);
        background = findViewById(R.id.view_entry_background);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        entryTitle = findViewById(R.id.view_entry_title);
        entryDate = findViewById(R.id.view_entry_date);
        entryDescription = findViewById(R.id.view_entry_description);
        deleteBtn = findViewById(R.id.view_delete_btn);
        editBtn = findViewById(R.id.view_edit_button);
        db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //getting entry values from intent
        Intent intent = getIntent();
        JournalEntry journalEntry = intent.getParcelableExtra("journalEntry");
        String entryID = intent.getStringExtra("journalID");

        entryTitle.setText(journalEntry.getTitle());
//        entryTitle.setText(id);
        entryDate.setText(journalEntry.getDate());
        entryDescription.setText(journalEntry.getDescription());
        Picasso.get()
                .load(journalEntry.getImageUrl())
                .placeholder(R.drawable.testbanner)
                .error(R.drawable.testbanner)
                .into(background);


        //delete button functionality
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewEntryActivity.this, "Delete button pressed", Toast.LENGTH_SHORT).show();
                progressDialog = new ProgressDialog(ViewEntryActivity.this);
                progressDialog.setMessage("Deleting Journal Entry");
                progressDialog.show();

                db.collection("users").document(userId)
                        .collection("entries")
                        .document(entryID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(ViewEntryActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ViewEntryActivity.this, MainActivity.class));
                                finish();
//                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewEntryActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
//                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        });


        //Navigates to the edit page
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewEntryActivity.this, "Edit button pressed", Toast.LENGTH_SHORT).show();
            }
        });



        //functionality to navigate back to the homescreen
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewEntryActivity.this, MainActivity.class));
                finish();
            }
        });


        //bottom navigation functionality
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent( ViewEntryActivity.this, MainActivity.class));
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent( ViewEntryActivity.this, SettingsActivity.class));
                        break;
                    case R.id.nav_add:
                        startActivity(new Intent( ViewEntryActivity.this, AddEntryActivity.class));
                        break;
                }

                return true;
            }
        });
    }
}