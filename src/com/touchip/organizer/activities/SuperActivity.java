package com.touchip.organizer.activities;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.request.SuperRequest;

/**
 * Parent FragmentActivity to any class which extends FragmentActivity in DataVision app and communicates via REST services
 * 
 * @author Karpachev Ihor
 */
public class SuperActivity extends FragmentActivity {

     // Spice manager
     private final SpiceManager   spiceManager = new SpiceManager(com.octo.android.robospice.JacksonSpringAndroidSpiceService.class);
     // Progress dialog for displaying progress
     public static ProgressDialog progressDialog;

     @Override protected void onResume() {
          super.onResume();
          progressDialog = new ProgressDialog(SuperActivity.this);
          progressDialog.setCancelable(true);
          progressDialog.setMessage(getResources().getString(R.string.loading));
     };

     /**
      * Execute request to server
      * 
      * @param request
      *             request object {@link SuperActivity}
      * @param response
      *             process response
      */
     @SuppressWarnings ( "unchecked" ) public void execute(@SuppressWarnings ( "rawtypes" ) SuperRequest request, RequestListener response) {
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, response);
     }

     public ProgressDialog getProgressDialog() {
          return progressDialog;
     }

     public void showProgressDialog() {
          if ( null != progressDialog ) {
               progressDialog.show();
          }
     }

     public void dissmissProgressDialog() {
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
