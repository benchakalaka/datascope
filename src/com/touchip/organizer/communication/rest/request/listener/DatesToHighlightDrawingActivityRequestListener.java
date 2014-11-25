package com.touchip.organizer.communication.rest.request.listener;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class DatesToHighlightDrawingActivityRequestListener implements RequestListener <ModelDatesToHighlightList> {

     // Reference to activity, for updating ui components
     protected SuperActivity       activity;

     public final CaldroidListener listener = new com.roomorama.caldroid.CaldroidListener() {
                                                 @Override public void onSelectDate(Date date, View view) {
                                                      ((ADrawingCompanies_) activity).loadImage(date, null);
                                                 }

                                                 @Override public void onChangeMonth(int month, int year) {
                                                 }

                                                 @Override public void onCaldroidViewCreated() {
                                                 }

                                                 @Override public void onLongClickDate(Date date, View view) {
                                                 }
                                            };

     // Constructor that accept activity
     public DatesToHighlightDrawingActivityRequestListener ( SuperActivity act ) {
          activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
          this.activity.dissmissProgressDialog();
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelDatesToHighlightList dates) {

          if ( QPreconditions.isNullOrEmpty(dates) ) {
               QNotifications.showShortToast(activity, "There is no site plans received from server");
               return;
          }

          GlobalConstants.datestoHighlight = dates;

          HashMap <Date, Integer> datesAndColours = QCollection.newHashMap();
          for ( ModelDatesToHighlightList.POJORoboOneDateToHighlight singleDate : dates ) {
               // highlight date in calendar with blue color
               try {
                    datesAndColours.put(Utils.DATE_FORMAT.parse(singleDate.date), com.caldroid.R.color.caldroid_sky_blue);
               } catch (ParseException e) {
                    QLog.debug(e.getMessage());
               }
          }

          final com.roomorama.caldroid.CaldroidFragment dialogCaldroidFragment = Utils.getConfiguredCaldroid(new Date(), datesAndColours);

          dialogCaldroidFragment.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
          dialogCaldroidFragment.setCaldroidListener(listener);

          this.activity.dissmissProgressDialog();

     }
}
