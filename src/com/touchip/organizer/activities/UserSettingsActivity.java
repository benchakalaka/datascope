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
 * Datascope Systems Ltd.
 * 
 * @author Karpachev Ihor
 */
@EActivity ( R.layout.activity_application_settings ) public class UserSettingsActivity extends SpiceActivity {
     // Load views
     @Pref AppSharedPreferences_ appPreff;
     @ViewById EditText          etIp;
     @ViewById EditText          etPort;
     @ViewById EditText          etTvId;
     @ViewById EditText          etSiteId;
     @ViewById Button            buttonOk;
     @ViewById Button            buttonCancel;
     @ViewById Button            buttonUpdateApp;

     @AfterViews void afterViews() {
          etIp.setText(appPreff.ip().get());
          etPort.setText(appPreff.port().get());
          etTvId.setText(appPreff.tvId().get());
          etSiteId.setText(appPreff.siteId().get());
          etIp.setSelection(appPreff.ip().get().length());
          etPort.setSelection(appPreff.port().get().length());

     }

     @Click void buttonUpdateApp() {

          Utils.updateApp(this);
     }

     /**
      * Save settings and GOTO MapActivity
      */
     @Click void buttonOk() {
          // Save ip and port
          appPreff.edit().siteId().put(etSiteId.getText().toString().trim()).ip().put(etIp.getText().toString().trim()).port().put(etPort.getText().toString().trim()).tvId().put(etTvId.getText().toString().trim()).apply();
          // building correct server's urls
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());
          // Start map activity
          // startActivity(new Intent(getApplicationContext(), TvActivity_.class));
          buttonCancel();
     }

     /**
      * GOTO StartActivity
      */
     @Click void buttonCancel() {
          onBackPressed();
     }
}
