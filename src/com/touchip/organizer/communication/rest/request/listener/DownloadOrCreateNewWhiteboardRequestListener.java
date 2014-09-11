package com.touchip.organizer.communication.rest.request.listener;

import java.util.List;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.Utils;

public class DownloadOrCreateNewWhiteboardRequestListener implements RequestListener <byte[]> {

     public DownloadOrCreateNewWhiteboardRequestListener () {
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          GeneralWhiteBoardActivity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          Utils.logw("..................................... Recieved from server " + fileData.length + " bytes");

          if ( 1 != fileData.length ) {
               try {
                    List <PathSerializable> listPathes = Utils.parseByteArryaToPathSerializable(fileData, WhiteBoardDrawingView.WIDTH, WhiteBoardDrawingView.HEIGHT);
                    GeneralWhiteBoardActivity.WHITE_BOARD_DRAWING.setPaths(listPathes);
                    Utils.logw(GeneralWhiteBoardActivity.WHITE_BOARD_DRAWING.getPaths().size() + " paths has been downloaded");
               } catch (Exception e) {
                    Utils.logw(e.getMessage());
               }
          }
          GeneralWhiteBoardActivity.dissmissProgressDialog();
     }
}
