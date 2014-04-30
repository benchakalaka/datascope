package com.touchip.organizer.communication.rest.request.listener;

import java.text.ParseException;

import android.app.Activity;
import android.os.Bundle;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.MapActivity;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class DatesToHighlightRequestListener implements RequestListener <DatesToHighlightList> {

     // Reference to activity, for updating ui components
     protected MapActivity activity;

     // Constructor that accept activity
     public DatesToHighlightRequestListener ( Activity act ) {
          activity = (MapActivity) act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(DatesToHighlightList dates) {
          DataAccess.datestoHighlight = dates;
          Bundle bundle = new Bundle();
          bundle.putString(com.roomorama.caldroid.CaldroidFragment.DIALOG_TITLE, "Select a date");
          activity.dialogCaldroidFragment = new CaldroidFragment();
          activity.dialogCaldroidFragment.setArguments(bundle);
          activity.dialogCaldroidFragment.setCaldroidListener(activity.onDateChangeListener);

          // If there is no dates to highlight, show appropriate messsage
          if ( (dates == null) || dates.isEmpty() ) {
               Utils.showCustomToast(activity, "There is no data for this marker", R.drawable.hide_hotspot);
               activity.setProgressBarIndeterminateVisibility(false);
               return;
          }

          for ( DatesToHighlightList.POJORoboOneDateToHighlight singleDate : dates ) {
               // highlight date in calendar with blue color
               try {
                    activity.dialogCaldroidFragment.setBackgroundResourceForDate(com.caldroid.R.color.caldroid_sky_blue, Utils.DATE_FORMAT.parse(singleDate.date));
               } catch (ParseException e) {
                    Utils.logw(e.getMessage());
               }
          }
          activity.dialogCaldroidFragment.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
          activity.setProgressBarIndeterminateVisibility(false);
     }
}
