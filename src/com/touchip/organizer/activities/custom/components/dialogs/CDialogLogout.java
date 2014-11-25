package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QSystem;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ATv_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_logout ) public class CDialogLogout extends RelativeLayout {

     // ===================== views
     @ViewById Button            btnCancel , btnChangeUser , btnLogout;

     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     public CDialogLogout ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @Click void btnChangeUser() {
          // Create dialog
          Dialog changeUserdialog = new Dialog(activity);
          // Remove title
          changeUserdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          // Set view for dialog and display it
          changeUserdialog.setContentView(CDialogEnterPin_.build(activity));
          changeUserdialog.show();
          // hide choose action dialog
          this.dialog.dismiss();
     }

     @Click void btnLogout() {
          Utils.showCustomToast(this.activity, "Succesfully logged out.", R.drawable.logout);
          dialog.dismiss();
          QSystem.navigateToActivity(activity, ATv_.class);
     }

     @Click void btnCancel() {
          dialog.dismiss();
     }

}