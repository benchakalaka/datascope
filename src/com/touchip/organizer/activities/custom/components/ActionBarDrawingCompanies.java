package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.TvActivity_;
import com.touchip.organizer.communication.rest.request.GetPathsCreationTimeRequest;
import com.touchip.organizer.communication.rest.request.listener.GetPathsCreationTimeRequestListener;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.ab_company_drawing ) public class ActionBarDrawingCompanies extends HorizontalScrollView {

     // Load views
     @ViewById ImageView                    ivCurrentCompanyColor;
     @ViewById TextView                     tvCurrentCompanyName;
     @ViewById ToggleButton                 tbShowFilters;
     @ViewById ToggleButton                 tbShowTrades;
     @ViewById LinearLayout                 whiteboardLayout;
     @ViewById LinearLayout                 llTv;

     private WebView                        webView;

     // Parent activity
     private final DrawingCompaniesActivity activity;

     /**
      * Default constructor
      * 
      * @param context
      *             parent activity as parametr
      */
     public ActionBarDrawingCompanies ( Context context ) {
          super(context);
          this.activity = (DrawingCompaniesActivity) context;
     }

     // After all views has been injected, create dialog
     @AfterViews void afterViews() {
          new Dialog(activity);
     }

     @Click void llTv() {
          activity.startActivity(new Intent(activity, TvActivity_.class));
     }

     @UiThread void reloadBrowser() {
          webView.reload();
     }

     @Background void refreshBrowser() {
          while ( true ) {
               try {
                    Thread.sleep(500);
                    reloadBrowser();
               } catch (InterruptedException e) {
                    Utils.logw(e.getMessage());
               }
          }
     }

     /**
      * Update company name and color in action bar and animate view after
      */
     public void updateCurrentCompanyInActionBar() {
          // set last clicked company color
          ivCurrentCompanyColor.setBackgroundColor(Color.parseColor(DataAccess.LAST_CLICKED_COMPANY.colour));
          // set last clicked company name
          tvCurrentCompanyName.setText(DataAccess.LAST_CLICKED_COMPANY.companyName);
          // animate view
          tvCurrentCompanyName.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
     }

     /**
      * Return trade menu state
      * 
      * @return true if it visible, false otherwise
      */
     public boolean isTradesMenuVisible() {
          return tbShowTrades.isChecked();
     }

     /**
      * Set toggle button check state
      * 
      * @param visibility
      *             value to be set
      */
     public void setTradesMenuVisibility(boolean visibility) {
          tbShowTrades.setChecked(visibility);
     }

     /**
      * Show or hide trades menu
      * 
      * @param toggleButton
      *             trades button
      * @param isChecked
      *             state of trade menu
      */
     @CheckedChange void tbShowTrades(CompoundButton toggleButton, boolean isChecked) {
          // if user has not choosen any company - show appropriate message and uncheck toggle button
          if ( (isChecked) && (null == DataAccess.LAST_CLICKED_COMPANY) ) {
               Utils.showCustomToast(this.activity, "Choose company!", R.drawable.trade);
               toggleButton.setChecked(false);
               return;
          }
          if ( isChecked ) {
               // if user choose company at least once
               this.activity.showOrHideResources();
          } else {
               AnimationManager.animateMenu(DrawingCompaniesActivity.getSvTrades(), View.GONE, R.anim.disappear, 500);
          }
     }

     /**
      * Show or hide filters based on value of toggle button
      * 
      * @param toggleButton
      *             flter button
      * @param isChecked
      *             current toggle button state
      */
     @CheckedChange void tbShowFilters(CompoundButton toggleButton, boolean isChecked) {
          this.activity.setFilterLayoutVisibility(isChecked);
     }

     @Click void whiteboardLayout() {
          GetPathsCreationTimeRequest request = new GetPathsCreationTimeRequest();
          this.activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetPathsCreationTimeRequestListener(activity));
     }
}
