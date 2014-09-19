package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class SaveHSWhiteboardRequestListener implements RequestListener <POJORoboPathCreationTime> {

     private final GeneralWhiteBoardActivity activity;

     public SaveHSWhiteboardRequestListener ( Activity act ) {
          this.activity = (GeneralWhiteBoardActivity) act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          GeneralWhiteBoardActivity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(POJORoboPathCreationTime pathData) {
          GeneralWhiteBoardActivity.dissmissProgressDialog();
          if ( null != pathData ) {
               try {
                    GlobalConstants.LAST_CLICKED_WHITE_BOARD = pathData;
                    Utils.showCustomToast(activity, "Drawing has been loaded successfully!", R.drawable.server2);
                    this.activity.customActionBar.setTimeCreatedText(GlobalConstants.SITE_PLAN_IMAGE_NAME + " created at " + pathData.time);
               } catch (Exception e) {
                    Utils.logw(e.getMessage());
               }
          }
     }
}