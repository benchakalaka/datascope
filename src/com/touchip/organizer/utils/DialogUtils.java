package com.touchip.organizer.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;

public class DialogUtils {

     private DialogUtils () {

     }

     public static void showSaveDrawingDialog(final boolean askSave, final DrawingCompaniesActivity activity) {
          // save drawing
          AlertDialog.Builder saveDialog = new AlertDialog.Builder(activity);
          saveDialog.setTitle(Utils.getResources(R.string.save_drawing));
          saveDialog.setIcon(R.drawable.save);
          saveDialog.setMessage("Save drawing to device Gallery and to server?");
          saveDialog.setPositiveButton(Utils.getResources(R.string.yes), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    activity.saveAndSendDrawing(askSave);
               }
          });
          saveDialog.setNegativeButton(Utils.getResources(R.string.cancel), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    activity.onBackPressed();
               }
          });
          if ( askSave ) {
               saveDialog.show();
          } else {
               activity.saveAndSendDrawing(askSave);
          }
     }

}
