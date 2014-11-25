package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogDisplayUserInformation_;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

/**
 * Class represents action bar (top panel) for Menu modules activity
 * 
 * @author Karpachev Ihor
 */
@EViewGroup ( R.layout.ab_menu_modules ) public class ActionBarMenuModules extends HorizontalScrollView {

     // Load views
     @ViewById LinearLayout      llUser;
     @ViewById TextView          tvUserName;

     // Parent activity
     private final SuperActivity activity;

     /**
      * Default constructor
      * 
      * @param context
      *             parent activity as parametr
      */
     public ActionBarMenuModules ( SuperActivity context ) {
          super(context);
          this.activity = context;
     }

     @AfterViews void afterViews() {
          tvUserName.setText(GlobalConstants.CURRENT_USER.firstName + " " + GlobalConstants.CURRENT_USER.lastName);
     }

     @Click void llUser() {
          Dialog d = Utils.getConfiguredDialog(activity);
          d.setCanceledOnTouchOutside(true);
          d.setContentView(CDialogDisplayUserInformation_.build(activity, d));
          d.show();
     }

}
