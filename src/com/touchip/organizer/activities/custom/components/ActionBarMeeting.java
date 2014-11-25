package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.graphics.Color;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AMeetingPlan;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.ab_meeting ) public class ActionBarMeeting extends HorizontalScrollView {

     // Load views
     @ViewById ImageView ivBlue , ivGreen , ivRed , ivTextSizeUp , ivTextSizeDown;

     int                 animationId = R.anim.push_up_in;
     AMeetingPlan        activity;

     /**
      * Default constructor
      * 
      * @param context
      *             parent activity as parametr
      */
     public ActionBarMeeting ( AMeetingPlan context ) {
          super(context);
          this.activity = context;
     }

     @AfterViews void afterViews() {
          if ( android.os.Build.MANUFACTURER.contains("Amazon") ) {
               ivTextSizeUp.setEnabled(false);
               ivTextSizeUp.setVisibility(View.INVISIBLE);
          }
     }

     @Click void ivBlue() {
          ivBlue.startAnimation(AnimationManager.load(animationId));
          this.activity.applyTextColor(Color.BLUE);
     }

     @Click void ivGreen() {
          ivGreen.startAnimation(AnimationManager.load(animationId));
          this.activity.applyTextColor(Color.parseColor("#006400"));
     }

     @Click void ivRed() {
          ivRed.startAnimation(AnimationManager.load(animationId));
          this.activity.applyTextColor(Color.RED);
     }

     @Click void ivTextSizeUp() {
          ivTextSizeUp.startAnimation(AnimationManager.load(animationId));
          this.activity.changeEntireTextSize(-6);
     }

     @Click void ivTextSizeDown() {
          ivTextSizeDown.startAnimation(AnimationManager.load(animationId));
          this.activity.changeEntireTextSize(6);
     }

}
