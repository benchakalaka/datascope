package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelFullSiteInfo;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.HotspotManager.Hotspots;
import com.touchip.organizer.utils.Utils;

public class AssignUnassignHotspotRequestListener implements RequestListener <ModelFullSiteInfo> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity_ activity;

     // Constructor that accept activity
     public AssignUnassignHotspotRequestListener ( Activity act ) {
          activity = (DrawingCompaniesActivity_) act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelFullSiteInfo hotspots) {
          if ( (null != hotspots.hotSpotWrapperList) || (null != hotspots.unassignHotspotsWrapperList) ) {
               GlobalConstants.SITE_PLAN_FULL_INFO.unassignHotspotsWrapperList = hotspots.unassignHotspotsWrapperList;
               GlobalConstants.SITE_PLAN_FULL_INFO.hotSpotWrapperList = hotspots.hotSpotWrapperList;

               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.SHOW_ALL);
          } else {
               Utils.showToast(activity, R.string.no_hotspots_from_server, true);
          }
     }
}
