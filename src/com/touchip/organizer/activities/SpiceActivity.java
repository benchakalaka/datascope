package com.touchip.organizer.activities;

import android.app.Activity;

import com.octo.android.robospice.SpiceManager;

public class SpiceActivity extends Activity {

     private final SpiceManager spiceManager = new SpiceManager(com.octo.android.robospice.JacksonSpringAndroidSpiceService.class);

     @Override protected void onStart() {
          spiceManager.start(this);
          super.onStart();
     }

     @Override protected void onStop() {
          spiceManager.shouldStop();
          super.onStop();

     }

     public SpiceManager getSpiceManager() {
          return spiceManager;
     }

}
