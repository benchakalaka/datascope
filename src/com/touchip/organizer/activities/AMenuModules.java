package com.touchip.organizer.activities;

import java.util.Date;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import quickutils.core.QuickUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.ModelFullSiteInfo;
import com.touchip.organizer.communication.rest.request.GetDeliveriesListRequest;
import com.touchip.organizer.communication.rest.request.GetMeetingPlanNamesRequest;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.GetDeliveriesListRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetMeetingPlanNamesRequestListener;
import com.touchip.organizer.communication.rest.request.listener.ResponseFullSitePlanInfo;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EActivity ( R.layout.activity_menu ) public class AMenuModules extends SpiceActivity {
     // ====================================== VIEWS
     @ViewById TextView                                   twUserDetails , twDate;

     @ViewById RelativeLayout                             rlQuilt , rlWboard , rlTv , rlDelivery , rlPrsr , rlNotes;

     private static Date                                  currentDate          = new Date();

     @ViewById ImageView                                  ivPrevDay , ivNextDay;

     // ====================================== VARIABLES
     // Setup listener
     public final com.roomorama.caldroid.CaldroidListener onDateChangeListener = new com.roomorama.caldroid.CaldroidListener() {
                                                                                    @Override public void onSelectDate(final Date date, View view) {
                                                                                         showProgressDialog();
                                                                                         GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(date);
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
          if ( null != GlobalConstants.CURRENT_USER ) {
               twUserDetails.setText(GlobalConstants.CURRENT_USER.firstName + " " + GlobalConstants.CURRENT_USER.lastName);
          }
          setUpCurrentDate(currentDate);
     }

     @Trace private void setUpCurrentDate(Date dateToSetUp) {
          GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(currentDate);
          twDate.setText(Utils.formatDate(dateToSetUp));
     }

     @Click void ivPrevDay() {
          ivPrevDay.startAnimation(AnimationManager.load(R.anim.fade));
          twDate.startAnimation(AnimationManager.load(R.anim.growview));
          currentDate = DateUtils.addDays(currentDate, -1);
          setUpCurrentDate(currentDate);
     }

     @Click void ivNextDay() {
          ivNextDay.startAnimation(AnimationManager.load(R.anim.fade));
          twDate.startAnimation(AnimationManager.load(R.anim.growview));
          currentDate = DateUtils.addDays(currentDate, 1);
          setUpCurrentDate(currentDate);
     }

     @Click void rlQuilt() {
          showProgressDialog();
          HashMap <String, String> params = new HashMap <String, String>();
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.SITE_ID, GlobalConstants.SITE_ID);
          SuperRequest <ModelFullSiteInfo> request = new SuperRequest <ModelFullSiteInfo>(ModelFullSiteInfo.class, RestAddresses.DOWNLOAD_SITE_PLAN, null, params);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseFullSitePlanInfo(AMenuModules.this));
     }

     @Click void rlTv() {
          QuickUtils.system.navigateToActivity(this, TvActivity_.class);
     }

     @Click void rlNotes() {
          QuickUtils.system.navigateToActivity(this, ANotes_.class);
     }

     @Click void rlDelivery() {
          // showProgressDialog();
          HashMap <String, String> params = new HashMap <String, String>();
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(/* HTTP_PARAMS.SITE_ID */"markerId", GlobalConstants.SITE_ID);

          GetDeliveriesListRequest request = new GetDeliveriesListRequest(params);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetDeliveriesListRequestListener(this));
     }

     @Click void rlWboard() {
          showProgressDialog();
          // GetPathsCreationTimeRequest request = new GetPathsCreationTimeRequest();
          // getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponsePathsCreationTime(this));
     }

     @Click void rlPrsr() {
          GetMeetingPlanNamesRequest request2 = new GetMeetingPlanNamesRequest();
          getSpiceManager().execute(request2, request2.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetMeetingPlanNamesRequestListener(AMenuModules.this));
     }
}