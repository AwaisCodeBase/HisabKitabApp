package com.shera.hisabkitab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shera.hisabkitab.databinding.ActivityMainBinding;
import com.shera.hisabkitab.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    EditText signupEmail, signupPassword, userName;
    Button btnLogin;
    Button btnSignup;

    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnLogin = findViewById(R.id.btnLogin);
        signupPassword = findViewById(R.id.SignUpPassword);
        userName = findViewById(R.id.UserName);
        signupEmail = findViewById(R.id.SignUpEmail);
        btnSignup = findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String username = userName.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Email: " + email + "\nPassword: " + password, Toast.LENGTH_LONG).show();
                }
            }
        });



        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}