package com.touchip.organizer.communication.rest.request.listener;

import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.custom.components.AssetsView.AssetsListViewAdapter;
import com.touchip.organizer.communication.rest.model.AssetsList;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class GetAssetsRequestListener implements RequestListener <AssetsList> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity activity;

     // Constructor that accept activity
     public GetAssetsRequestListener ( DrawingCompaniesActivity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // DrawingCompaniesActivity.dissmissProgressDialog();
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(AssetsList assets) {
          // DrawingCompaniesActivity.dissmissProgressDialog();
          if ( null != assets && !assets.isEmpty() ) {
               DrawingCompaniesActivity.getLwAssets().setAdapter(new AssetsListViewAdapter(activity, assets));
               AnimationManager.animateMenu(DrawingCompaniesActivity.getLlAssets(), View.VISIBLE, R.anim.push_left_in, 200);
               DrawingCompaniesActivity.getllFilters().setVisibility(View.GONE);
               DrawingCompaniesActivity.getLlTrades().setVisibility(View.GONE);
          } else {
               Utils.showToast(activity, "There is no assets for " + GlobalConstants.LAST_CLICKED_COMPANY.companyName, true);
               DrawingCompaniesActivity.getLlAssets().setVisibility(View.GONE);
          }
     }
}
