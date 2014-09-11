package com.touchip.organizer.communication.rest.request.listener;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.net.Uri;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.StartActivity;
import com.touchip.organizer.activities.UserSettingsActivity;
import com.touchip.organizer.utils.Utils;

public class UpdateAppRequestListener implements RequestListener <byte[]> {

     protected UserSettingsActivity activity;

     public UpdateAppRequestListener ( UserSettingsActivity userSettingsActivity ) {
          activity = userSettingsActivity;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          activity.dissmissProgressDialog();
          // StartActivity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          activity.dissmissProgressDialog();
          if ( 1 == fileData.length ) {
               StartActivity.dissmissProgressDialog();
               // / Utils.showCustomToast(, messageResourcesId, imageResourcesId);
               // Error on server or during parsing responce
               return;
          }
          try {
               File updateApkFile = Utils.writeFile(fileData, "NewVersion.apk");
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setDataAndType(Uri.fromFile(updateApkFile), "application/vnd.android.package-archive");
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
               // StartActivity.dissmissProgressDialog();
               Utils.getAppContext().startActivity(intent);
          } catch (IOException e) {
               e.printStackTrace();
               // StartActivity.dissmissProgressDialog();
          }
     }

     /**
      * This method generates a unique cache key for this request.
      * 
      * @return
      */
     public String createCacheKey() {
          return getClass().getSimpleName() + ".cache";
     }

}
