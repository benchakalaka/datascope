package com.touchip.organizer.communication.rest.request.listener;

import java.util.HashMap;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanList;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanNamesList;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanNamesList.POJORoboSingleMeetingPlanName;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class ResponseGetMeetingPlanNames implements RequestListener <ModelMeetingPlanNamesList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetMeetingPlanNames ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(this.activity, Utils.getResources(R.string.connection_problem), R.drawable.hide_hotspot);
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(final ModelMeetingPlanNamesList result) {
          if ( null != result && !result.isEmpty() ) {
               AlertDialog.Builder builder = new AlertDialog.Builder(activity);
               builder.setTitle(Utils.getResources(R.string.sel_briefing_name));
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
                         HashMap <String, String> vars = QCollection.newHashMap();
                         for ( POJORoboSingleMeetingPlanName singlePlanName : result ) {
                              if ( singlePlanName.name.equals(planName) ) {
                                   vars.put(HTTP_PARAMS.PLAN_ID, singlePlanName.id);
                              }
                         }

                         SuperRequest <ModelMeetingPlanList> request = new SuperRequest <ModelMeetingPlanList>(activity, ModelMeetingPlanList.class, RestAddresses.GET_MEETING_PLAN, vars);
                         activity.execute(request, new GetMeetingPlanRequestListener(activity));

                         dialog.dismiss();
                    }
               });

               dialog.show();
          } else {
               Utils.showCustomToast(activity, Utils.getResources(R.string.no_meeting_plan) + " - " + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.hide_hotspot);
          }
          this.activity.dissmissProgressDialog();
     }
}
