package com.touchip.organizer.activities;

import java.util.Date;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList.POJORoboOneDateToHighlight;
import com.touchip.organizer.communication.rest.model.User;
import com.touchip.organizer.communication.rest.request.LoginRequest;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

/**
 * @author Karpachev Ihor
 */
public class EnterPinActivity extends SpiceFragmentActivity {

     String                                               userEntered;
     String                                               userPin              = "8888";

     final int                                            PIN_LENGTH           = 4;
     boolean                                              keyPadLockedFlag     = false;

     TextView                                             titleView;

     TextView                                             pinBox0;
     TextView                                             pinBox1;
     TextView                                             pinBox2;
     TextView                                             pinBox3;

     TextView[]                                           pinBoxArray;

     TextView                                             statusView;

     Button                                               button0;
     Button                                               button1;
     Button                                               button2;
     Button                                               button3;
     Button                                               button4;
     Button                                               button5;
     Button                                               button6;
     Button                                               button7;
     Button                                               button8;
     Button                                               button9;
     Button                                               button10;
     Button                                               buttonExit;
     Button                                               buttonDelete;

     // Setup listener
     public final com.roomorama.caldroid.CaldroidListener onDateChangeListener = new com.roomorama.caldroid.CaldroidListener() {
                                                                                    @Override public void onSelectDate(final Date date, View view) {
                                                                                         showProgressDialog();
                                                                                         GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(date);
                                                                                         for ( POJORoboOneDateToHighlight singleDate : DataAccess.datestoHighlight ) {
                                                                                              if ( singleDate.date.equals(GlobalConstants.SITE_PLAN_IMAGE_NAME) ) {
                                                                                                   if ( (singleDate.floors) != null && (!singleDate.floors.isEmpty()) ) {
                                                                                                        GlobalConstants.CURRENT_FLOOR = singleDate.floors.get(0);
                                                                                                   } else {
                                                                                                        Utils.showCustomToast(EnterPinActivity.this, R.string.error_obtaining_floors_areas, R.drawable.hide_hotspot);
                                                                                                        return;
                                                                                                   }
                                                                                              }
                                                                                         }
                                                                                         // DownloadSitePlanRequest request = new DownloadSitePlanRequest();
                                                                                         // getSpiceManager().execute(request, request.createCacheKey(),
                                                                                         // DurationInMillis.ALWAYS_EXPIRED, new
                                                                                         // DownloadSitePlanRequestListenerStartActivity(EnterPinActivity.this));
                                                                                         startActivity(new Intent(EnterPinActivity.this, AMenuModules_.class));
                                                                                    }

                                                                                    @Override public void onChangeMonth(int month, int year) {
                                                                                    }

                                                                                    @Override public void onLongClickDate(Date date, View view) {
                                                                                    }

                                                                                    @Override public void onCaldroidViewCreated() {
                                                                                    }
                                                                               };

     @Override protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
          userEntered = "";

          setContentView(R.layout.activity_pin_entry_view);
          Typeface xpressive = Typeface.createFromAsset(getAssets(), "fonts/XpressiveBold.ttf");

          buttonExit = (Button) findViewById(R.id.buttonExit);
          buttonExit.setOnClickListener(new View.OnClickListener() {
               @Override public void onClick(View v) {
                    onBackPressed();
               }
          });
          buttonExit.setTypeface(xpressive);

          buttonDelete = (Button) findViewById(R.id.buttonDeleteBack);
          buttonDelete.setOnClickListener(new View.OnClickListener() {
               @Override public void onClick(View v) {

                    if ( keyPadLockedFlag == true ) { return; }

                    if ( userEntered.length() > 0 ) {
                         userEntered = userEntered.substring(0, userEntered.length() - 1);
                         pinBoxArray[userEntered.length()].setText("");
                    }

               }

          });

          titleView = (TextView) findViewById(R.id.titleBox);
          titleView.setTypeface(xpressive);

          pinBox0 = (TextView) findViewById(R.id.pinBox0);
          pinBox1 = (TextView) findViewById(R.id.pinBox1);
          pinBox2 = (TextView) findViewById(R.id.pinBox2);
          pinBox3 = (TextView) findViewById(R.id.pinBox3);

          pinBoxArray = new TextView[PIN_LENGTH];
          pinBoxArray[0] = pinBox0;
          pinBoxArray[1] = pinBox1;
          pinBoxArray[2] = pinBox2;
          pinBoxArray[3] = pinBox3;

          statusView = (TextView) findViewById(R.id.statusMessage);
          statusView.setTypeface(xpressive);

