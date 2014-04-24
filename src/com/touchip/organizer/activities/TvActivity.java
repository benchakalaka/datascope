package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.utils.Utils;

/**
 * Datascope Systems Ltd.
 * 
 * @author Karpachev Ihor
 */
@EActivity ( R.layout.activity_tv ) public class TvActivity extends Activity {

     // Load Views
     @ViewById WebView           webViewTV;
     @ViewById ProgressBar       pb;
     private static final String URL = "http://www.datascopesystem.com/QUILT_Demo_ORIGINAL/DigitalSignage/";

     @AfterViews void afterViews() {
          // configure webview and actionbar
          Utils.configureCustomActionBar(getActionBar(), null);
          Utils.configureWebView(webViewTV).loadUrl(URL);
     }

     @OptionsItem void homeSelected() {
          onBackPressed();
     }

     /**
      * Small webview client which provides simple show/hide downloading progress bar
      * 
      * @author Karpachev Ihor
      */
     public class TvWebClient extends WebViewClient {

          @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
               view.loadUrl(url);
               return true;
          }

          @Override public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               pb.setVisibility(View.GONE);
          }
     }
}
