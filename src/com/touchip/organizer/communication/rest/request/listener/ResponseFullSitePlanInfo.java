package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.fragments.FragmentCompaniesListOffSite;
import com.touchip.organizer.communication.rest.model.ModelFullSiteInfo;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseFullSitePlanInfo implements RequestListener <ModelFullSiteInfo> {

     // Reference to activity, for updating ui components
     protected Activity activity;

     // Constructor that accept activity
     public ResponseFullSitePlanInfo ( Activity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelFullSiteInfo modelFullSiteInfo) {
          if ( null != modelFullSiteInfo ) {
               GlobalConstants.SITE_PLAN_FULL_INFO = modelFullSiteInfo;
               FragmentCompaniesListOffSite.COMPANIES_LIST = modelFullSiteInfo.companyWrappersList;
               Intent i = new Intent(activity, DrawingCompaniesActivity_.class);
               i.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
               // DrawingCompaniesActivity.dissmissProgressDialog();
               activity.startActivity(i);
          } else {
               Utils.showCustomToast(activity, "error occured", R.drawable.hide_hotspot);
          }
     }
}
