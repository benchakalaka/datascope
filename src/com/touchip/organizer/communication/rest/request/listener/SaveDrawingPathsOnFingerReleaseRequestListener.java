package com.touchip.organizer.communication.rest.request.listener;

import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.TvActivity_;
import com.touchip.organizer.utils.Utils;

public class SaveDrawingPathsOnFingerReleaseRequestListener implements RequestListener <String> {
     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity activity;
     // private final File file;
     private final boolean              askSave;

     public SaveDrawingPathsOnFingerReleaseRequestListener ( DrawingCompaniesActivity act , boolean askSave ) {
          this.activity = act;
          // this.file = file;
          this.askSave = askSave;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          // file.delete();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(String response) {
          // file.delete();
          if ( askSave ) {
               activity.startActivity(new Intent(activity, TvActivity_.class));
          }
     }
}
