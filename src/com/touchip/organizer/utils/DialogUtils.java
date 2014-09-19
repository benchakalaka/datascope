package com.touchip.organizer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.communication.rest.model.ActivitiesAndRisksList.POJORoboSingleActivityAndRisk;
import com.touchip.organizer.communication.rest.model.RiskSchedule;
import com.touchip.organizer.communication.rest.model.SafetyAndEnvironmentalControlList.POJORoboSafetyAndEnvironmentalControl;
import com.touchip.organizer.communication.rest.model.TaskBriefing;

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
          tv.setGravity(Gravity.CENTER);
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

     public static void showRiskScheduleDialog(final DrawingCompaniesActivity_ activity, RiskSchedule risks) {
          final Dialog dialogRiskSchedule = new Dialog(activity);
          dialogRiskSchedule.setTitle(R.string.risk_information);
          dialogRiskSchedule.setContentView(R.layout.dialog_risk_schedule);

          TextView twPrinted = ((TextView) dialogRiskSchedule.findViewById(R.id.twPrinted));
          TextView twCompanyAndProjectName = ((TextView) dialogRiskSchedule.findViewById(R.id.twCompanyAndProjectName));
          TextView twCompanyName = ((TextView) dialogRiskSchedule.findViewById(R.id.twCompanyName));
          TextView twMeansAndFrequency = ((TextView) dialogRiskSchedule.findViewById(R.id.twMeansAndFrequency));
          TextView twActivities = ((TextView) dialogRiskSchedule.findViewById(R.id.twActivities));

          twPrinted.setText(String.format("Printed : %s", Utils.getCurrentDate()));
          twCompanyAndProjectName.setText(String.format("%s / %s", risks.companyName, risks.projectName));
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

     public static void showTaskBriefingDialog(final DrawingCompaniesActivity_ activity, TaskBriefing taskBriefing) {
          final Dialog dialogRiskSchedule = new Dialog(activity);
          dialogRiskSchedule.setTitle(R.string.task_control_information);
          dialogRiskSchedule.setContentView(R.layout.dialog_task_briefing);

          TextView twCompanyName = ((TextView) dialogRiskSchedule.findViewById(R.id.twCompanyName));

          TextView twScopeOfWorks = ((TextView) dialogRiskSchedule.findViewById(R.id.twScopeOfWorks));

          TextView twNameOfSupervisior = ((TextView) dialogRiskSchedule.findViewById(R.id.twNameOfSupervisior));
          TextView twNameOfSupervisiorDate = ((TextView) dialogRiskSchedule.findViewById(R.id.twNameOfSupervisiorDate));

          TextView twRevievedBy = ((TextView) dialogRiskSchedule.findViewById(R.id.twRevievedBy));
          TextView twRevievedByDate = ((TextView) dialogRiskSchedule.findViewById(R.id.twRevievedByDate));

          twCompanyName.setText(taskBriefing.siteName);

          twScopeOfWorks.setText(taskBriefing.scopeOfWork);

          twNameOfSupervisior.setText(taskBriefing.supervisor);
          twNameOfSupervisiorDate.setText(taskBriefing.supervisionDate);

          twRevievedBy.setText(taskBriefing.reviewer);
          twRevievedByDate.setText(taskBriefing.reviewingDate);

          TableLayout rootTableLayout = (TableLayout) dialogRiskSchedule.findViewById(R.id.tableLayout_risk_schedule);
          for ( POJORoboSafetyAndEnvironmentalControl safetyOrEnvControl : taskBriefing.safetyAndEnvironmentalControls ) {
               TableLayout singleActivityAndRiskHazard = (TableLayout) activity.getLayoutInflater().inflate(R.layout.single_risk_and_description_table_layout, null, false);
               ((TextView) singleActivityAndRiskHazard.findViewById(R.id.twActivitiesAndRiskHazard)).setText("* " + safetyOrEnvControl.controlName);
               ((TextView) singleActivityAndRiskHazard.findViewById(R.id.twControlMeasure)).setVisibility(View.GONE);
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
