package com.touchip.organizer.activities;

import static com.touchip.organizer.utils.Utils.formatDate;
import static com.touchip.organizer.utils.Utils.showCustomToast;

import java.util.Date;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QPreconditions;
import quickutils.core.QUFactory.QSystem;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarMenuModules_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogLogout_;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList.POJORoboOneDateToHighlight;
import com.touchip.organizer.communication.rest.model.ModelDelivery;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanNamesList;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseDatesToHighlight;
import com.touchip.organizer.communication.rest.request.listener.ResponseDownloadSitePlan;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetDeliveriesList;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetMeetingPlanNames;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetPathsCreationTime;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EActivity ( R.layout.activity_menu ) public class AMenuModules extends SuperActivity {
     // ====================================== VIEWS
     @ViewById TextView            twDate;

     @ViewById RelativeLayout      rlQuilt , rlWboard , rlTv , rlDelivery , rlPrsr , rlNotes;

     private static Date           currentDate          = new Date();

     @ViewById ImageView           ivPrevDay , ivNextDay;

     // ====================================== VARIABLES
     // Setup listener
     public final CaldroidListener onDateChangeListener = new CaldroidListener() {
                                                             @Override public void onSelectDate(final Date date, View view) {
                                                                  GlobalConstants.SITE_PLAN_IMAGE_NAME = formatDate(date);
                                                                  for ( POJORoboOneDateToHighlight singleDate : GlobalConstants.datestoHighlight ) {
                                                                       // single date is date when photo has been uploaded
                                                                       if ( singleDate.date.compareTo(GlobalConstants.SITE_PLAN_IMAGE_NAME) < 1 ) {
                                                                            if ( !QPreconditions.isNullOrEmpty(singleDate.floors) ) {
                                                                                 GlobalConstants.CURRENT_FLOOR = singleDate.floors.get(0);
                                                                            } else {
                                                                                 showCustomToast(AMenuModules.this, R.string.error_obtaining_floors_areas, R.drawable.hide_hotspot);
                                                                                 return;
                                                                            }
                                                                       }
                                                                  }
                                                                  downloadSitePlan();
                                                             }

                                                             @Override public void onChangeMonth(int month, int year) {
                                                             }

                                                             @Override public void onLongClickDate(Date date, View view) {
                                                             }

                                                             @Override public void onCaldroidViewCreated() {
                                                             }
                                                        };

     // ====================================== METHODS
     @AfterViews void afterViews() {
          // set current date
          setUpCurrentDate(currentDate);

          // configure webview and actionbar
          Utils.configureCustomActionBar(getActionBar(), ActionBarMenuModules_.build(AMenuModules.this));
          getActionBar().setIcon(R.drawable.logout);
     }

     /**
      * Display pin pad
      */
     @OptionsItem void homeSelected() {
          Dialog dialog = Utils.getConfiguredDialog(this);
          dialog.setContentView(CDialogLogout_.build(this, dialog));
          dialog.show();
     }

     /**
      * Get dates to highlight from server and show calendar with highlighted dates
      */
     @Click void twDate() {
          twDate.startAnimation(AnimationManager.load(R.anim.bounce));
          getDatesToHighLight(true);
     }

     /**
      * Request to server, show dialog with hioghlighted dates in calendat
      */
     private void getDatesToHighLight(boolean showCalendar) {
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <ModelDatesToHighlightList> requestGetDatesToHighlight = new SuperRequest <ModelDatesToHighlightList>(this, ModelDatesToHighlightList.class, RestAddresses.GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR, null, params);
          execute(requestGetDatesToHighlight, new ResponseDatesToHighlight(AMenuModules.this, showCalendar));
     }

     /**
      * Request to server, download site plan
      */
     private void downloadSitePlan() {
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <byte[]> request = new SuperRequest <byte[]>(this, byte[].class, RestAddresses.DOWNLOAD_SITE_PLAN, new ByteArrayHttpMessageConverter(), params);
          execute(request, new ResponseDownloadSitePlan(AMenuModules.this));
     }

     /**
      * Override back bress and just do nothing (in order to reutrn to first screren user should click tv module)
      */
     @Override public void onBackPressed() {
     }

     /**
      * Format date and display in textview
      * 
      * @param dateToSetUp
      *             Date object with date to setup
      */
     private void setUpCurrentDate(Date dateToSetUp) {
          GlobalConstants.SITE_PLAN_IMAGE_NAME = formatDate(currentDate);
          twDate.setText(formatDate(dateToSetUp));
     }

     /**
      * Substract one day from current day and display it
      */
     @Click void ivPrevDay() {
          ivPrevDay.startAnimation(AnimationManager.load(R.anim.fade));
          twDate.startAnimation(AnimationManager.load(R.anim.grow_from_top));
          currentDate = DateUtils.addDays(currentDate, -1);
          setUpCurrentDate(currentDate);
     }

     /**
      * Add one day from current day and display it
      */
     @Click void ivNextDay() {
          ivNextDay.startAnimation(AnimationManager.load(R.anim.fade));
          twDate.startAnimation(AnimationManager.load(R.anim.grow_from_top));
          currentDate = DateUtils.addDays(currentDate, 1);
          setUpCurrentDate(currentDate);
     }

     /**
      * Data touch module click
      */
     @Click void rlQuilt() {
          rlQuilt.startAnimation(AnimationManager.load(R.anim.fade_in));
          getDatesToHighLight(false);
          downloadSitePlan();
     }

     /**
      * Tv module click
      */
     @Click void rlTv() {
          rlTv.startAnimation(AnimationManager.load(R.anim.fade_in));
          QSystem.navigateToActivity(AMenuModules.this, ATv_.class);
     }

     /**
      * Notes module click
      */
     @Click void rlNotes() {
          rlNotes.startAnimation(AnimationManager.load(R.anim.fade_in));
          QSystem.navigateToActivity(AMenuModules.this, ANotes_.class);
     }

     /**
      * Delivery module clicked
      */
     @Click void rlDelivery() {
          rlDelivery.startAnimation(AnimationManager.load(R.anim.fade_in));
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <ModelDelivery> request = new SuperRequest <ModelDelivery>(this, ModelDelivery.class, RestAddresses.GET_DELIVERIES_LIST, params);
          execute(request, new ResponseGetDeliveriesList(this));
     }

     /**
      * WhiteBoard module clicked
      */
     @Click void rlWboard() {
          rlWboard.startAnimation(AnimationManager.load(R.anim.fade_in));
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <ModelPathsCreationTimeList> request = new SuperRequest <ModelPathsCreationTimeList>(this, ModelPathsCreationTimeList.class, RestAddresses.GET_TIMES_FOR_PATHS, params);
          execute(request, new ResponseGetPathsCreationTime(this));
     }

     /**
      * Daily briefing clicked
      */
     @Click void rlPrsr() {
          rlPrsr.startAnimation(AnimationManager.load(R.anim.fade_in));
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <ModelMeetingPlanNamesList> request = new SuperRequest <ModelMeetingPlanNamesList>(this, ModelMeetingPlanNamesList.class, RestAddresses.GET_MEETINGPLAN_NAMES, params);
          execute(request, new ResponseGetMeetingPlanNames(AMenuModules.this));
     }
}