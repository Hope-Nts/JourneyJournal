package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeyjournal.Model.JournalEntry;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddEntryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView backButton, edit_image;
    private ImageView uploadPhotoFrame;
    private ProgressDialog progressDialog;
    private String imageUrl, imageRef,title, publisher, location, description, body, date ;
    private Button addButton;
    private Uri imageUri;
    private TextView uploadText;
    private final FirebaseStorage Fstorage = FirebaseStorage.getInstance();
    private FirebaseFirestore db;
    private EditText entryTitle, entryDescription, entryBody, entryLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_add);

        backButton = findViewById(R.id.back_button);
        uploadPhotoFrame = findViewById(R.id.entry_background);
        addButton = findViewById(R.id.add_entry_button);
        uploadText = findViewById(R.id.upload_text);
        entryTitle = findViewById(R.id.add_entry_title);
        entryDescription = findViewById(R.id.add_entry_description);
        entryLocation = findViewById(R.id.add_entry_location);

        entryBody = findViewById(R.id.add_entry_body);
        db = FirebaseFirestore.getInstance();


        //On click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
            }
        });

        uploadPhotoFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImagePicker();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJournalEntry();
            }
        });


        // method that handles bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent( AddEntryActivity.this, MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent( AddEntryActivity.this, SettingsActivity.class));
                        finish();
                        break;
                    case R.id.nav_add:
                        break;
                }

                return true;
            }
        });
    }

    //method opens the image picker
    private void startImagePicker(){
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    //used to get the image from the image cropper activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                imageUri = result.getUri();
                uploadText.setText("");
                uploadPhotoFrame.setImageURI(imageUri);
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                try {
                    throw error;
                } catch (Exception e) {
                    Toast.makeText(this, "Error taking photo", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
            }
        }
    }

    private void addJournalEntry(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Journal Entry");
        progressDialog.show();

        if(imageUri != null){

            String type = getMimeType(this,imageUri);
            type = type.substring(type.lastIndexOf("/") + 1);
            StorageReference storageRef = Fstorage.getReference();
            imageRef = "images/"+System.currentTimeMillis()+"."+type;
            final StorageReference ref = storageRef.child(imageRef);
            entryBody.setText(type);

            UploadTask uploadTask = ref.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Toast.makeText(AddEntryActivity.this, "upload failed", Toast.LENGTH_LONG).show();
//                        entryBody.setText(task.getException().toString());
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imageUrl = downloadUri.toString();
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        title = entryTitle.getText().toString();
                        location = entryLocation.getText().toString();
                        description = entryDescription.getText().toString();
                        body =entryBody.getText().toString();


                        JournalEntry journalEntry = new JournalEntry(title,userId, imageUrl, imageRef, location, description, body,date);

                        db.collection("users").document(userId)
                                .collection("entries")
                                .add(journalEntry)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AddEntryActivity.this, "Entry Added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
                                        finish();
//                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddEntryActivity.this, "Entry Addition Unsuccessful", Toast.LENGTH_SHORT).show();
//                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });


//                        startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
//                        finish();
//                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(AddEntryActivity.this, "Entry upload failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        // Handle failures
                        // ...
                    }
                }
            });



        }else{
            Toast.makeText(this, "Failed to add image to entry", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }


    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

//    public void makeDbEntry(){
//
//        Map<String, Object> userProfile = new HashMap<>();
//        userProfile.put("email","email@test.com");
//        String userId = "1234";
//
//        db.collection("users").document("userId3")
//                .set(userProfile)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AddEntryActivity.this, "Added Collection", Toast.LENGTH_SHORT).show();
////                        startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
////                        finish();
////                                                Log.d(TAG, "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AddEntryActivity.this, "Failed To Add Collection", Toast.LENGTH_SHORT).show();
//                        entryBody.setText(e.toString());
////                                                Log.w(TAG, "Error writing document", e);
//                    }
//                });
//
//
//    }



}