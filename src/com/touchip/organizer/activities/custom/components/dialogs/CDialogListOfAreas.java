package com.touchip.organizer.activities.custom.components.dialogs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import quickutils.core.QUFactory.QCollection;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseDownloadSitePlanWithFloor;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_list_of_areas ) public class CDialogListOfAreas extends RelativeLayout {

     // ===================== views
     @ViewById public static ListView lvFloors;

     // ====================== variable
     private final SuperActivity      activity;

     public static Dialog             hostDialog;

     public static Date               date;

     public CDialogListOfAreas ( SuperActivity act , Date date , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          CDialogListOfAreas.date = date;
          CDialogListOfAreas.hostDialog = hostDialog;
     }

     @AfterViews void afterViews() {

          List <String> floors = null;
          String selectedDate = (null != date) ? Utils.formatDate(date) : GlobalConstants.SITE_PLAN_IMAGE_NAME;

          for ( int i = 0; i < GlobalConstants.datestoHighlight.size(); i++ ) {
               /**
                * 
                * 
                */
               if ( GlobalConstants.datestoHighlight.get(i).date.compareTo(selectedDate) < 1 ) {
                    GlobalConstants.CURRENT_FLOOR = GlobalConstants.datestoHighlight.get(i).floors.get(0);
                    floors = GlobalConstants.datestoHighlight.get(i).floors;
               }
          }

          try {
               lvFloors.setAdapter(new ListViewAreasAdapter(activity, floors.toArray(new String[floors.size()])));
          } catch (Exception ez) {
               ez.printStackTrace();
               return;
          }

     }

     // --------------------------------- COMPANIES LIST ADAPTER
     /**
      * Description: adapter, which represents list of companies and colours for list view
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved.
      *         Date: 24 Sep 2014
      *         Time: 15:05:27
      */
     public static class ListViewAreasAdapter extends ArrayAdapter <String> {

          private final SuperActivity activity;
          private final String[]      areas;

          public ListViewAreasAdapter ( SuperActivity act , String[] areas ) {
               super(act, R.layout.listview_areas_list_item);
               this.activity = act;
               this.areas = areas;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               return this.areas.length;
          }

          @Override public String getItem(int position) {
               return this.areas[position];
          }

          @Override public View getView(final int position, View view, ViewGroup parent) {
               final View rowView = activity.getLayoutInflater().inflate(R.layout.listview_areas_list_item, null, true);
               TextView tvArea = (TextView) rowView.findViewById(R.id.tvArea);

               tvArea.setText(this.areas[position]);

               rowView.setOnClickListener(new OnClickListener() {

                    @Override public void onClick(View v) {
                         final String floor = getItem(position);
                         GlobalConstants.CURRENT_FLOOR = floor;
                         if ( null != date ) {
                              GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(date).toString();
                         }

                         HashMap <String, String> params = QCollection.newHashMap();

                         params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                         params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                         params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

                         SuperRequest <byte[]> request = new SuperRequest <byte[]>(activity, byte[].class, RestAddresses.DOWNLOAD_SITE_PLAN, new ByteArrayHttpMessageConverter(), params);
                         activity.execute(request, new ResponseDownloadSitePlanWithFloor(activity));
                         hostDialog.dismiss();

                    }
               });

               return rowView;
          }

          @Override public long getItemId(int position) {
               return 0;
          }
     }
}
