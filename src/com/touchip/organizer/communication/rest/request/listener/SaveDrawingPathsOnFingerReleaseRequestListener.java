package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QSystem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.ATv_;

public class SaveDrawingPathsOnFingerReleaseRequestListener implements RequestListener <String> {
     // Reference to activity, for updating ui components
     protected ADrawingCompanies activity;
     private final boolean       askSave;

     public SaveDrawingPathsOnFingerReleaseRequestListener ( ADrawingCompanies act , boolean askSave ) {
          this.activity = act;
          // this.file = file;
          this.askSave = askSave;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(String response) {
          if ( askSave ) {
               QSystem.navigateToActivity(activity, ATv_.class);
          }
     }
}
