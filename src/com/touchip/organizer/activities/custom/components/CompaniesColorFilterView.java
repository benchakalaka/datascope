package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.communication.rest.model.CompaniesList.POJORoboCompany;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.listview_company_list_item ) public class CompaniesColorFilterView extends RelativeLayout {

     // load views
     @ViewById public ImageView              ivCompanyColor;
     @ViewById public TextView               twAmountOfPeople;
     @ViewById public TextView               twCompanyTitle;

     // parent activity
     private static DrawingCompaniesActivity activity;

     public static OnClickListener           filterColorListener = new OnClickListener() {

                                                                      @Override public void onClick(View v) {
                                                                           // -1 means that user clicked show/hide all
                                                                           int colourFilter = -1;
                                                                           String companyColorFilter = v.getTag().toString();
                                                                           // if filter == 0 or 1 - it's show/hide all
                                                                           if ( "0".equals(companyColorFilter) || "1".equals(companyColorFilter) ) {
                                                                                DrawingCompaniesActivity.getDrawView().setCompanyColourFilter(Integer.parseInt(companyColorFilter));
                                                                           } else {
                                                                                for ( int i = 0; i < FragmentCompaniesList.COMPANIES_LIST.size(); i++ ) {
                                                                                     if ( FragmentCompaniesList.COMPANIES_LIST.get(i).companyName.equals(companyColorFilter) ) {
                                                                                          colourFilter = Color.parseColor(FragmentCompaniesList.COMPANIES_LIST.get(i).colour);
                                                                                     }
                                                                                }
                                                                                DrawingCompaniesActivity.getDrawView().setCompanyColourFilter(colourFilter);
                                                                           }
                                                                           String messageText = "";
                                                                           int imageResourcesId = 0;

                                                                           if ( -1 != colourFilter ) {
                                                                                messageText = companyColorFilter;
                                                                                Utils.showCustomToastWithBackgroundColour(activity, messageText, colourFilter);
                                                                           } else {
                                                                                messageText = "0".equals(companyColorFilter) ? "SHOW ALL COMPANIES DRAWINGS" : "HIDE ALL COMPANIES DRAWINGS";
                                                                                imageResourcesId = "0".equals(companyColorFilter) ? R.drawable.color_pallete : R.drawable.hide_hotspot;
                                                                                Utils.showCustomToast(activity, messageText, imageResourcesId);
                                                                           }
                                                                      }
                                                                 };

     public CompaniesColorFilterView ( Context context ) {
          super(context);
          CompaniesColorFilterView.activity = (DrawingCompaniesActivity) context;
     }

     /**
      * Set one company for this view
      * 
      * @param company
      *             to be set
      */
     public void setCompany(POJORoboCompany company) {
          // set company color and amount of resources for this view
          ivCompanyColor.setBackgroundColor(Color.parseColor(company.colour));
          twAmountOfPeople.setText(String.valueOf(company.amountOfPeople));
          // set view's tag as company name
          setTag(company.companyName);
          // Same view is using for displaying companies list, but here there is no necessity to show company name, so hide it
          twCompanyTitle.setVisibility(View.GONE);
          this.setOnClickListener(filterColorListener);
     }

     /**
      * Create show all drawing button in filter menu
      * 
      * @return one view by clicking which all drawings on canvas will appear
      */
     public static CompaniesColorFilterView createShowAllDrawingsFilterButtons() {
          CompaniesColorFilterView showAllDrawings = CompaniesColorFilterView_.build(activity);
          showAllDrawings.ivCompanyColor.setBackgroundResource(R.drawable.color_pallete);
          showAllDrawings.twAmountOfPeople.setVisibility(View.GONE);
          // 0 flag means show all drawings, so on canvas there is no filter
          showAllDrawings.setTag("0");
          showAllDrawings.setOnClickListener(CompaniesColorFilterView.filterColorListener);
          return showAllDrawings;
     }

     /**
      * Create hide all drawing button in filter menu
      * 
      * @return one view by clicking which all drawings on canvas will dissappear
      */
     public static CompaniesColorFilterView createHideAllDrawingsFilterButtons() {
          CompaniesColorFilterView hideAllDrawings = CompaniesColorFilterView_.build(activity);
          hideAllDrawings.ivCompanyColor.setBackgroundResource(R.drawable.hide_hotspot);
          hideAllDrawings.twAmountOfPeople.setVisibility(View.GONE);
          // 1 flag means hide all drawings, so on canvas will be no any shapes/lines etc. Canvas will be clear
          hideAllDrawings.setTag("1");
          hideAllDrawings.setOnClickListener(CompaniesColorFilterView.filterColorListener);
          return hideAllDrawings;
     }
}