package com.touchip.organizer.activities.fragments;

import java.util.HashMap;

import quickutils.core.QUFactory.QCollection;
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
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelCompaniesList;
import com.touchip.organizer.communication.rest.model.ModelCompaniesList.POJORoboCompany;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.model.ModelTaskList;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetOnTheFlyTasks;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class FragmentCompaniesList extends ListFragment {

     public static ModelCompaniesList        COMPANIES_LIST;
     private static ListViewCompaniesAdapter ADAPTER;
     Paint                                   paint;

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          paint = new Paint();
          paint.setAntiAlias(true);
          ADAPTER = new ListViewCompaniesAdapter(getActivity(), COMPANIES_LIST);
          setListAdapter(ADAPTER);
     }

     @Override public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
          getListView().setSelector(android.R.color.darker_gray);
     }

     @Override public void onListItemClick(ListView l, View v, int position, long id) {
          GlobalConstants.LAST_CLICKED_COMPANY = COMPANIES_LIST.get(position);
          ADrawingCompanies.getDrawView().setIsNeedToStopOnDrawMethod(true);

          ADrawingCompanies.getDrawView().setColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));
          ADrawingCompanies.ivCompanyColor.setBackgroundColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));

          ADrawingCompanies.ivCompanyColor.startAnimation(AnimationManager.load(R.anim.bounce));

          Utils.showCustomToastWithBackgroundColour(getActivity(), GlobalConstants.LAST_CLICKED_COMPANY.companyName, Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));

          ADrawingCompanies.getLlAssets().setVisibility(View.GONE);
          ADrawingCompanies.getLlTrades().setVisibility(View.GONE);

          // ListView item scroll to selected item
          l.smoothScrollToPositionFromTop(position, 100, 400);
          ADAPTER.notifyDataSetChanged();

          HashMap <String, String> params = QCollection.newHashMap();
          params.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
          params.put(HTTP_PARAMS.USER_ID, String.valueOf(GlobalConstants.CURRENT_USER.userId));

          SuperRequest <ModelTaskList> request = new SuperRequest <ModelTaskList>((SuperActivity) getActivity(), ModelTaskList.class, RestAddresses.GET_ON_THE_FLY_TASKS, params);
          ((SuperActivity) getActivity()).execute(request, new ResponseGetOnTheFlyTasks((SuperActivity) getActivity()));

          // filter unassigned hotspot by company
          GlobalConstants.UNASSIGNED_HOTSPOTS = new ModelHotspotsList();
          for ( POJORoboHotspot hotspot : GlobalConstants.UNASSIGNED_HOTSPOTS_ALL ) {
               if ( hotspot.companyId == GlobalConstants.LAST_CLICKED_COMPANY.companyId ) {
                    GlobalConstants.UNASSIGNED_HOTSPOTS.add(hotspot);
               }
          }
          FragmentUnsignedHotspotsList.ADAPTER.notifyDataSetChanged();

     }

     public static ListViewCompaniesAdapter getCompaniesAdapter() {
          return ADAPTER;
     }

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

          public ListViewCompaniesAdapter ( Activity act , ModelCompaniesList companies ) {
               super(act, R.layout.listview_company_list_item);
               COMPANIES_LIST = companies;
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               if ( null != COMPANIES_LIST ) {
                    return COMPANIES_LIST.size();
               } else {
                    return 0;
               }

          }

          @Override public String getItem(int position) {
               return COMPANIES_LIST.get(position).companyName;
          }

          @Override public View getView(int position, View view, ViewGroup parent) {
               View rowView = activity.getLayoutInflater().inflate(R.layout.listview_company_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.twCompanyTitle);
               TextView txtHasAssets = (TextView) rowView.findViewById(R.id.twHasAssets);
               TextView twHasTrades = (TextView) rowView.findViewById(R.id.twHasTrades);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.ivCompanyColor);

               ImageView ivChecked = (ImageView) rowView.findViewById(R.id.ivChecked);

               if ( GlobalConstants.LAST_CLICKED_COMPANY != null ) {
                    if ( COMPANIES_LIST.get(position).companyId == GlobalConstants.LAST_CLICKED_COMPANY.companyId ) {
                         GlobalConstants.LAST_CLICKED_COMPANY = COMPANIES_LIST.get(position);
                         ivChecked.setVisibility(View.VISIBLE);
                         ADrawingCompanies.DRAW_VIEW.setColor(GlobalConstants.LAST_CLICKED_COMPANY.colour);
                    } else {
                         ivChecked.setVisibility(View.INVISIBLE);
                    }
               }

               txtTitle.setText(COMPANIES_LIST.get(position).companyName);
               paint.setColor(Color.parseColor(COMPANIES_LIST.get(position).colour));
               imageView.setImageBitmap(createRoundImage(paint));
               // imageView.setBackgroundColor(Color.parseColor(COMPANIES_LIST.get(position).colour));

               txtHasAssets.setText("ASSETS");
               twHasTrades.setText("TRADES");

               txtHasAssets.setTextColor(COMPANIES_LIST.get(position).hasAssets ? Color.parseColor("#42CC3B") : Color.parseColor("#AAADA5"));
               twHasTrades.setTextColor(COMPANIES_LIST.get(position).hasTrades ? Color.parseColor("#42CC3B") : Color.parseColor("#AAADA5"));
               return rowView;
          }
     }

     public static Bitmap createRoundImage(Paint paint) {
          Bitmap circleBitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
          Canvas c = new Canvas(circleBitmap);
          c.drawCircle(circleBitmap.getWidth() - 15, circleBitmap.getHeight() - 15, 14, paint);
          return circleBitmap;
     }

     public static Bitmap createRoundImage(String colour, int bitmapW, int bitmapH) {
          Bitmap circleBitmap = Bitmap.createBitmap(bitmapW, bitmapH, Bitmap.Config.ARGB_8888);
          Canvas c = new Canvas(circleBitmap);

          Paint p = new Paint();
          p.setAntiAlias(true);
          p.setStrokeWidth(5);
          p.setColor(Color.parseColor(colour));
          c.drawCircle(circleBitmap.getWidth() - bitmapW / 2, circleBitmap.getHeight() - bitmapH / 2, bitmapW / 2, p);
          return circleBitmap;
     }

     public static int getCompanyColorById(int companyId) {
          int retCompanyColor = 1;
          if ( null != COMPANIES_LIST ) {
               for ( POJORoboCompany company : COMPANIES_LIST ) {
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
          if ( null != COMPANIES_LIST ) {
               for ( POJORoboCompany company : COMPANIES_LIST ) {
                    if ( companyId == company.companyId ) {
                         retCompanyName = company.companyName;
                         break;
                    }
               }
          }
          return retCompanyName;
     }

}