package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QuickUtils;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity_;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import com.touchip.organizer.communication.rest.model.ModelPathList;
import com.touchip.organizer.utils.Utils;

public class ResponseDownloadPathById implements RequestListener <ModelPathList> {

     private final SpiceFragmentActivity activity;

     public ResponseDownloadPathById ( SpiceFragmentActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          GeneralWhiteBoardActivity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(ModelPathList paths) {
          try {
               WhiteBoardDrawingView.paths = Utils.convertStringToPathsList(paths.drawingPaths);
               QuickUtils.system.navigateToActivity(activity, GeneralWhiteBoardActivity_.class);
          } catch (Exception e) {
               Utils.logw(e.getMessage());
          }
          GeneralWhiteBoardActivity.dissmissProgressDialog();
     }
}
