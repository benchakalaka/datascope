package com.touchip.organizer.communication.rest.request.listener;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import android.os.Bundle;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules;
import com.touchip.organizer.activities.StartActivity;
import com.touchip.organizer.activities.TvActivity;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class DatesToHighlightRequestListener implements RequestListener <DatesToHighlightList> {

     // Reference to activity, for updating ui components
     protected AMenuModules activity;

     // Constructor that accept activity
     public DatesToHighlightRequestListener ( AMenuModules act ) {
          activity = act;
     }

     // Have got error from server or (MapActivity) actblem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          TvActivity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(DatesToHighlightList dates) {

          // If there is no dates to highlight, show appropriate messsage
          if ( (dates == null) || dates.isEmpty() ) {
               Utils.showCustomToast(activity, R.string.there_is_no_site_plan_found, R.drawable.hide_hotspot);
               TvActivity.dissmissProgressDialog();
               return;
          }

          DataAccess.datestoHighlight = dates;
          Bundle bundle = new Bundle();
          bundle.putString(com.roomorama.caldroid.CaldroidFragment.DIALOG_TITLE, "Select a date");
          bundle.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
          StartActivity.dialogCaldroidFragment = new CaldroidFragment();
          StartActivity.dialogCaldroidFragment.setArguments(bundle);
          StartActivity.dialogCaldroidFragment.setCaldroidListener(activity.onDateChangeListener);

          HashMap <Date, Integer> datesAndColour = new HashMap <Date, Integer>();
          for ( DatesToHighlightList.POJORoboOneDateToHighlight singleDate : dates ) {
               try {
                    datesAndColour.put(Utils.DATE_FORMAT.parse(singleDate.date), com.caldroid.R.color.caldroid_sky_blue);
               } catch (ParseException e) {
                    e.printStackTrace();
               }
          }
          // highlight dates in calendar with blue color
          StartActivity.dialogCaldroidFragment.setBackgroundResourceForDates(datesAndColour);

          StartActivity.dialogCaldroidFragment.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
          TvActivity.dissmissProgressDialog();
     }
}
