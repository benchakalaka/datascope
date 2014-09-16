package com.touchip.organizer.communication.rest.request.listener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules;
import com.touchip.organizer.activities.TvActivity;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.communication.rest.request.RequestFullSitePlanInfo;
import com.touchip.organizer.utils.Utils;

public class DownloadSitePlanRequestListenerStartActivity implements RequestListener <byte[]> {

     protected AMenuModules activity;

     public DownloadSitePlanRequestListenerStartActivity ( AMenuModules act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          TvActivity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(byte[] fileData) {
          // There is no data from server, show toast and return
          if ( fileData.length == 1 ) {
               Utils.showToast(activity, R.string.there_is_no_images, true);
               TvActivity.dissmissProgressDialog();
               return;
          }
          // FileOutputStream fis;
          // try {
          // fileData = Base64.decode(fileData, Base64.DEFAULT);
          // String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "test.png";
          // fis = new FileOutputStream(path);
          // fis.write(fileData);
          // } catch (Exception e) {
          // e.printStackTrace();
          // }

          // decode recieved byte array and set image option "isMutable" to true
          CompaniesDrawingView.startBitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
          CompaniesDrawingView.startBitmap = CompaniesDrawingView.startBitmap.copy(Bitmap.Config.ARGB_8888, true);
          CompaniesDrawingView.canvasBitmap = CompaniesDrawingView.startBitmap;

          RequestFullSitePlanInfo request = new RequestFullSitePlanInfo();
          activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ONE_MINUTE, new ResponseFullSitePlanInfo(activity));

     }
}