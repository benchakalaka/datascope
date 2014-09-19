package com.touchip.organizer.activities.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.model.CompaniesList;
import com.touchip.organizer.communication.rest.model.CompaniesList.POJORoboCompany;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class FragmentCompaniesList extends ListFragment {

     private static ListViewCompaniesAdapter ADAPTER;
     Paint                                   paint;

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          paint = new Paint();
          paint.setAntiAlias(true);
          ADAPTER = new ListViewCompaniesAdapter(getActivity(), GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList);
          setListAdapter(ADAPTER);
     }

     @Override public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
          getListView().setSelector(android.R.color.darker_gray);
     }

     @Override public void onListItemClick(ListView l, View v, int position, long id) {
          GlobalConstants.LAST_CLICKED_COMPANY = GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(position);
          DrawingCompaniesActivity.getDrawView().setColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));
          DrawingCompaniesActivity.getDrawView().setCompanyColourFilter(0);
          DrawingCompaniesActivity.ivCompanyColor.setBackgroundColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));

          DrawingCompaniesActivity.ivCompanyColor.startAnimation(AnimationManager.load(R.anim.bounce));

          Utils.showCustomToastWithBackgroundColour(getActivity(), GlobalConstants.LAST_CLICKED_COMPANY.companyName, Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));

          DrawingCompaniesActivity.getLlAssets().setVisibility(View.GONE);
          DrawingCompaniesActivity.getLlTrades().setVisibility(View.GONE);

          /*
           * HIDE TRADES LIST VIEW
           * if ( DrawingCompaniesActivity.getCustomActionBar().isTradesMenuVisible() ) {
           * Utils.AnimationManager.animateMenu(DrawingCompaniesActivity.getLvTrades(), View.INVISIBLE, R.anim.disappear, 500);
           * DrawingCompaniesActivity.getCustomActionBar().setTradesMenuVisibility(false);
           * }
           */
          // ListView item scroll to selected item
          l.smoothScrollToPositionFromTop(position, 100, 400);
     }

     public static ListViewCompaniesAdapter getCompaniesAdapter() {
          return ADAPTER;
     }

     /*
      * public static void leaveListViewSelection() {
      * INSTANCE.getListView().smoothScrollToPositionFromTop(LIST_VIEW_ITEM_POSITION, 100, 400);
      * }
      */

     /**
      * Description: adapter, which represents list of companies and colours for list view
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved.
      *         Date: 25 Dec 2013
      *         Time: 00:05:27
      */
     public class ListViewCompaniesAdapter extends ArrayAdapter <String> {

          private final Activity activity;

          public ListViewCompaniesAdapter ( Activity act , CompaniesList companies ) {
               super(act, R.layout.listview_company_list_item);
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               return GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.size();
          }

          @Override public String getItem(int position) {
               return GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(position).companyName;
          }

          @Override public View getView(int position, View view, ViewGroup parent) {
               View rowView = activity.getLayoutInflater().inflate(R.layout.listview_company_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.twCompanyTitle);
               TextView txtHasAssets = (TextView) rowView.findViewById(R.id.twHasAssets);
               TextView twHasTrades = (TextView) rowView.findViewById(R.id.twHasTrades);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.ivCompanyColor);

               txtTitle.setText(GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(position).companyName);
               paint.setColor(Color.parseColor(GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(position).colour));
               imageView.setImageBitmap(createRoundImage(paint));
               // imageView.setBackgroundColor(Color.parseColor(COMPANIES_LIST.get(position).colour));

               txtHasAssets.setText("ASSETS");
               twHasTrades.setText("TRADES");

               txtHasAssets.setTextColor(GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(position).hasAssets ? Color.parseColor("#42CC3B") : Color.parseColor("#AAADA5"));
               twHasTrades.setTextColor(GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(position).hasTrades ? Color.parseColor("#42CC3B") : Color.parseColor("#AAADA5"));
               return rowView;
          }
     }

     public static Bitmap createRoundImage(Paint paint) {
          Bitmap circleBitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
          Canvas c = new Canvas(circleBitmap);
          c.drawCircle(circleBitmap.getWidth() - 15, circleBitmap.getHeight() - 15, 14, paint);
          return circleBitmap;
     }

     public static int getCompanyColorById(int companyId) {
          int retCompanyColor = 1;
          if ( null != GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList ) {
               for ( POJORoboCompany company : GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList ) {
                    if ( companyId == company.companyId ) {
                         retCompanyColor = Color.parseColor(company.colour);
                         break;
                    }
               }
          }
          return retCompanyColor;
     }

     public static String getCompanyNameById(int companyId) {
          String retCompanyName = "";
          if ( null != GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList ) {
               for ( POJORoboCompany company : GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList ) {
                    if ( companyId == company.companyId ) {
                         retCompanyName = company.companyName;
                         break;
                    }
               }
          }
          return retCompanyName;
     }

}