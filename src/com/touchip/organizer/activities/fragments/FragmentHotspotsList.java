package com.touchip.organizer.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
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

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity_;
import com.touchip.organizer.activities.ImagePagerActivity_;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.activities.custom.components.NumPad;
import com.touchip.organizer.activities.custom.components.QuickAction;
import com.touchip.organizer.communication.rest.model.HotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class FragmentHotspotsList extends ListFragment {

     private static TextView                    twId , twDescription , twHotspotType , twIsAssigned;
     private static ImageView                   imageHotspotType , imageHotspotCompanyColor;
     private static Dialog                      dialogHotspotDetail , dialogTradeHotspotDetail;

     public static QuickAction                  quickActionPopUpMenu;

     private static RelativeLayout.LayoutParams param                 = new RelativeLayout.LayoutParams(60, 60);

     public static RelativeLayout               hotspotsButtonLayout;
     public static ListViewHotsportsAdapter     ADAPTER;

     private static ProgressDialog              progressDialog;

     View.OnClickListener                       clickListener         = new View.OnClickListener() {

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

     OnLongClickListener                        longClickDragListener = new OnLongClickListener() {

                                                                           @Override public boolean onLongClick(View v) {
                                                                                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, new ClipData.Item(v.getTag()
                                                                                          .toString()));
                                                                                View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                                v.startDrag(dragData, shadow, null, 0);
                                                                                return true;
                                                                           }
                                                                      };

     @Override public void onAttach(Activity activity) {
          super.onAttach(activity);
          ADAPTER = new ListViewHotsportsAdapter(getActivity());
          setListAdapter(ADAPTER);
     }

     @Override public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          quickActionPopUpMenu = new QuickAction(getActivity());

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
          imageViewShowAllHotspots.setTag(Hotspots.SHOW_ALL);
          imageViewHideAllHotspots.setTag(Hotspots.HIDE_ALL);

          imageViewCamera.setOnClickListener(clickListener);
          imageViewNotesHotspot.setOnClickListener(clickListener);
          imageViewSafetyHotspot.setOnClickListener(clickListener);
          imageViewWasteHotspot.setOnClickListener(clickListener);
          imageViewPermitsHotspot.setOnClickListener(clickListener);
          imageViewWhiteBoard.setOnClickListener(clickListener);
          imageViewTrades.setOnClickListener(clickListener);
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

          quickActionPopUpMenu.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {

               @Override public void onItemClick(int pos) {
                    switch (pos) {
                         case 0:
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
          });

          imageHotspotType = ((ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_type));
          imageHotspotCompanyColor = (ImageView) dialogHotspotDetail.findViewById(R.id.dialog_image_view_company_color);

          ((Button) dialogHotspotDetail.findViewById(R.id.dialog_button_ok)).setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialogHotspotDetail.dismiss();
               }
          });
     }

     public static void showDialog(POJORoboHotspot hotspot, Activity activity) {
          String isSigned = (Double.valueOf(hotspot.x) > 0) ? "assigned" : "not assigned";
          dialogHotspotDetail.setTitle(hotspot.type.replace("hotspot", ""));

          String hotspotDescription = Hotspots.PERMITS_HOTSPOT.equals(hotspot.type)
                    ? "Description:" + hotspot.description + "\n Valid from:" + hotspot.validFromDate + "\n Valid to: " + hotspot.validToDate
                    : "Description:" + hotspot.description;
          twDescription.setText(hotspotDescription + "\nCreated by " + FragmentCompaniesList.getCompanyNameById(hotspot.companyId));

          twId.setText("ID: " + hotspot.id);
          twHotspotType.setText("Type: " + hotspot.type);
          twIsAssigned.setText("Status: hotspot is " + isSigned);

          imageHotspotType.setBackgroundResource(Utils.getImageIdByType(hotspot.type));

          imageHotspotCompanyColor.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(hotspot.companyId));
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
               updateHotspotsButtonsList(Hotspots.SHOW_ALL);
               notifyDataSetChanged();
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
                                        ibutton.startAnimation(AnimationManager.load(R.anim.growview));
                                   }
                                   return false;
                              }
                         });

                         ibutton.setOnDragListener(new OnDragListener() {

                              @Override public boolean onDrag(View v, DragEvent event) {
                                   switch (event.getAction()) {

                                        case DragEvent.ACTION_DRAG_ENTERED:
                                             ibutton.startAnimation(AnimationManager.load(R.anim.shake, 300));
                                             break;
                                        default:
                                             return true;
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
                                   GlobalConstants.LAST_CLICKED_HOTSPOT = (POJORoboHotspot) v.getTag();
                                   if ( typeOfHS.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                                        final int maxPickerValue = GlobalConstants.LAST_CLICKED_HOTSPOT.availableAmount + GlobalConstants.LAST_CLICKED_HOTSPOT.amount;
                                        dialogTradeHotspotDetail.setTitle("Trade hotspot details");
                                        NumPad npa = new NumPad();
                                        npa.show(maxPickerValue, activity, "Trade hotspot details", NumPad.NOFLAGS, new NumPad.NumbPadInterface() {
                                             @Override public String numPadInputValue(String value) {
                                                  if ( "1234".equals(value) ) {
                                                       Utils.logw("Pin is CORRECT! What do you want me to do?");
                                                  } else {
                                                       Utils.logw("Manager Pin is incorrect");
                                                  }
                                                  return null;
                                             }

                                             @Override public String numPadCanceled() {
                                                  return null;
                                             }
                                        });
                                   } else if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.SAFETY_HOTSPOT) || GlobalConstants.LAST_CLICKED_HOTSPOT.type
                                             .equals(Hotspots.WASTE_HOTSPOT) ) {
                                        quickActionPopUpMenu.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
                                        quickActionPopUpMenu.show(ibutton, location);
                                   } else {
                                        if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.WHITEBOARD_HOTSPOT) ) {
                                             GeneralWhiteBoardActivity_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING;
                                             activity.startActivity(new Intent(activity, GeneralWhiteBoardActivity_.class));
                                        } else {

                                             if ( typeOfHS.equals(GlobalConstants.Hotspots.CAMERA_HOTSPOT) ) {

                                                  final Dialog d = new Dialog(activity);
                                                  d.setContentView(R.layout.dialog_cctv_camera);
                                                  d.setCanceledOnTouchOutside(true);
                                                  final WebView webViewTV = (WebView) d.findViewById(R.id.webView1);
                                                  Utils.configureWebView(webViewTV).loadUrl("http://194.28.136.8:8000/SnapshotJPEG?Resolution=1027x768&Quality=Standard");
                                                  d.show();

                                                  Thread t = new Thread(new Runnable() {

                                                       @Override public void run() {
                                                            while ( d.isShowing() ) {
                                                                 try {
                                                                      Thread.sleep(500);
                                                                      webViewTV.reload();
                                                                 } catch (InterruptedException e) {
                                                                      Utils.logw(e.getMessage());
                                                                 }
                                                            }
                                                       }
                                                  });
                                                  t.start();

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

          @SuppressWarnings ( "deprecation" ) @Override public View getView(int position, View view, ViewGroup parent) {
               LayoutInflater inflater = activity.getLayoutInflater();
               View rowView = inflater.inflate(R.layout.listview_hotspot_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.list_hotspots_text_view);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.list_hotspots_image);
               ImageView imageViewCompanyColour = (ImageView) rowView.findViewById(R.id.list_hotspots_companyColour);

               imageView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(DataAccess.SIGNED_HOTSPOTS.get(position).type)));
               try {
                    imageViewCompanyColour.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(DataAccess.SIGNED_HOTSPOTS.get(position).companyId));
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