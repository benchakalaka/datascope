package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.utils.AppSharedPreferences_;

/**
 * Datascope Systems Ltd.
 * 
 * @author Karpachev Ihor
 */
@EActivity ( R.layout.activity_application_settings ) public class UserSettingsActivity extends Activity {
     // Load views
     @Pref AppSharedPreferences_ appPreff;
     @ViewById EditText          etIp;
     @ViewById EditText          etPort;
     @ViewById Button            buttonOk;
     @ViewById Button            buttonCancel;

     @AfterViews void afterViews() {
          etIp.setText(appPreff.ip().get());
          etPort.setText(appPreff.port().get());
          etIp.setSelection(appPreff.ip().get().length());
          etPort.setSelection(appPreff.port().get().length());
     }

     @Click void buttonOk() {
          // Save ip and port
          appPreff.edit().ip().put(etIp.getText().toString().trim()).port().put(etPort.getText().toString().trim()).apply();
          // building correct server's urls
          RestAddresses.setServerAddress(appPreff.ip().get(), appPreff.port().get());
          // Start map activity
          startActivity(new Intent(getApplicationContext(), MapActivity_.class));
     }

     @Click void buttonCancel() {
          onBackPressed();
     }
}
