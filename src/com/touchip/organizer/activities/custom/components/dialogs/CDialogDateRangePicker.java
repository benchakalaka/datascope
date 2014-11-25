package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QLog;
import android.app.Dialog;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.calendar.DatePickerController;
import com.touchip.organizer.utils.calendar.DayPickerView;

@EViewGroup ( R.layout.dialog_calendar_range_date_picker ) public class CDialogDateRangePicker extends RelativeLayout implements DatePickerController {

     // ===================== views
     @ViewById Button            btnOk , btnCancel;
     @ViewById DayPickerView     dpvDateRangePicker;
     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     public CDialogDateRangePicker ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          // set current brush size
          dpvDateRangePicker.setmController(this);
     }

     @Click void btnOk() {
          Utils.showCustomToast(this.activity, Utils.getResources(R.string.brush_size_is_set_to_pixels), R.drawable.medium_2);
          dialog.dismiss();
     }

     @Click void btnCancel() {
          dialog.dismiss();
     }

     @Override public int getMaxYear() {
          // TODO Auto-generated method stub
          return 2016;
     }

     @Override public void onDayOfMonthSelected(int year, int month, int day) {
          QLog.debug(year + " - " + month + " - " + day);
     }

}