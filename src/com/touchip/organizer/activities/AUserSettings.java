package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.widget.Button;
import android.widget.EditText;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.utils.AppSharedPreferences_;
import com.touchip.organizer.utils.Utils;

/**
 * Class represents app settings
 * Datascope Systems Ltd.
 * 
 * @author Karpachev Ihor
 */
@EActivity ( R.layout.activity_application_settings ) public class AUserSettings extends SuperActivity {
     // ======================================================= Load views
     @ViewById EditText          etTvId , etSiteId , etPort , etIp , etTvUrl;
     @ViewById Button            buttonOk , buttonCancel , buttonUpdateApp;
     // ======================================================= Load views

     // =============================================== variables
     @Pref AppSharedPreferences_ appPreff;

     // =============================================== variables

     /**
      * This method called straight after all views has been loaded
      */
     @AfterViews void afterViews() {
          // Set up all fields for user
          etIp.setText(appPreff.ip().get());
          etPort.setText(appPreff.port().get());
          etTvId.setText(appPreff.tvId().get());
          etSiteId.setText(appPreff.siteId().get());
          etTvUrl.setText(appPreff.tvUrl().get());

          etIp.setSelection(appPreff.ip().get().length());
          etPort.setSelection(appPreff.port().get().length());
          etTvUrl.setSelection(appPreff.tvUrl().get().length());
     }

     /**
      * Update application button
      */
     @Click void buttonUpdateApp() {
          Utils.updateApp(this);
     }

     /**
      * Save settings
      */
     @Click void buttonOk() {
          // Save ip, port, site id, tv url to sharedprefferences
          appPreff.edit().siteId().put(etSiteId.getText().toString().trim()).ip().put(etIp.getText().toString().trim()).port().put(etPort.getText().toString().trim()).tvId().put(etTvId.getText().toString().trim()).tvUrl().put(etTvUrl.getText().toString().trim()).apply();
          // building correct server's urls
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());
          buttonCancel.performClick();
     }

     /**
      * on back press perform click
      */
     @Click void buttonCancel() {
          onBackPressed();
     }
}