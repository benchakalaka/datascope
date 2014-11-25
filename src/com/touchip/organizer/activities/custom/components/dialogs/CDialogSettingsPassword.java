package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QSystem;
import android.app.Dialog;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AUserSettings_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_settings_password ) public class CDialogSettingsPassword extends RelativeLayout {

     // ===================== views
     @ViewById EditText          etPassword;
     @ViewById Button            btnOk , btnCancel;

     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     /**
      * Create view
      * 
      * @param act
      *             activity that triggered show dialog event
      * @param hostDialog
      *             dialog that hots this view (in order to dissmis dialog in case of cancel click)
      */
     public CDialogSettingsPassword ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
     }

     @Click void btnCancel() {
          this.dialog.dismiss();
     }

     @Click void btnOk() {
          if ( etPassword.getText().toString().equals("datascope") ) {
               this.dialog.dismiss();
               QSystem.navigateToActivity(activity, AUserSettings_.class);
          } else {
               Utils.showCustomToast(activity, R.string.invalid_password, R.drawable.hide_hotspot);
          }

     }

}