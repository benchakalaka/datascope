package com.touchip.organizer.activities.fragments;

import quickutils.core.QUFactory.QLog;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCompleteHotspot_;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class FragmentUnsignedHotspotsList extends ListFragment {

     // buttons images references
     public static ListViewUnsignedHotspotsAdapter ADAPTER;

     private TextView                              twId , twDescription , twHotspotType , twIsAssigned;
     private ImageView                             imageHotspotType , imageHotspotComapnyColor;
     private Dialog                                dialogHotspotDetail;
     static Paint                                  paint;

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          paint = new Paint();
          paint.setAntiAlias(true);
          ADAPTER = new ListViewUnsignedHotspotsAdapter(getActivity());
          setListAdapter(ADAPTER);
          loadViews();
     }

     public static Bitmap createRoundImage(Paint paint) {
          Bitmap circleBitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
          Canvas c = new Canvas(circleBitmap);
          c.drawCircle(circleBitmap.getWidth() - 15, circleBitmap.getHeight() - 15, 14, paint);
          return circleBitmap;
     }

     @Override public void onListItemClick(ListView l, View v, int position, long id) {
          final POJORoboHotspot hotspot = GlobalConstants.UNASSIGNED_HOTSPOTS.get(position);

          twDescription.setText("Description:" + hotspot.description + "\nCreated by " + FragmentCompaniesList.getCompanyNameById(hotspot.companyId));
          twId.setText("ID: " + hotspot.id);
          twHotspotType.setText("Type: " + hotspot.type);
          twIsAssigned.setText("Status: hotspot is not assigned");

          imageHotspotType.setBackgroundResource(Utils.getImageIdByType(hotspot.type));
          imageHotspotComapnyColor.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(hotspot.companyId));

          int resImageId = hotspot.isCompleted ? R.drawable.oki : R.drawable.ok48;

          ((ImageView) dialogHotspotDetail.findViewById(R.id.ivIsCompleted)).setImageResource(resImageId);

          ((TextView) dialogHotspotDetail.findViewById(R.id.tvTitle)).setText(hotspot.isCompleted ? "Completed" : "Not completed");

          ((ImageView) dialogHotspotDetail.findViewById(R.id.ivIsCompleted)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {

                    if ( hotspot.isCompleted ) {
                         Utils.showCustomToast(getActivity(), "Hotspot is completed", R.drawable.failure);
                         return;
                    }

                    if ( GlobalConstants.SITE_PLAN_IMAGE_NAME.compareTo(GlobalConstants.TODAY_FROM_SERVER) > 0 ) {
                         Utils.showCustomToast(getActivity(), "Date in past", R.drawable.failure);
                         return;
                    }

                    Dialog d = Utils.getConfiguredDialog((SuperActivity) getActivity());
                    d.setContentView(CDialogCompleteHotspot_.build((SuperActivity) getActivity(), d, hotspot.id, dialogHotspotDetail));
                    d.show();
               }
          });

          // dialogHotspotDetail.show();
     }

     /**
      * Description: provides data for TASKS imageview
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved. Date:
      *         21 Dec 2013 Time: 13:05:59
      */
     public static class ListViewUnsignedHotspotsAdapter extends ArrayAdapter <String> {

          public static Activity        activity;
          public static POJORoboHotspot draggedUnsignedHotspot;

          public ListViewUnsignedHotspotsAdapter ( Activity act ) {
               super(act, R.layout.listview_hotspot_list_item, new String[0]);
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               if ( null != GlobalConstants.UNASSIGNED_HOTSPOTS ) {
                    return GlobalConstants.UNASSIGNED_HOTSPOTS.size();
               } else {
                    return 0;
               }
          }

          @SuppressWarnings ( "deprecation" ) @Override public View getView(int position, View view, ViewGroup parent) {

               LayoutInflater inflater = activity.getLayoutInflater();
               View rowView = inflater.inflate(R.layout.listview_hotspot_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.list_hotspots_text_view);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.list_hotspots_image);
               ImageView imageViewCompanyColour = (ImageView) rowView.findViewById(R.id.list_hotspots_companyColour);

               imageView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(GlobalConstants.UNASSIGNED_HOTSPOTS.get(position).type)));
               imageView.setTag(GlobalConstants.UNASSIGNED_HOTSPOTS.get(position));
               rowView.setTag(GlobalConstants.UNASSIGNED_HOTSPOTS.get(position));

               try {
                    paint.setColor(FragmentCompaniesList.getCompanyColorById(GlobalConstants.UNASSIGNED_HOTSPOTS.get(position).companyId));
                    imageViewCompanyColour.setImageBitmap(createRoundImage(paint));
               } catch (Exception e) {
                    QLog.debug(e.getMessage());
                    imageViewCompanyColour.setVisibility(View.INVISIBLE);
               }

               imageView.setOnTouchListener(new OnTouchListener() {

                    @Override public boolean onTouch(View v, MotionEvent event) {

                         switch (event.getAction()) {
                              case MotionEvent.ACTION_DOWN:
                                   ClipData.Item item = new ClipData.Item(v.getTag().toString());
                                   draggedUnsignedHotspot = (POJORoboHotspot) v.getTag();
                                   String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                   // Id of Hotspot and ID of company (necessary for parameter in ADrawingCompanies :: createHotspot)
                                   ClipData dragData = new ClipData(String.valueOf(draggedUnsignedHotspot.id), mimeTypes, item);

                                   // Instantiates the drag shadow builder.
                                   View.DragShadowBuilder shadow = new DragShadowBuilder(v);

                                   // Starts the drag
                                   v.startDrag(dragData, shadow, null, 0);
                                   break;
                         }

                         return false;
                    }
               });

               /*
                * imageView.setOnTouchListener(new OnTouchListener() {
                * @Override public boolean onTouch(View v, MotionEvent event) {
                * switch (event.getAction()) {
                * case MotionEvent.ACTION_DOWN:
                * ClipData.Item item = new ClipData.Item(v.getTag().toString());
                * draggedUnsignedHotspot = (POJORoboHotspot) v.getTag();
                * String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                * // Id of Hotspot and ID of company (necessary for parameter in ADrawingCompanies :: createHotspot)
                * ClipData dragData = new ClipData(String.valueOf(draggedUnsignedHotspot.id), mimeTypes, item);
                * // Instantiates the drag shadow builder.
                * View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                * // Starts the drag
                * v.startDrag(dragData, shadow, null, 0);
                * break;
                * }
                * return false;
                * }
                * });
                */

               txtTitle.setText("(" + GlobalConstants.UNASSIGNED_HOTSPOTS.get(position).id + ") " + GlobalConstants.UNASSIGNED_HOTSPOTS.get(position).description);

               return rowView;
          }
     }

     public void loadViews() {
          dialogHotspotDetail = Utils.getConfiguredDialog((SuperActivity) getActivity());
          dialogHotspotDetail.setContentView(R.layout.dialog_hotspot_detail);
          twDescription = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_description));
          twId = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_id));
          twHotspotType = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_type));
          twIsAssigned = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_is_signed));
          imageHotspotType = ((ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_type));
          imageHotspotComapnyColor = ((ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_company_color));

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_ok)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialogHotspotDetail.dismiss();
               }
          });
     }
}