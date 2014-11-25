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
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.colorpicker.ColorPicker;
import com.touchip.organizer.utils.colorpicker.ColorPicker.OnColorChangedListener;
import com.touchip.organizer.utils.colorpicker.OpacityBar;
import com.touchip.organizer.utils.colorpicker.OpacityBar.OnOpacityChangedListener;
import com.touchip.organizer.utils.colorpicker.SaturationBar;
import com.touchip.organizer.utils.colorpicker.SaturationBar.OnSaturationChangedListener;

@EViewGroup ( R.layout.dialog_color_picker ) public class CDialogColorPicker extends RelativeLayout implements OnColorChangedListener , OnOpacityChangedListener , OnSaturationChangedListener {

     // ===================== views
     @ViewById Button            btnOk , btnCancel;
     @ViewById ColorPicker       picker;
     @ViewById OpacityBar        opacitybar;
     @ViewById SaturationBar     saturationbar;

     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;
     private int                 colorToSet;

     public CDialogColorPicker ( SuperActivity context , Dialog d ) {
          super(context);
          this.activity = context;
          this.dialog = d;
     }

     @AfterViews void afterViews() {
          picker.addOpacityBar(opacitybar);
          picker.addSaturationBar(saturationbar);
          // adds listener to the colorpicker which is implemented in the activity
          picker.setOnColorChangedListener(this);
          // to turn of showing the old color
          picker.setShowOldCenterColor(true);
          // set listener when opacity has been changed
          opacitybar.setOnOpacityChangedListener(this);
          // set listener when saturation has been changed
          saturationbar.setOnSaturationChangedListener(this);

          Utils.showCustomToast(this.activity, R.string.choose_custom_color, R.drawable.color_picker);
          try {
               picker.setOldCenterColor(AGeneralWhiteBoard.WHITE_BOARD_DRAWING.getCurrentColor());
               picker.setColor(AGeneralWhiteBoard.WHITE_BOARD_DRAWING.getCurrentColor());
          } catch (Exception ex) {
               QLog.debug(ex.getMessage());
          }
     }

     @Override public void onColorChanged(int color) {
          colorToSet = color;
     }

     @Click void btnOk() {
          AGeneralWhiteBoard.WHITE_BOARD_DRAWING.disableEraserMode();
          // color selected by the user.
          AGeneralWhiteBoard.WHITE_BOARD_DRAWING.setColor(colorToSet);
          ((AGeneralWhiteBoard) this.activity).ivDrawingState.setBackgroundColor(colorToSet);
          Utils.showCustomToast(this.activity, R.string.new_color_has_been_set, R.drawable.color_picker);
          this.dialog.dismiss();
     }

     @Click void btnCancel() {
          this.dialog.dismiss();
     }

     @Override public void onSaturationChanged(int saturation) {
     }

     @Override public void onOpacityChanged(int opacity) {
     }
}