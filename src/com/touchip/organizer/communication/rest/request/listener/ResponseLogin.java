package com.touchip.organizer.communication.rest.request.listener;

import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules_;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.communication.rest.model.ModelUser;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseLogin implements RequestListener <ModelUser> {

     private final SpiceFragmentActivity activity;

     public ResponseLogin ( SpiceFragmentActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException ex) {
          this.activity.dissmissProgressDialog();
          Utils.showToast(activity, R.string.connection_problem, false);
          ex.printStackTrace();
     }

     @Override public void onRequestSuccess(ModelUser modelUser) {
          this.activity.dissmissProgressDialog();
          if ( null != modelUser ) {
               GlobalConstants.CURRENT_USER = modelUser;
               Utils.showCustomToast(this.activity, "Hi " + modelUser.firstName + " " + modelUser.lastName, R.drawable.user);
               this.activity.startActivity(new Intent(this.activity, AMenuModules_.class));
          } else {
               Utils.showCustomToast(this.activity, R.string.user_not_found, R.drawable.user);
          }
     }
}