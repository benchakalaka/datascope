package com.touchip.organizer.activities.custom.components.dialogs;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ANotes;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_create_note ) public class CDialogCreateNote extends RelativeLayout implements android.view.View.OnClickListener , OnDateSetListener {

     // ===================== views
     @ViewById TextView     twEndDateL , twStartDate;
     @ViewById EditText     etComments , etName;
     @ViewById ImageView    ivTypeOfNote , ivOk , ibColour1 , ibColour2 , ibColour3 , ibColour4 , ibColour5 , ibColour6 , ibColour7 , ibColour8 , ibColour9 , ibColour10 , ibColour11 , ibColour12 , ibColour13 , ibColour14 , ibColour15 , ibColour16 , ibColour17;

     // ====================== variable
     private final Activity activity;
     public static int      noteTypeId = R.drawable.rectangle_1;

     public CDialogCreateNote ( Context context , Activity act ) {
          super(context);
          this.activity = act;
     }

     @Click void twEndDateL() {
          Date date = null;
          try {
               date = DateUtils.parseDate(twEndDateL.getText().toString(), Utils.DATE_FORMAT_YYYY_MM_DD);
          } catch (ParseException e) {
               e.printStackTrace();
          }
          if ( null == date ) {
               date = new Date();
          }
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
          datePickerDialog.setYearRange(2010, 2030);
          datePickerDialog.setCloseOnSingleTapDay(false);
          datePickerDialog.show(((ANotes) activity).getSupportFragmentManager(), "TAG");
     }

     @AfterViews void afterViews() {
          ibColour1.setOnClickListener(this);
          ibColour2.setOnClickListener(this);
          ibColour3.setOnClickListener(this);
          ibColour4.setOnClickListener(this);
          ibColour5.setOnClickListener(this);
          ibColour6.setOnClickListener(this);
          ibColour7.setOnClickListener(this);
          ibColour8.setOnClickListener(this);
          ibColour9.setOnClickListener(this);
          ibColour10.setOnClickListener(this);
          ibColour11.setOnClickListener(this);
          ibColour12.setOnClickListener(this);
          ibColour13.setOnClickListener(this);
          ibColour14.setOnClickListener(this);
          ibColour15.setOnClickListener(this);
          ibColour16.setOnClickListener(this);
          ibColour17.setOnClickListener(this);
     }

     @Click void ivTypeOfNote() {
          if ( noteTypeId == R.drawable.rectangle_1 ) {
               noteTypeId = R.drawable.triangle_1;
               ivTypeOfNote.setImageResource(noteTypeId);
               return;
          }

          if ( noteTypeId == R.drawable.triangle_1 ) {
               noteTypeId = R.drawable.circle;
               ivTypeOfNote.setImageResource(noteTypeId);
               return;
          }

          if ( noteTypeId == R.drawable.circle ) {
               noteTypeId = R.drawable.rectangle_1;
               ivTypeOfNote.setImageResource(noteTypeId);
               return;
          }
     }

     @Override public void onClick(View v) {
          ibColour1.setBackgroundResource(android.R.color.transparent);
          ibColour2.setBackgroundResource(android.R.color.transparent);
          ibColour3.setBackgroundResource(android.R.color.transparent);
          ibColour4.setBackgroundResource(android.R.color.transparent);
          ibColour5.setBackgroundResource(android.R.color.transparent);
          ibColour6.setBackgroundResource(android.R.color.transparent);
          ibColour7.setBackgroundResource(android.R.color.transparent);
          ibColour8.setBackgroundResource(android.R.color.transparent);
          ibColour9.setBackgroundResource(android.R.color.transparent);
          ibColour10.setBackgroundResource(android.R.color.transparent);
          ibColour11.setBackgroundResource(android.R.color.transparent);
          ibColour12.setBackgroundResource(android.R.color.transparent);
          ibColour13.setBackgroundResource(android.R.color.transparent);
          ibColour14.setBackgroundResource(android.R.color.transparent);
          ibColour15.setBackgroundResource(android.R.color.transparent);
          ibColour16.setBackgroundResource(android.R.color.transparent);
          ibColour17.setBackgroundResource(android.R.color.transparent);
          v.setBackgroundResource(R.drawable.transparent_inside_and_white_round_border);
          // set bg color of note
          int color = Color.parseColor(v.getTag().toString());
          if ( color == Color.WHITE ) {
               ivTypeOfNote.setBackgroundColor(Color.TRANSPARENT);
          } else {
               ivTypeOfNote.setBackgroundColor(color);
          }
     }

     @Override public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
          month = month + 1;
          twEndDateL.setText(year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day));
     }
}