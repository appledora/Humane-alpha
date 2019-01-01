package com.thegorgeouscows.team.finalrev;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class FoodPostForm extends AppCompatActivity implements View.OnClickListener {

    private ImageButton selectPhoto;
    private Button post_button;
    private EditText production_date,expiration_date,pickup_time,quantity,pickup_address,contact_no;
    private int year,month,date,hour,minute;
    private Uri imageUri;
    private ProgressDialog progress;
    private StorageReference storageReference;
    private static final int GALLERY_REQUEST = 1;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private String currentUserID,nm,photoUri,cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_donation_post);

        production_date = (EditText)findViewById(R.id.production);
        expiration_date = (EditText)findViewById(R.id.expiration_date);
        pickup_time = (EditText)findViewById(R.id.time);
        quantity = (EditText)findViewById(R.id.quantity);
        pickup_address = (EditText)findViewById(R.id.address);
        post_button = (Button)findViewById(R.id.donate);
        selectPhoto = (ImageButton)findViewById(R.id.photoButton);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        progress = new ProgressDialog(this);

        production_date.setOnClickListener(this);
        expiration_date.setOnClickListener(this);
        pickup_time.setOnClickListener(this);
        post_button.setOnClickListener(this);
        selectPhoto.setOnClickListener(this);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                     nm = dataSnapshot.child("Name").getValue().toString();
                     cn = dataSnapshot.child("Contact").getValue().toString();
                     Log.i("my CONTACT: ",cn);
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


        if (v == production_date || v == expiration_date) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            date = c.get(Calendar.DAY_OF_MONTH);


            final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if (v == production_date) {
                                production_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                            if (v == expiration_date) {
                                expiration_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }

                        }
                    }, year, month, date);
            datePickerDialog.show();
        }

        if (v == pickup_time) {
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
        
        if (v == post_button){
            startPosting();
        }
    }

    private void startPosting() {
        progress.setMessage("posting");
        progress.show();

        final String quan = quantity.getText().toString().trim();
        final String prod = production_date.getText().toString().trim();
        final String expi = expiration_date.getText().toString().trim();
        final String pick = pickup_address.getText().toString().trim();
        final String add = pickup_address.getText().toString().trim();

        if(imageUri!= null && !TextUtils.isEmpty(quan) && !TextUtils.isEmpty(prod) && !TextUtils.isEmpty(expi) && !TextUtils.isEmpty(pick)  && !TextUtils.isEmpty(add) ){
            String randomName = UUID.randomUUID().toString();
            final StorageReference filePath = storageReference.child("post_images").child(randomName + ".jpg");

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                  Task<Uri> urlTask =  taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    String downloadUrl = urlTask.getResult().toString();
                    progress.dismiss();

                    Map<String,Object> postMap = new HashMap<>();
                    postMap.put("image_url",downloadUrl);
                    postMap.put("quantity",quan);
                    postMap.put("productiondate",prod);
                    postMap.put("expirationdate",expi);
                    postMap.put("pickuptime",pick);
                    postMap.put("address",add);
                    postMap.put("contact",cn);
                    postMap.put("userid",nm);
                    postMap.put("timestamp", FieldValue.serverTimestamp());
                    postMap.put("profilePhoto",photoUri);


                    firebaseFirestore.collection("POSTS").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FoodPostForm.this,"POST WAS ADDED",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(FoodPostForm.this,DonatorProfile.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Uri resultUri = result.getUri();
                selectPhoto.setImageURI(resultUri);
            }
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
