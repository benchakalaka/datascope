package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseUpdateTradeHotspot implements RequestListener <ModelHotspotsList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseUpdateTradeHotspot ( SuperActivity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          QNotifications.showShortToast(activity, R.string.connection_problem);
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelHotspotsList hotspots) {
          this.activity.dissmissProgressDialog();
          if ( null != hotspots ) {
               ((ADrawingCompanies) this.activity).showTradesPanel();
               GlobalConstants.SIGNED_HOTSPOTS = hotspots;
               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList();
          } else {
               Utils.showCustomToast(activity, "There is no hotposts recieved from server", R.drawable.hide_hotspot);
          }
     }

}
