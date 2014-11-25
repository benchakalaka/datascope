package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QPreconditions;
import quickutils.core.QUFactory.QSystem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMeetingPlan;
import com.touchip.organizer.activities.AMeetingPlan_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetMeetingPlanRequestListener implements RequestListener <ModelMeetingPlanList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public GetMeetingPlanRequestListener ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(activity, Utils.getResources(R.string.error_during_downloading_site_plan_meeting) + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.hide_hotspot);
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(ModelMeetingPlanList result) {
          this.activity.dissmissProgressDialog();
          if ( !QPreconditions.isNullOrEmpty(result) ) {
               AMeetingPlan.MEETING_PLAN_ARRAY = result;
               QSystem.navigateToActivity(activity, AMeetingPlan_.class);
          } else {
               Utils.showCustomToast(activity, Utils.getResources(R.string.no_meeting_plan) + " - " + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.hide_hotspot);
               activity.onBackPressed();
          }
     }
}