          View.OnClickListener pinButtonHandler = new View.OnClickListener() {
               @Override public void onClick(View v) {

                    if ( keyPadLockedFlag == true ) { return; }

                    Button pressedButton = (Button) v;

                    if ( userEntered.length() < PIN_LENGTH ) {
                         userEntered = userEntered + pressedButton.getText();
                         Log.v("PinView", "User entered=" + userEntered);

                         // Update pin boxes
                         pinBoxArray[userEntered.length() - 1].setText("8");

                         if ( userEntered.length() == PIN_LENGTH ) {
                              showProgressDialog();
                              HashMap <String, String> params = new HashMap <String, String>();

                              params.put("pin", userEntered);
                              LoginRequest request = new LoginRequest(params);
                              // getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new
                              // LoginRequestListener(EnterPinActivity.this));

                         }
                    } else {
                         // Roll over
                         pinBoxArray[0].setText("");
                         pinBoxArray[1].setText("");
                         pinBoxArray[2].setText("");
                         pinBoxArray[3].setText("");

                         userEntered = "";

                         statusView.setText("");

                         userEntered = userEntered + pressedButton.getText();
                         Log.v("PinView", "User entered=" + userEntered);

                         // Update pin boxes
                         pinBoxArray[userEntered.length() - 1].setText("8");

                    }

               }
          };

          button0 = (Button) findViewById(R.id.button0);
          button0.setTypeface(xpressive);
          button0.setOnClickListener(pinButtonHandler);

          button1 = (Button) findViewById(R.id.button1);
          button1.setTypeface(xpressive);
          button1.setOnClickListener(pinButtonHandler);

          button2 = (Button) findViewById(R.id.button2);
          button2.setTypeface(xpressive);
          button2.setOnClickListener(pinButtonHandler);

          button3 = (Button) findViewById(R.id.button3);
          button3.setTypeface(xpressive);
          button3.setOnClickListener(pinButtonHandler);

          button4 = (Button) findViewById(R.id.button4);
          button4.setTypeface(xpressive);
          button4.setOnClickListener(pinButtonHandler);

          button5 = (Button) findViewById(R.id.button5);
          button5.setTypeface(xpressive);
          button5.setOnClickListener(pinButtonHandler);

          button6 = (Button) findViewById(R.id.button6);
          button6.setTypeface(xpressive);
          button6.setOnClickListener(pinButtonHandler);

          button7 = (Button) findViewById(R.id.button7);
          button7.setTypeface(xpressive);
          button7.setOnClickListener(pinButtonHandler);

          button8 = (Button) findViewById(R.id.button8);
          button8.setTypeface(xpressive);
          button8.setOnClickListener(pinButtonHandler);

          button9 = (Button) findViewById(R.id.button9);
          button9.setTypeface(xpressive);
          button9.setOnClickListener(pinButtonHandler);

          buttonDelete = (Button) findViewById(R.id.buttonDeleteBack);
          buttonDelete.setTypeface(xpressive);

     }

     public void displayNetwqorkProblemLabel() {
          dissmissProgressDialog();
          statusView.setTextColor(Color.RED);
          statusView.setText(R.string.check_network);
          keyPadLockedFlag = true;
          Utils.logw("Check network connection");

          // Roll over
          pinBoxArray[0].setText("");
          pinBoxArray[1].setText("");
          pinBoxArray[2].setText("");
          pinBoxArray[3].setText("");

          userEntered = "";

          keyPadLockedFlag = false;

          Utils.showToast(EnterPinActivity.this, R.string.user_not_found, true);
     }

     public void checkUser(User u) {
          dissmissProgressDialog();
          // Check if entered PIN is correct
          if ( null != u ) {
               Utils.showCustomToast(EnterPinActivity.this, "Hi " + u.firstName + " " + u.lastName, R.drawable.user);
               statusView.setTextColor(Color.GREEN);
               statusView.setText("Correct");
               Utils.logw("Correct PIN");
               pinBoxArray[0].setText("");
               pinBoxArray[1].setText("");
               pinBoxArray[2].setText("");
               pinBoxArray[3].setText("");
               userEntered = "";
               keyPadLockedFlag = false;
               showProgressDialog();
               // 2 is id for llandudno project
               // GetDatesToHighlightRequest request = new GetDatesToHighlightRequest();
               // getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new
               // DatesToHighlightRequestListener(EnterPinActivity.this));
          } else {
               statusView.setTextColor(Color.RED);
               statusView.setText(R.string.user_not_found);
               keyPadLockedFlag = true;
               Utils.logw("Wrong PIN");

               // Roll over
               pinBoxArray[0].setText("");
               pinBoxArray[1].setText("");
               pinBoxArray[2].setText("");
               pinBoxArray[3].setText("");

               userEntered = "";

               keyPadLockedFlag = false;

               Utils.showToast(EnterPinActivity.this, R.string.user_not_found, true);
          }
     }
}
