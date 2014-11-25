package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;

public class UploadCapturedPhotoRequestListener implements RequestListener <String> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public UploadCapturedPhotoRequestListener ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage(), e);
          QNotifications.showShortToast(activity, R.string.connection_problem);
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(String response) {
          if ( "-1".equals(response) ) {
               QNotifications.showShortToast(activity, R.string.connection_problem);
               return;
          }
          Utils.showCustomToast(activity, R.string.photo_successfully_uploaded, R.drawable.success);
     }
}
