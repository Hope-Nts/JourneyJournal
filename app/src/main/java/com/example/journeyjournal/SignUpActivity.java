package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailTxt, passwordTxt, reEnterPasswordTxt;
    private TextView loginSwitch;
    private Button signUpBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailTxt = findViewById(R.id.signUp_email);
        passwordTxt = findViewById(R.id.signUp_password);
        reEnterPasswordTxt = findViewById(R.id.signUp_password_confirm);
        loginSwitch = findViewById(R.id.signUp_login_button);
        signUpBtn = findViewById(R.id.signUp_signUp_button);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }


    // Method for signing a user up
    private void signUp() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String reEnterPassword = reEnterPasswordTxt.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(reEnterPassword)){
            Toast.makeText(this, "Enter Email or Password are fields empty! ", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(reEnterPassword)){
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }else{

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                emailTxt.setText("");
                                passwordTxt.setText("");
                                reEnterPasswordTxt.setText("");

                                FirebaseUser user = mAuth.getCurrentUser();

                                Map<String, Object> userProfile = new HashMap<>();
                                userProfile.put("email",email);
                                String userId = user.getUid();


                                db.collection("users").document(userId)
                                        .set(userProfile)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign Up Unsuccessful. Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}