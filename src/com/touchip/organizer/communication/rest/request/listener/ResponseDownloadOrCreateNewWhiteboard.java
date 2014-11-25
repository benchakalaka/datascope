package com.touchip.organizer.communication.rest.request.listener;

import java.util.List;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.Utils;

public class ResponseDownloadOrCreateNewWhiteboard implements RequestListener <byte[]> {

     private final SuperActivity activity;

     public ResponseDownloadOrCreateNewWhiteboard ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          QLog.debug("..................................... Recieved from server " + fileData.length + " bytes");

          if ( 1 != fileData.length ) {
               try {
                    List <PathSerializable> listPathes = Utils.parseByteArryaToPathSerializable(fileData, WhiteBoardDrawingView.WIDTH, WhiteBoardDrawingView.HEIGHT);
                    AGeneralWhiteBoard.WHITE_BOARD_DRAWING.setPaths(listPathes);
                    QLog.debug(AGeneralWhiteBoard.WHITE_BOARD_DRAWING.getPaths().size() + " paths has been downloaded");
               } catch (Exception e) {
                    QLog.debug(e.getMessage(), e);
               }
          }
          this.activity.dissmissProgressDialog();
     }
}
