package com.touchip.organizer.activities.fragments;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.time.DateUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.DrawingCompaniesActivity_;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity_;
import com.touchip.organizer.activities.ImagePagerActivity_;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.activities.custom.components.NumPad;
import com.touchip.organizer.activities.custom.components.QuickAction;
import com.touchip.organizer.activities.custom.components.QuickAction.OnActionItemClickListener;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.communication.rest.request.GetActivitiesAndRisksRequest;
import com.touchip.organizer.communication.rest.request.GetSuitableOperativesListRequest;
import com.touchip.organizer.communication.rest.request.GetTaskBriefingRequest;
import com.touchip.organizer.communication.rest.request.listener.GetActivitiesAndRisksRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetSuitableOperativesListRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetTaskBriefingRequestListener;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class FragmentHotspotsList extends ListFragment {

     private static TextView                    twId , twDescription , twHotspotType , twIsAssigned;
     private static ImageView                   imageHotspotType , imageHotspotCompanyColor;
     private static Dialog                      dialogHotspotDetail , dialogTradeHotspotDetail;

     public static QuickAction                  quickActionPopUpMenu , quickActionMenuAssetHotspot , quickActionPopUpNoteHs;

     private static RelativeLayout.LayoutParams param                                 = new RelativeLayout.LayoutParams(60, 60);

     public static RelativeLayout               hotspotsButtonLayout;
     public static ListViewHotsportsAdapter     ADAPTER;

     private static ProgressDialog              progressDialog;

     static Paint                               paint;

     View.OnClickListener                       clickListener                         = new View.OnClickListener() {

                                                                                           @Override public void onClick(View v) {
                                                                                                String type = v.getTag().toString();
                                                                                                ADAPTER.updateHotspotsButtonsList(type);

                                                                                                String message = "";
                                                                                                if ( (!type.equals(Hotspots.SHOW_ALL)) && (!type.equals(Hotspots.HIDE_ALL)) ) {
                                                                                                     message = "Filter ON: show only " + type;
                                                                                                } else {
                                                                                                     message = type.equals(Hotspots.HIDE_ALL) ? "Hide all hotspots" : "Show all hotspots";
                                                                                                }
                                                                                                Utils.showCustomToast(getActivity(), message, Utils.getImageIdByType(type));
                                                                                           }
                                                                                      };

     static OnLongClickListener                 longClickDragListener                 = new OnLongClickListener() {

                                                                                           @Override public boolean onLongClick(View v) {
                                                                                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                                                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, new ClipData.Item(v.getTag().toString()));
                                                                                                View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                                                v.startDrag(dragData, shadow, null, 0);
                                                                                                return true;
                                                                                           }

                                                                                      };

     static OnLongClickListener                 longClickUpdateHsPositionDragListener = new OnLongClickListener() {

                                                                                           @Override public boolean onLongClick(View v) {
                                                                                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                                                ClipData dragData = new ClipData("update", mimeTypes, new ClipData.Item("update"));
                                                                                                View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                                                v.startDrag(dragData, shadow, null, 0);
                                                                                                return true;
                                                                                           }

                                                                                      };
     private final OnActionItemClickListener    quickActionListener                   = new QuickAction.OnActionItemClickListener() {

                                                                                           @Override public void onItemClick(int pos) {
                                                                                                switch (pos) {
                                                                                                     case 0:
                                                                                                          try {
                                                                                                               Date today = DateUtils.parseDate(GlobalConstants.TODAY_FROM_SERVER, "yyyy-MM-dd");
                                                                                                               Date sitePlanDate = DateUtils.parseDate(GlobalConstants.SITE_PLAN_IMAGE_NAME, "yyyy-MM-dd");

                                                                                                               if ( !DateUtils.isSameDay(today, sitePlanDate) && today.compareTo(sitePlanDate) > 0 ) {
                                                                                                                    Utils.showToast(getActivity(), "Could not take a picture, date in past", true);
                                                                                                                    return;
                                                                                                               }
                                                                                                          } catch (Exception e1) {
                                                                                                               e1.printStackTrace();
                                                                                                          }

                                                                                                          // Utils.captureCameraPhoto(getActivity());
                                                                                                          // Intent intent = new
                                                                                                          // Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                                                                          // startActivityForResult(intent,
                                                                                                          // GlobalConstants.CAPTURE_CAMERA_PHOTO);

                                                                                                          if ( Utils.hasDeviceCamera() ) {
                                                                                                               Utils.captureCameraPhoto(getActivity());
                                                                                                          } else {
                                                                                                               Utils.showCustomToast(getActivity(), R.string.device_has_no_camera, R.drawable.hide_hotspot);
                                                                                                          }
                                                                                                          break;

                                                                                                     case 1:
                                                                                                          startActivity(new Intent(getActivity(), ImagePagerActivity_.class));
                                                                                                          break;

                                                                                                     case 2:
                                                                                                          showDialog(GlobalConstants.LAST_CLICKED_HOTSPOT, getActivity());
                                                                                                          break;
                                                                                                     default:
                                                                                                          return;
                                                                                                }
                                                                                           }
                                                                                      };

     private final OnActionItemClickListener    quickActionListenerAsset              = new QuickAction.OnActionItemClickListener() {

                                                                                           @Override public void onItemClick(int pos) {
                                                                                                switch (pos) {
                                                                                                     case 0:
                                                                                                          startActivity(new Intent(getActivity(), ImagePagerActivity_.class));
                                                                                                          break;

                                                                                                     case 1:
                                                                                                          showDialog(GlobalConstants.LAST_CLICKED_HOTSPOT, getActivity());
                                                                                                          break;
                                                                                                     default:
                                                                                                          return;
                                                                                                }
                                                                                           }
                                                                                      };

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          paint = new Paint();
          paint.setAntiAlias(true);
          ADAPTER = new ListViewHotsportsAdapter(getActivity());
          setListAdapter(ADAPTER);
     }

     @Override public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          quickActionPopUpMenu = new QuickAction(getActivity());
          quickActionMenuAssetHotspot = new QuickAction(getActivity());
          quickActionPopUpNoteHs = new QuickAction(getActivity());

          // preapare progress dialog
          progressDialog = new ProgressDialog(getActivity());
          progressDialog.setMessage(Utils.getResources(R.string.loading));

          progressDialog.setCancelable(true);

          dialogHotspotDetail = new Dialog(getActivity());
          dialogTradeHotspotDetail = new Dialog(getActivity());

          dialogHotspotDetail.setContentView(R.layout.dialog_hotspot_detail);
          dialogTradeHotspotDetail.setContentView(R.layout.dialog_trade_detail);

          twDescription = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_description));
          twId = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_id));
          twHotspotType = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_type));
          twIsAssigned = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_is_signed));

          // dragging elements in right bottom part of the screen
          final ImageView imageViewNotesHotspot = (ImageView) getActivity().findViewById(R.id.imageViewNotesHotspot);
          final ImageView imageViewSafetyHotspot = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_safety);
          final ImageView imageViewWasteHotspot = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_waste);
          final ImageView imageViewPermitsHotspot = (ImageView) getActivity().findViewById(R.id.drawing_hotspots_permits);
          final ImageView imageViewWhiteBoard = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_white_board);
          final ImageView imageViewTrades = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_trades);
          final ImageView imageViewAssets = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_assets);
          final ImageView imageViewCamera = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_camera);
          final ImageView imageViewShowAllHotspots = (ImageView) getActivity().findViewById(R.id.drawing_hotspots_show_all);
          final ImageView imageViewHideAllHotspots = (ImageView) getActivity().findViewById(R.id.drawing_hotspots_hide_all);

          imageViewCamera.setTag(Hotspots.CAMERA_HOTSPOT);
          imageViewNotesHotspot.setTag(Hotspots.NOTE_HOTSPOT);
          imageViewSafetyHotspot.setTag(Hotspots.SAFETY_HOTSPOT);
          imageViewWasteHotspot.setTag(Hotspots.WASTE_HOTSPOT);
          imageViewPermitsHotspot.setTag(Hotspots.PERMITS_HOTSPOT);
          imageViewWhiteBoard.setTag(Hotspots.WHITEBOARD_HOTSPOT);
          imageViewTrades.setTag(Hotspots.TRADE_HOTSPOT);
          imageViewAssets.setTag(Hotspots.ASSET_HOTSPOT);
          imageViewShowAllHotspots.setTag(Hotspots.SHOW_ALL);
          imageViewHideAllHotspots.setTag(Hotspots.HIDE_ALL);

          imageViewCamera.setOnClickListener(clickListener);
          imageViewNotesHotspot.setOnClickListener(clickListener);
          imageViewSafetyHotspot.setOnClickListener(clickListener);
          imageViewWasteHotspot.setOnClickListener(clickListener);
          imageViewPermitsHotspot.setOnClickListener(clickListener);
          imageViewWhiteBoard.setOnClickListener(clickListener);
          imageViewTrades.setOnClickListener(clickListener);
          imageViewAssets.setOnClickListener(clickListener);
          imageViewShowAllHotspots.setOnClickListener(clickListener);
          imageViewHideAllHotspots.setOnClickListener(clickListener);

          imageViewCamera.setOnLongClickListener(longClickDragListener);
          imageViewNotesHotspot.setOnLongClickListener(longClickDragListener);
          imageViewSafetyHotspot.setOnLongClickListener(longClickDragListener);
          imageViewWasteHotspot.setOnLongClickListener(longClickDragListener);
          imageViewPermitsHotspot.setOnLongClickListener(longClickDragListener);
          imageViewWhiteBoard.setOnLongClickListener(longClickDragListener);

          // Add action item
          QuickAction.ActionItem pinPicture = new QuickAction.ActionItem();
          QuickAction.ActionItem showPictures = new QuickAction.ActionItem();
          QuickAction.ActionItem hotspotDetails = new QuickAction.ActionItem();

          // set all titles from resources to action items
          pinPicture.setTitle(Utils.getResources(R.string.map_pin_a_picture));
          showPictures.setTitle(Utils.getResources(R.string.map_show_pictures));
          hotspotDetails.setTitle(Utils.getResources(R.string.hotspot_details));

          pinPicture.setIcon(GlobalConstants.PIN_PICTURE_IMAGE);
          showPictures.setIcon(GlobalConstants.SHOW_PICTURE_IMAGE);
          hotspotDetails.setIcon(GlobalConstants.SHOW_HOTSPOT_DETAIL_IMAGE);

          quickActionPopUpMenu.addActionItem(pinPicture);
          quickActionPopUpMenu.addActionItem(showPictures);
          quickActionPopUpMenu.addActionItem(hotspotDetails);

          quickActionPopUpNoteHs.addActionItem(pinPicture);
          quickActionPopUpNoteHs.addActionItem(showPictures);
          quickActionPopUpNoteHs.addActionItem(hotspotDetails);

          quickActionMenuAssetHotspot.addActionItem(showPictures);
          quickActionMenuAssetHotspot.addActionItem(hotspotDetails);

          quickActionPopUpMenu.setOnActionItemClickListener(quickActionListener);
          quickActionMenuAssetHotspot.setOnActionItemClickListener(quickActionListenerAsset);
          quickActionPopUpNoteHs.setOnActionItemClickListener(quickActionListener);

          imageHotspotType = ((ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_type));
          imageHotspotCompanyColor = (ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_company_color);

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_ok)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialogHotspotDetail.dismiss();
               }
          });
     }

     public static void showDialog(final POJORoboHotspot hotspot, final Activity activity) {
          String isSigned = (Double.valueOf(hotspot.x) > 0) ? "assigned" : "not assigned";
          dialogHotspotDetail.setTitle(hotspot.type.replace("hotspot", ""));

          String hotspotDescription = Hotspots.PERMITS_HOTSPOT.equals(hotspot.type) ? "Description:" + hotspot.description + "\n Valid from:" + hotspot.validFromDate + "\n Valid to: " + hotspot.validToDate : "Description:" + hotspot.description;
          twDescription.setText(hotspotDescription + "\nCreated by " + FragmentCompaniesList.getCompanyNameById(hotspot.companyId));

          twId.setText("ID: " + hotspot.id);
          twHotspotType.setText("Type: " + hotspot.type);
          twIsAssigned.setText("Status: hotspot is " + isSigned);

          imageHotspotType.setBackgroundResource(Utils.getImageIdByType(hotspot.type));

          imageHotspotCompanyColor.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(hotspot.companyId));

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_risk)).setVisibility(View.VISIBLE);
          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_task_briefing)).setVisibility(View.VISIBLE);

          if ( hotspot.type.equals(GlobalConstants.Hotspots.NOTE_HOTSPOT) || hotspot.type.equals(GlobalConstants.Hotspots.ASSET_HOTSPOT) || hotspot.type.equals(GlobalConstants.Hotspots.WASTE_HOTSPOT) ) {
               ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_risk)).setVisibility(View.GONE);
               ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_task_briefing)).setVisibility(View.GONE);
          } else {
               ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_risk)).setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View v) {
                         DrawingCompaniesActivity.showProgressDialog();
                         HashMap <String, Integer> vars = new HashMap <String, Integer>();
                         vars.put(HTTP_PARAMS.HOTSPOT_ID, hotspot.id);
                         GetActivitiesAndRisksRequest request = new GetActivitiesAndRisksRequest(vars);
                         ((DrawingCompaniesActivity_) activity).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetActivitiesAndRisksRequestListener(activity));
                    }
               });

               ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_task_briefing)).setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View v) {
                         DrawingCompaniesActivity.showProgressDialog();
                         HashMap <String, Integer> vars = new HashMap <String, Integer>();
                         vars.put(HTTP_PARAMS.HOTSPOT_ID, hotspot.id);
                         GetTaskBriefingRequest request = new GetTaskBriefingRequest(vars);
                         ((DrawingCompaniesActivity_) activity).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetTaskBriefingRequestListener(activity));
                    }
               });
          }

          dialogHotspotDetail.show();
     }

     @Override public void onListItemClick(ListView l, View v, int position, long id) {
          showDialog(DataAccess.SIGNED_HOTSPOTS.get(position), getActivity());
          GlobalConstants.HOTSPOTS_BUTTONS_ARRAY.get(position).startAnimation(AnimationManager.load(R.anim.growview));
     }

     /**
      * Description: provides data for hotspots imageview
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved. Date:
      *         21 Dec 2013 Time: 13:05:59
      */
     public static class ListViewHotsportsAdapter extends ArrayAdapter <String> {

          protected static final int TAKE_PHOTO_WASTE_CODE  = 0;
          protected static final int TAKE_PHOTO_SAFETY_CODE = 1;
          public static Activity     activity;

          public ListViewHotsportsAdapter ( Activity act ) {
               super(act, R.layout.listview_hotspot_list_item, new String[0]);
               activity = act;
               // updateHotspotsButtonsList(Hotspots.SHOW_ALL);
               // notifyDataSetChanged();
          }

          @SuppressWarnings ( "deprecation" ) @SuppressLint ( "NewApi" ) public void updateHotspotsButtonsList(String type) {
               if ( !Utils.isNull(hotspotsButtonLayout) ) {
                    // 1st view is drawing pannel, all others are hotspot button, remove them
                    hotspotsButtonLayout.removeViews(1, hotspotsButtonLayout.getChildCount() - 1);
               } else {
                    return;
               }

               if ( type.equals(Hotspots.HIDE_ALL) ) { return; }

               for ( int i = 0; i < DataAccess.SIGNED_HOTSPOTS.size(); i++ ) {
                    try {
                         param = new RelativeLayout.LayoutParams(60, 60);

                         param.leftMargin = (int) Math.round(Double.valueOf(DataAccess.SIGNED_HOTSPOTS.get(i).x) * CompaniesDrawingView.WIDTH);
                         param.topMargin = (int) Math.round(Double.valueOf(DataAccess.SIGNED_HOTSPOTS.get(i).y) * CompaniesDrawingView.HEIGHT);

                         // CREATE ADN SET UP BUTTON
                         final ImageButton ibutton = new ImageButton(activity);
                         ibutton.setTag(DataAccess.SIGNED_HOTSPOTS.get(i));
                         ibutton.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(DataAccess.SIGNED_HOTSPOTS.get(i).type)));

                         ibutton.setOnTouchListener(new View.OnTouchListener() {
                              @Override public boolean onTouch(View arg0, MotionEvent event) {

                                   if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                                        GlobalConstants.LAST_CLICKED_HOTSPOT = (POJORoboHotspot) ibutton.getTag();
                                   }

                                   if ( event.getAction() == MotionEvent.ACTION_UP ) {
                                        ibutton.startAnimation(AnimationManager.load(R.anim.growview));
                                   }
                                   return false;
                              }
                         });

                         ibutton.setOnLongClickListener(longClickUpdateHsPositionDragListener);

                         ibutton.setOnDragListener(new OnDragListener() {

                              @Override public boolean onDrag(View v, DragEvent event) {
                                   switch (event.getAction()) {

                                        case DragEvent.ACTION_DRAG_ENTERED:
                                             POJORoboHotspot hs1 = (POJORoboHotspot) ibutton.getTag();
                                             String typeOfDraggingHs1 = event.getClipDescription().getLabel().toString();
                                             if ( typeOfDraggingHs1.contains("trade") && hs1.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                                                  v.setBackgroundResource(R.drawable.target);
                                                  v.setScaleX(1.5f);
                                                  v.setScaleY(1.5f);

                                             }

                                             if ( typeOfDraggingHs1.contains("asset") && hs1.type.equals(Hotspots.TRADE_HOTSPOT) ) {
                                                  v.setBackgroundResource(R.drawable.target);
                                                  v.setScaleX(1.5f);
                                                  v.setScaleY(1.5f);
                                             }
                                             break;

                                        case DragEvent.ACTION_DRAG_EXITED:
                                             POJORoboHotspot hs2 = (POJORoboHotspot) ibutton.getTag();
                                             String typeOfDraggingHs2 = event.getClipDescription().getLabel().toString();
                                             if ( typeOfDraggingHs2.contains("trade") && hs2.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                                                  v.setBackgroundResource(R.drawable.asset);
                                                  v.setScaleX(1f);
                                                  v.setScaleY(1f);
                                             }

                                             if ( typeOfDraggingHs2.contains("asset") && hs2.type.equals(Hotspots.TRADE_HOTSPOT) ) {
                                                  v.setBackgroundResource(R.drawable.trade);
                                                  v.setScaleX(1f);
                                                  v.setScaleY(1f);
                                             }
                                             break;

                                        case DragEvent.ACTION_DROP:
                                             POJORoboHotspot hs = (POJORoboHotspot) ibutton.getTag();
                                             String typeOfDraggingHs = event.getClipDescription().getLabel().toString();
                                             ClipData d = event.getClipData();
                                             // dragging Trade hotspot over Asset on canvas
                                             if ( typeOfDraggingHs.contains("trade") && hs.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                                                  v.setBackgroundResource(R.drawable.asset);
                                                  Utils.showToast(activity, "Obtaining suitable operatives...", true);
                                                  v.setScaleX(1);
                                                  v.setScaleY(1);
                                                  String tradeDescr = typeOfDraggingHs.substring(0, typeOfDraggingHs.indexOf("_"));

                                                  HashMap <String, String> vars = new HashMap <String, String>();
                                                  vars.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(DataAccess.LAST_CLICKED_COMPANY.companyId));
                                                  vars.put(HTTP_PARAMS.TRADE_DESCRIPTION, String.valueOf(tradeDescr));
                                                  vars.put(HTTP_PARAMS.SITE_ID, String.valueOf(2));
                                                  vars.put(HTTP_PARAMS.ASSET_ID, String.valueOf(hs.assetId));
                                                  vars.put(HTTP_PARAMS.DATE, String.valueOf(GlobalConstants.SITE_PLAN_IMAGE_NAME));
                                                  GetSuitableOperativesListRequest request = new GetSuitableOperativesListRequest(vars);
                                                  ((DrawingCompaniesActivity) activity).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetSuitableOperativesListRequestListener(activity));
                                             }

                                             // dragging Asset hotspot over Trade on canvas
                                             if ( typeOfDraggingHs.contains("asset") && hs.type.equals(Hotspots.TRADE_HOTSPOT) ) {
                                                  v.setBackgroundResource(R.drawable.trade);
                                                  v.setScaleX(1);
                                                  v.setScaleY(1);
                                                  Utils.showToast(activity, "Obtaining suitable operatives...", true);
                                                  HashMap <String, String> vars = new HashMap <String, String>();
                                                  vars.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(DataAccess.LAST_CLICKED_COMPANY.companyId));
                                                  vars.put(HTTP_PARAMS.TRADE_DESCRIPTION, String.valueOf(hs.description));
                                                  vars.put(HTTP_PARAMS.SITE_ID, String.valueOf(2));
                                                  vars.put(HTTP_PARAMS.ASSET_ID, String.valueOf(DataAccess.LAST_CLICKED_ASSET.id));
                                                  vars.put(HTTP_PARAMS.DATE, String.valueOf(GlobalConstants.SITE_PLAN_IMAGE_NAME));
                                                  DrawingCompaniesActivity.showProgressDialog();
                                                  GetSuitableOperativesListRequest request = new GetSuitableOperativesListRequest(vars);
                                                  ((DrawingCompaniesActivity) activity).getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetSuitableOperativesListRequestListener(activity));
                                             }

                                             ibutton.startAnimation(AnimationManager.load(R.anim.shake, 300));
                                             break;

                                   }
                                   return true;
                              }
                         });

                         TextView textViewDescription = new TextView(activity);
                         TextView textViewAmountResources = null;

                         if ( DataAccess.SIGNED_HOTSPOTS.get(i).type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                              textViewDescription.setTextColor(Color.BLACK);
                              textViewDescription.setBackgroundResource(R.drawable.background_view_rounded_single_trades);
                              textViewAmountResources = new TextView(activity);
                              textViewAmountResources.setText(String.valueOf(DataAccess.SIGNED_HOTSPOTS.get(i).amount));
                              textViewAmountResources.setBackgroundResource(R.drawable.background_view_rounded_single_amount_of_people);
                              textViewAmountResources.setPadding(7, 5, 7, 5);
                         } else {
                              textViewDescription.setTextColor(Color.WHITE);
                              textViewDescription.setBackgroundResource(R.drawable.background_view_rounded_single);
                         }

                         textViewDescription.setTextSize(15);

                         textViewDescription.setText(String.valueOf(DataAccess.SIGNED_HOTSPOTS.get(i).id));

                         int leftMargin = (int) Math.round(Double.valueOf(DataAccess.SIGNED_HOTSPOTS.get(i).x) * CompaniesDrawingView.WIDTH) + 50;
                         int topMargin = (int) Math.round(Double.valueOf(DataAccess.SIGNED_HOTSPOTS.get(i).y) * CompaniesDrawingView.HEIGHT);

                         if ( type.equals(Hotspots.SHOW_ALL) ) {
                              hotspotsButtonLayout.addView(ibutton, param);

                              param = new RelativeLayout.LayoutParams(60, 25);

                              param.leftMargin = leftMargin;
                              param.topMargin = topMargin;

                              param.width = LayoutParams.WRAP_CONTENT;
                              param.height = LayoutParams.WRAP_CONTENT;

                              textViewDescription.setPadding(5, 5, 5, 5);
                              hotspotsButtonLayout.addView(textViewDescription, param);

                              textViewDescription.startAnimation(AnimationManager.load(android.R.anim.slide_in_left, 800));

                              if ( DataAccess.SIGNED_HOTSPOTS.get(i).type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                                   android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(60, 25);
                                   param2.width = LayoutParams.WRAP_CONTENT;
                                   param2.height = LayoutParams.WRAP_CONTENT;

                                   param2.leftMargin = leftMargin - 45;
                                   param2.topMargin = topMargin + 30;

                                   hotspotsButtonLayout.addView(textViewAmountResources, param2);
                              }

                         } else {
                              if ( type.equals(DataAccess.SIGNED_HOTSPOTS.get(i).type) ) {
                                   hotspotsButtonLayout.addView(ibutton, param);

                                   param = new RelativeLayout.LayoutParams(60, 25);

                                   param.leftMargin = leftMargin;
                                   param.topMargin = topMargin;
                                   param.width = LayoutParams.WRAP_CONTENT;
                                   param.height = LayoutParams.WRAP_CONTENT;
                                   textViewDescription.setPadding(5, 5, 5, 5);
                                   hotspotsButtonLayout.addView(textViewDescription, param);
                                   textViewDescription.startAnimation(AnimationManager.load(android.R.anim.slide_in_left, 800));

                                   if ( DataAccess.SIGNED_HOTSPOTS.get(i).type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                                        android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(60, 25);
                                        param2.width = LayoutParams.WRAP_CONTENT;
                                        param2.height = LayoutParams.WRAP_CONTENT;

                                        param2.leftMargin = leftMargin - 45;
                                        param2.topMargin = topMargin + 30;

                                        hotspotsButtonLayout.addView(textViewAmountResources, param2);
                                   }
                              }
                         }

                         final int[] location = new int[2];
                         location[0] = param.leftMargin;
                         location[1] = param.topMargin;

                         final String typeOfHS = DataAccess.SIGNED_HOTSPOTS.get(i).type;

                         ibutton.setOnClickListener(new OnClickListener() {

                              @Override public void onClick(View v) {
                                   // GlobalConstants.LAST_CLICKED_HOTSPOT = (POJORoboHotspot) v.getTag();
                                   if ( typeOfHS.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                                        dialogTradeHotspotDetail.setTitle("Trade hotspot details");
                                        NumPad npa = new NumPad();
                                        npa.show(activity, "Trade hotspot details", NumPad.NOFLAGS, new NumPad.NumbPadInterface() {
                                             @Override public String numPadInputValue(String value) {
                                                  return null;
                                             }

                                             @Override public String numPadCanceled() {
                                                  return null;
                                             }
                                        });
                                   } else if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.SAFETY_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.WASTE_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.ASSET_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type
                                             .equals(Hotspots.NOTE_HOTSPOT) ) {

                                        if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.ASSET_HOTSPOT) ) {
                                             quickActionMenuAssetHotspot.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
                                             quickActionMenuAssetHotspot.show(ibutton, location);
                                        } else {

                                             if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.NOTE_HOTSPOT) ) {
                                                  quickActionPopUpNoteHs.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
                                                  quickActionPopUpNoteHs.show(ibutton, location);
                                             } else {

                                                  quickActionPopUpMenu.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
                                                  quickActionPopUpMenu.show(ibutton, location);
                                             }
                                        }
                                   } else {
                                        if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.WHITEBOARD_HOTSPOT) ) {
                                             GeneralWhiteBoardActivity.WHITEBOARD_TYPE = GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING;
                                             GeneralWhiteBoardActivity_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING;
                                             activity.startActivity(new Intent(activity, GeneralWhiteBoardActivity_.class));
                                        } else {

                                             if ( typeOfHS.equals(GlobalConstants.Hotspots.CAMERA_HOTSPOT) ) {

                                                  final Dialog d = new Dialog(activity);
                                                  d.setContentView(R.layout.dialog_cctv_camera);
                                                  d.setCanceledOnTouchOutside(true);
                                                  final WebView webViewTV = (WebView) d.findViewById(R.id.webView1);
                                                  // Utils.configureWebView(webViewTV);
                                                  Utils.configureWebView(webViewTV).loadUrl("http://194.28.136.8:8001/record/current.jpg");
                                                  d.show();

                                                  Thread t = new Thread(new Runnable() {

                                                       @Override public void run() {
                                                            while ( d.isShowing() ) {
                                                                 try {
                                                                      Thread.sleep(400);
                                                                      webViewTV.reload();
                                                                 } catch (InterruptedException e) {
                                                                      Utils.logw(e.getMessage());
                                                                 }
                                                            }
                                                       }
                                                  });
                                                  t.start();
                                                  /*
                                                   * activity.runOnUiThread(new Runnable() {
                                                   * @Override public void run() {
                                                   * webViewTV.loadUrl("http://www.datascopesystem.com/QUILT_Demo_ORIGINAL/CCTV/cctv.htm");
                                                   * }
                                                   * });
                                                   */

                                             } else {
                                                  activity.runOnUiThread(new Runnable() {
                                                       @Override public void run() {
                                                            showDialog(GlobalConstants.LAST_CLICKED_HOTSPOT, activity);
                                                       }
                                                  });
                                             }
                                        }
                                   }
                              }
                         });
                         GlobalConstants.HOTSPOTS_BUTTONS_ARRAY.add(i, ibutton);
                    } catch (Exception e) {
                         Utils.logw(e.getMessage());
                    }
               }
          }

          @Override public int getCount() {
               return DataAccess.SIGNED_HOTSPOTS.size();
          }

          public static Bitmap createRoundImage(Paint paint) {
               Bitmap circleBitmap = Bitmap.createBitmap(30, 30, Bitmap.Config.ARGB_8888);
               Canvas c = new Canvas(circleBitmap);
               c.drawCircle(circleBitmap.getWidth() - 15, circleBitmap.getHeight() - 15, 14, paint);
               return circleBitmap;
          }

          @SuppressWarnings ( "deprecation" ) @Override public View getView(int position, View view, ViewGroup parent) {
               LayoutInflater inflater = activity.getLayoutInflater();
               View rowView = inflater.inflate(R.layout.listview_hotspot_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.list_hotspots_text_view);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.list_hotspots_image);
               ImageView imageViewCompanyColour = (ImageView) rowView.findViewById(R.id.list_hotspots_companyColour);

               imageView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(DataAccess.SIGNED_HOTSPOTS.get(position).type)));
               try {
                    // imageViewCompanyColour.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(DataAccess.SIGNED_HOTSPOTS.get(position).companyId));
                    paint.setColor(FragmentCompaniesList.getCompanyColorById(DataAccess.SIGNED_HOTSPOTS.get(position).companyId));
                    imageViewCompanyColour.setImageBitmap(createRoundImage(paint));
               } catch (Exception e) {
                    Utils.logw(e.getMessage());
                    imageViewCompanyColour.setVisibility(View.INVISIBLE);
               }
               try {
                    txtTitle.setText("(" + DataAccess.SIGNED_HOTSPOTS.get(position).id + ") " + DataAccess.SIGNED_HOTSPOTS.get(position).description);
               } catch (Exception ex) {
                    Utils.logw(ex.getMessage());
                    txtTitle.setText("Def value");
               }
               return rowView;
          }
     }

}