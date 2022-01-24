package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditEntryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private ImageView backButton;
    private ImageView uploadPhotoFrame;
    private ProgressDialog progressDialog;
    private String title,  location, description, body;
    private Button updateButton;
    private FirebaseFirestore db;
    private String entryID;
    private String userId;
    private JournalEntry journalEntry;
    private EditText entryTitle, entryDescription, entryBody, entryLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        //getting elements from layout
        backButton = findViewById(R.id.edit_back_button);
        uploadPhotoFrame = findViewById(R.id.edit_entry_background);
        updateButton = findViewById(R.id.edit_entry_button);
        entryTitle = findViewById(R.id.edit_entry_title);
        entryDescription = findViewById(R.id.edit_entry_description);
        entryLocation = findViewById(R.id.edit_entry_location);
        entryBody = findViewById(R.id.edit_entry_body);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        //getting entry values from intent
        Intent intent = getIntent();
        journalEntry = intent.getParcelableExtra("journalEntry");
        entryID = intent.getStringExtra("journalID");

        entryTitle.setText(journalEntry.getTitle());
        entryLocation.setText(journalEntry.getLocation());
        entryDescription.setText(journalEntry.getDescription());
        entryBody.setText(journalEntry.getBody());
        Picasso.get()
                .load(journalEntry.getImageUrl())
                .placeholder(R.drawable.testbanner)
                .error(R.drawable.testbanner)
                .into(uploadPhotoFrame);




        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_add);

        //updating the referenced
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(EditEntryActivity.this);
                progressDialog.setMessage("Saving Journal Entry");
                progressDialog.show();

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                title = entryTitle.getText().toString();
                location = entryLocation.getText().toString();
                description = entryDescription.getText().toString();
                body = entryBody.getText().toString();

                journalEntry.setTitle(title);
                journalEntry.setLocation(location);
                journalEntry.setDescription(description);
                journalEntry.setBody(body);

                db.collection("users").document(userId)
                        .collection("entries")
                        .document(entryID)
                        .set(journalEntry)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(EditEntryActivity.this, "Entry Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditEntryActivity.this, MainActivity.class));
                                finish();
//                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditEntryActivity.this, "Entry Update Failed", Toast.LENGTH_SHORT).show();
//                                Log.w(TAG, "Error writing document", e);
                            }
                        });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditEntryActivity.this, MainActivity.class));
                finish();
            }
        });

        uploadPhotoFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditEntryActivity.this, "Images can't be changed", Toast.LENGTH_SHORT).show();
            }
        });



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent( EditEntryActivity.this, MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent( EditEntryActivity.this, SettingsActivity.class));
                        finish();
                        break;
                    case R.id.nav_add:
                        break;
                }

                return true;
            }
        });
    }
}