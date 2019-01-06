package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VolunteerListFeedBase extends AppCompatActivity {
    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_list_feed_base);
        addBtn = findViewById(R.id.add_vol_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerListFeedBase.this, registerVolunteerActivity.class);
                startActivity(intent);
            }
        });
    }
}
