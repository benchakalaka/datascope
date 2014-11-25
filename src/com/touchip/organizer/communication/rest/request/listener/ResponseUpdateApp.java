package com.touchip.organizer.communication.rest.request.listener;

import java.io.File;
import java.io.IOException;

import quickutils.core.QUFactory.QLog;
import android.content.Intent;
import android.net.Uri;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.AUserSettings;
import com.touchip.organizer.utils.Utils;

public class ResponseUpdateApp implements RequestListener <byte[]> {

     protected AUserSettings activity;

     public ResponseUpdateApp ( AUserSettings aUserSettings ) {
          activity = aUserSettings;
     }

     @Override public void onRequestFailure(SpiceException e) {
          QLog.debug(e.getMessage());
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          if ( 1 == fileData.length ) {
               // / Utils.showCustomToast(, messageResourcesId, imageResourcesId);
               // Error on server or during parsing responce
               return;
          }
          try {
               File updateApkFile = Utils.writeFile(fileData, "NewVersion.apk");
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setDataAndType(Uri.fromFile(updateApkFile), "application/vnd.android.package-archive");
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
               Utils.getAppContext().startActivity(intent);
          } catch (IOException e) {
               e.printStackTrace();
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
