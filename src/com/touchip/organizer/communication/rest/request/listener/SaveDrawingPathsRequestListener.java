package com.touchip.organizer.communication.rest.request.listener;

import java.io.File;

import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.TvActivity_;
import com.touchip.organizer.utils.Utils;

public class SaveDrawingPathsRequestListener implements RequestListener <String> {
     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity activity;
     private final File                 file;
     private final boolean              askSave;

     public SaveDrawingPathsRequestListener ( DrawingCompaniesActivity act , File file , boolean askSave ) {
          this.activity = act;
          this.file = file;
          this.askSave = askSave;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          file.delete();
          // DrawingCompaniesActivity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(String response) {
          file.delete();
          if ( "-1".equals(response) ) {
               Utils.showCustomToast(activity, R.string.uploading_drawings_failed, R.drawable.hide_hotspot);
          } else {
               Utils.showToast(activity, R.string.site_plan_uploaded_to_server, true);
          }
          // DrawingCompaniesActivity.dissmissProgressDialog();
          if ( askSave ) {
               activity.startActivity(new Intent(activity, TvActivity_.class));
          }
     }
}
