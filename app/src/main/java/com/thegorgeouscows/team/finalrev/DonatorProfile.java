package com.thegorgeouscows.team.finalrev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonatorProfile extends AppCompatActivity {
    private TextView name ;
    private TextView email;
    private TextView contact;
    private Button butt;
    private Uri mImageUri = null,imageUri;

    DatabaseReference ref,mDatabase;
    FirebaseAuth auth;


    String uid;
    CircleImageView profilePhoto;
    FirebaseStorage storage;
    StorageReference storageReference,mStorageRef,mStorageImage;
    DatabaseReference mUserDatabase;

    ProgressDialog mProgress;
    FirebaseAuth mAuth;

    private static final int GALLERY_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.donator_ui_skull);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mStorageImage = storage.getReference().child("profile_images");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mStorageRef = FirebaseStorage.getInstance().getReference();



        name = (TextView)findViewById(R.id.name_display);
        email = (TextView)findViewById(R.id.mail_display);
        contact = (TextView) findViewById(R.id.contact_display);
        butt = (Button)findViewById(R.id.start_donation);
        profilePhoto = (CircleImageView) findViewById(R.id.main_dp);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nm = dataSnapshot.child("Name").getValue().toString();
                    String em = dataSnapshot.child("Email").getValue(String.class);
                    String cn = dataSnapshot.child("Contact").getValue().toString();
                    if(dataSnapshot.child("Image").getValue().toString() != "default"){
                        String photoadd = dataSnapshot.child("Image").getValue().toString();
                        Uri photoURI = Uri.parse(photoadd);
                        Glide.with(DonatorProfile.this).load(photoURI).into(profilePhoto);
                    }


                    name.setText(nm);
                    email.setText(em);
                    contact.setText(cn);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DonatorProfile.this, SlideActivity.class);
                startActivity(intent);
            }
        });
        setupBottomNavigationView();

        }

    private void selectPhoto() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                mImageUri = result.getUri();
                Glide.with(this).load(mImageUri).into(profilePhoto);
                uploadImage();
            }
        }
    }

    private void uploadImage() {

            if(mImageUri != null){
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

            final StorageReference photref = mStorageRef.child(imageUri.getLastPathSegment());
            photref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mUserDatabase.child("Image").setValue(uri.toString());

                        }
                    });
                }
            });




            }

    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        auth.signOut();
                        Intent intent = new Intent(DonatorProfile.this,loginActivity.class);
                        startActivity(intent);
                    case R.id.current_profile:
                        Toast.makeText(DonatorProfile.this,"HOME",Toast.LENGTH_SHORT).show();
                    case R.id.feed:

                        Intent i = new Intent(DonatorProfile.this,FeedBase.class);
                        startActivity(i);
                }
                return true;
            }
        });
    }
}




