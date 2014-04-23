package com.touchip.organizer.activities.custom.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.communication.rest.request.UpdateTradeHotspotRequest;
import com.touchip.organizer.communication.rest.request.listener.UpdateTradeHotspotRequestListener;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class NumPad {

     static TextView         twAmount;
     static TextView         twAvailableAmount;
     static TextView         twDescritpion;
     static TextView         twCompanyName;

     // flag values
     public static final int NOFLAGS        = 0;
     public static final int HIDE_INPUT     = 1;
     public static final int HIDE_PROMPT    = 2;

     static Float            amountDue;

     static TextView         prompt;
     static TextView         promptValue;

     static String           buttonTag;

     static Button           btn1;
     static Button           btn2;
     static Button           btn3;
     static Button           btn4;
     static Button           btn5;
     static Button           btn6;
     static Button           btn7;
     static Button           btn8;
     static Button           btn9;
     static Button           btn0;
     static Button           btnC;
     static ImageButton      btnDot;
     AlertDialog             dialog;

     private String          value          = "";
     private NumPad          me;

     private int             flagHideInput  = 0;
     private int             flagHidePrompt = 0;
     private int             maxInputValue;
     private Activity        activity;

     public interface NumbPadInterface {
          public String numPadInputValue(String value);

          public String numPadCanceled();
     }

     OnClickListener        listener = new Button.OnClickListener() {
                                          @Override public void onClick(View v) {
                                               buttonTag = v.getTag().toString();
                                               v.startAnimation(AnimationManager.load(R.anim.pump_top, 200));
                                               if ( "".equals(buttonTag) ) {
                                                    value = "";
                                                    promptValue.setText("");

                                                    twAmount.setText("	Current amount: " + GlobalConstants.LAST_CLICKED_HOTSPOT.amount);
                                                    twAvailableAmount.setText("	Available amount: +" + GlobalConstants.LAST_CLICKED_HOTSPOT.availableAmount);
                                               } else {
                                                    appendNumber(buttonTag);
                                               }
                                          }
                                     };
     private ProgressDialog progressDialog;

     public String getValue() {

          return value;
     }

     public int getIntValue() {
          int retValue = 0;
          try {
               if ( !"".equals(value) ) {
                    retValue = Integer.valueOf(value);
               }
          } catch (Exception e) {
               Utils.logw(e.getMessage());
          }
          return retValue;
     }

     public int getInputMaxValue() {
          return this.maxInputValue;
     }

     public void show(int maximumInoutValue, final Activity a, final String promptString, int inFlags, final NumbPadInterface postrun) {
          me = this;
          this.maxInputValue = maximumInoutValue;
          flagHideInput = inFlags % 2;
          flagHidePrompt = (inFlags / 2) % 2;

          final Builder dlg = new AlertDialog.Builder(a);
          this.activity = a;
          if ( flagHidePrompt == 0 ) {
               dlg.setTitle(promptString);
          }
          // Inflate the dialog layout
          LayoutInflater inflater = a.getLayoutInflater();
          View iView = inflater.inflate(R.layout.dialog_trade_detail, null, true);

          ((Button) iView.findViewById(R.id.buttonCancel)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    dialog.dismiss();
               }
          });

          ((Button) iView.findViewById(R.id.buttonOk)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {

                    Map <String, Integer> params = new HashMap <String, Integer>();
                    params.put("id", GlobalConstants.LAST_CLICKED_HOTSPOT.id);
                    params.put("newAmount", me.getIntValue());

                    UpdateTradeHotspotRequest request = new UpdateTradeHotspotRequest(params);
                    ((DrawingCompaniesActivity) activity).getSpiceManager()
                              .execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new UpdateTradeHotspotRequestListener(activity, NumPad.this));

                    dialog.hide();
               }

          });

          twAmount = ((TextView) iView.findViewById(R.id.dialog_trade_details_textview_amount));
          twAvailableAmount = ((TextView) iView.findViewById(R.id.dialog_trade_details_textview_available_amount));
          twDescritpion = ((TextView) iView.findViewById(R.id.dialog_trade_details_textview_descritpion));
          twCompanyName = ((TextView) iView.findViewById(R.id.dialog_trade_details_textview_company_name));

          progressDialog = new ProgressDialog(activity);
          progressDialog.setTitle(Utils.getResources(R.string.loading));

          twAmount.setText("	Current amount: " + GlobalConstants.LAST_CLICKED_HOTSPOT.amount);
          twAvailableAmount.setText("	Available amount: +" + GlobalConstants.LAST_CLICKED_HOTSPOT.availableAmount);
          twDescritpion.setText("	Trade: " + GlobalConstants.LAST_CLICKED_HOTSPOT.description);
          twCompanyName.setText("	Company: " + FragmentCompaniesList.getCompanyNameById(GlobalConstants.LAST_CLICKED_HOTSPOT.companyId));

          promptValue = (TextView) iView.findViewById(R.id.promptValue);

          // Defaults
          value = "";
          promptValue.setText("");

          btn1 = (Button) iView.findViewById(R.id.button1);
          btn2 = (Button) iView.findViewById(R.id.button2);
          btn3 = (Button) iView.findViewById(R.id.button3);
          btn4 = (Button) iView.findViewById(R.id.button4);
          btn5 = (Button) iView.findViewById(R.id.button5);
          btn6 = (Button) iView.findViewById(R.id.button6);
          btn7 = (Button) iView.findViewById(R.id.button7);
          btn8 = (Button) iView.findViewById(R.id.button8);
          btn9 = (Button) iView.findViewById(R.id.button9);
          btn0 = (Button) iView.findViewById(R.id.button0);
          btnC = (Button) iView.findViewById(R.id.buttonC);
          btnDot = (ImageButton) iView.findViewById(R.id.buttonDot);

          btn1.setTag("1");
          btn2.setTag("2");
          btn3.setTag("3");
          btn4.setTag("4");
          btn5.setTag("5");
          btn6.setTag("6");
          btn7.setTag("7");
          btn8.setTag("8");
          btn9.setTag("9");
          btn0.setTag("0");
          btnC.setTag("");
          btnDot.setTag("-");

          btn1.setOnClickListener(listener);
          btn2.setOnClickListener(listener);
          btn3.setOnClickListener(listener);
          btn4.setOnClickListener(listener);
          btn5.setOnClickListener(listener);
          btn6.setOnClickListener(listener);
          btn7.setOnClickListener(listener);
          btn8.setOnClickListener(listener);
          btn9.setOnClickListener(listener);
          btn0.setOnClickListener(listener);
          btnC.setOnClickListener(listener);
          btnDot.setOnClickListener(listener);

          dlg.setView(iView);
          dialog = dlg.show();
     }

     void appendNumber(String inNumb) {
          if ( "-".equals(inNumb) ) {

               if ( StringUtils.isNotEmpty(value) ) {
                    value = value.substring(0, value.length() - 1);
                    promptValue.setText(value);
                    if ( value.length() > 0 ) {
                         twAmount.setText("	Current amount: " + Integer.valueOf(value));
                         twAvailableAmount.setText("	Available amount: +" + (maxInputValue - Integer.valueOf(value)));
                    } else {
                         twAmount.setText("	Current amount: " + GlobalConstants.LAST_CLICKED_HOTSPOT.amount);
                         twAvailableAmount.setText("	Available amount: +" + GlobalConstants.LAST_CLICKED_HOTSPOT.availableAmount);
                    }
                    twAmount.startAnimation(AnimationManager.load(R.anim.shake, 150));
                    twAvailableAmount.startAnimation(AnimationManager.load(R.anim.shake, 150));

               }
               return;
          }
          int inputedValue = Integer.valueOf(value + inNumb);

          if ( !"".equals(value) && inputedValue == 0 && Integer.valueOf(value) == 0 ) { return; }

          if ( inputedValue > maxInputValue ) {
               Utils.showCustomToast(activity, "Sorry you have no enough : " + GlobalConstants.LAST_CLICKED_HOTSPOT.description + ", max value is " + maxInputValue, R.drawable.trade);
               return;
          }

          value = value + inNumb;
          if ( flagHideInput == 1 ) {
               promptValue.setText(promptValue.getText() + "*");
          } else {
               promptValue.setText(promptValue.getText() + inNumb);
               twAmount.setText("	Current amount: " + inputedValue);
               twAvailableAmount.setText("	Available amount: +" + (maxInputValue - inputedValue));

               twAmount.startAnimation(AnimationManager.load(R.anim.shake, 150));
               twAvailableAmount.startAnimation(AnimationManager.load(R.anim.shake, 150));
          }
     }
}