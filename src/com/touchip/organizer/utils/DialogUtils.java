package com.touchip.organizer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;

public class DialogUtils {

     private DialogUtils () {

     }

     public static final void showAcceptedPendingMessage(String message, final Activity act) {

          AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act);

          LinearLayout layout = new LinearLayout(act);
          LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          layout.setOrientation(LinearLayout.VERTICAL);
          layout.setLayoutParams(parms);

          layout.setGravity(Gravity.CLIP_VERTICAL);
          layout.setPadding(2, 2, 2, 2);

          TextView tv = new TextView(act);
          tv.setText(message);
          tv.setPadding(40, 40, 40, 40);
          tv.setGravity(Gravity.START);
          tv.setTextSize(20);

          layout.addView(tv);

          alertDialogBuilder.setView(layout);
          alertDialogBuilder.setCancelable(true);

          // Setting Positive "Yes" Button
          alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
          });

          AlertDialog alertDialog = alertDialogBuilder.create();

          try {
               alertDialog.show();
          } catch (Exception e) {
               // WindowManager$BadTokenException will be caught and the app would
               // not display the 'Force Close' message
               e.printStackTrace();
          }

     }

     public static void showSaveDrawingDialog(final boolean askSave, final ADrawingCompanies activity) {
          // save drawing
          AlertDialog.Builder saveDialog = new AlertDialog.Builder(activity);
          saveDialog.setTitle(Utils.getResources(R.string.save_drawing));
          saveDialog.setIcon(R.drawable.save);
          saveDialog.setMessage(R.string.save_and_logoff);
          saveDialog.setPositiveButton(Utils.getResources(R.string.yes), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    activity.saveAndSendDrawing(askSave, GlobalConstants.CURRENT_FLOOR);
               }
          });
          saveDialog.setNegativeButton(Utils.getResources(R.string.cancel), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
               }
          });
          if ( askSave ) {
               saveDialog.show();
          } else {
               activity.saveAndSendDrawing(askSave, GlobalConstants.CURRENT_FLOOR);
          }
     }

}