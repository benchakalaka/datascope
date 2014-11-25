package com.touchip.organizer.activities.fragments;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import quickutils.core.QUFactory.QSystem;
import quickutils.core.QUFactory.QViews;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AImagePager_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.HotspotButton;
import com.touchip.organizer.activities.custom.components.QuickAction;
import com.touchip.organizer.activities.custom.components.QuickAction.OnActionItemClickListener;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCompleteHotspot_;
import com.touchip.organizer.communication.rest.model.ModelActivitiesAndRisksList.POJORoboSingleActivityAndRisk;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot;
import com.touchip.organizer.utils.FilterManager;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

public class FragmentHotspotsList extends ListFragment {

     private static TextView                 tvActivities , tvCompanyAndProjectName , tvCompanyNameRiskSchedule , tvMeansAndFrequency , twId , twDescription , twHotspotType , tvRevievedByDate , tvBriefingItemsList , twIsAssigned , tvRevievedBy , tvNameOfSupervisiorDate ,
               tvCompanyName , tvScopeOfWorks , tvNameOfSupervisior;
     private static ImageView                imageHotspotType , imageHotspotCompanyColor;
     private static Dialog                   dialogHotspotDetail , dialogTradeHotspotDetail;

     public static QuickAction               quickActionPopUpMenu , quickActionMenuAssetHotspot , quickActionPopUpNoteHs;
     public static TableLayout               tlRiskControl;

     public static RelativeLayout            hotspotsButtonLayout;
     private static LinearLayout             llTaskBriefing , llRiskSchedule;
     public static ListViewHotsportsAdapter  ADAPTER;

     private static ProgressDialog           progressDialog;

     static Paint                            paint;

