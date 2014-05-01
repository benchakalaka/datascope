package com.touchip.organizer.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.communication.rest.model.ActivitiesAndRisksList.POJORoboSingleActivityAndRisk;
import com.touchip.organizer.communication.rest.model.RiskSchedule;

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

     public static void showRiskScheduleDialog(final DrawingCompaniesActivity_ activity, RiskSchedule risks) {
          final Dialog dialogRiskSchedule = new Dialog(activity);
          dialogRiskSchedule.setTitle("Risks inforamtion");
          dialogRiskSchedule.setContentView(R.layout.dialog_risk_schedule);

          TextView twPrinted = ((TextView) dialogRiskSchedule.findViewById(R.id.twPrinted));
          TextView twCompanyAndProjectName = ((TextView) dialogRiskSchedule.findViewById(R.id.twCompanyAndProjectName));
          TextView twCompanyName = ((TextView) dialogRiskSchedule.findViewById(R.id.twCompanyName));
          TextView twMeansAndFrequency = ((TextView) dialogRiskSchedule.findViewById(R.id.twMeansAndFrequency));
          TextView twActivities = ((TextView) dialogRiskSchedule.findViewById(R.id.twActivities));

          twPrinted.setText("Printed : " + Utils.getCurrentDate());
          twCompanyAndProjectName.setText(risks.companyName + " / " + risks.projectName);
          twCompanyName.setText(risks.companyName);
          twMeansAndFrequency.setText(risks.mean);
          twActivities.setText("Activities:\n   Start on Site - " + risks.startDate + "\n   End on Site - " + risks.endDate + "\n   Scope of Works - " + risks.scope);

          TableLayout rootTableLayout = (TableLayout) dialogRiskSchedule.findViewById(R.id.tableLayout_risk_schedule);
          for ( POJORoboSingleActivityAndRisk risk : risks.activitiesAndRisksList ) {
               TableLayout singleActivityAndRiskHazard = (TableLayout) activity.getLayoutInflater().inflate(R.layout.single_risk_and_description_table_layout, null, false);
               ((TextView) singleActivityAndRiskHazard.findViewById(R.id.twActivitiesAndRiskHazard)).setText(risk.riskName);
               ((TextView) singleActivityAndRiskHazard.findViewById(R.id.twControlMeasure)).setText(risk.description);
               rootTableLayout.addView(singleActivityAndRiskHazard);
          }

          ((Button) dialogRiskSchedule.findViewById(R.id.dialog_button_ok)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialogRiskSchedule.dismiss();
               }
          });

          dialogRiskSchedule.show();
     }
}
