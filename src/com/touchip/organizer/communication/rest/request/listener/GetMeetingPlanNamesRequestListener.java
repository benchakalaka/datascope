package com.touchip.organizer.communication.rest.request.listener;

import java.util.HashMap;

import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.model.MeetingPlanNamesList;
import com.touchip.organizer.communication.rest.model.MeetingPlanNamesList.POJORoboSingleMeetingPlanName;
import com.touchip.organizer.communication.rest.request.GetMeetingPlanRequest;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class GetMeetingPlanNamesRequestListener implements RequestListener <MeetingPlanNamesList> {

     // Reference to activity, for updating ui components
     protected AMenuModules activity;

     // Constructor that accept activity
     public GetMeetingPlanNamesRequestListener ( AMenuModules act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(this.activity, Utils.getResources(R.string.connection_problem), R.drawable.hide_hotspot);
          Utils.logw(e.getMessage());
          DrawingCompaniesActivity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(final MeetingPlanNamesList result) {
          if ( null != result && !result.isEmpty() ) {
               AlertDialog.Builder builder = new AlertDialog.Builder(activity);
               builder.setTitle(Utils.getResources(R.string.sel_meeting_name));
               ListView meetingNamesList = new ListView(activity);
               String meetingPlanNames[] = new String[result.size()];
               for ( int i = 0; i < result.size(); i++ ) {
                    meetingPlanNames[i] = result.get(i).name;
               }
               ArrayAdapter <String> modeAdapter = new ArrayAdapter <String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, meetingPlanNames);
               meetingNamesList.setAdapter(modeAdapter);
               builder.setView(meetingNamesList);
               final AlertDialog dialog = builder.create();

               meetingNamesList.setOnItemClickListener(new OnItemClickListener() {

                    @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                         final String planName = adapter.getItemAtPosition(pos).toString();
                         HashMap <String, String> vars = new HashMap <String, String>();
                         for ( POJORoboSingleMeetingPlanName singlePlanName : result ) {
                              if ( singlePlanName.name.equals(planName) ) {
                                   vars.put(HTTP_PARAMS.PLAN_ID, singlePlanName.id);
                              }
                         }
                         DrawingCompaniesActivity.showProgressDialog();
                         GetMeetingPlanRequest request = new GetMeetingPlanRequest(vars);
                         activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetMeetingPlanRequestListener(activity));

                         dialog.dismiss();
                    }
               });

               dialog.show();
          } else {
               Utils.showCustomToast(activity, Utils.getResources(R.string.no_meeting_plan) + " - " + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.hide_hotspot);
          }
          DrawingCompaniesActivity.dissmissProgressDialog();
     }
}
