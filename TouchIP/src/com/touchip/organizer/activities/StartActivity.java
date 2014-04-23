package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.utils.AppSharedPreferences_;
import com.touchip.organizer.utils.Utils;

/**
 * Description: activity which first appear when user starts application
 * 
 * @author Ihor Karpachev
 *         Copyright (c) 2013-2014 Datascope Systems Ltd. All Rights Reserved. Date: 26
 *         Dec 2013 Time: 21:54:28
 */

@EActivity ( R.layout.activity_start ) public class StartActivity extends Activity {

     // find views
     @ViewById ImageButton       buttonCalendar;
     @ViewById ImageButton       buttonDrawing;
     @ViewById ImageButton       buttonGmaps;
     @ViewById ImageButton       buttonSettings;
     @ViewById ImageButton       buttonExit;
     @Pref AppSharedPreferences_ appPreff;

     @AfterViews void afterViews() {
          // set application context as static resource
          Utils.setAppContext(getApplicationContext());
     }

     @Override protected void onResume() {
          super.onResume();
          // Configure paths to rest services
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());
     }

     /**
      * gmaps button, check GooglePlayService accessibility, if ok, go to Google map
      */
     @Click void buttonGmaps() {
          // Check status of Google Play Services
          int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
          // Check Google Play Service Available
          try {
               // show error dialog
               if ( status != ConnectionResult.SUCCESS ) {
                    GooglePlayServicesUtil.getErrorDialog(status, this, 1).show();
               } else {
                    // everything is ok, go to MapActivity
                    startActivity(new Intent(getApplicationContext(), com.touchip.organizer.activities.MapActivity_.class));
               }
          } catch (Exception e) {
               Utils.showCustomToast(StartActivity.this, "Error: GooglePlayServiceUtil: " + e.getMessage(), R.drawable.hide_hotspot);
          }
     }

     /**
      * exit button, move current task to background
      */
     @Click void buttonExit() {
          moveTaskToBack(true);
     }

     /**
      * settings button, go to settings
      */
     @Click void buttonSettings() {
          startActivity(new Intent(getApplicationContext(), UserSettingsActivity_.class));
     }

}