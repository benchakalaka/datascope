package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QSystem;
import android.app.Dialog;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarTv_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogEnterPin_;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.utils.AppSharedPreferences_;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

/**
 * Class represents TV screen
 * Datascope Systems Ltd.
 * 
 * @author Karpachev Ihor
 */
@EActivity ( R.layout.activity_tv ) public class ATv extends SuperActivity {

     // =================================================== Load Views
     @Pref AppSharedPreferences_ appPreff;
     @ViewById WebView           webViewTV;
     @ViewById RelativeLayout    relativeRoot;

     // =================================================== Load Views

     @AfterViews void afterViews() {
          // configure webview and actionbar
          Utils.configureCustomActionBar(getActionBar(), ActionBarTv_.build(ATv.this));
          Utils.configureWebView(webViewTV);
     }

     @Override protected void onResume() {
          super.onResume();

          // Configure server paths to services
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());

          // set application context as static resource
          Utils.setAppContext(getApplicationContext());

          // Set up SITE ID from settings, if there is no site id in settings, set 1 as default
          GlobalConstants.LAST_CLICKED_MARKER_ID = appPreff.siteId().getOr("1");

          // Set up application version to display for user
          QNotifications.showShortToast(ATv.this, "Version " + (GlobalConstants.APP_VERSION = QSystem.getVersionName(this)));

          // get url from settings, by default display datascope main web site
          webViewTV.loadUrl(Utils.normalizeUrl(appPreff.tvUrl().getOr("http://www.datascopesystem.com")));
     }

     /**
      * Display pin pad
      */
     @OptionsItem void homeSelected() {
          Dialog dialog = Utils.getConfiguredDialog(this);
          // Set view for dialog and display it
          dialog.setContentView(CDialogEnterPin_.build(this));
          dialog.show();
     }

     /**
      * On back press from tv screen user should quit app, instead of back to previous screen by android screens stack
      */
     @Override public void onBackPressed() {
          moveTaskToBack(true);
     }
}
