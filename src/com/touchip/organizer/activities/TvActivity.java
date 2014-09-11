package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Dialog;
import android.os.StrictMode;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarTv;
import com.touchip.organizer.activities.custom.components.ActionBarTv_;
import com.touchip.organizer.activities.custom.components.CDialogEnterPin_;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.utils.AppSharedPreferences_;
import com.touchip.organizer.utils.GlobalConstants;
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

          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

          StrictMode.setThreadPolicy(policy);

          customActionBar = ActionBarTv_.build(TvActivity.this);

          // configure webview and actionbar
          Utils.configureCustomActionBar(getActionBar(), customActionBar);

          Utils.configureCustomActionBar(getActionBar(), null);
          Utils.configureWebView(webViewTV);
          // webViewTV.loadUrl("file:///android_asset/cctv.htm");
          // webViewTV.loadUrl("http://194.28.136.8:8000/nphMotionJpeg");

          // requestWindowFeature(Window.FEATURE_NO_TITLE);
          // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
          /*
           * com.michogarcia.mjpegview.MjpegView mv = new com.michogarcia.mjpegview.MjpegView(this, new ImjpegViewListener() {
           * @Override public void sucess() {
           * Utils.logw("Success");
           * }
           * @Override public void hasBitmap(Bitmap bm) {
           * Utils.logw("has bitmap");
           * }
           * @Override public void error() {
           * Utils.logw("error");
           * }
           * });
           * relativeRoot.addView(mv);
           * // setContentView(mv);
           * mv.setSource("http://194.28.136.8:8000/nphMotionJpeg");
           * mv.showFps(false);
           */
     }

     @Override protected void onResume() {
          super.onResume();
          // Configure paths to rest services
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());

          // set application context as static resource
          Utils.setAppContext(getApplicationContext());
          try {
               GlobalConstants.LAST_CLICKED_MARKER_ID = appPreff.siteId().getOr("1");
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
          // HashMap <String, String> params = new HashMap <String, String>();
          // params.put(HTTP_PARAMS.PIN, "1111");
          // LoginRequest request = new LoginRequest(params);
          // getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new LoginRequestListener(this));

          Dialog dialog = new Dialog(this);
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          dialog.setContentView(CDialogEnterPin_.build(this.getApplicationContext(), this));
          dialog.show();

     }
}
