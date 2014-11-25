package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;

@EViewGroup ( R.layout.dialog_save_whiteboard ) public class CDialogSaveWhiteBoard extends RelativeLayout {

     // ===================== views
     @ViewById Button            btnOk , btnCancel;
     @ViewById EditText          etWbName;
     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     public CDialogSaveWhiteBoard ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          etWbName.setVisibility(AGeneralWhiteBoard.IS_WHITEBOARD_NEW ? View.VISIBLE : View.GONE);
     }

     @Click void btnOk() {
          AGeneralWhiteBoard.IS_NEED_TO_BACK = true;
          ((AGeneralWhiteBoard) this.activity).saveAndSendDrawing(AGeneralWhiteBoard.IS_WHITEBOARD_NEW ? etWbName.getText().toString() : null);
          dialog.dismiss();
     }

     @Click void btnCancel() {
          AGeneralWhiteBoard.WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.STANDART_DRAWING);
          ((AGeneralWhiteBoard) this.activity).onBackPressed();
          dialog.dismiss();
     }

}