package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.utils.GlobalConstants;

@EViewGroup ( R.layout.dialog_display_user_information ) public class CDialogDisplayUserInformation extends RelativeLayout {

     // ===================== views
     @ViewById TextView          tvCompany , tvFullName;
     @ViewById Button            btnOk;
     @ViewById ImageView         ivCompanyColour;

     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;

     /**
      * Create view
      * 
      * @param act
      *             activity that triggered show dialog event
      * @param hostDialog
      *             dialog that hots this view (in order to dissmis dialog in case of cancel click)
      */
     public CDialogDisplayUserInformation ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          tvFullName.setText(GlobalConstants.CURRENT_USER.firstName + " " + GlobalConstants.CURRENT_USER.lastName);
          tvCompany.setText(String.valueOf(GlobalConstants.CURRENT_USER.companyName));
          try {
               ivCompanyColour.setImageBitmap(FragmentCompaniesList.createRoundImage(GlobalConstants.CURRENT_USER.companyColour, 40, 40));
          } catch (Exception ex) {
               ivCompanyColour.setImageBitmap(FragmentCompaniesList.createRoundImage("#ffcc33", 40, 40));
          }

     }

     @Click void btnCancel() {
          this.dialog.dismiss();
     }

     @Click void btnOk() {
          this.dialog.dismiss();
     }

}