     View.OnTouchListener                    touchListener                         = new OnTouchListener() {

                                                                                        @Override public boolean onTouch(View v, MotionEvent event) {
                                                                                             switch (event.getAction()) {
                                                                                                  case MotionEvent.ACTION_DOWN:
                                                                                                       if ( null == GlobalConstants.LAST_CLICKED_COMPANY ) {
                                                                                                            QNotifications.showShortToast(getActivity(), R.string.choose_company_before_creating_hotspot);
                                                                                                            return true;
                                                                                                       }
                                                                                                       String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                                                       ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, new ClipData.Item(v.getTag().toString()));
                                                                                                       View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                                                       v.startDrag(dragData, shadow, null, 0);
                                                                                                       break;
                                                                                             }
                                                                                             return true;
                                                                                        }
                                                                                   };

     static OnLongClickListener              longClickUpdateHsPositionDragListener = new OnLongClickListener() {

                                                                                        @Override public boolean onLongClick(View v) {
                                                                                             String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                                             ClipData dragData = new ClipData("update", mimeTypes, new ClipData.Item("update"));
                                                                                             View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                                             v.startDrag(dragData, shadow, null, 0);
                                                                                             return true;
                                                                                        }

                                                                                   };
     private final OnActionItemClickListener quickActionListener                   = new QuickAction.OnActionItemClickListener() {

                                                                                        @Override public void onItemClick(int pos) {
                                                                                             switch (pos) {
                                                                                                  case 0:
                                                                                                       try {
                                                                                                            Date today = DateUtils.parseDate(GlobalConstants.TODAY_FROM_SERVER, "yyyy-MM-dd");
                                                                                                            Date sitePlanDate = DateUtils.parseDate(GlobalConstants.SITE_PLAN_IMAGE_NAME, "yyyy-MM-dd");

                                                                                                            if ( !DateUtils.isSameDay(today, sitePlanDate) && today.compareTo(sitePlanDate) > 0 ) {
                                                                                                                 QNotifications.showShortToast(getActivity(), "Could not take a picture, date in past");
                                                                                                                 return;
                                                                                                            }
                                                                                                       } catch (Exception e1) {
                                                                                                            e1.printStackTrace();
                                                                                                       }

                                                                                                       if ( Utils.hasDeviceCamera() ) {
                                                                                                            Utils.captureCameraPhoto(getActivity());
                                                                                                       } else {
                                                                                                            Utils.showCustomToast(getActivity(), R.string.device_has_no_camera, R.drawable.hide_hotspot);
                                                                                                       }
                                                                                                       break;

                                                                                                  case 1:

                                                                                                       QSystem.navigateToActivity(getActivity(), AImagePager_.class);
                                                                                                       break;

                                                                                                  case 2:
                                                                                                       showDialog(GlobalConstants.LAST_CLICKED_HOTSPOT, (SuperActivity) getActivity());
                                                                                                       break;
                                                                                                  default:
                                                                                                       return;
                                                                                             }
                                                                                        }
                                                                                   };

     private final OnActionItemClickListener quickActionListenerAsset              = new QuickAction.OnActionItemClickListener() {

                                                                                        @Override public void onItemClick(int pos) {
                                                                                             switch (pos) {
                                                                                                  case 0:
                                                                                                       QSystem.navigateToActivity(getActivity(), AImagePager_.class);
                                                                                                       break;

                                                                                                  case 1:
                                                                                                       showDialog(GlobalConstants.LAST_CLICKED_HOTSPOT, (SuperActivity) getActivity());
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
          ADAPTER = new ListViewHotsportsAdapter((SuperActivity) getActivity());
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

          dialogHotspotDetail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

          dialogHotspotDetail.setContentView(R.layout.dialog_hotspot_detail);
          dialogTradeHotspotDetail.setContentView(R.layout.dialog_trade_detail);
          dialogTradeHotspotDetail.setTitle("Trade hotspot details");

          llTaskBriefing = ((LinearLayout) dialogHotspotDetail.findViewById(R.id.llTaskBriefing));
          llRiskSchedule = ((LinearLayout) dialogHotspotDetail.findViewById(R.id.llRiskSchedule));
          twDescription = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_description));
          twId = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_id));
          twHotspotType = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_type));
          twIsAssigned = ((TextView) dialogHotspotDetail.findViewById(R.id.dialog_edit_text_is_signed));
          tvCompanyName = ((TextView) dialogHotspotDetail.findViewById(R.id.tvCompanyName));
          tvScopeOfWorks = ((TextView) dialogHotspotDetail.findViewById(R.id.tvScopeOfWorks));
          tvNameOfSupervisior = ((TextView) dialogHotspotDetail.findViewById(R.id.tvNameOfSupervisior));
          tvNameOfSupervisiorDate = ((TextView) dialogHotspotDetail.findViewById(R.id.tvNameOfSupervisiorDate));
          tvRevievedBy = ((TextView) dialogHotspotDetail.findViewById(R.id.tvRevievedBy));
          tvRevievedByDate = ((TextView) dialogHotspotDetail.findViewById(R.id.tvRevievedByDate));
          tvBriefingItemsList = ((TextView) dialogHotspotDetail.findViewById(R.id.tvBriefingItemsList));
          tvActivities = ((TextView) dialogHotspotDetail.findViewById(R.id.tvActivities));
          tvCompanyAndProjectName = ((TextView) dialogHotspotDetail.findViewById(R.id.tvCompanyAndProjectName));
          tvMeansAndFrequency = ((TextView) dialogHotspotDetail.findViewById(R.id.tvMeansAndFrequency));
          tlRiskControl = (TableLayout) dialogHotspotDetail.findViewById(R.id.tlRiskControl);
          tvCompanyNameRiskSchedule = (TextView) dialogHotspotDetail.findViewById(R.id.tvCompanyNameRiskSchedule);

          // dragging elements in right bottom part of the screen
          final ImageView imageViewNotesHotspot = (ImageView) getActivity().findViewById(R.id.imageViewNotesHotspot);
          final ImageView imageViewSafetyHotspot = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_safety);
          final ImageView imageViewWasteHotspot = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_waste);
          final ImageView imageViewPermitsHotspot = (ImageView) getActivity().findViewById(R.id.drawing_hotspots_permits);
          final ImageView imageViewWhiteBoard = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_white_board);
          final ImageView imageViewCamera = (ImageView) getActivity().findViewById(R.id.drawing_hotspot_camera);
          final ImageView imageViewQuickNote = (ImageView) getActivity().findViewById(R.id.ivQuickNote);
          final ImageView imageViewHighRisk = (ImageView) getActivity().findViewById(R.id.ivHighRisk);
          final ImageView imageViewOnTheFly = (ImageView) getActivity().findViewById(R.id.ivOnTheFly);

          imageViewCamera.setTag(Hotspots.CAMERA_HOTSPOT);
          imageViewNotesHotspot.setTag(Hotspots.NOTE_HOTSPOT);
          imageViewSafetyHotspot.setTag(Hotspots.SAFETY_HOTSPOT);
          imageViewWasteHotspot.setTag(Hotspots.WASTE_HOTSPOT);
          imageViewPermitsHotspot.setTag(Hotspots.PERMITS_HOTSPOT);
          imageViewWhiteBoard.setTag(Hotspots.WHITEBOARD_HOTSPOT);
          imageViewQuickNote.setTag(Hotspots.QUICK_NOTE_HOTSPOT);
          imageViewHighRisk.setTag(Hotspots.HIGH_RISK);
          imageViewOnTheFly.setTag(Hotspots.ON_THE_FLY);

          imageViewCamera.setOnTouchListener(touchListener);
          imageViewNotesHotspot.setOnTouchListener(touchListener);
          imageViewSafetyHotspot.setOnTouchListener(touchListener);
          imageViewWasteHotspot.setOnTouchListener(touchListener);
          imageViewPermitsHotspot.setOnTouchListener(touchListener);
          imageViewWhiteBoard.setOnTouchListener(touchListener);
          imageViewQuickNote.setOnTouchListener(touchListener);
          imageViewHighRisk.setOnTouchListener(touchListener);
          imageViewOnTheFly.setOnTouchListener(touchListener);

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

     public static void showPopUpWindow(int[] location, View v) {
          if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.ASSET_HOTSPOT) ) {
               quickActionMenuAssetHotspot.show(v, location);
          }
          if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.NOTE_HOTSPOT) ) {
               quickActionPopUpNoteHs.show(v, location);
          }
          if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equals(Hotspots.WASTE_HOTSPOT) ) {
               quickActionPopUpMenu.show(v, location);
          }

          if ( GlobalConstants.LAST_CLICKED_HOTSPOT.type.equalsIgnoreCase(Hotspots.SAFETY_HOTSPOT) ) {
               quickActionPopUpMenu.show(v, location);
          }
     }

     public static void showDialog(final POJORoboHotspot hotspot, final SuperActivity activity) {

          twId.setText("Company:" + FragmentCompaniesList.getCompanyNameById(hotspot.companyId));

          String hotspotDescription = "";
          if ( hotspot.type.equals(GlobalConstants.Hotspots.ON_THE_FLY) ) {
               if ( null != hotspot.taskList && !hotspot.taskList.isEmpty() ) {
                    hotspotDescription += "Task list\n";
                    for ( int i = 0; i < hotspot.taskList.size(); i++ ) {
                         hotspotDescription += (i + 1) + ") " + hotspot.taskList.get(i).description + "\n";
                    }
               }
          }

          hotspotDescription += "Description:" + hotspot.description;

          twDescription.setText(hotspotDescription);

          twHotspotType.setText("Type: " + hotspot.type);
          twIsAssigned.setText(" Valid from " + hotspot.validFromDate + " to " + hotspot.validToDate);

          if ( !QPreconditions.isNull(hotspot.risks) ) {
               QViews.gone(llRiskSchedule, false);
               tvCompanyNameRiskSchedule.setText(hotspot.risks.companyName);
               tvCompanyAndProjectName.setText(String.format("%s / %s", hotspot.risks.companyName, hotspot.risks.projectName));
               tvMeansAndFrequency.setText(hotspot.risks.mean);
               tvActivities.setText("Activities:\n   Start on Site - " + hotspot.risks.startDate + "\n   End on Site - " + hotspot.risks.endDate + "\n   Scope of Works - " + hotspot.risks.scope);

               if ( !QPreconditions.isNullOrEmpty(hotspot.risks.modelActivitiesAndRisksList) ) {
                    for ( POJORoboSingleActivityAndRisk risk : hotspot.risks.modelActivitiesAndRisksList ) {
                         TableLayout singleActivityAndRiskHazard = (TableLayout) activity.getLayoutInflater().inflate(R.layout.single_risk_and_description_table_layout, null, false);
                         ((TextView) singleActivityAndRiskHazard.findViewById(R.id.twActivitiesAndRiskHazard)).setText(risk.riskName);
                         ((TextView) singleActivityAndRiskHazard.findViewById(R.id.twControlMeasure)).setText(risk.description);
                         tlRiskControl.addView(singleActivityAndRiskHazard);
                    }
               }
          } else {
               QViews.gone(llRiskSchedule, true);
          }

          if ( !QPreconditions.isNull(hotspot.briefingTasks) ) {
               QViews.gone(llTaskBriefing, false);

               tvCompanyName.setText(hotspot.briefingTasks.siteName);
               tvNameOfSupervisior.setText(hotspot.briefingTasks.supervisor);
               tvNameOfSupervisiorDate.setText(hotspot.briefingTasks.supervisionDate);
               tvRevievedByDate.setText(hotspot.briefingTasks.reviewingDate);
               tvRevievedBy.setText(hotspot.briefingTasks.reviewer);
               tvScopeOfWorks.setText(hotspot.briefingTasks.scopeOfWork);

               StringBuilder sb = new StringBuilder();
               if ( !QPreconditions.isNullOrEmpty(hotspot.briefingTasks.safetyAndEnvironmentalControls) ) {
                    for ( int i = 0; i < hotspot.briefingTasks.safetyAndEnvironmentalControls.size(); i++ ) {
                         sb.append(hotspot.briefingTasks.safetyAndEnvironmentalControls.get(i).controlName);
                         // avoiding new line on last item in the list
                         if ( i != (hotspot.briefingTasks.safetyAndEnvironmentalControls.size() - 1) ) {
                              sb.append("\n");
                         }
                    }
               }

               tvBriefingItemsList.setText(sb.toString());
          } else {
               QViews.gone(llTaskBriefing, true);
          }

          imageHotspotType.setBackgroundResource(Utils.getImageIdByType(hotspot.type));
          imageHotspotCompanyColor.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(hotspot.companyId));
          int resImageId = hotspot.isCompleted ? R.drawable.oki : R.drawable.ok48;

          ((ImageView) dialogHotspotDetail.findViewById(R.id.ivIsCompleted)).setImageResource(resImageId);

          ((TextView) dialogHotspotDetail.findViewById(R.id.tvTitle)).setText(hotspot.isCompleted ? "Completed" : "Not completed");

          ((ImageView) dialogHotspotDetail.findViewById(R.id.ivIsCompleted)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {

                    if ( hotspot.isCompleted ) {
                         Utils.showCustomToast(activity, "Hotspot is completed", R.drawable.failure);
                         return;
                    }

                    Dialog d = Utils.getConfiguredDialog(activity);
                    d.setContentView(CDialogCompleteHotspot_.build(activity, d, hotspot.id, dialogHotspotDetail));
                    d.show();
               }
          });

          dialogHotspotDetail.show();
     }

     @Override public void onListItemClick(ListView l, View v, final int position, long id) {
          Animation animation = AnimationManager.load(R.anim.growview);
          animation.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    showDialog(GlobalConstants.SIGNED_HOTSPOTS.get(position), (SuperActivity) getActivity());
               }
          });
          GlobalConstants.HOTSPOTS_BUTTONS_ARRAY.get(position).startAnimation(animation);
     }

     /**
      * Description: provides data for hotspots imageview
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved. Date:
      *         21 Dec 2013 Time: 13:05:59
      */
     public static class ListViewHotsportsAdapter extends ArrayAdapter <String> {

          protected static final int  TAKE_PHOTO_WASTE_CODE  = 0;
          protected static final int  TAKE_PHOTO_SAFETY_CODE = 1;
          public static SuperActivity activity;

          public ListViewHotsportsAdapter ( SuperActivity act ) {
               super(act, R.layout.listview_hotspot_list_item, new String[0]);
               activity = act;
          }

          public void updateHotspotsButtonsList() {
               // check if hotspot layout over canvas
               if ( !QPreconditions.isNull(hotspotsButtonLayout) ) {
                    // 1st view is drawing pannel, all others are hotspot button, remove them
                    hotspotsButtonLayout.removeViews(1, hotspotsButtonLayout.getChildCount() - 1);
               } else {
                    return;
               }

               // if there is no hotspots in filter, just return
               // if ( FilterManager.activeHotspots.isEmpty() ) { return; }

               if ( null != GlobalConstants.SIGNED_HOTSPOTS ) {
                    for ( int i = 0; i < GlobalConstants.SIGNED_HOTSPOTS.size(); i++ ) {
                         try {
                              // CREATE ADN SET UP BUTTON
                              final HotspotButton ibutton = new HotspotButton(activity, GlobalConstants.SIGNED_HOTSPOTS.get(i));

                              // listener if user deside to reallocate hotspot
                              ibutton.setOnLongClickListener(longClickUpdateHsPositionDragListener);

                              TextView textViewDescription = new TextView(activity);
                              TextView textViewAmountResources = null;

                              if ( ibutton.getHotspot().type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                                   textViewDescription.setTextColor(Color.BLACK);
                                   textViewDescription.setBackgroundResource(R.drawable.background_view_rounded_single_trades);
                                   textViewAmountResources = new TextView(activity);
                                   textViewAmountResources.setText(String.valueOf(ibutton.getHotspot().amount));
                                   textViewAmountResources.setBackgroundResource(R.drawable.background_view_rounded_single_amount_of_people);
                                   textViewAmountResources.setPadding(7, 5, 7, 5);
                              } else {
                                   textViewDescription.setTextColor(Color.WHITE);
                                   textViewDescription.setBackgroundResource(R.drawable.background_view_rounded_single);
                              }

                              textViewDescription.setTextSize(15);
                              textViewDescription.setText(String.valueOf(ibutton.getHotspot().id));

                              // add only views from filter (check if filter allowed us to display this type of hotspot)
                              if ( FilterManager.activeHotspots.get(ibutton.getHotspot().type) == Boolean.TRUE ) {
                                   // add only hotspots for companies whifch is in filter list
                                   if ( FilterManager.activeCompaniesColor.contains(FragmentCompaniesList.getCompanyColorById(ibutton.getHotspot().companyId)) ) {

                                        // Check if hotspot is completed state is the same as in the filter
                                        if ( ibutton.getHotspot().isCompleted == FilterManager.displayCompleted ) {
                                             hotspotsButtonLayout.addView(ibutton, ibutton.getPositionOnCanvas());
                                        }

                                        if ( FilterManager.displayHSId ) {
                                             // if it's note hotspot display description instead of id
                                             // Add id to all hotspots exclude Quick Note
                                             if ( !ibutton.getHotspot().type.equals(GlobalConstants.Hotspots.QUICK_NOTE_HOTSPOT) && !ibutton.getHotspot().type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) ) {
                                                  textViewDescription.setPadding(5, 5, 5, 5);
                                                  if ( ibutton.getHotspot().isCompleted == FilterManager.displayCompleted ) {
                                                       hotspotsButtonLayout.addView(textViewDescription, ibutton.getPositionOnCanvas(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 10, 50));
                                                  }
                                                  textViewDescription.startAnimation(AnimationManager.load(android.R.anim.slide_in_left, 600));
                                             }
                                        }
                                        if ( GlobalConstants.SIGNED_HOTSPOTS.get(i).type.equals(GlobalConstants.Hotspots.TRADE_HOTSPOT) && ibutton.getHotspot().isCompleted == FilterManager.displayCompleted ) {
                                             hotspotsButtonLayout.addView(textViewAmountResources, ibutton.getPositionOnCanvas(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 30, 0));
                                        }
                                   }
                              }
                              if ( ibutton.getHotspot().isCompleted == FilterManager.displayCompleted ) {
                                   GlobalConstants.HOTSPOTS_BUTTONS_ARRAY.add(i, ibutton);
                              }
                         } catch (Exception ex) {
                              ex.printStackTrace();
                         }
                    }
               }
          }

          @Override public int getCount() {
               int count = 0;
               if ( null != GlobalConstants.SIGNED_HOTSPOTS ) {
                    for ( POJORoboHotspot hotspot : GlobalConstants.SIGNED_HOTSPOTS ) {
                         if ( hotspot.isCompleted == FilterManager.displayCompleted ) {
                              count++;
                         }
                    }
               }
               return count;
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
               imageView.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), Utils.getBitmapByHotspotType(GlobalConstants.SIGNED_HOTSPOTS.get(position).type)));
               try {
                    // imageViewCompanyColour.setBackgroundColor(FragmentCompaniesList.getCompanyColorById(GlobalConstants.SIGNED_HOTSPOTS.get(position).companyId));
                    paint.setColor(FragmentCompaniesList.getCompanyColorById(GlobalConstants.SIGNED_HOTSPOTS.get(position).companyId));
                    imageViewCompanyColour.setImageBitmap(createRoundImage(paint));
               } catch (Exception e) {
                    QLog.debug(e.getMessage(), e);
                    imageViewCompanyColour.setVisibility(View.INVISIBLE);
               }
               try {
                    txtTitle.setText("(" + GlobalConstants.SIGNED_HOTSPOTS.get(position).id + ") " + GlobalConstants.SIGNED_HOTSPOTS.get(position).description);
               } catch (Exception ex) {
                    QLog.debug(ex.getMessage(), ex);
                    txtTitle.setText("Def value");
               }
               return rowView;
          }
     }
}