package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.TradesView;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class ResponseGetTrades implements RequestListener <ModelHotspotsList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetTrades ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          this.activity.dissmissProgressDialog();
          // update UI
          QNotifications.showShortToast(activity, R.string.connection_problem);
          QLog.debug(e.getMessage());
          AnimationManager.animateMenu(ADrawingCompanies.getLlTrades(), View.GONE, R.anim.push_left_in, 200);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelHotspotsList hotspots) {
          this.activity.dissmissProgressDialog();
          // if list empty or null show appropriate message and hide
          if ( (null == hotspots) || (hotspots.isEmpty()) ) {
               QNotifications.showShortToast(activity, "There is no trades for " + GlobalConstants.LAST_CLICKED_COMPANY.companyName);
               ADrawingCompanies.getLlTrades().setVisibility(View.GONE);
               return;
          }
          int total = 0;
          for ( POJORoboHotspot hs : hotspots ) {
               total += hs.amount;
          }
          ((ADrawingCompanies) activity).setTotalAmountOfPeople(total);
          ADrawingCompanies.getLvTrades().setAdapter(new TradesView.TradesListViewAdapter(activity, hotspots));
          ADrawingCompanies.getllFilters().setVisibility(View.GONE);
          ADrawingCompanies.getLlAssets().setVisibility(View.GONE);
          AnimationManager.animateMenu(ADrawingCompanies.getLlTrades(), View.VISIBLE, R.anim.push_left_in, 200);
     }
}
