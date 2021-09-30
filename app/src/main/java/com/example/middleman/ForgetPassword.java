package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private TextInputEditText reset_password_email_id;
    private Button reset_password_button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        reset_password_button = (Button) findViewById(R.id.reset_password_button);
        reset_password_email_id = (TextInputEditText) findViewById(R.id.reset_password_email_id);
        auth = FirebaseAuth.getInstance();

        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email_id = reset_password_email_id.getText().toString().trim();

        if (email_id.isEmpty()) {
            reset_password_email_id.setError("Email is required!");
            reset_password_email_id.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
            reset_password_email_id.setError("Please Enter a valid Email!");
            reset_password_email_id.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email_id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgetPassword.this,"Try Again!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}