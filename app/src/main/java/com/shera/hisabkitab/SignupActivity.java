package com.shera.hisabkitab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shera.hisabkitab.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private EditText signupEmail, signupPassword, userName;
    private Button btnSignup, btnLogin;
    private FirebaseAuth mAuth;

    private ProgressBar progressBar;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // View binding
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find views
        signupEmail = findViewById(R.id.SignUpEmail);
        signupPassword = findViewById(R.id.SignUpPassword);
        userName = findViewById(R.id.UserName);
        btnSignup = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        // Sign Up button click
        btnSignup.setOnClickListener(view -> {
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String username = userName.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                progressBar = findViewById(R.id.signupProgressBar);
                progressBar.setVisibility(View.VISIBLE); // Show when loading
                createUser(email, password);

            }
        });

        // Go to login
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);    // Hide after done
                        Intent intent = new Intent(SignupActivity.this, HisabActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);    // Hide after done
                        Toast.makeText(SignupActivity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
