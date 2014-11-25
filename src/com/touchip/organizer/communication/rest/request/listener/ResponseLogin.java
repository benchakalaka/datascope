package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import quickutils.core.QUFactory.QSystem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelUserList.ModelUser;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseLogin implements RequestListener <ModelUser> {

     private final SuperActivity activity;

     public ResponseLogin ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException ex) {
          QLog.warning(ex.getMessage(), ex);
          QNotifications.showShortToast(activity, R.string.connection_problem);
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(ModelUser modelUser) {
          this.activity.dissmissProgressDialog();
          if ( !QPreconditions.isNull(modelUser) ) {
               GlobalConstants.CURRENT_USER = modelUser;
               Utils.showCustomToast(this.activity, "Hi " + modelUser.firstName + " " + modelUser.lastName, R.drawable.success);
               QSystem.navigateToActivity(this.activity, AMenuModules_.class);
          } else {
               Utils.showCustomToast(this.activity, R.string.user_not_found, R.drawable.trade);
          }
     }
}