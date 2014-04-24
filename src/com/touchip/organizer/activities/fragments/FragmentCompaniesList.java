package com.touchip.organizer.activities.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.model.CompaniesList;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.Utils;

public class FragmentCompaniesList extends ListFragment {

     public static CompaniesList COMPANIES_LIST;

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          setListAdapter(new ListViewCompaniesAdapter(getActivity(), COMPANIES_LIST));
     }

     @Override public void onListItemClick(ListView l, View v, int position, long id) {
          DataAccess.LAST_CLICKED_COMPANY = COMPANIES_LIST.get(position);
          Utils.logw("ID = " + DataAccess.LAST_CLICKED_COMPANY.companyId);
          DrawingCompaniesActivity.getDrawView().setColor(Color.parseColor(DataAccess.LAST_CLICKED_COMPANY.colour));
          DrawingCompaniesActivity.getCustomActionBar().updateCurrentCompanyInActionBar();
          DrawingCompaniesActivity.getDrawView().setCompanyColourFilter(0);

          Utils.showCustomToastWithBackgroundColour(getActivity(), DataAccess.LAST_CLICKED_COMPANY.companyName, Color.parseColor(DataAccess.LAST_CLICKED_COMPANY.colour));

          if ( DrawingCompaniesActivity.getCustomActionBar().isTradesMenuVisible() ) {
               Utils.AnimationManager.animateMenu(DrawingCompaniesActivity.getSvTrades(), View.INVISIBLE, R.anim.disappear, 500);
               DrawingCompaniesActivity.getCustomActionBar().setTradesMenuVisibility(false);
          }
     }

     /**
      * Description: adapter, which represents list of companies and colours for list view
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved.
      *         Date: 25 Dec 2013
      *         Time: 00:05:27
      */
     public static class ListViewCompaniesAdapter extends ArrayAdapter <String> {

          private static Activity activity;

          public ListViewCompaniesAdapter ( Activity act , CompaniesList companies ) {
               super(act, R.layout.listview_company_list_item);
               COMPANIES_LIST = companies;
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               return COMPANIES_LIST.size();
          }

          @Override public String getItem(int position) {
               return COMPANIES_LIST.get(position).companyName;
          }

          @Override public View getView(int position, View view, ViewGroup parent) {
               LayoutInflater inflater = activity.getLayoutInflater();
               View rowView = inflater.inflate(R.layout.listview_company_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.twCompanyTitle);
               TextView txtAmountOfPeople = (TextView) rowView.findViewById(R.id.twAmountOfPeople);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.ivCompanyColor);

               txtTitle.setText(COMPANIES_LIST.get(position).companyName);
               txtAmountOfPeople.setText(String.valueOf(COMPANIES_LIST.get(position).amountOfPeople));
               imageView.setBackgroundColor(Color.parseColor(COMPANIES_LIST.get(position).colour));

               return rowView;
          }
     }

     public static int getCompanyColorById(int companyId) {
          int retCompanyColor = 1;
          if ( null != COMPANIES_LIST ) {
               for ( int i = 0; i < COMPANIES_LIST.size(); i++ ) {
                    if ( companyId == COMPANIES_LIST.get(i).companyId ) {
                         retCompanyColor = Color.parseColor(COMPANIES_LIST.get(i).colour);
                    }
               }
          }
          return retCompanyColor;
     }

     public static String getCompanyNameById(int companyId) {
          String retCompanyName = "";
          if ( null != COMPANIES_LIST ) {
               for ( int i = 0; i < COMPANIES_LIST.size(); i++ ) {
                    if ( companyId == COMPANIES_LIST.get(i).companyId ) {
                         retCompanyName = COMPANIES_LIST.get(i).companyName;
                    }
               }
          }
          return retCompanyName;
     }
}