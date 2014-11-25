package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogSettingsPassword_;
import com.touchip.organizer.communication.rest.request.GetInductionFileListRequest;
import com.touchip.organizer.communication.rest.request.listener.GetInductionFileListRequestListener;
import com.touchip.organizer.utils.Utils;

/**
 * Class represents action bar (top panel) for TV activitry
 * 
 * @author Karpachev Ihor
 */
@EViewGroup ( R.layout.ab_tv ) public class ActionBarTv extends HorizontalScrollView {

     // Load views
     @ViewById LinearLayout      llSettings , llInduction;

     // Parent activity
     private final SuperActivity activity;

     /**
      * Default constructor
      * 
      * @param context
      *             parent activity as parametr
      */
     public ActionBarTv ( SuperActivity context ) {
          super(context);
          this.activity = context;
     }

     /**
      * Handle induction button
      */
     @Click void llInduction() {
          GetInductionFileListRequest request = new GetInductionFileListRequest();
          activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetInductionFileListRequestListener(this.activity));
     }

     /**
      * Handle settings button
      */
     @Click void llSettings() {
          // get configured titiel (dialog without title)
          Dialog d = Utils.getConfiguredDialog(activity);
          d.setContentView(CDialogSettingsPassword_.build(activity, d));
          d.show();
     }
}
