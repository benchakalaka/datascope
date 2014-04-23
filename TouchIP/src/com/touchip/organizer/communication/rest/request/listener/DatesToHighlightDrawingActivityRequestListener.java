package com.touchip.organizer.communication.rest.request.listener;

import java.text.ParseException;
import java.util.Date;

import android.app.Activity;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class DatesToHighlightDrawingActivityRequestListener implements RequestListener <DatesToHighlightList> {

     // Reference to activity, for updating ui components
     protected DrawingCompaniesActivity_ activity;

     public final CaldroidListener       listener = new com.roomorama.caldroid.CaldroidListener() {
                                                       @Override public void onSelectDate(Date date, View view) {
                                                            activity.loadImage(date, null);
                                                       }

                                                       @Override public void onChangeMonth(int month, int year) {
                                                       }

                                                       @Override public void onCaldroidViewCreated() {
                                                       }

                                                       @Override public void onLongClickDate(Date date, View view) {
                                                       }
                                                  };

     // Constructor that accept activity
     public DatesToHighlightDrawingActivityRequestListener ( Activity act ) {
          activity = (DrawingCompaniesActivity_) act;
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

          final com.roomorama.caldroid.CaldroidFragment dialogCaldroidFragment = new com.roomorama.caldroid.CaldroidFragment();

          for ( DatesToHighlightList.POJORoboOneDateToHighlight singleDate : dates ) {
               // highlight date in calendar with blue color
               try {
                    dialogCaldroidFragment.setBackgroundResourceForDate(com.caldroid.R.color.caldroid_sky_blue, Utils.DATE_FORMAT.parse(singleDate.date));
               } catch (ParseException e) {
                    Utils.logw(e.getMessage());
               }
          }

          dialogCaldroidFragment.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
          dialogCaldroidFragment.setCaldroidListener(listener);
          activity.setProgressBarIndeterminateVisibility(false);

     }
}
