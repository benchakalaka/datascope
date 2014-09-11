package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.TvActivity_;
import com.touchip.organizer.activities.UserSettingsActivity_;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.ab_tv ) public class ActionBarTv extends HorizontalScrollView {

     // Load views
     @ViewById LinearLayout            llSettings;

     // Parent activity
     private final TvActivity_         activity;

     private final AlertDialog.Builder builder;

     /**
      * Default constructor
      * 
      * @param context
      *             parent activity as parametr
      */
     public ActionBarTv ( Context context ) {
          super(context);
          this.activity = (TvActivity_) context;
          builder = new AlertDialog.Builder(activity);
     }

     @Click void llSettings() {
          // activity.startActivity(new Intent(activity, MapActivity_.class));

          // if ( hasMapSupport() ) {
          // activity.startActivity(new Intent(activity, KindleMapActivity_.class));
          // } else {
          // activity.startActivity(new Intent(activity, UserSettingsActivity_.class));
          // }
          final EditText input = new EditText(activity);

          input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          // input.setSelection(input.getText().length());

          // input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
          builder.setView(input);
          builder.setTitle(getResources().getString(R.string.password_required));
          builder.setIcon(R.drawable.menu_info);
          builder.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    if ( input.getText().toString().equals("datascope") ) {
                         activity.startActivity(new Intent(activity, UserSettingsActivity_.class));
                    } else {
                         Utils.showCustomToast(activity, R.string.invalid_password, R.drawable.hide_hotspot);
                    }
               }
          });
          builder.setNegativeButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
               }
          });
          builder.create().show();

     }

     public boolean hasMapSupport() {
          try {
               Class.forName("com.amazon.geo.maps.MapView");
               return true;
          } catch (Exception e) {
               return false;
          }
     }

}
