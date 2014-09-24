package com.touchip.organizer.communication.rest.request.listener;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.HotspotManager.Hotspots;
import com.touchip.organizer.utils.Utils;

public class UpdateTradeHotspotRequestListener implements RequestListener <HotspotsList> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity activity;

     // Constructor that accept activity
     public UpdateTradeHotspotRequestListener ( DrawingCompaniesActivity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.showToast(activity, R.string.connection_problem, true);
          Utils.logw(e.getMessage());
          // DrawingCompaniesActivity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(HotspotsList hotspots) {
          // DrawingCompaniesActivity.dissmissProgressDialog();
          if ( null != hotspots ) {
               this.activity.showTradesPanel();
               GlobalConstants.SITE_PLAN_FULL_INFO.hotSpotWrapperList = hotspots;

               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.SHOW_ALL);
          } else {
               Utils.showCustomToast(activity, "There is no hotposts recieved from server", R.drawable.hide_hotspot);
          }
     }

}
