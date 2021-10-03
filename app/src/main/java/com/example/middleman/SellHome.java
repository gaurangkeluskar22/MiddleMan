package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class SellHome extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    Property property;
    private String randomKey;
    private StorageReference storageReference;
    FirebaseStorage storage;

    private Double latitude = HomeScreen.latitude;
    private Double longitude = HomeScreen.longitude;
    private String property_locality = HomeScreen.locality;
    private String property_address = HomeScreen.full_address;
    private String property_name,property_email_id,property_BHK,property_area,property_price,property_owner_name,property_phone_number;

    private String upload_first_image_uri,upload_second_image_uri,upload_third_image_uri,upload_fourth_image_uri,upload_front_image_uri;

    private TextInputEditText property_name_textinputedittext,property_BHK_textinputedittext,property_area_textinputedittext,property_price_textinputedittext,property_address_textinputedittext,property_owner_name_textinputedittext,property_phone_number_textinputedittext,property_locality_textinputedittext;
    private ImageView upload_front_image_imageview;
    private Button upload_first_image_button,upload_second_image_button,upload_third_image_button,upload_fourth_image_button,add_property_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_home);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        property_name_textinputedittext = (TextInputEditText) findViewById(R.id.property_name_textinputedittext);
        property_BHK_textinputedittext = (TextInputEditText) findViewById(R.id.property_BHK_textinputedittext);
        property_area_textinputedittext = (TextInputEditText) findViewById(R.id.property_area_textinputedittext);
        property_price_textinputedittext = (TextInputEditText) findViewById(R.id.property_price_textinputedittext);
        property_address_textinputedittext = (TextInputEditText) findViewById(R.id.property_address_textinputedittext);
        property_owner_name_textinputedittext = (TextInputEditText) findViewById(R.id.property_owner_name_textinputedittext);
        property_phone_number_textinputedittext = (TextInputEditText) findViewById(R.id.property_phone_number_textinputedittext);
        property_locality_textinputedittext = (TextInputEditText) findViewById(R.id.property_locality_textinputedittext);
        upload_front_image_imageview = (ImageView) findViewById(R.id.upload_front_image_imageview);
        upload_first_image_button = (Button) findViewById(R.id.upload_first_image_button);
        upload_second_image_button = (Button) findViewById(R.id.upload_second_image_button);
        upload_third_image_button = (Button) findViewById(R.id.upload_first_third_button);
        upload_fourth_image_button = (Button) findViewById(R.id.aupload_first_fourth_button);
        add_property_button = (Button) findViewById(R.id.add_property_button);

        upload_front_image_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SellHome.this).crop().compress(1024).maxResultSize(1080,1080).start(1);
            }
        });


        upload_first_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SellHome.this).crop().compress(1024).maxResultSize(1080,1080).start(2);
            }
        });

        upload_second_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SellHome.this).crop().compress(1024).maxResultSize(1080,1080).start(3);
            }
        });

        upload_third_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SellHome.this).crop().compress(1024).maxResultSize(1080,1080).start(4);
            }
        });

        upload_fourth_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SellHome.this).crop().compress(1024).maxResultSize(1080,1080).start(5);
            }
        });


        add_property_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomKey = UUID.randomUUID().toString();

                property_name = property_name_textinputedittext.getText().toString();
                property_owner_name = property_owner_name_textinputedittext.getText().toString();
                property_BHK = property_BHK_textinputedittext.getText().toString();
                property_area = property_area_textinputedittext.getText().toString();
                property_price = property_price_textinputedittext.getText().toString();
                property_address = property_address_textinputedittext.getText().toString();
                property_owner_name = property_owner_name_textinputedittext.getText().toString();
                property_phone_number = property_phone_number_textinputedittext.getText().toString();
                property_locality = property_locality_textinputedittext.getText().toString();

                property = new Property(randomKey.toString(),userID,property_name,property_email_id,property_BHK,property_area,property_price,property_address,property_owner_name,property_locality,property_phone_number,latitude,longitude,upload_front_image_uri,upload_first_image_uri,upload_second_image_uri,upload_third_image_uri,upload_fourth_image_uri);

                FirebaseDatabase.getInstance("https://middleman-adb5a-default-rtdb.firebaseio.com/").getReference("Properties").child(randomKey).setValue(property).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SellHome.this,"Property Added successfully!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SellHome.this,HomeScreen.class));
                        }else{
                            Toast.makeText(SellHome.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri imageUri = data.getData();
            upload_front_image_imageview.setImageURI(imageUri);
            uploadPicture(imageUri,"front_main_image");
        }
        if(requestCode==2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri imageUri = data.getData();
            uploadPicture(imageUri,"image_1");
        }
        if(requestCode==3 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri imageUri = data.getData();
            uploadPicture(imageUri,"image_2");
        }
        if(requestCode==4 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri imageUri = data.getData();
            uploadPicture(imageUri,"image_3");
        }
        if(requestCode==5 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri imageUri = data.getData();
            uploadPicture(imageUri,"image_4");
        }
    }

    private void uploadPicture(Uri imageUri,String upload_image_tag_name) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey_for_image_name = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+randomKey_for_image_name);
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(upload_image_tag_name.equals("front_main_image")){
                            upload_front_image_uri=uri.toString();
                        }
                        if(upload_image_tag_name.equals("image_1")){
                            upload_first_image_uri=uri.toString();
                        }
                        if(upload_image_tag_name.equals("image_2")){
                            upload_second_image_uri=uri.toString();
                        }
                        if(upload_image_tag_name.equals("image_3")){
                            upload_third_image_uri=uri.toString();
                        }
                        if(upload_image_tag_name.equals("image_4")){
                            upload_fourth_image_uri=uri.toString();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed To Upload...",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage:" + (int) progressPercent + "%");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        display_fetched_data();
    }

    protected void display_fetched_data(){
        // Display data fetched from firebase in the TextInputEditText
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://middleman-adb5a-default-rtdb.firebaseio.com/").getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile!=null){
                    property_owner_name = userProfile.full_name;
                    property_phone_number = userProfile.phone_number;
                    property_email_id = userProfile.email_id;
                    property_owner_name_textinputedittext.setText(property_owner_name);
                    property_phone_number_textinputedittext.setText(property_phone_number);
                    property_address_textinputedittext.setText(HomeScreen.full_address);
                    property_locality_textinputedittext.setText(HomeScreen.locality);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SellHome.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });
    }
}