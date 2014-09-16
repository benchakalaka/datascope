package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.communication.rest.model.CompaniesAndHotspots;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.HotspotManager.Hotspots;
import com.touchip.organizer.utils.Utils;

public class CreateHotspotRequestListener implements RequestListener <CompaniesAndHotspots> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity_ activity;

     // Constructor that accept activity
     public CreateHotspotRequestListener ( Activity act ) {
          activity = (DrawingCompaniesActivity_) act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          DrawingCompaniesActivity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(CompaniesAndHotspots hotspots) {
          if ( null != hotspots ) {
               if ( null != hotspots.hotSpotWrapperList || (null != hotspots.unassignHotspotsWrapperList) ) {
                    DataAccess.SIGNED_HOTSPOTS = hotspots.hotSpotWrapperList;
                    DataAccess.UNASSIGNED_HOTSPOTS = hotspots.unassignHotspotsWrapperList;
                    FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
                    FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();
                    FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.HOTSPOTS_NAMES[Hotspots.SHOW_ALL]);
               } else {
                    Utils.showToast(activity, R.string.cannot_create_hotspot, true);
               }
          } else {
               Utils.showToast(activity, R.string.cannot_create_hotspot, true);
          }
          DrawingCompaniesActivity.dissmissProgressDialog();
     }
}
