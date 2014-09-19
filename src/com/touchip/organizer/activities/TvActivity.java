package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Dialog;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarTv;
import com.touchip.organizer.activities.custom.components.ActionBarTv_;
import com.touchip.organizer.activities.custom.components.CDialogEnterPin_;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.AppSharedPreferences_;
import com.touchip.organizer.utils.Utils;

/**
 * Datascope Systems Ltd.
 * 
 * @author Karpachev Ihor
 */
@EActivity ( R.layout.activity_tv ) public class TvActivity extends SpiceFragmentActivity {

     // Load Views
     @Pref AppSharedPreferences_ appPreff;
     @ViewById WebView           webViewTV;

     private static String       URL;

     public static ActionBarTv   customActionBar;

     @ViewById RelativeLayout    relativeRoot;

     @AfterViews void afterViews() {
          customActionBar = ActionBarTv_.build(TvActivity.this);

          // configure webview and actionbar
          Utils.configureCustomActionBar(getActionBar(), customActionBar);

          Utils.configureCustomActionBar(getActionBar(), null);
          Utils.configureWebView(webViewTV);
     }

     @Override protected void onResume() {
          super.onResume();
          // Configure paths to rest services
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());

          // set application context as static resource
          Utils.setAppContext(getApplicationContext());
          try {
               GlobalConstants.SITE_ID = appPreff.siteId().getOr("1");
               GlobalConstants.APP_VERSION = Utils.getAppContext().getPackageManager().getPackageInfo(Utils.getAppContext().getPackageName(), 0).versionName;
          } catch (Exception ex) {
               GlobalConstants.APP_VERSION = "-1";
               ex.printStackTrace();
          }
          if ( appPreff.tvId().get().equals("1") ) {
               URL = "http://www.datascopesystem.com/Kier_KingsCrossLondon/DigitalSignage/";
          } else {
               if ( appPreff.tvId().get().equals("2") ) {
                    URL = "http://www.datascopesystem.com/Kier_StBernards/DigitalSignage/";
               }
          }
          if ( null != webViewTV ) {
               webViewTV.loadUrl(URL);
          }
     }

     @OptionsItem void homeSelected() {
          Dialog dialog = new Dialog(this);
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          dialog.setContentView(CDialogEnterPin_.build(this.getApplicationContext(), this));
          dialog.show();
     }
}