package com.touchip.organizer.activities;

import android.support.v4.app.FragmentActivity;

import com.octo.android.robospice.SpiceManager;

public class SpiceFragmentActivity extends FragmentActivity {

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
