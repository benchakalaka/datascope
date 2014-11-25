package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QPreconditions;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.AImagePager;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelCapturedImagesUrlList;
import com.touchip.organizer.utils.Utils;

public class ResponseGetCapturedImageNames implements RequestListener <ModelCapturedImagesUrlList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetCapturedImageNames ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(this.activity, Utils.getResources(R.string.connection_problem).replace("marker", "hotspot"), R.drawable.failure);
          ADrawingCompanies.progressDialog.dismiss();
          this.activity.onBackPressed();
          QLog.debug(e.getMessage());

     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelCapturedImagesUrlList result) {
          ADrawingCompanies.progressDialog.dismiss();
          if ( !QPreconditions.isNullOrEmpty(result) ) {
               // Create default options which will be used for every image loading
               DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
               ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(defaultOptions).build();
               ImageLoader.getInstance().init(config);
               // load data
               ((AImagePager) this.activity).setAdapterArray(result);
          } else {
               Utils.showCustomToast(this.activity, Utils.getResources(R.string.there_is_no_images).replace("marker", "hotspot"), R.drawable.failure);
               this.activity.onBackPressed();
          }

     }
}
