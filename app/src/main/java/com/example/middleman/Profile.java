package com.example.middleman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

import org.w3c.dom.Text;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private String userID;
    private Button profile_logout_button, profile_update_button;
    private TextView full_name_display_textview,change_profile_textview;
    private TextInputEditText profile_full_name_textinputedittext,profile_address_textinputedittext,profile_email_id_textinputedittext,profile_phone_number_textinputedittext;
    private CircleImageView profile_image_display;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        full_name_display_textview = (TextView) findViewById(R.id.full_name_display_textview);
        change_profile_textview = (TextView) findViewById(R.id.change_profile_textview);
        profile_email_id_textinputedittext = (TextInputEditText) findViewById(R.id.profile_email_id_textinputedittext);
        profile_full_name_textinputedittext = (TextInputEditText) findViewById(R.id.profile_full_name_textinputedittext);
        profile_phone_number_textinputedittext = (TextInputEditText) findViewById(R.id.profile_phone_number_textinputedittext);
        profile_address_textinputedittext = (TextInputEditText) findViewById(R.id.profile_address_textinputedittext); 
        profile_logout_button = (Button) findViewById(R.id.profile_logout_button);
        profile_update_button = (Button) findViewById(R.id.profile_update_button);
        profile_image_display = (CircleImageView) findViewById(R.id.profile_image_display);

        // Logout Button
        profile_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Login.class));
            }
        });

        // Update profile image
        change_profile_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Profile.this).crop().compress(1024).maxResultSize(1080,1080).start(1);
            }
        });

        // Update Button
        profile_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userID).child("full_name").setValue(profile_full_name_textinputedittext.getText().toString());
                reference.child(userID).child("phone_number").setValue(profile_phone_number_textinputedittext.getText().toString());
                reference.child(userID).child("user_address").setValue(profile_address_textinputedittext.getText().toString());
                Toast.makeText(getApplicationContext(),"Profile Updated Successfully!!",Toast.LENGTH_LONG).show();
                display_fetched_data();
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
                    String full_name = userProfile.full_name;
                    String email_id = userProfile.email_id;
                    String phone_number = userProfile.phone_number;

                    full_name_display_textview.setText(full_name);
                    profile_full_name_textinputedittext.setText(full_name);
                    profile_email_id_textinputedittext.setText(email_id);
                    profile_phone_number_textinputedittext.setText(phone_number);
                    profile_address_textinputedittext.setText(HomeScreen.full_address);
                    Picasso.with(getApplicationContext()).load(userProfile.profile_image_url).into(profile_image_display);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profile_image_display.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+randomKey);
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        reference.child(userID).child("profile_image_url").setValue(uri.toString());
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
}