package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.utils.Utils;

public class UploadCapturedPhotoRequestListener implements RequestListener <String> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity_ activity;

     // Constructor that accept activity
     public UploadCapturedPhotoRequestListener ( Activity act ) {
          activity = (DrawingCompaniesActivity_) act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(String response) {
          if ( "-1".equals(response) ) {
               Utils.showToast(activity, R.string.connection_problem, true);
               return;
          }
          Utils.showCustomToast(activity, R.string.photo_successfully_uploaded, R.drawable.server2);
     }
}
