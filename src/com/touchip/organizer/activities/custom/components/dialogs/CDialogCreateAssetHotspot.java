package com.touchip.organizer.activities.custom.components.dialogs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelCompaniesAndHotspots;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseCreateAssetHotspot;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_create_asset ) public class CDialogCreateAssetHotspot extends RelativeLayout {

     // ===================== views
     @ViewById EditText                     etHighRiskText;
     @ViewById Button                       btnOk , btnCancel;
     @ViewById TextView                     twValidFrom , twValidTo;
     @ViewById ImageButton                  ibValidFrom , ibValidTo;

     // ====================== variable
     private final SuperActivity            activity;
     private final Dialog                   dialog;

     private final HashMap <String, Object> requestParams;

     public CDialogCreateAssetHotspot ( SuperActivity context , Map requestParams , Dialog hostDialog ) {
          super(context);
          this.activity = context;
          this.dialog = hostDialog;
          this.requestParams = (HashMap <String, Object>) requestParams;
     }

     @AfterViews void afterViews() {
          twValidFrom.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);
          twValidTo.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);
     }

     @Click void ibValidTo() {
          final CaldroidFragment calendar = Utils.getConfiguredCaldroid(new Date(), null);
          calendar.setCaldroidListener(new CaldroidListener() {

               @Override public void onSelectDate(Date date, View view) {

                    try {
                         String validFrom = twValidFrom.getText().toString();
                         Date dateFrom = Utils.DATE_FORMAT.parse(validFrom);
                         if ( dateFrom.after(date) ) {
                              QNotifications.showShortToast(activity, Utils.getResources(R.string.date_should_equal_or_grater) + validFrom);
                              return;
                         }
                    } catch (Exception e) {
                         QLog.debug(e.getMessage(), e);
                    }

                    twValidTo.setText(Utils.DATE_FORMAT.format(date));
                    calendar.dismiss();
               }
          });
          calendar.show(activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
     }

     @Click void ibValidFrom() {
          final CaldroidFragment calendar = Utils.getConfiguredCaldroid(new Date(), null);
          calendar.setCaldroidListener(new CaldroidListener() {

               @Override public void onSelectDate(Date date, View view) {

                    Date now = new Date();
                    if ( !DateUtils.isSameDay(date, now) && now.compareTo(date) > 0 ) {
                         QNotifications.showShortToast(activity, "Start date could not be in past");
                         return;
                    }
                    Date validTo;
                    try {
                         validTo = Utils.DATE_FORMAT.parse(twValidTo.getText().toString());
                         if ( date.after(validTo) ) {
                              twValidTo.setText(Utils.DATE_FORMAT.format(date));
                         }
                    } catch (Exception e) {
                         QLog.debug(e.getMessage(), e);
                    }
                    twValidFrom.setText(Utils.DATE_FORMAT.format(date));
                    calendar.dismiss();
               }
          });
          calendar.show(this.activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
     }

     @Click void btnOk() {
          requestParams.put(HTTP_PARAMS.DATE_VALID_FROM, twValidFrom.getText().toString());
          requestParams.put(HTTP_PARAMS.DATE_VALID_TO, twValidTo.getText().toString());
          SuperRequest <ModelCompaniesAndHotspots> request = new SuperRequest <ModelCompaniesAndHotspots>(activity, ModelCompaniesAndHotspots.class, RestAddresses.CREATE_HOTSPOT, requestParams);
          activity.execute(request, new ResponseCreateAssetHotspot(activity));
          dialog.dismiss();
     }

     @Click void btnCancel() {
          dialog.dismiss();
     }

}