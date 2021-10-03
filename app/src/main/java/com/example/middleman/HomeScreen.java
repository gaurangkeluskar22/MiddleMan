package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity{
    private RecyclerView recyclerview_homescreen;
    private ArrayList<Property> list;
    private MyAdapter adapter;
    private DatabaseReference root = FirebaseDatabase.getInstance("https://middleman-adb5a-default-rtdb.firebaseio.com/").getReference("Properties");


    FusedLocationProviderClient fusedLocationProviderClient;
    public static double latitude;
    public static double longitude;
    public static String full_address;
    public static String locality,country_name;
    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    private ImageView profile_image;
    private TextView location_name_textview;
    private CardView sell_a_home_button;
    private CardView buy_a_home_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sell_a_home_button = (CardView) findViewById(R.id.sell_a_home_button);
        buy_a_home_button = (CardView) findViewById(R.id.buy_a_home_button);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        location_name_textview = (TextView) findViewById(R.id.location_name_textview);
        location_name_textview.setText("");

        recyclerview_homescreen = (RecyclerView) findViewById(R.id.recyclerview_homescreen);
        recyclerview_homescreen.setHasFixedSize(true);
        recyclerview_homescreen.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter(this,list);
        recyclerview_homescreen.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Property property = dataSnapshot1.getValue(Property.class);
                    list.add(property);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sell_a_home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,SellHome.class));
            }
        });

        buy_a_home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,BuyHome.class));
            }
        });

        // profile image section
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,Profile.class));
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
                    Picasso.with(getApplicationContext()).load(userProfile.profile_image_url).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeScreen.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });

    }


    protected void onStart(){
        super.onStart();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission granted getlocation()
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    // Initialized location
                    Location location = task.getResult();
                    if(location!=null){
                        try {
                            Geocoder geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            latitude = addresses.get(0).getLatitude();
                            longitude = addresses.get(0).getLongitude();
                            full_address = addresses.get(0).getAddressLine(0);
                            locality = addresses.get(0).getLocality();
                            country_name = addresses.get(0).getCountryName();
                            location_name_textview.setText(locality);

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                }
            });

        } else {
            ActivityCompat.requestPermissions(HomeScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }
}