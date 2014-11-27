package com.touchip.organizer.communication.rest.request.listener;

import java.util.List;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.squareup.timessquare.sample.R.color;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelDelieveriesList.POJORoboDelieverySlot;
import com.touchip.organizer.communication.rest.model.ModelDelivery;
import com.touchip.organizer.communication.rest.model.ModelDelivery.POJORoboDelieveryInfo;
import com.touchip.organizer.utils.DialogUtils;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class ResponseGetDeliveriesList implements RequestListener <ModelDelivery> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetDeliveriesList ( SuperActivity act ) {
          this.activity = act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          // update your UI
          QLog.debug(e.getMessage());
          // Utils.showToast(activity, R.string.connection_problem, true);
          this.activity.dissmissProgressDialog();
          QNotifications.showShortToast(activity, R.string.connection_problem);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(final ModelDelivery modelDeliveries) {
          if ( !QPreconditions.isNull(modelDeliveries) ) {
               final Dialog dialog = new Dialog(activity);
               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dialog.setContentView(R.layout.dialog_deliveries_list);
               ((TextView) dialog.findViewById(R.id.tvTitle)).setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);

               // DELIVERIES TABLE
               if ( !QPreconditions.isNullOrEmpty(modelDeliveries.deliveriesTable) ) {
                    TableLayout tlDelivgeriesList = (TableLayout) dialog.findViewById(R.id.tlDeliveriesList);
                    for ( int i = 0; i < modelDeliveries.deliveriesTable.size(); i++ ) {
                         POJORoboDelieveryInfo singleRow = modelDeliveries.deliveriesTable.get(i);
                         TableRow row = new TableRow(activity);
                         // Text Views
                         TextView twRef = new TextView(activity);
                         TextView twDeliverAt = new TextView(activity);
                         TextView twDuration = new TextView(activity);
                         TextView twGate = new TextView(activity);
                         TextView twDescription = new TextView(activity);
                         TextView twCompany = new TextView(activity);
                         TextView twContact = new TextView(activity);
                         TextView twStatus = new TextView(activity);

                         twRef.setText(String.valueOf(singleRow.refId));
                         twDeliverAt.setText(singleRow.deliveryTime);
                         twDuration.setText(singleRow.duration);
                         twGate.setText(singleRow.gate);
                         twDescription.setText(singleRow.description);
                         twCompany.setText(singleRow.company);
                         twContact.setText(singleRow.contact);
                         twStatus.setText(singleRow.status);

                         int white = activity.getResources().getColor(android.R.color.white);
                         twRef.setTextColor(white);
                         twDeliverAt.setTextColor(white);
                         twDuration.setTextColor(white);
                         twGate.setTextColor(white);
                         twDescription.setTextColor(white);
                         twCompany.setTextColor(white);
                         twContact.setTextColor(white);
                         twStatus.setTextColor(white);

                         twRef.setGravity(Gravity.CENTER);
                         twDeliverAt.setGravity(Gravity.CENTER);
                         twDuration.setGravity(Gravity.CENTER);
                         twGate.setGravity(Gravity.CENTER);
                         twDescription.setGravity(Gravity.CENTER);
                         twCompany.setGravity(Gravity.CENTER);
                         twContact.setGravity(Gravity.CENTER);
                         twStatus.setGravity(Gravity.CENTER);

                         twDeliverAt.setGravity(Gravity.CENTER);
                         twDuration.setGravity(Gravity.CENTER);
                         twGate.setGravity(Gravity.CENTER);
                         twDescription.setGravity(Gravity.CENTER);
                         twCompany.setGravity(Gravity.CENTER);
                         twContact.setGravity(Gravity.CENTER);
                         twStatus.setGravity(Gravity.CENTER);

                         row.addView(twRef, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twDeliverAt, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twDuration, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twGate, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twDescription, new TableRow.LayoutParams(300, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twCompany, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twContact, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         row.addView(twStatus, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));

                         TableRow rowDivider = new TableRow(activity);
                         TextView tvDivider = new TextView(activity);
                         tvDivider.setBackgroundColor(activity.getResources().getColor(color.Saddlebrown));
                         rowDivider.addView(tvDivider, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 2, 1f));

                         tlDelivgeriesList.addView(row);
                         tlDelivgeriesList.addView(rowDivider);
                    }
               }

               // DELIVERIES GATES
               if ( !QPreconditions.isNullOrEmpty(modelDeliveries.deliveries) ) {

                    TableLayout tlRoot = (TableLayout) dialog.findViewById(R.id.tlRoot);

                    ((Button) dialog.findViewById(R.id.dialogOk)).setOnClickListener(new OnClickListener() {
                         @Override public void onClick(View v) {
                              dialog.dismiss();
                         }
                    });

                    // Fill header (time)
                    TableRow headerRow = (TableRow) tlRoot.findViewById(R.id.tableHeader);
                    List <POJORoboDelieverySlot> slots = modelDeliveries.deliveries.get(0).slots;

                    for ( int i = 0; i < slots.size(); i++ ) {
                         TextView tw = new TextView(activity);
                         tw.setText(modelDeliveries.deliveries.get(0).slots.get(i).slot.substring(0, 5));
                         tw.setBackgroundColor(activity.getResources().getColor(color.Amazon));
                         tw.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                         tw.setGravity(Gravity.CENTER);
                         tw.setTextColor(activity.getResources().getColor(color.Gainsboro));
                         tw.setTextAppearance(activity, android.R.style.TextAppearance_Small);
                         headerRow.addView(tw);
                    }

                    for ( int i = 0; i < modelDeliveries.deliveries.size(); i++ ) {
                         List <POJORoboDelieverySlot> singleSlotRow = modelDeliveries.deliveries.get(i).slots;

                         TableRow slotRow = new TableRow(activity);
                         for ( int j = 0; j < singleSlotRow.size(); j++ ) {
                              TextView twSingleSlotItem = new TextView(activity);
                              int amount = singleSlotRow.get(j).deliveriesAmount;
                              twSingleSlotItem.setText(String.valueOf(amount));

                              twSingleSlotItem.setOnTouchListener(new OnTouchListener() {

                                   @Override public boolean onTouch(View v, MotionEvent event) {
                                        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                                             v.startAnimation(AnimationManager.load(R.anim.fade, 500));
                                        }
                                        return false;
                                   }
                              });

                              twSingleSlotItem.setGravity(Gravity.CENTER);
                              twSingleSlotItem.setTextAppearance(activity, android.R.style.TextAppearance_Large);
                              if ( amount == 0 ) {
                                   twSingleSlotItem.setBackgroundResource(R.layout.cell_shape);
                              } else {
                                   twSingleSlotItem.setBackgroundResource(amount >= 5 ? R.layout.cell_shape_more_than_five : R.layout.cell_shape_less_than_five);
                              }

                              twSingleSlotItem.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));

                              final String pending[] = singleSlotRow.get(j).pending;
                              final String accepted[] = singleSlotRow.get(j).accepted;

                              final String gateName = modelDeliveries.deliveries.get(i).gateName;

                              twSingleSlotItem.setOnClickListener(new OnClickListener() {

                                   @Override public void onClick(View v) {
                                        String pendingAcceptedMessage = "Gate: " + gateName + "\n";
                                        pendingAcceptedMessage += "Accepted (ref / materials)\n";
                                        if ( null != accepted ) {
                                             for ( String element : accepted ) {
                                                  pendingAcceptedMessage += element + "\n";
                                             }
                                        }
                                        pendingAcceptedMessage += "Pending (ref / materials)\n";
                                        if ( null != pending ) {
                                             for ( String element : pending ) {
                                                  pendingAcceptedMessage += element + "\n";
                                             }
                                        }

                                        DialogUtils.showAcceptedPendingMessage(pendingAcceptedMessage, activity);

                                        // Utils.showToast(activity, pendingAcceptedMessage, false);
                                   }
                              });

                              slotRow.addView(twSingleSlotItem);
                         }
                         tlRoot.addView(slotRow);

                    }

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    int width , height;

                    if ( Build.VERSION.SDK_INT > VERSION_CODES.FROYO ) {
                         width = activity.getWindowManager().getDefaultDisplay().getWidth();
                         height = activity.getWindowManager().getDefaultDisplay().getHeight();
                    } else {
                         Point point = new Point();
                         activity.getWindowManager().getDefaultDisplay().getSize(point);
                         width = point.x;
                         height = point.y;
                    }
                    lp.width = width;
                    lp.height = height;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();
               } else {
                    Utils.showCustomToast(activity, Utils.getResources(R.string.deliveries_list_empty) + " for " + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.delivery);
               }
          } else {
               Utils.showCustomToast(activity, Utils.getResources(R.string.deliveries_list_empty) + " for " + GlobalConstants.SITE_PLAN_IMAGE_NAME, R.drawable.delivery);
          }

          this.activity.dissmissProgressDialog();

     }
}
