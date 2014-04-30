package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.custom.components.TradesView;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils;

public class CreateTradeHotspotRequestListener implements RequestListener <HotspotsList> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity_ activity;
     private final String                description;

     // Constructor that accept activity
     public CreateTradeHotspotRequestListener ( Activity act , String tradeHotspotDescription ) {
          activity = (DrawingCompaniesActivity_) act;
          description = tradeHotspotDescription;
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
               // DataAccess.UNASSIGNED_HOTSPOTS = hotspots;

               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();

               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.SHOW_ALL);

               POJORoboHotspot hs = DataAccess.findTradeHotspotByDescription(description);
               if ( null != hs ) {
                    TradesView tradeView = DataAccess.findTradeViewByDescription(description);
                    tradeView.twTradeAmount.setText((hs.availableAmount == 0) ? "0" : String.valueOf(DataAccess.findTradeHotspotByDescription(description).availableAmount));
               }
          }
     }
}
