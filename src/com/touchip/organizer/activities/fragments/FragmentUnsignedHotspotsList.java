package com.touchip.organizer.activities.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.HotspotsList;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.Utils;

public class FragmentUnsignedHotspotsList extends ListFragment {

     // buttons images references
     public static ListViewUnsignedHotspotsAdapter ADAPTER;

     private TextView                              twId , twDescription , twHotspotType , twIsAssigned;
     private ImageView                             imageHotspotType;
     private Dialog                                dialogHotspotDetail;

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          ADAPTER = new ListViewUnsignedHotspotsAdapter(getActivity());
          setListAdapter(ADAPTER);
     }

     @Override public void onListItemClick(ListView l, View v, int position, long id) {
          POJORoboHotspot hotspot = ListViewUnsignedHotspotsAdapter.UNSIGNED_HOTSPOTS.get(position);
          String isSigned = (Double.valueOf(hotspot.x) > 0) ? "assigned" : "not assigned";
          dialogHotspotDetail.setTitle(hotspot.type.replace("hotspot", ""));

          twDescription.setText("Description:" + hotspot.description);
          twId.setText("ID: " + hotspot.id);
          twHotspotType.setText("Type: " + hotspot.type);
          twIsAssigned.setText("Status: hotspot is " + isSigned);

          imageHotspotType.setBackgroundResource(Utils.getImageIdByType(hotspot.type));
          dialogHotspotDetail.show();
     }

     /**
      * Description: provides data for TASKS imageview
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved. Date:
      *         21 Dec 2013 Time: 13:05:59
      */
     public static class ListViewUnsignedHotspotsAdapter extends ArrayAdapter <String> {

          public static Activity     activity;
          public static HotspotsList UNSIGNED_HOTSPOTS;

          public ListViewUnsignedHotspotsAdapter ( Activity act ) {
               super(act, R.layout.listview_hotspot_list_item, new String[0]);
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               return UNSIGNED_HOTSPOTS.size();
          }

          @SuppressWarnings ( "deprecation" ) @Override public View getView(int position, View view, ViewGroup parent) {

               LayoutInflater inflater = activity.getLayoutInflater();
               View rowView = inflater.inflate(R.layout.listview_hotspot_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.list_hotspots_text_view);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.list_hotspots_image);
               imageView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(UNSIGNED_HOTSPOTS.get(position).type)));
               imageView.setTag(UNSIGNED_HOTSPOTS.get(position));
               imageView.setOnLongClickListener(new OnLongClickListener() {

                    @Override public boolean onLongClick(View v) {
                         ClipData.Item item = new ClipData.Item(v.getTag().toString());
                         POJORoboHotspot unsignedHotspot = (POJORoboHotspot) v.getTag();
                         String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                         ClipData dragData = new ClipData(String.valueOf(unsignedHotspot.id), mimeTypes, item);

                         // Instantiates the drag shadow builder.
                         View.DragShadowBuilder shadow = new DragShadowBuilder(v);

                         // Starts the drag
                         v.startDrag(dragData, shadow, null, 0);
                         return true;
                    }
               });
               txtTitle.setText(UNSIGNED_HOTSPOTS.get(position).description);

               return rowView;
          }
     }

     public void loadViews() {
          dialogHotspotDetail = new Dialog(getActivity());
          dialogHotspotDetail.setContentView(R.layout.dialog_hotspot_detail);
          twDescription = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_description));
          twId = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_id));
          twHotspotType = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_type));
          twIsAssigned = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_is_signed));
          imageHotspotType = ((ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_type));

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_ok)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialogHotspotDetail.dismiss();
               }
          });
     }
}