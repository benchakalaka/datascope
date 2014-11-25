package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.AssetsView.AssetsListViewAdapter;
import com.touchip.organizer.communication.rest.model.ModelAssetsList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class ResponseGetAssets implements RequestListener <ModelAssetsList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetAssets ( SuperActivity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          this.activity.dissmissProgressDialog();
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelAssetsList assets) {
          this.activity.dissmissProgressDialog();
          if ( !QPreconditions.isNullOrEmpty(assets) ) {
               ((ADrawingCompanies) this.activity).getLwAssets().setAdapter(new AssetsListViewAdapter((ADrawingCompanies) activity, assets));
               AnimationManager.animateMenu(ADrawingCompanies.getLlAssets(), View.VISIBLE, R.anim.push_left_in, 200);
               ADrawingCompanies.getllFilters().setVisibility(View.GONE);
               ADrawingCompanies.getLlTrades().setVisibility(View.GONE);
          } else {
               QNotifications.showShortToast(activity, "There is no assets for " + GlobalConstants.LAST_CLICKED_COMPANY.companyName);
               ADrawingCompanies.getLlAssets().setVisibility(View.GONE);
          }
     }
}
