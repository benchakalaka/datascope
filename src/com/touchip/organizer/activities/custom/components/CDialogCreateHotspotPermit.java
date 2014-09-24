package com.touchip.organizer.activities.custom.components;

import java.util.Date;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.communication.rest.model.ModelHotspotsAndTrades;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.CreateHotspotRequestListener;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_create_hotspot_permit ) public class CDialogCreateHotspotPermit extends RelativeLayout {

     // ===================== views
     @ViewById Spinner                   spinner;
     @ViewById ImageButton               ibValidFrom , ibValidTo;
     @ViewById TextView                  tvValidFrom , tvValidTo;

     // ====================== variable
     private final SpiceFragmentActivity activity;
     private Map <String, String>        params;
     public static int                   noteTypeId = R.drawable.rectangle_1;

     public CDialogCreateHotspotPermit ( SpiceFragmentActivity act ) {
          super(act.getApplicationContext());
          this.activity = act;
     }

     @AfterViews void afterViews() {
          // Create an ArrayAdapter using the string array and
          // a default spinner layout
          ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this.activity, R.array.permits_types, android.R.layout.simple_spinner_item);
          // Specify the layout to use when the list of
          // choices appears
          adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
          // Apply the adapter to the spinner
          spinner.setAdapter(adapter);
          // set up start date as today, so duration of hs is one day
          String date = Utils.DATE_FORMAT.format(new Date());
          tvValidFrom.setText(date);
          tvValidTo.setText(date);
     }

     public void setHttpParams(Map <String, String> vars) {
          this.params = vars;
     }

     @Click void ibValidTo() {
          final CaldroidFragment calendar = new CaldroidFragment();
          calendar.setCaldroidListener(new CaldroidListener() {

               @Override public void onSelectDate(Date date, View view) {

                    try {
                         String validFrom = tvValidFrom.getText().toString();
                         Date dateFrom = Utils.DATE_FORMAT.parse(validFrom);
                         if ( dateFrom.after(date) ) {
                              Utils.showToast(activity, Utils.getResources(R.string.date_should_equal_or_grater) + validFrom, true);
                              return;
                         }
                    } catch (Exception e) {
                         Utils.logw(e.getMessage());
                    }

                    tvValidTo.setText(Utils.DATE_FORMAT.format(date));
                    calendar.dismiss();
               }
          });
          calendar.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
     }

     @Click void ibValidFrom() {
          final CaldroidFragment calendar = new CaldroidFragment();
          calendar.setCaldroidListener(new CaldroidListener() {

               @Override public void onSelectDate(Date date, View view) {

                    Date now = new Date();
                    if ( !DateUtils.isSameDay(date, now) && now.compareTo(date) > 0 ) {
                         Utils.showToast(activity, "Start date could not be in past", true);
                         return;
                    }
                    Date validTo;
                    try {
                         validTo = Utils.DATE_FORMAT.parse(tvValidTo.getText().toString());
                         if ( date.after(validTo) ) {
                              tvValidTo.setText(Utils.DATE_FORMAT.format(date));
                         }
                    } catch (Exception e) {
                         Utils.logw(e.getMessage());
                    }
                    tvValidFrom.setText(Utils.DATE_FORMAT.format(date));
                    calendar.dismiss();
               }
          });
          calendar.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
     }

     @Click void ivOk() {
          params.put(HTTP_PARAMS.VALID_FROM, tvValidFrom.getText().toString());
          params.put(HTTP_PARAMS.VALID_TO, tvValidTo.getText().toString());
          params.put(HTTP_PARAMS.DESCRIPTION, spinner.getSelectedItem().toString());
          this.activity.showProgressDialog();

          SuperRequest <ModelHotspotsAndTrades> request = new SuperRequest <ModelHotspotsAndTrades>(ModelHotspotsAndTrades.class, RestAddresses.CREATE_HOTSPOT, null, this.params);
          this.activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateHotspotRequestListener(this.activity));
     }
}