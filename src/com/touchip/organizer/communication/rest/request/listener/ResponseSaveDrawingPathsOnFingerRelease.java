package com.touchip.organizer.communication.rest.request.listener;

import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.TvActivity_;
import com.touchip.organizer.communication.rest.model.ModelPathId;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseSaveDrawingPathsOnFingerRelease implements RequestListener <ModelPathId> {
     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity activity;
     // private final File file;
     private final boolean              askSave;

     public ResponseSaveDrawingPathsOnFingerRelease ( DrawingCompaniesActivity act , boolean askSave ) {
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
     @Override public void onRequestSuccess(ModelPathId response) {
          if ( null != response ) {
               GlobalConstants.SITE_PLAN_FULL_INFO.pathId = response.pathId;
          }
          // file.delete();
          if ( askSave ) {
               activity.startActivity(new Intent(activity, TvActivity_.class));
          }
     }
}
