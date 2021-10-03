package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class signup extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private TextInputEditText editText_full_name,editText_email_id,editText_password,editText_phone_number;
    private Button login_button,signup_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        editText_full_name = (TextInputEditText) findViewById(R.id.full_name);
        editText_email_id = (TextInputEditText) findViewById(R.id.email_id);
        editText_password = (TextInputEditText) findViewById(R.id.password);
        editText_phone_number =(TextInputEditText) findViewById(R.id.phone_number);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this,Login.class));
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    private void registerUser() {
        String full_name = editText_full_name.getText().toString().trim();
        String email_id = editText_email_id.getText().toString().trim();
        String password = editText_password.getText().toString().trim();
        String phone_number = editText_phone_number.getText().toString().trim();
        String user_address="";
        String profile_image_url = "https://firebasestorage.googleapis.com/v0/b/middleman-adb5a.appspot.com/o/images%2Fdemo_face.png?alt=media&token=0189a8f1-bb3f-4fbc-9bd1-d370c34c2a88";

        if(full_name.isEmpty()){
            editText_full_name.setError("Full Name is required!");
            editText_full_name.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editText_password.setError("Password is required!");
            editText_password.requestFocus();
            return;
        }
        if(phone_number.isEmpty()){
            editText_phone_number.setError("Phone number is required!");
            editText_phone_number.requestFocus();
            return;
        }
        if(email_id.isEmpty()){
            editText_email_id.setError("Email ID is required!");
            editText_email_id.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()){
            editText_email_id.setError("Please provide valid Email ID!");
            editText_email_id.requestFocus();
            return;
        }
        if(password.length()<6){
            editText_password.setError("Min password length should be 6 characters!");
            editText_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email_id,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(full_name,email_id,phone_number,user_address,profile_image_url);

                    FirebaseDatabase.getInstance("https://middleman-adb5a-default-rtdb.firebaseio.com/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(signup.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(signup.this,Login.class));
                            }else{
                                Toast.makeText(signup.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(signup.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}