package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QNotifications;
import android.app.Dialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;

@EViewGroup ( R.layout.dialog_input_text_for_drawing ) public class CDialogInputTextToDraw extends RelativeLayout {

     // ===================== views
     @ViewById Button            btnOk , btnCancel;
     @ViewById EditText          etTextToDraw;

     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     public CDialogInputTextToDraw ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @Click void btnCancel() {
          this.dialog.dismiss();
     }

     @Click void btnOk() {
          if ( TextUtils.isEmpty(etTextToDraw.getText().toString()) ) {
               QNotifications.showShortToast(activity, R.string.field_cannot_be_empty);
          } else {
               AGeneralWhiteBoard.WHITE_BOARD_DRAWING.disableEraserMode();
               AGeneralWhiteBoard.WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.DRAW_TEXT);
               AGeneralWhiteBoard.WHITE_BOARD_DRAWING.setTextToDraw(etTextToDraw.getText().toString().replace("\n", " "));
               this.dialog.dismiss();
          }
     }

}