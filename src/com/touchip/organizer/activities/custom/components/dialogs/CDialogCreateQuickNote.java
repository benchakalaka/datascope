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
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_create_quick_note ) public class CDialogCreateQuickNote extends RelativeLayout {

     // ===================== views
     @ViewById EditText                     etQuickNoteText;
     @ViewById Button                       btnOk;
     @ViewById TextView                     twValidFrom , twValidTo;
     @ViewById ImageButton                  ibValidFrom , ibValidTo;

     // ====================== variable
     private final SuperActivity            activity;
     private final HashMap <String, Object> requestParams;
     private final Dialog                   dialog;

     public CDialogCreateQuickNote ( SuperActivity context , Map requestParams , Dialog d ) {
          super(context);
          this.activity = context;
          this.requestParams = (HashMap <String, Object>) requestParams;
          this.dialog = d;
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
                         QLog.debug(e.getMessage());
                    }
                    twValidFrom.setText(Utils.DATE_FORMAT.format(date));
                    calendar.dismiss();
               }
          });
          calendar.show(this.activity.getSupportFragmentManager(), GlobalConstants.LOG_TAG);
     }

     /**
      * Handle create quick note button
      */
     @Click void btnOk() {
          requestParams.put(HTTP_PARAMS.DESCRIPTION, etQuickNoteText.getText().toString());
          requestParams.put(HTTP_PARAMS.DATE_VALID_FROM, twValidFrom.getText().toString());
          requestParams.put(HTTP_PARAMS.DATE_VALID_TO, twValidTo.getText().toString());
          ((ADrawingCompanies) this.activity).createHotspot(requestParams);
          dialog.dismiss();
     }

}