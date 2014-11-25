package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelCompaniesAndHotspots;
import com.touchip.organizer.utils.GlobalConstants;

public class ResponseCreateAssetHotspot implements RequestListener <ModelCompaniesAndHotspots> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseCreateAssetHotspot ( SuperActivity act ) {
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
     @Override public void onRequestSuccess(ModelCompaniesAndHotspots hotspots) {
          this.activity.dissmissProgressDialog();
          if ( !QPreconditions.isNull(hotspots) ) {
               if ( !QPreconditions.isNull(hotspots.hotSpotWrapperList) || !QPreconditions.isNull(hotspots.unassignHotspotsWrapperList) ) {
                    GlobalConstants.SIGNED_HOTSPOTS = hotspots.hotSpotWrapperList;
                    GlobalConstants.UNASSIGNED_HOTSPOTS = hotspots.unassignHotspotsWrapperList;
                    FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
                    FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();
                    FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList();
               } else {
                    QNotifications.showShortToast(activity, R.string.cannot_create_hotspot);
               }
          } else {
               QNotifications.showShortToast(activity, R.string.cannot_create_hotspot);
          }
     }
}
