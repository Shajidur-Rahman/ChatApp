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
import com.shajidurrahman.chatapp.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog dialog;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setTitle("Signing...");
        dialog.setMessage("Please wait while signing to your account");
        dialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();

        signInButton();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }

        binding.dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();

            }
        });

    }

    private void signInButton() {
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                signInPogress();


            }
        });
    }

    private void signInPogress() {
        auth.signInWithEmailAndPassword(binding.signInemail.getText().toString(), binding.signInpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if (task.isSuccessful()){
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}