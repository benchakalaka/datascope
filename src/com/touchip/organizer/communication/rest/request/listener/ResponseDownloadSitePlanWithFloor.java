package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QSystem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.communication.rest.request.CompaniesAndHotspotsRequest;
import com.touchip.organizer.utils.Utils;

public class ResponseDownloadSitePlanWithFloor implements RequestListener <byte[]> {

     protected SuperActivity activity;

     public ResponseDownloadSitePlanWithFloor ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException exception) {
          QLog.debug(exception.getMessage());
          Utils.showCustomToast(activity, R.string.connection_problem, R.drawable.hide_hotspot);
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          // There is no data from server, show toast and return
          if ( fileData.length == 1 ) {
               QNotifications.showShortToast(activity, R.string.there_is_no_images);
               return;
          }

          try {
               // First decode with inJustDecodeBounds=true to check dimensions
               // decode recieved byte array and set image option "isMutable" to true
               CompaniesDrawingView.startBitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length).copy(Bitmap.Config.ARGB_8888, true); // XXX
               CompaniesDrawingView.canvasBitmap = CompaniesDrawingView.startBitmap;

               CompaniesDrawingView.DRAWING_PATHS_BYTES = null;

               CompaniesAndHotspotsRequest request = new CompaniesAndHotspotsRequest();
               activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CompaniesAndHotspotsRequestListener(activity));
          } catch (Exception ex) {
               ex.printStackTrace();
               Utils.showCustomToast(activity, "Error occured during parsing site plan image", R.drawable.failure);
               QSystem.navigateToActivity(activity, AMenuModules_.class);
          }
     }
}