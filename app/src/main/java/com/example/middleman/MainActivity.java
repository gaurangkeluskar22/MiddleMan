package com.example.middleman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button first = findViewById(R.id.first);
        Button second = findViewById(R.id.second);
        Button third = findViewById(R.id.third);
        Button fourth = findViewById(R.id.fourth);
        Button signup = findViewById(R.id.signup_screen);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_intent = new Intent(MainActivity.this, Get_started.class);
                startActivity(first_intent);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent second_intent = new Intent(MainActivity.this, Login.class);
                startActivity(second_intent);
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent third_intent = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(third_intent);
            }
        });

        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fourth_intent = new Intent(MainActivity.this, Detail.class);
                startActivity(fourth_intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_intent = new Intent(MainActivity.this, signup.class);
                startActivity(signup_intent);
            }
        });

    }
}