package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.communication.rest.model.CompaniesAndHotspots;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.Utils;

public class CompaniesAndHotspotsRequestListener implements RequestListener <CompaniesAndHotspots> {

     // Reference to activity, for updating ui components
     protected Activity activity;

     // Constructor that accept activity
     public CompaniesAndHotspotsRequestListener ( Activity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(CompaniesAndHotspots companiesAndHotspots) {
          FragmentCompaniesList.COMPANIES_LIST = companiesAndHotspots.companyWrappersList;
          DataAccess.SIGNED_HOTSPOTS = companiesAndHotspots.hotSpotWrapperList;
          DataAccess.UNASSIGNED_HOTSPOTS = companiesAndHotspots.unassignHotspotsWrapperList;

          activity.setProgressBarIndeterminateVisibility(false);

          Intent i = new Intent(activity, DrawingCompaniesActivity_.class);
          i.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
          activity.startActivity(i);

     }
}
