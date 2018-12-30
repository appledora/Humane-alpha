package com.thegorgeouscows.team.finalrev;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideActivity extends AppCompatActivity {
private ViewPager slideViewPager;
private LinearLayout dotLayout;
private  SliderAdapter sliderAdapter;
private TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        slideViewPager = (ViewPager)findViewById(R.id.slideviewpager);
        sliderAdapter = new SliderAdapter(this);

        slideViewPager.setAdapter(sliderAdapter);

    }


}
