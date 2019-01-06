package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopUpActivity extends AppCompatActivity {
    String username,location,contact_num,id,dp_link;
     Button quit;
    CircleImageView dp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        location = i.getStringExtra("location");
        contact_num = i.getStringExtra("contact_num");
        id = i.getStringExtra("id");
        dp_link = i.getStringExtra("dp");



        TextView name = findViewById(R.id.pop_user_name);
        name.setText(username);
        TextView place = findViewById(R.id.pop_user_location);
        place.setText(location);
        TextView call = findViewById(R.id.pop_user_contact);
        call.setText(contact_num);
        dp = findViewById(R.id.main_dp);
        Glide.with(this).load(dp_link).into(dp);

        quit = findViewById(R.id.pop_quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
