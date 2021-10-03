package com.example.middleman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Detail extends AppCompatActivity implements PaymentResultListener, OnMapReadyCallback {
    String userID,property_address,property_owner_name,property_locality,property_area,property_price,property_BHK,property_email_id,property_name,property_phone_number,upload_first_image_uri,upload_fourth_image_uri,upload_second_image_uri,upload_third_image_uri,upload_front_image_uri;
    private MaterialButton back_button,book_now_button;
    private ShapeableImageView property_front_main_image_imageview_display,upload_first_image_shapeableimageview_display,upload_second_image_shapeableimageview_display,upload_first_third_shapeableimageview_display,upload_first_fourth_shapeableimageview_display;
    private TextView getProperty_bath_textview_display,property_name_textview_display,property_price_textview_display,property_area_textview_display,property_BHK_textview_display,property_address_textview_display,property_owner_name_textview_display,property_email_id_textview_display,property_phone_number_textview_display;
    GoogleMap map;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back_button=findViewById(R.id.back_button);
        book_now_button=findViewById(R.id.book_now_button);
        property_front_main_image_imageview_display=findViewById(R.id.property_front_main_image_imageview_display);
        upload_first_image_shapeableimageview_display=findViewById(R.id.upload_first_image_shapeableimageview_display);
        upload_second_image_shapeableimageview_display=findViewById(R.id.upload_second_image_shapeableimageview);
        upload_first_third_shapeableimageview_display=findViewById(R.id.upload_first_third_shapeableimageview);
        upload_first_fourth_shapeableimageview_display=findViewById(R.id.aupload_first_fourth_shapeableimageview);
        property_name_textview_display=findViewById(R.id.property_name_textview_display);
        property_price_textview_display=findViewById(R.id.property_price_textview_display);
        property_area_textview_display=findViewById(R.id.property_area_textview_display);
        property_BHK_textview_display=findViewById(R.id.property_BHK_textview_display);
        property_price_textview_display=findViewById(R.id.property_price_textview_display);
        property_address_textview_display=findViewById(R.id.property_address_textview_display);
        property_owner_name_textview_display=findViewById(R.id.property_owner_name_textview_display);
        property_email_id_textview_display=findViewById(R.id.property_email_id_textview_display);
        property_phone_number_textview_display=findViewById(R.id.property_phone_number_textview_display);
        getProperty_bath_textview_display=findViewById(R.id.property_bath_textview_display);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude=extras.getString("latitude");
            longitude = extras.getString("longitude");
            property_address=extras.getString("property_address");
            property_area=extras.getString("property_area");
            property_BHK=extras.getString("property_BHK");
            property_email_id=extras.getString("property_email_id");
            property_locality=extras.getString("property_locality");
            property_name=extras.getString("property_name");
            property_owner_name=extras.getString("property_owner_name");
            property_phone_number=extras.getString("property_phone_number");
            property_price=extras.getString("property_price");
            upload_first_image_uri=extras.getString("upload_first_image_uri");
            upload_fourth_image_uri=extras.getString("upload_fourth_image_url");
            upload_second_image_uri=extras.getString("upload_second_image_url");
            upload_front_image_uri=extras.getString("upload_front_image_url");
            upload_third_image_uri=extras.getString("upload_third_image_url");
            userID=extras.getString("userID");
        }


        Picasso.with(getApplicationContext()).load(upload_first_image_uri).into(upload_first_image_shapeableimageview_display);
        Picasso.with(getApplicationContext()).load(upload_second_image_uri).into(upload_second_image_shapeableimageview_display);
        Picasso.with(getApplicationContext()).load(upload_third_image_uri).into(upload_first_third_shapeableimageview_display);
        Picasso.with(getApplicationContext()).load(upload_front_image_uri).into(property_front_main_image_imageview_display);
        Picasso.with(getApplicationContext()).load(upload_fourth_image_uri).into(upload_first_fourth_shapeableimageview_display);

        property_name_textview_display.setText(property_name);
        property_area_textview_display.setText(" "+property_area+" m Area");
        property_BHK_textview_display.setText(" "+property_BHK+" Beds");
        getProperty_bath_textview_display.setText(" "+property_BHK+" Baths");
        property_price_textview_display.setText("₹ "+property_price);
        property_address_textview_display.setText(property_address);
        property_owner_name_textview_display.setText(property_owner_name);
        property_email_id_textview_display.setText(property_email_id);
        property_phone_number_textview_display.setText(property_phone_number);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Detail.super.onBackPressed();
            }
        });
        int amount = Math.round(Float.parseFloat(property_price)*100);
        book_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_iAnqhVzGcIJ33e");
                checkout.setImage(R.drawable.razorpay_logo);
                JSONObject object = new JSONObject();
                try {
                    object.put("name","MIDDLEMAN");
                    object.put("description","Payment");
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",amount);
                    object.put("prefill.contact",property_phone_number);
                    object.put("prefill.email",property_email_id);
                    checkout.open(Detail.this,object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng property_location = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        map.addMarker(new MarkerOptions().position(property_location).title(property_address));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(property_location,10));
    }


}