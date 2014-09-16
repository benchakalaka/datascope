package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.communication.rest.model.RiskSchedule;
import com.touchip.organizer.utils.DialogUtils;
import com.touchip.organizer.utils.Utils;

public class GetActivitiesAndRisksRequestListener implements RequestListener <RiskSchedule> {

     // Reference to activity, for updating ui components
     protected Activity activity;

     // Constructor that accept activity
     public GetActivitiesAndRisksRequestListener ( Activity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          DrawingCompaniesActivity.dissmissProgressDialog();
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(RiskSchedule risks) {
          if ( null != risks ) {
               DialogUtils.showRiskScheduleDialog((DrawingCompaniesActivity_) this.activity, risks);
          } else {
               Utils.showCustomToast(activity, R.string.there_are_no_risks, R.drawable.hide_hotspot);
          }
          DrawingCompaniesActivity.dissmissProgressDialog();
     }
}
