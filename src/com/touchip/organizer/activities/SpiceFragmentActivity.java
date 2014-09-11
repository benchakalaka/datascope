package com.touchip.organizer.activities;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.octo.android.robospice.SpiceManager;
import com.squareup.timessquare.sample.R;

/**
 * Parent FragmentActivity to any class which extends FragmentActivity in DataVision app and communicates via REST services
 * 
 * @author Karpachev Ihor
 */
public class SpiceFragmentActivity extends FragmentActivity {

     // Spice manager
     private final SpiceManager    spiceManager = new SpiceManager(com.octo.android.robospice.JacksonSpringAndroidSpiceService.class);
     // Progress dialog for displaying progress
     private static ProgressDialog progressDialog;

     @Override protected void onResume() {
          super.onResume();
          progressDialog = new ProgressDialog(SpiceFragmentActivity.this);
          progressDialog.setCancelable(true);
          progressDialog.setMessage(getResources().getString(R.string.loading));
     };

     public static ProgressDialog getProgressDialog() {
          return progressDialog;
     }

     public static void showProgressDialog() {
          if ( null != progressDialog ) {
               progressDialog.show();
          }
     }

     public static void dissmissProgressDialog() {
          if ( null != progressDialog ) {
               progressDialog.dismiss();
          }
     }

     /**
      * Start manager on activity start
      */
     @Override protected void onStart() {
          spiceManager.start(this);
          super.onStart();
     }

     /**
      * Stop manager on activity stop
      */
     @Override protected void onStop() {
          spiceManager.shouldStop();
          super.onStop();
     }

     /**
      * Getter for spice manager to any class which extends current class
      * 
      * @return SpiceManager
      */
     public SpiceManager getSpiceManager() {
          return spiceManager;
     }
}
