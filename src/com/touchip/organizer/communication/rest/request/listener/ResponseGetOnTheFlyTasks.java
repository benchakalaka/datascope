package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.MultiSpinner;
import com.touchip.organizer.communication.rest.model.ModelTaskList;

public class ResponseGetOnTheFlyTasks implements RequestListener <ModelTaskList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetOnTheFlyTasks ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelTaskList tasks) {
          MultiSpinner.setEntries(tasks);
          this.activity.dissmissProgressDialog();
     }
}
