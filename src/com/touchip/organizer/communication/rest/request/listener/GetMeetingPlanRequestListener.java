package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.MeetingPlanActivity;
import com.touchip.organizer.activities.MeetingPlanActivity_;
import com.touchip.organizer.communication.rest.model.MeetingPlanList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetMeetingPlanRequestListener implements RequestListener <MeetingPlanList> {

     // Reference to activity, for updating ui components
     protected Activity activity;

     // Constructor that accept activity
     public GetMeetingPlanRequestListener ( Activity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(activity, "Error appear during downloading site plan meeting for *" + GlobalConstants.SITE_PLAN_IMAGE_NAME + "*", R.drawable.hide_hotspot);
          Utils.logw(e.getMessage());
     }

     @Override public void onRequestSuccess(MeetingPlanList result) {
          if ( !Utils.isNullOrEmpty(result) ) {
               Intent meetingPlan = new Intent(activity, MeetingPlanActivity_.class);
               MeetingPlanActivity.MEETING_PLAN_ARRAY = result;
               activity.startActivity(meetingPlan);
          } else {
               Utils.showCustomToast(activity, "There is no meeting plan for " + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.hide_hotspot);
               activity.onBackPressed();
          }
     }
}
