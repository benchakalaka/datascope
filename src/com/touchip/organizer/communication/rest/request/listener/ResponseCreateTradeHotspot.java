package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.utils.GlobalConstants;

public class ResponseCreateTradeHotspot implements RequestListener <ModelHotspotsList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseCreateTradeHotspot ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelHotspotsList hotspots) {
          this.activity.dissmissProgressDialog();
          if ( !QPreconditions.isNullOrEmpty(hotspots) ) {
               GlobalConstants.SIGNED_HOTSPOTS = hotspots;
               // DataAccess.UNASSIGNED_HOTSPOTS = hotspots;
               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               // FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList();
               ((ADrawingCompanies_) activity).showTradesPanel();
          }
     }
}
