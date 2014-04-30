package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.custom.components.TradesView;
import com.touchip.organizer.activities.custom.components.TradesView_;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class GetTradesRequestListener implements RequestListener <HotspotsList> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity_ activity;

     // Constructor that accept activity
     public GetTradesRequestListener ( Activity act ) {
          activity = (DrawingCompaniesActivity_) act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update UI
          Utils.showToast(activity, R.string.connection_problem, true);
          Utils.logw(e.getMessage());
          DrawingCompaniesActivity.customActionBar.setTradesMenuVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(HotspotsList hotspots) {
          DataAccess.TRADES_ON_SITE = hotspots;
          if ( (null == DataAccess.TRADES_ON_SITE) || (DataAccess.TRADES_ON_SITE.isEmpty()) ) {
               Utils.showToast(activity, "There is no resources for " + DataAccess.LAST_CLICKED_COMPANY.companyName, true);
               DrawingCompaniesActivity.customActionBar.setTradesMenuVisibility(false);
               return;
          }

          DrawingCompaniesActivity.getLlTrades().removeAllViews();
          DataAccess.ARRAY_TRADES_ITEMS.clear();
          // Create trades views and add it to trades layout
          for ( int i = 0; i < DataAccess.TRADES_ON_SITE.size(); i++ ) {
               TradesView rowView = TradesView_.build(activity);
               // add all resources items to resource items list
               DataAccess.ARRAY_TRADES_ITEMS.add(rowView);
               // Set one item trade
               rowView.setTrade(DataAccess.TRADES_ON_SITE.get(i));
               DrawingCompaniesActivity.getLlTrades().addView(rowView);
          }
          AnimationManager.animateMenu(DrawingCompaniesActivity.getSvTrades(), View.VISIBLE, R.anim.push_left_in, 500);
     }
}
