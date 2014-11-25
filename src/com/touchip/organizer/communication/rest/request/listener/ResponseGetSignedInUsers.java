package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import android.app.Dialog;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogSignRegister_;
import com.touchip.organizer.communication.rest.model.ModelUserList;
import com.touchip.organizer.utils.Utils;

public class ResponseGetSignedInUsers implements RequestListener <ModelUserList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;
     private final String    time;

     // Constructor that accept activity
     public ResponseGetSignedInUsers ( SuperActivity act , String time ) {
          this.activity = act;
          this.time = time;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          this.activity.dissmissProgressDialog();
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelUserList users) {
          this.activity.dissmissProgressDialog();

          if ( null != users && !users.isEmpty() ) {
               Dialog d = Utils.getConfiguredDialog(this.activity);
               d.setContentView(CDialogSignRegister_.build(this.activity, users, d, time));
               d.show();
          } else {
               Utils.showCustomToast(activity, "There are no registered users", R.drawable.hide_hotspot);
          }
     }
}
