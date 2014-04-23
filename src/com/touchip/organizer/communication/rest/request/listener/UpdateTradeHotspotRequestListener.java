package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.custom.components.NumPad;
import com.touchip.organizer.activities.custom.components.TradesView;
import com.touchip.organizer.activities.custom.components.TradesView_;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList.ListViewUnsignedHotspotsAdapter;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils;

public class UpdateTradeHotspotRequestListener implements RequestListener <HotspotsList> {

     // Reference to activity, for updating ui components
     protected Activity   activity;
     private final NumPad numPad;

     // Constructor that accept activity
     public UpdateTradeHotspotRequestListener ( Activity act , NumPad pad ) {
          activity = act;
          this.numPad = pad;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.showToast(activity, R.string.connection_problem, true);
          Utils.logw(e.getMessage());
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(HotspotsList hotspots) {
          if ( !Utils.isNullOrEmpty(hotspots) ) {

               POJORoboHotspot tradeResources = null;
               // last hotspot could be new created trade
               if ( hotspots.get(hotspots.size() - 1).type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) && (hotspots.get(hotspots.size() - 1).x == -1) && (hotspots.get(hotspots.size() - 1).y == -1) ) {
                    tradeResources = hotspots.remove(hotspots.size() - 1);
               }

               DataAccess.SIGNED_HOTSPOTS = hotspots;
               ListViewUnsignedHotspotsAdapter.UNSIGNED_HOTSPOTS = hotspots;

               FragmentHotspotsList.ADAPTER.notifyDataSetChanged();
               FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();

               FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.SHOW_ALL);

               POJORoboHotspot tradeHotspot = DataAccess.findTradeHotspotByDescription(GlobalConstants.LAST_CLICKED_HOTSPOT.description);

               // if null == trade hotspot -> there is no this resources in hotspots list
               if ( null != tradeHotspot ) {
                    if ( tradeHotspot.availableAmount == 0 ) {
                         TradesView view = DataAccess.findTradeViewByDescription(tradeHotspot.description);
                         if ( view != null ) {
                              view.twTradeAmount.setText("0");
                         }
                         return;
                    } else {
                         try {
                              TradesView rowViewTrades = DataAccess.findTradeViewByDescription(GlobalConstants.LAST_CLICKED_HOTSPOT.description);
                              if ( null != rowViewTrades ) {
                                   rowViewTrades.twTradeAmount.setText(String.valueOf(tradeHotspot.availableAmount));
                              }
                         } catch (Exception ee) {
                              Utils.logw(ee.getMessage());
                         }
                    }
               } else {
                    TradesView rowViewTrades = DataAccess.findTradeViewByDescription(GlobalConstants.LAST_CLICKED_HOTSPOT.description);
                    // This item is in list, so make it visible and update amount of available trades
                    if ( null != rowViewTrades ) {
                         rowViewTrades.setVisibility(View.VISIBLE);
                         rowViewTrades.twTradeAmount.setText(String.valueOf(numPad.getInputMaxValue() - numPad.getIntValue()));
                    } else {
                         // Create new trade and add it to list of trades
                         TradesView rowView = TradesView_.build(activity);
                         // add all resources items to resource items list
                         DataAccess.ARRAY_TRADES_ITEMS.add(rowView);
                         // Set one item trade
                         rowView.setTrade(tradeResources);
                         if ( (null != tradeResources) && (null != DataAccess.TRADES_ON_SITE) ) {
                              DataAccess.TRADES_ON_SITE.add(tradeResources);
                         }
                         // Add one view to end of list
                         DrawingCompaniesActivity.getLlTrades().addView(rowView);
                    }
               }
          } else {
               Utils.showCustomToast(activity, "There is no hotposts recieved from server", R.drawable.hide_hotspot);
          }
     }

}
