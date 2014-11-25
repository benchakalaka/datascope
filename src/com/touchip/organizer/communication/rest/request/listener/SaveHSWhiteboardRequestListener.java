package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class SaveHSWhiteboardRequestListener implements RequestListener <String> {

     private final SuperActivity activity;

     public SaveHSWhiteboardRequestListener ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(String fileData) {
          this.activity.dissmissProgressDialog();
          if ( "".equals(fileData) ) {
               Utils.showCustomToast(activity, "Problem with downloading drawing", R.drawable.hide_hotspot);
          } else {
               try {
                    POJORoboPathCreationTime dptw = new POJORoboPathCreationTime();
                    dptw.id = Integer.valueOf(fileData.substring(0, fileData.indexOf(" ")));
                    dptw.time = fileData.substring(fileData.indexOf(" ") + 1, fileData.length());
                    GlobalConstants.LAST_CLICKED_WHITE_BOARD = dptw;
                    Utils.showCustomToast(activity, "Drawing has been loaded successfully!", R.drawable.success);
                    ((AGeneralWhiteBoard) activity).customActionBar.setTimeCreatedText(GlobalConstants.SITE_PLAN_IMAGE_NAME + " created at " + dptw.time);
               } catch (Exception e) {
                    QLog.debug(e.getMessage());
               }
          }

          if ( AGeneralWhiteBoard.IS_NEED_TO_BACK ) {
               this.activity.onBackPressed();
          }

     }

}
