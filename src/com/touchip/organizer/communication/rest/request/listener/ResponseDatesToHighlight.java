package com.touchip.organizer.communication.rest.request.listener;

import java.util.Date;
import java.util.HashMap;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMenuModules;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList.POJORoboOneDateToHighlight;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseDatesToHighlight implements RequestListener <ModelDatesToHighlightList> {

     // Reference to activity, for updating ui components
     protected AMenuModules activity;
     private final boolean  showCalendar;

     // Constructor that accept activity
     public ResponseDatesToHighlight ( AMenuModules act , boolean isNeedToShowCalendar ) {
          activity = act;
          this.showCalendar = isNeedToShowCalendar;
     }

     // Have got error from server or (MapActivity) actblem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          QNotifications.showShortToast(activity, R.string.connection_problem);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(ModelDatesToHighlightList dates) {

          this.activity.dissmissProgressDialog();

          // If there is no dates to highlight, show appropriate messsage
          if ( QPreconditions.isNullOrEmpty(dates) ) {
               Utils.showCustomToast(activity, R.string.there_is_no_site_plan_found, R.drawable.hide_hotspot);
               return;
          }
          GlobalConstants.datestoHighlight = dates;
          if ( !showCalendar ) {
               for ( POJORoboOneDateToHighlight singleDate : GlobalConstants.datestoHighlight ) {
                    if ( singleDate.date.compareTo(GlobalConstants.SITE_PLAN_IMAGE_NAME) < 1 ) {
                         if ( !QPreconditions.isNullOrEmpty(singleDate.floors) ) {
                              GlobalConstants.CURRENT_FLOOR = singleDate.floors.get(0);
                         }
                    }
               }
               return;
          }
          HashMap <Date, Integer> datesAndColour = QCollection.newHashMap();
          for ( ModelDatesToHighlightList.POJORoboOneDateToHighlight singleDate : dates ) {
               try {
                    datesAndColour.put(Utils.DATE_FORMAT.parse(singleDate.date), com.caldroid.R.color.caldroid_sky_blue);
               } catch (Exception e) {
                    e.printStackTrace();
               }
          }
          CaldroidFragment dialogCaldroidFragment = Utils.getConfiguredCaldroid(new Date(), datesAndColour);
          dialogCaldroidFragment.setCaldroidListener(activity.onDateChangeListener);
          // highlight dates in calendar with blue color

          dialogCaldroidFragment.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);

     }
}
