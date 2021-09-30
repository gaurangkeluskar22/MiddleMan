package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logout;
    private TextView full_name_display,email_id_display,phone_number_display,greeting_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        full_name_display = (TextView) findViewById(R.id.full_name_display);
        email_id_display = (TextView) findViewById(R.id.email_id_display);
        phone_number_display = (TextView) findViewById(R.id.phone_number_display);
        logout = (Button) findViewById(R.id.logout_button);
        greeting_text = (TextView) findViewById(R.id.greeting_text);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Login.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://middleman-adb5a-default-rtdb.firebaseio.com/").getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile!=null){
                    String full_name = userProfile.full_name;
                    String email_id = userProfile.email_id;
                    String phone_number = userProfile.phone_number;

                    full_name_display.setText(full_name);
                    email_id_display.setText(email_id);
                    phone_number_display.setText(phone_number);

                    greeting_text.setText("Welcome," +full_name+ "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });
    }
}