package com.touchip.organizer.communication.rest.request.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import quickutils.core.QUFactory.QSDcard;
import quickutils.core.QUFactory.QSystem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;

public class GetInductionFileListener implements RequestListener <byte[]> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;
     private final String    filename;

     // Constructor that accept activity
     public GetInductionFileListener ( SuperActivity act , String fName ) {
          this.activity = act;
          this.filename = fName;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(activity, "Cannot do execute request", R.drawable.hide_hotspot);
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] result) {

          if ( !QPreconditions.isNull(result) && result.length != 1 ) {
               try {
                    if ( !QSDcard.checkIfFileExists("induction") ) {
                         QSDcard.createFolder("induction");
                    }
                    String filename = QSDcard.getSDCardPath() + "induction" + File.separator + this.filename;
                    writeFile(result, filename);
                    QSystem.openFile(activity, new File(filename));

               } catch (Exception e) {
                    e.printStackTrace();
               }
          } else {
               QNotifications.showShortToast(activity, "There are no files found");
          }
     }

     public void writeFile(byte[] data, String fileName) throws IOException {
          FileOutputStream out = new FileOutputStream(fileName);
          out.write(data);
          out.close();
     }
}
