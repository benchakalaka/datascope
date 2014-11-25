package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.communication.rest.request.CompaniesAndHotspotsRequest;

public class ResponseDownloadSitePlan implements RequestListener <byte[]> {

     protected AMenuModules activity;

     public ResponseDownloadSitePlan ( AMenuModules act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          this.activity.dissmissProgressDialog();

          // There is no data from server, show toast and return
          if ( fileData.length == 1 ) {
               QNotifications.showShortToast(activity, R.string.there_is_no_images);
               return;
          }

          // decode recieved byte array and set image option "isMutable" to true
          CompaniesDrawingView.startBitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
          CompaniesDrawingView.startBitmap = CompaniesDrawingView.startBitmap.copy(Bitmap.Config.ARGB_8888, true);
          CompaniesDrawingView.canvasBitmap = CompaniesDrawingView.startBitmap;

          CompaniesAndHotspotsRequest request = new CompaniesAndHotspotsRequest();
          activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CompaniesAndHotspotsRequestListener(activity));

     }
}