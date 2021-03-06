package com.shajidurrahman.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shajidurrahman.chatapp.Models.Users;
import com.shajidurrahman.chatapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setTitle("Creating user...");
        dialog.setMessage("Please wait while creating an account");
        dialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        signUpButton();

        binding.signupBtnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });


    }

    private void signUpButton() {
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

                String name = binding.signUpname.getText().toString();
                String email = binding.signUpemail.getText().toString();
                String password = binding.signUppass.getText().toString();

                if (name.isEmpty()){
                    binding.signUpname.setError("Please enter your name ");
                } else if (email.isEmpty()){
                    binding.signUpemail.setError("Please enter your email");
                } else if (password.isEmpty()){
                    binding.signUppass.setError("Please enter your password");
                } else if (password.length() < 8){
                    binding.signUppass.setError("Password must be 8 character");
                } else {
                    Users users = new Users(name, email, password);
                    signUpProcess(email, password, users);
                }

            }
        });
    }

    private void signUpProcess(String email, String password, Users users) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uId = task.getResult().getUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("USERS").child(uId).setValue(users);
                    dialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    finish();

                } else {
                    Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }
    }


}