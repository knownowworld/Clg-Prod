package com.example.clgapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class MainActivity extends AppCompatActivity {


    AppCompatButton siginIn, signUp;
    AppCompatEditText email, pass;
    FirebaseAuth mAuth;
    String emailStr,passStr;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        email = (AppCompatEditText)findViewById(R.id.email);
        pass = (AppCompatEditText)findViewById(R.id.pass);
        siginIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        progress = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Students");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final Intent logedIn = new Intent(this, LogedIN.class);

        if (currentUser != null) {
            startActivity(logedIn);
            finish();
        } else {

            //For signUP
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.setVisibility(View.VISIBLE);
                    emailStr = (email.getText()).toString().trim();
                    passStr = (pass.getText()).toString().trim();


                    mAuth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                myRef.child(mAuth.getUid()).setValue("null");

                                //For marks
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Marks").child("CS101").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Marks").child("CS102").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Marks").child("CS105").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Marks").child("CS108").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Marks").child("CS107").setValue(0);

                                //For attendence
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Attendence").child("CS101").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Attendence").child("CS102").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Attendence").child("CS105").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Attendence").child("CS108").setValue(0);
                                myRef.child(Objects.requireNonNull(mAuth.getUid())).child("Attendence").child("CS107").setValue(0);

                                startActivity(logedIn);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            });

            //For signIN
            siginIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.setVisibility(View.VISIBLE);
                    emailStr = (email.getText()).toString().trim();
                    passStr = (pass.getText()).toString().trim();

                    mAuth.signInWithEmailAndPassword(emailStr,passStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        startActivity(logedIn);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Not a user",
                                        Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            });


        }
    }
}
