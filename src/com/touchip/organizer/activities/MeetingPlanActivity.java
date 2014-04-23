package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.annotations.res.ColorRes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.MeetingPlanList;
import com.touchip.organizer.communication.rest.model.MeetingPlanList.POJORoboMeetingPlanItem;
import com.touchip.organizer.utils.Utils;

@EActivity ( R.layout.activity_meeting_plan ) public class MeetingPlanActivity extends Activity implements OnClickListener {
     // Load views and resources
     @SystemService public LayoutInflater                      vi;
     @ColorRes int                                             meetingSelectedColor;
     @ColorRes int                                             meetingUnselectedColor;
     @AnimationRes ( android.R.anim.fade_in ) public Animation fadeIn;
     @ViewById public LinearLayout                             llRootMeeting;
     // Static array which represents meeting plan items
     public static MeetingPlanList                             MEETING_PLAN_ARRAY;

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
                    tw = (TextView) v.findViewById(R.id.tvHeader);
               }
               // SUB_HEADER = 2 , POINT = 3;
               if ( Integer.valueOf(singleMeetingPlan.type) == 3 ) {
                    v = vi.inflate(R.layout.meeting_single_bulletpoint, null);
                    tw = (TextView) v.findViewById(R.id.twPointText);
               }

               if ( null != tw ) {
                    tw.setText(singleMeetingPlan.description);
                    // 1 means that view is unselected
                    v.setTag("1");
                    v.setOnClickListener(this);
                    llRootMeeting.addView(v);
               }
          }
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
}