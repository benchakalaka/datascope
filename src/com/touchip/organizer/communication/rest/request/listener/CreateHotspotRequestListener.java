package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList.ListViewUnsignedHotspotsAdapter;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils;

public class CreateHotspotRequestListener implements RequestListener <HotspotsList> {

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
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(HotspotsList hotspots) {
          if ( !Utils.isNullOrEmpty(hotspots) ) {
               DataAccess.SIGNED_HOTSPOTS = hotspots;
               ListViewUnsignedHotspotsAdapter.UNSIGNED_HOTSPOTS = DataAccess.SIGNED_HOTSPOTS;
               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.SHOW_ALL);
          } else {
               Utils.showToast(activity, "Hotspots list is empty", true);
          }
     }
}
