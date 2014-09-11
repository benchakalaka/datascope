package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.custom.components.TradesView;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
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
          DrawingCompaniesActivity.dissmissProgressDialog();
          // update UI
          Utils.showToast(activity, R.string.connection_problem, true);
          Utils.logw(e.getMessage());
          AnimationManager.animateMenu(DrawingCompaniesActivity.getLlTrades(), View.GONE, R.anim.push_left_in, 200);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(HotspotsList hotspots) {
          DrawingCompaniesActivity.dissmissProgressDialog();
          // if list empty or null show appropriate message and hide
          if ( (null == hotspots) || (hotspots.isEmpty()) ) {
               Utils.showToast(activity, "There is no trades for " + DataAccess.LAST_CLICKED_COMPANY.companyName, true);
               DrawingCompaniesActivity.getLlTrades().setVisibility(View.GONE);
               return;
          }
          int total = 0;
          for ( POJORoboHotspot hs : hotspots ) {
               total += hs.amount;
          }
          activity.setTotalAmountOfPeople(total);
          DrawingCompaniesActivity.getLvTrades().setAdapter(new TradesView.TradesListViewAdapter(activity, hotspots));
          DrawingCompaniesActivity.getllFilters().setVisibility(View.GONE);
          DrawingCompaniesActivity.getLlAssets().setVisibility(View.GONE);
          AnimationManager.animateMenu(DrawingCompaniesActivity.getLlTrades(), View.VISIBLE, R.anim.push_left_in, 200);
     }
}
