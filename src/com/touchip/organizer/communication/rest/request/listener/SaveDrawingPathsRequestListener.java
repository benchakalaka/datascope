package com.touchip.organizer.communication.rest.request.listener;

import java.io.File;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QSystem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.ATv_;
import com.touchip.organizer.utils.Utils;

public class SaveDrawingPathsRequestListener implements RequestListener <String> {
     // Reference to activity, for updating ui components
     protected ADrawingCompanies activity;
     private final File          file;
     private final boolean       askSave;

     public SaveDrawingPathsRequestListener ( ADrawingCompanies act , File file , boolean askSave ) {
          this.activity = act;
          this.file = file;
          this.askSave = askSave;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
          file.delete();
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(String response) {
          file.delete();
          if ( "-1".equals(response) ) {
               Utils.showCustomToast(activity, R.string.uploading_drawings_failed, R.drawable.hide_hotspot);
          } else {
               QNotifications.showShortToast(activity, R.string.site_plan_uploaded_to_server);
          }
          // ADrawingCompanies.dissmissProgressDialog();
          if ( askSave ) {
               QSystem.navigateToActivity(activity, ATv_.class);
          }
     }
}
