package com.touchip.organizer.activities.fragments;

import java.util.HashMap;

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

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.request.GetActivitiesAndRisksRequest;
import com.touchip.organizer.communication.rest.request.GetTaskBriefingRequest;
import com.touchip.organizer.communication.rest.request.listener.GetActivitiesAndRisksRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetTaskBriefingRequestListener;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.HTTP_PARAMS;
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
          final POJORoboHotspot hotspot = DataAccess.UNASSIGNED_HOTSPOTS.get(position);
          dialogHotspotDetail.setTitle("Default title"/* hotspot.type.replace("hotspot", "") */);

          twDescription.setText("Description:" + hotspot.description + "\nCreated by " + FragmentCompaniesList.getCompanyNameById(hotspot.companyId));
          twId.setText("ID: " + hotspot.id);
          twHotspotType.setText("Type: " + hotspot.type);
          twIsAssigned.setText("Status: hotspot is not assigned");

          imageHotspotType.setBackgroundResource(Utils.getImageIdByType(hotspot.type));
          imageHotspotComapnyColor.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(hotspot.companyId));

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_risk)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    DrawingCompaniesActivity.showProgressDialog();
                    HashMap <String, Integer> vars = new HashMap <String, Integer>();
                    vars.put(HTTP_PARAMS.HOTSPOT_ID, hotspot.id);
                    GetActivitiesAndRisksRequest request = new GetActivitiesAndRisksRequest(vars);
                    ((DrawingCompaniesActivity_) getActivity()).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetActivitiesAndRisksRequestListener(getActivity()));
               }
          });

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_task_briefing)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    DrawingCompaniesActivity.showProgressDialog();
                    HashMap <String, Integer> vars = new HashMap <String, Integer>();
                    vars.put(HTTP_PARAMS.HOTSPOT_ID, hotspot.id);
                    GetTaskBriefingRequest request = new GetTaskBriefingRequest(vars);
                    ((DrawingCompaniesActivity_) getActivity()).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetTaskBriefingRequestListener(getActivity()));
               }
          });

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

          public static Activity        activity;
          public static POJORoboHotspot draggedUnsignedHotspot;

          public ListViewUnsignedHotspotsAdapter ( Activity act ) {
               super(act, R.layout.listview_hotspot_list_item, new String[0]);
               activity = act;
               notifyDataSetChanged();
          }

          @Override public int getCount() {
               return DataAccess.UNASSIGNED_HOTSPOTS.size();
          }

          @SuppressWarnings ( "deprecation" ) @Override public View getView(int position, View view, ViewGroup parent) {

               LayoutInflater inflater = activity.getLayoutInflater();
               View rowView = inflater.inflate(R.layout.listview_hotspot_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.list_hotspots_text_view);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.list_hotspots_image);
               ImageView imageViewCompanyColour = (ImageView) rowView.findViewById(R.id.list_hotspots_companyColour);

               imageView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(DataAccess.UNASSIGNED_HOTSPOTS.get(position).type)));
               imageView.setTag(DataAccess.UNASSIGNED_HOTSPOTS.get(position));

               try {
                    paint.setColor(FragmentCompaniesList.getCompanyColorById(DataAccess.UNASSIGNED_HOTSPOTS.get(position).companyId));
                    imageViewCompanyColour.setImageBitmap(createRoundImage(paint));
               } catch (Exception e) {
                    Utils.logw(e.getMessage());
                    imageViewCompanyColour.setVisibility(View.INVISIBLE);
               }

               imageView.setOnLongClickListener(new OnLongClickListener() {

                    @Override public boolean onLongClick(View v) {
                         ClipData.Item item = new ClipData.Item(v.getTag().toString());
                         draggedUnsignedHotspot = (POJORoboHotspot) v.getTag();
                         String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                         // Id of Hotspot and ID of company (necessary for parameter in DrawingCompaniesActivity :: createHotspot)
                         ClipData dragData = new ClipData(String.valueOf(draggedUnsignedHotspot.id), mimeTypes, item);

                         // Instantiates the drag shadow builder.
                         View.DragShadowBuilder shadow = new DragShadowBuilder(v);

                         // Starts the drag
                         v.startDrag(dragData, shadow, null, 0);
                         return true;
                    }
               });
               txtTitle.setText("(" + DataAccess.UNASSIGNED_HOTSPOTS.get(position).id + ") " + DataAccess.UNASSIGNED_HOTSPOTS.get(position).description);

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
          imageHotspotComapnyColor = ((ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_company_color));

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_ok)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialogHotspotDetail.dismiss();
               }
          });
     }
}