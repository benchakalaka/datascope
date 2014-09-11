package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules_;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.communication.rest.model.User;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class LoginRequestListener implements RequestListener <User> {

     private final Activity activity;

     public LoginRequestListener ( Activity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException ex) {
          ex.printStackTrace();
          Utils.showToast(activity, R.string.connection_problem, false);
     }

     @Override public void onRequestSuccess(User user) {
          SpiceFragmentActivity.dissmissProgressDialog();
          if ( null != user ) {
               GlobalConstants.CURRENT_USER = user;
               Utils.showCustomToast(this.activity, "Hi " + user.firstName + " " + user.lastName, R.drawable.user);
               this.activity.startActivity(new Intent(this.activity, AMenuModules_.class));
          } else {
               Utils.showCustomToast(this.activity, R.string.user_not_found, R.drawable.user);
          }

     }
}
