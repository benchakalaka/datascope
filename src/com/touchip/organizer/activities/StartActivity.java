package com.touchip.organizer.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.ProgressDialog;
import android.widget.ImageButton;

import com.roomorama.caldroid.CaldroidFragment;
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

@EActivity ( R.layout.activity_start ) public class StartActivity extends SpiceFragmentActivity {

     // find views
     @ViewById ImageButton          buttonSettings;
     @Pref AppSharedPreferences_    appPreff;

     public static String           APP_VERSION            = "0";

     // Calendar view
     public static CaldroidFragment dialogCaldroidFragment = new CaldroidFragment();

     private static ProgressDialog  progressDialog;

     @Override protected void onResume() {
          super.onResume();
          // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE |
          // View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

          // Configure paths to rest services
          RestAddresses.setServerAddress(appPreff.ip().getOr("194.28.136.6"), appPreff.port().getOr("8071"));
          // set application context as static resource
          Utils.setAppContext(getApplicationContext());
          try {
               APP_VERSION = Utils.getAppContext().getPackageManager().getPackageInfo(Utils.getAppContext().getPackageName(), 0).versionName;
          } catch (Exception ex) {
               APP_VERSION = "-1";
          }
          Utils.showCustomToast(StartActivity.this, "App version : " + APP_VERSION, R.drawable.ic_launcher);

          // preapare progress dialog
          progressDialog = new ProgressDialog(StartActivity.this);
          progressDialog.setMessage(Utils.getResources(R.string.loading));
          progressDialog.setCancelable(true);
     }

     /**
      * Gmaps button, check GooglePlayService accessibility, if ok, go to Google map
      */
     // @Click void buttonGmaps() {
     /*
      * // Check status of Google Play Services
      * int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
      * // Check Google Play Service Available
      * try {
      * // show error dialog
      * if ( status != ConnectionResult.SUCCESS ) {
      * GooglePlayServicesUtil.getErrorDialog(status, this, 1).show();
      * } else {
      * // everything is ok, go to MapActivity
      * startActivity(new Intent(getApplicationContext(), MapActivity_.class));
      * }
      * } catch (Exception e) {
      * Utils.showCustomToast(StartActivity.this, String.format("Error: GooglePlayServiceUtil: %s", e.getMessage()), R.drawable.hide_hotspot);
      * }
      */
     // GlobalConstants.LAST_CLICKED_MARKER_ID = "2";
     // GetDatesToHighlightRequest request = new GetDatesToHighlightRequest();
     // getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DatesToHighlightRequestListener(this));

     // }

     /**
      * Exit button, move current task to background
      */

     /**
      * Settings button, go to settings
      */
     @Click void buttonSettings() {
          // startActivity(new Intent(getApplicationContext(), UserSettingsActivity_.class));
     }

}