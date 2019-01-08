package com.thegorgeouscows.team.finalrev;

import android.app.Application;
import com.thegorgeouscows.team.finalrev.FontsOverride;

public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/Freight.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Freight.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/Freight.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/Freight.otf");
    }
}