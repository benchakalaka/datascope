package com.touchip.organizer.communication.rest.request.listener;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.communication.rest.model.ModelPathId;
import com.touchip.organizer.utils.Utils;

public class ResponseGetDrawingPathByHotspotId implements RequestListener <ModelPathId> {

     private final SpiceFragmentActivity activity;

     public ResponseGetDrawingPathByHotspotId ( SpiceFragmentActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException ex) {
          this.activity.dissmissProgressDialog();
          Utils.showToast(activity, R.string.connection_problem, false);
          ex.printStackTrace();
     }

     @Override public void onRequestSuccess(ModelPathId response) {
          this.activity.dissmissProgressDialog();
          if ( null != response ) {
               GeneralWhiteBoardActivity.WHITE_BOARD_DRAWING.setPaths(Utils.convertStringToPathsList(response.drawingPaths));
          } else {

          }
     }
}