package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.utils.GlobalConstants;

@EViewGroup ( R.layout.ab_white_board_drawing ) public class ActionBarGeneralWhiteboard extends HorizontalScrollView {
     // Load views
     @ViewById TextView                             twDataAndTimeCreated;
     @AnimationRes ( R.anim.pump_bottom ) Animation pumpBottom;

     /**
      * Default constructor
      */
     public ActionBarGeneralWhiteboard ( Context context ) {
          super(context);
     }

     @AfterViews void afterViews() {
          try {
               twDataAndTimeCreated.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME + (null == GlobalConstants.LAST_CLICKED_WHITE_BOARD ? " not saved"
                         : " created at " + GlobalConstants.LAST_CLICKED_WHITE_BOARD.time));
          } catch (Exception e) {
               twDataAndTimeCreated.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);
          }
     }

     public void setTimeCreatedText(String string) {
          twDataAndTimeCreated.setText(String.valueOf(string));
          pumpBottom.setDuration(600);
          twDataAndTimeCreated.startAnimation(pumpBottom);
     }
}
