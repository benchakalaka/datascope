package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;

@EViewGroup ( R.layout.ab_company_drawing ) public class ActionBarDrawingCompanies extends HorizontalScrollView {

     // Load views
     @ViewById TextView                     twToday;
     // @ViewById TextView twSPN;
     @ViewById TextView                     tvArea;
     // @ViewById TextView tvCompanyName;

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

     public void setUpCurrentSiteInfo(String today, String siteName, String area) {
          twToday.setText(today);
          // twSPN.setText("Site:" + siteName);
          tvArea.setText("Area/Floor " + area);
     }

     public void setUpCompanyName(String companyName) {
          // tvCompanyName.setText(companyName);
     }

}
