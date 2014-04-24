package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.MapActivity;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.communication.rest.request.CompaniesAndHotspotsRequest;
import com.touchip.organizer.utils.Utils;

public class DownloadSitePlanRequestListener implements RequestListener <byte[]> {

     protected MapActivity activity;

     public DownloadSitePlanRequestListener ( Activity act ) {
          activity = (MapActivity) act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          // There is no data from server, show toast and return
          if ( fileData.length == 1 ) {
               Utils.showToast(activity, R.string.there_is_no_images, true);
               return;
          }
          // decode recieved byte array and set image option
          // "isMutable" to true
          CompaniesDrawingView.startBitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length).copy(Bitmap.Config.ARGB_8888, true);
          CompaniesDrawingView.canvasBitmap = CompaniesDrawingView.startBitmap;

          activity.setProgressBarIndeterminateVisibility(true);

          CompaniesAndHotspotsRequest request = new CompaniesAndHotspotsRequest();
          activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ONE_MINUTE, new CompaniesAndHotspotsRequestListener(activity));

     }

}
