package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PopUpActivity extends AppCompatActivity {
    String username,location,contact_num,id;
    ImageButton quit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);
        Intent i = getIntent();
        username = i.getStringExtra("username");
        location = i.getStringExtra("location");
        contact_num = i.getStringExtra("contact_num");
        id = i.getStringExtra("id");


        TextView name = findViewById(R.id.pop_user_name);
        name.setText(username);
        TextView place = findViewById(R.id.pop_user_location);
        place.setText(location);
        TextView call = findViewById(R.id.pop_user_contact);
        call.setText(contact_num);

        quit = findViewById(R.id.pop_quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
