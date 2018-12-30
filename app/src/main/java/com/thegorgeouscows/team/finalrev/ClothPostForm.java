package com.thegorgeouscows.team.finalrev;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ClothPostForm extends AppCompatActivity implements View.OnClickListener {

    private EditText pickup_time,quantity,pickup_address,contact_no,pickup_date;
    private int year,month,date,hour,minute;
    private Button post_button;
    private ImageButton selectPhoto;
    private Uri postImageUri;
    private String currentUserID,nm,photoUri;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloth_donation_post);

        pickup_time = (EditText)findViewById(R.id.time_cloth);
        quantity = (EditText)findViewById(R.id.quantity_cloth);
        pickup_address = (EditText)findViewById(R.id.address_cloth);
        contact_no = (EditText)findViewById(R.id.contact_number_cloth);
        post_button = (Button)findViewById(R.id.donate_cloth);
        pickup_date = (EditText)findViewById(R.id.pickup_date_cloth);
        selectPhoto = (ImageButton)findViewById(R.id.photoButton_cloth);

        storageReference =FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        pickup_date.setOnClickListener(this);
        pickup_time.setOnClickListener(this);
        post_button.setOnClickListener(this);
        selectPhoto.setOnClickListener(this);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    nm = dataSnapshot.child("Name").getValue().toString();
                    photoUri = dataSnapshot.child("Image").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onClick(final View v) {


        if(v == pickup_date){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            date = c.get(Calendar.DAY_OF_MONTH);


            final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                                pickup_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                        }
                    }, year, month, date);
            datePickerDialog.show();
        }

        if(v == pickup_time){
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            pickup_time.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }
        if (v == selectPhoto){
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_REQUEST);
        }
        if(v == post_button){
            startClothPosting();

        } }

    private void startClothPosting() {
        Log.i("my","post button pressed");
        final String quan = quantity.getText().toString().trim();
        final String pickd = pickup_date.getText().toString().trim();
        final String pick = pickup_address.getText().toString().trim();
        final String cont = contact_no.getText().toString().trim();
        final String add = pickup_address.getText().toString().trim();

        if(postImageUri!= null && !TextUtils.isEmpty(quan) && !TextUtils.isEmpty(pickd)  && !TextUtils.isEmpty(pick) && !TextUtils.isEmpty(cont) && !TextUtils.isEmpty(add) ){
            //postProgress.setVisibility(View.VISIBLE);
            Log.i("my", "nothing is empty");
            String randomName = UUID.randomUUID().toString();
            final StorageReference filePath = storageReference.child("cloth_images").child(randomName + ".jpg");
            filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Log.i("my","putting file?");


                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            Log.i("my","got through downloadUri");
                            final String downloadUri = uri.toString();
                            Map<String,Object> postMap = new HashMap<>();
                            postMap.put("cimage_url",downloadUri);
                            postMap.put("cquantity",quan);
                            postMap.put("cpickupdate",pickd);
                            postMap.put("cpickuptime",pick);
                            postMap.put("caddress",add);
                            postMap.put("ccontact",cont);
                            postMap.put("cuserid",nm);
                            postMap.put("cprofilePhoto",photoUri);
                            postMap.put("ctimestamp", FieldValue.serverTimestamp());


                            firebaseFirestore.collection("ClothPosts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ClothPostForm.this,"POST WAS ADDED",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ClothPostForm.this,DonatorProfile.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        //postProgress.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ClothPostForm.this,"POST WAS NOOOOT ADDED",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //postProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });
        }

        else{
            Toast.makeText(ClothPostForm.this,"POST WAS NOOOOT ADDED",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            postImageUri = data.getData();
            CropImage.activity(postImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                Glide.with(ClothPostForm.this).load(resultUri).into(selectPhoto);
            }
        }

    }
}

