package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.ab_company_drawing ) public class ActionBarDrawingCompanies extends HorizontalScrollView {

     // Load views
     @ViewById TextView              twToday;
     // @ViewById TextView twSPN;
     @ViewById TextView              tvArea;
     @ViewById RelativeLayout        rlRightPanel;

     // Parent activity
     private final ADrawingCompanies activity;

     /**
      * Default constructor
      * 
      * @param context
      *             parent activity as parametr
      */
     public ActionBarDrawingCompanies ( Context context ) {
          super(context);
          this.activity = (ADrawingCompanies) context;
     }

     public void setUpCurrentSiteInfo(String today, String siteName, String area) {
          twToday.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);
          tvArea.setText("Area: " + area);
     }

     @Click void rlRightPanel() {
          rlRightPanel.startAnimation(AnimationManager.load(R.anim.fade_in));
          activity.getDatesToHighliht();;
     }
}