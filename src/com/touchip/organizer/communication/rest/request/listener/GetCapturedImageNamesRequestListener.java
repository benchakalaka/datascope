package com.touchip.organizer.communication.rest.request.listener;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ImagePagerActivity;
import com.touchip.organizer.communication.rest.model.CapturedImagesUrlList;
import com.touchip.organizer.utils.Utils;

public class GetCapturedImageNamesRequestListener implements RequestListener <CapturedImagesUrlList> {

     // Reference to activity, for updating ui components
     protected ImagePagerActivity activity;

     // Constructor that accept activity
     public GetCapturedImageNamesRequestListener ( ImagePagerActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(this.activity, Utils.getResources(R.string.connection_problem).replace("marker", "hotspot"), R.drawable.empty);
          this.activity.onBackPressed();
          Utils.logw(e.getMessage());
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(CapturedImagesUrlList result) {
          if ( !Utils.isNullOrEmpty(result) ) {
               this.activity.setAdapterArray(result);
          } else {
               Utils.showCustomToast(this.activity, Utils.getResources(R.string.there_is_no_images).replace("marker", "hotspot"), R.drawable.empty);
               this.activity.onBackPressed();
          }
     }
}
