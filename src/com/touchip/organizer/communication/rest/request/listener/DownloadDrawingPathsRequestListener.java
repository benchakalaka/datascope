package com.touchip.organizer.communication.rest.request.listener;

import java.util.List;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.Utils;

public class DownloadDrawingPathsRequestListener implements RequestListener <byte[]> {

     protected DrawingCompaniesActivity_ activity;

     public DownloadDrawingPathsRequestListener ( Activity act ) {
          activity = (DrawingCompaniesActivity_) act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          if ( 1 == fileData.length ) {
               // Error on server or during parsing responce
               return;
          }

          List <PathSerializable> listPathes = Utils.parseByteArryaToPathSerializable(fileData, CompaniesDrawingView.WIDTH, CompaniesDrawingView.HEIGHT);
          DrawingCompaniesActivity.DRAW_VIEW.setPaths(listPathes);
          Utils.logw(DrawingCompaniesActivity.DRAW_VIEW.getPaths().size() + " paths has been downloaded");
     }

     /**
      * This method generates a unique cache key for this request.
      * 
      * @return
      */
     public String createCacheKey() {
          return "drawingPaths.cache";
     }

}
