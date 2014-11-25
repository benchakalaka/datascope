package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogColorPicker_;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.ab_white_board_drawing ) public class ActionBarGeneralWhiteboard extends HorizontalScrollView implements View.OnClickListener {
     // Load views
     @ViewById TextView                             twDataAndTimeCreated;
     @AnimationRes ( R.anim.pump_bottom ) Animation pumpBottom;
     @ViewById ImageView                            ibColour1 , ibColour2 , ibColour3 , ibColour5;
     private final SuperActivity                    activity;
     @ViewById ImageButton                          ibColorPicker;

     /**
      * Default constructor
      */
     public ActionBarGeneralWhiteboard ( SuperActivity context ) {
          super(context);
          this.activity = context;
     }

     @Click void ibColorPicker() {
          ibColorPicker.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          Dialog changeColor = Utils.getConfiguredDialog(this.activity);
          changeColor.setContentView(CDialogColorPicker_.build(this.activity, changeColor));
          changeColor.show();
     }

     @AfterViews void afterViews() {
          try {
               twDataAndTimeCreated.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME + (null == GlobalConstants.LAST_CLICKED_WHITE_BOARD ? " not saved" : " created at " + GlobalConstants.LAST_CLICKED_WHITE_BOARD.time));
          } catch (Exception e) {
               twDataAndTimeCreated.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);
          }

          ibColorPicker.setTag(GlobalConstants.COLOURS_BUTTON);

          ibColour1.setOnClickListener(this);
          ibColour2.setOnClickListener(this);
          ibColour3.setOnClickListener(this);
          ibColour5.setOnClickListener(this);
     }

     public void setTimeCreatedText(String string) {
          twDataAndTimeCreated.setText(String.valueOf(string));
          pumpBottom.setDuration(600);
          twDataAndTimeCreated.startAnimation(pumpBottom);
     }

     @Override public void onClick(View view) {
          switch (view.getId()) {
               default:
                    ibColour1.setBackgroundResource(android.R.color.transparent);
                    ibColour2.setBackgroundResource(android.R.color.transparent);
                    ibColour3.setBackgroundResource(android.R.color.transparent);
                    ibColour5.setBackgroundResource(android.R.color.transparent);
                    view.setBackgroundResource(R.drawable.transparent_inside_and_white_round_border);
                    // set bg color of note
                    int color = Color.parseColor(view.getTag().toString());
                    // set backfround state
                    ((AGeneralWhiteBoard) activity).ivDrawingState.setBackgroundColor(color);
                    AGeneralWhiteBoard.WHITE_BOARD_DRAWING.disableEraserMode();
                    AGeneralWhiteBoard.WHITE_BOARD_DRAWING.setColor(color);
                    Utils.showCustomToastWithBackgroundColour(activity, getResources().getString(R.string.colour_has_been_changed), color);
                    return;
          }
     }
}
