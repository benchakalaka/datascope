package com.touchip.organizer.activities;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.annotations.res.ColorRes;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarMeeting_;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanList;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanList.POJORoboMeetingPlanItem;
import com.touchip.organizer.utils.Utils;

@EActivity ( R.layout.activity_meeting_plan ) public class AMeetingPlan extends Activity implements OnClickListener {
     // Load views and resources
     @SystemService public LayoutInflater                      vi;
     @ColorRes int                                             meetingSelectedColor;
     @ColorRes int                                             meetingUnselectedColor;
     @AnimationRes ( android.R.anim.fade_in ) public Animation fadeIn;
     @ViewById public LinearLayout                             llRootMeeting;
     // Static array which represents meeting plan items
     public static ModelMeetingPlanList                        MEETING_PLAN_ARRAY;

     private final ArrayList <View>                            textViews = new ArrayList <View>();
     private final int                                         SIZE      = 22;

     @OptionsItem void homeSelected() {
          onBackPressed();
     }

     @AfterViews void afterViews() {
          // Set animation duration
          fadeIn.setDuration(800);
          // Configure action bar
          Utils.configureCustomActionBar(getActionBar(), null);
          for ( int i = 0; i < MEETING_PLAN_ARRAY.size(); i++ ) {
               POJORoboMeetingPlanItem singleMeetingPlan = MEETING_PLAN_ARRAY.get(i);
               View v = null;
               TextView tw = null;
               // NAME_OF_MEETING = 0
               if ( Integer.valueOf(singleMeetingPlan.type) == 0 ) {
                    v = vi.inflate(R.layout.meeting_name, null);
                    tw = (TextView) llRootMeeting.findViewById(R.id.twMeetingHeader);
                    tw.setText(singleMeetingPlan.description);
                    continue;
               }
               // HEADER = 1
               if ( Integer.valueOf(singleMeetingPlan.type) == 1 ) {
                    v = vi.inflate(R.layout.meeting_header, null);
                    tw = (TextView) v.findViewById(R.id.tv);

               }
               // SUB_HEADER = 2 , POINT = 3;
               if ( Integer.valueOf(singleMeetingPlan.type) == 3 ) {
                    v = vi.inflate(R.layout.meeting_single_bulletpoint, null);
                    tw = (TextView) v.findViewById(R.id.tv);
                    textViews.add(tw);
               }

               if ( null != tw ) {
                    tw.setText(singleMeetingPlan.description);
                    // 1 means that view is unselected
                    v.setTag("1");
                    v.setOnClickListener(this);
                    llRootMeeting.addView(v);

               }
          }

          // load all views
          Utils.configureCustomActionBar(getActionBar(), ActionBarMeeting_.build(AMeetingPlan.this));
          getActionBar().setIcon(R.drawable.menu);
     }

     @Override public void onClick(View v) {
          // animate view
          v.startAnimation(fadeIn);
          // change one bullet point color
          boolean isSelected = "0".equals(v.getTag());
          // invert state of view (1 - unselected, 0 - selected)
          v.setTag(isSelected ? "1" : "0");
          // change background color depends on it's state
          v.setBackgroundColor(isSelected ? meetingSelectedColor : meetingUnselectedColor);
     }

     public void applyTextColor(int color) {
          try {
               for ( int i = 0; i < textViews.size(); i++ ) {
                    if ( ((View) ((TextView) textViews.get(i)).getParent().getParent()).getTag().equals("0") ) {
                         ((TextView) textViews.get(i)).setTextColor(color);
                    }
               }
          } catch (Exception ex) {
               ex.printStackTrace();
          }
     }

     public void changeEntireTextSize(float delta) {
          try {
               for ( int i = 0; i < textViews.size(); i++ ) {
                    if ( ((View) ((TextView) textViews.get(i)).getParent().getParent()).getTag().equals("0") ) {
                         DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                         float textsize = ((TextView) textViews.get(i)).getTextSize() / metrics.density;
                         float delta2 = delta / metrics.density;
                         ((TextView) textViews.get(i)).setTextSize(textsize + delta2);
                    }
               }
          } catch (Exception ex) {
               ex.printStackTrace();
          }
     }
}