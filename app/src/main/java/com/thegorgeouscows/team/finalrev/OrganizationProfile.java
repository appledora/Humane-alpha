package com.thegorgeouscows.team.finalrev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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


public class OrganizationProfile extends AppCompatActivity {

    private CardView pendingVerification;
    private CardView availableTasks;
    private CardView volunteerList;
    private TextView name,email ;
    private CircleImageView profilePhoto;
    private Uri mImageUri = null,imageUri;


    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;
    StorageReference mStorageRef;
    DatabaseReference mUserDatabase;
    String uid;

    private static final int GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_ui_skull);

        pendingVerification = (CardView) findViewById(R.id.pending);
        availableTasks = (CardView)findViewById(R.id.available);
        volunteerList = (CardView)findViewById(R.id.volunteer);
        name = (TextView)findViewById(R.id.name_display_org);
        email = (TextView)findViewById(R.id.mail_display_org);
        profilePhoto = (CircleImageView)findViewById(R.id.dp_org);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nm = dataSnapshot.child("Name").getValue().toString();
                    String em = dataSnapshot.child("Email").getValue().toString();
                    Log.i("my TAG:",dataSnapshot.child("Image").getValue().toString());

                    if(dataSnapshot.child("Image").getValue().toString() != "default"){
                        String photoadd = dataSnapshot.child("Image").getValue().toString();
                        Uri photoURI = Uri.parse(photoadd);
                        Glide.with(OrganizationProfile.this).load(photoURI).into(profilePhoto);
                    }

                    name.setText(nm);
                    email.setText(em);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        pendingVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"PENDING",Toast.LENGTH_SHORT).show();

            }
        });


        availableTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"AVAILABLE",Toast.LENGTH_SHORT).show();
            }
        });

        volunteerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrganizationProfile.this,VolunteerListFeedBase.class);
                startActivity(i);
                Toast.makeText(OrganizationProfile.this,"Volunteer",Toast.LENGTH_SHORT).show();

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
        bottomNavigationView.setSelectedItemId(R.id.current_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        auth.signOut();
                        Intent intent = new Intent(OrganizationProfile.this,loginActivity.class);
                        startActivity(intent);
                    case R.id.feed:
                        Intent i = new Intent(OrganizationProfile.this,FeedBase.class);
                        startActivity(i);
                }
                return true;
            }
        });
    }
}
