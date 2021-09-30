package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText editText_email_id,editText_password;
    private Button login_button,signup_button,forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        login_button = (Button) findViewById(R.id.loginbutton);
        signup_button = (Button) findViewById(R.id.signupbutton);
        forget_password = (Button) findViewById(R.id.forget_password);
        editText_email_id = (TextInputEditText) findViewById(R.id.login_email_id);
        editText_password = (TextInputEditText) findViewById(R.id.login_password);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,signup.class));
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgetPassword.class));
            }
        });

    }
    private void LoginUser() {
        String email_id = editText_email_id.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (email_id.isEmpty()) {
            editText_email_id.setError("Email is required!");
            editText_email_id.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
            editText_email_id.setError("Please Enter a valid Email!");
            editText_email_id.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editText_password.setError("Password is required!");
            editText_password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editText_password.setError("Min password length should be 6 characters!");
            editText_password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email_id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(Login.this,HomeScreen.class));
                } else {
                    Toast.makeText(Login.this, "Failed to Login! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

