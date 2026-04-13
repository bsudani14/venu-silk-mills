package com.newtech.vplus.util;

import android.app.Application;

public class OverRideFont_util extends Application {
    @Override
    public void onCreate() {
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/josefinsans_regular.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/josefinsans_regular.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/josefinsans_regular.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/josefinsans_regular.ttf");
        super.onCreate();
    }
}
