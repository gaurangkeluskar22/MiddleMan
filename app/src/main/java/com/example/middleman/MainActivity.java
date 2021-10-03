package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();


//        Button first = findViewById(R.id.first);
//        Button second = findViewById(R.id.second);
//        Button third = findViewById(R.id.third);
//        Button fourth = findViewById(R.id.fourth);
//        Button signup = findViewById(R.id.signup_screen);
//
//        first.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent first_intent = new Intent(MainActivity.this, Get_started.class);
//                startActivity(first_intent);
//            }
//        });
//
//        second.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent second_intent = new Intent(MainActivity.this, Login.class);
//                startActivity(second_intent);
//            }
//        });
//
//        third.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent third_intent = new Intent(MainActivity.this, HomeScreen.class);
//                startActivity(third_intent);
//            }
//        });
//
//        fourth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent fourth_intent = new Intent(MainActivity.this, Detail.class);
//                startActivity(fourth_intent);
//            }
//        });
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signup_intent = new Intent(MainActivity.this, signup.class);
//                startActivity(signup_intent);
//            }
//        });

    }


    protected void onStart(){
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser!=null){
            startActivity(new Intent(MainActivity.this,HomeScreen.class));
        }
        else{
            startActivity(new Intent(MainActivity.this,Get_started.class));
        }
    }
}