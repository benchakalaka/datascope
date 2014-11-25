package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_clear_all ) public class CDialogClearWhiteBoard extends RelativeLayout {

     // ===================== views
     @ViewById Button            btnOk , btnCancel;
     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     public CDialogClearWhiteBoard ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @Click void btnOk() {
          AGeneralWhiteBoard.WHITE_BOARD_DRAWING.disableEraserMode();
          AGeneralWhiteBoard.WHITE_BOARD_DRAWING.clearAll();
          Utils.showCustomToast(activity, R.string.whiteboard_clear, R.drawable.success);
          dialog.dismiss();
     }

     @Click void btnCancel() {
          dialog.dismiss();
     }

}