package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import android.app.Dialog;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogSignRegisterList_;
import com.touchip.organizer.communication.rest.model.ModelSignRegisterList;
import com.touchip.organizer.utils.Utils;

public class ResponseGetSignRegisterList implements RequestListener <ModelSignRegisterList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetSignRegisterList ( SuperActivity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          this.activity.dissmissProgressDialog();
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelSignRegisterList users) {
          this.activity.dissmissProgressDialog();
          Dialog d = Utils.getConfiguredDialog(this.activity);
          d.setContentView(CDialogSignRegisterList_.build(this.activity, users, d));
          d.show();
     }
}
