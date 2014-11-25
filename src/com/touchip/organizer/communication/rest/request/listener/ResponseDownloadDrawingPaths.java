package com.touchip.organizer.communication.rest.request.listener;

import java.util.List;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.Utils;

public class ResponseDownloadDrawingPaths implements RequestListener <byte[]> {

     protected SuperActivity activity;

     public ResponseDownloadDrawingPaths ( SuperActivity act ) {
          activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          activity.dissmissProgressDialog();

          if ( 1 == fileData.length ) {
               ADrawingCompanies.DRAW_VIEW.setPaths(null);
               // Error on server or during parsing responce
               return;
          }
          CompaniesDrawingView.DRAWING_PATHS_BYTES = fileData;
          List <PathSerializable> listPathes = Utils.parseByteArryaToPathSerializable(fileData, CompaniesDrawingView.WIDTH, CompaniesDrawingView.HEIGHT);
          ADrawingCompanies.DRAW_VIEW.setPaths(listPathes);
          QLog.debug(ADrawingCompanies.DRAW_VIEW.getPaths().size() + " paths has been downloaded");
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
