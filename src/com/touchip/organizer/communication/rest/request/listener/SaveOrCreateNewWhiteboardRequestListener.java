package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class SaveOrCreateNewWhiteboardRequestListener implements RequestListener <String> {

     private final GeneralWhiteBoardActivity activity;

     public SaveOrCreateNewWhiteboardRequestListener ( Activity act ) {
          this.activity = (GeneralWhiteBoardActivity) act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
     }

     @Override public void onRequestSuccess(String fileData) {
          if ( "".equals(fileData) ) {
               Utils.showCustomToast(activity, "Problem with downloading drawing", R.drawable.hide_hotspot);
          } else {
               try {
                    POJORoboPathCreationTime dptw = new POJORoboPathCreationTime();
                    dptw.id = Integer.valueOf(fileData.substring(0, fileData.indexOf(" ")));
                    dptw.time = fileData.substring(fileData.indexOf(" ") + 1, fileData.length());
                    GlobalConstants.LAST_CLICKED_WHITE_BOARD = dptw;
                    Utils.showCustomToast(activity, "Drawing has been loaded successfully!", R.drawable.server2);
                    this.activity.sideSlideMenu.setTimeCreatedText(GlobalConstants.SITE_PLAN_IMAGE_NAME + " created at " + dptw.time);
               } catch (Exception e) {
                    Utils.logw(e.getMessage());
               }
          }
     }

}
