package com.touchip.organizer.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarDrawingCompanies;
import com.touchip.organizer.activities.custom.components.ActionBarDrawingCompanies_;
import com.touchip.organizer.activities.custom.components.CompaniesColorFilterView;
import com.touchip.organizer.activities.custom.components.CompaniesColorFilterView_;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.activities.custom.components.SlideMenuDrawingCopmanies;
import com.touchip.organizer.activities.custom.components.SlideMenuDrawingCopmanies_;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.communication.rest.request.CreateHotspotRequest;
import com.touchip.organizer.communication.rest.request.CreateTradeHotspotRequest;
import com.touchip.organizer.communication.rest.request.DownloadDrawingPathsRequest;
import com.touchip.organizer.communication.rest.request.DownloadSitePlanWithFloorRequest;
import com.touchip.organizer.communication.rest.request.GetTradesRequest;
import com.touchip.organizer.communication.rest.request.SaveDrawingPathsRequest;
import com.touchip.organizer.communication.rest.request.UploadCapturedPhotoRequest;
import com.touchip.organizer.communication.rest.request.listener.CreateHotspotRequestListener;
import com.touchip.organizer.communication.rest.request.listener.CreateTradeHotspotRequestListener;
import com.touchip.organizer.communication.rest.request.listener.DownloadDrawingPathsRequestListener;
import com.touchip.organizer.communication.rest.request.listener.DownloadSitePlanWithFloorRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetTradesRequestListener;
import com.touchip.organizer.communication.rest.request.listener.SaveDrawingPathsRequestListener;
import com.touchip.organizer.communication.rest.request.listener.UploadCapturedPhotoRequestListener;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.DialogUtils;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

/**
 * Description: class represents drawing functions on site plans
 * 
 * @author Ihor Karpachev
 *         Copyright (c) 2013-2014 Datascope Systems Ltd. All Rights Reserved. Date: 17
 *         Dec 2013 Time: 11:24:58
 */

@EActivity ( R.layout.fragments_companies_drawing ) public class DrawingCompaniesActivity extends SpiceFragmentActivity {

     // get drawing view
     @ViewById ImageButton                        menuStatusArrow;
     @ViewById public static CompaniesDrawingView DRAW_VIEW;
     @ViewById LinearLayout                       llCompanies;
     @ViewById static LinearLayout                llTrades;
     @ViewById static ScrollView                  svTrades;
     @ViewById LinearLayout                       llFilters;
     @ViewById TextView                           twBottomStatus;
     @ViewById RelativeLayout                     rlHotspotsOnCanvas;
     @ViewById ImageButton                        refresh;

     public static DrawingCompaniesActivity       INSTANCE;
     public static ActionBarDrawingCompanies      customActionBar;
     private SlideMenuDrawingCopmanies            sideSlideMenu;
     private Dialog                               dialog;
     private static ProgressDialog                progressDialog;

     @AfterViews void afterViews() {
          INSTANCE = this;
          // load all views
          customActionBar = ActionBarDrawingCompanies_.build(DrawingCompaniesActivity.this);
          sideSlideMenu = SlideMenuDrawingCopmanies_.build(DrawingCompaniesActivity.this);

          sideSlideMenu.setMenuControlButton(menuStatusArrow);
          Utils.configureCustomActionBar(getActionBar(), customActionBar);

          // preapare progress dialog
          progressDialog = new ProgressDialog(DrawingCompaniesActivity.this);
          progressDialog.setMessage(Utils.getResources(R.string.loading));
          progressDialog.setCancelable(true);

          dialog = new Dialog(DrawingCompaniesActivity.this);
          dialog.setCancelable(false);

          for ( int i = 0; i < FragmentCompaniesList.COMPANIES_LIST.size(); i++ ) {
               CompaniesColorFilterView singleTradeItem = CompaniesColorFilterView_.build(DrawingCompaniesActivity.this);
               singleTradeItem.setCompany(FragmentCompaniesList.COMPANIES_LIST.get(i));
               llCompanies.addView(singleTradeItem);
          }

          // Add show all drawings button to companies filter
          llCompanies.addView(CompaniesColorFilterView.createShowAllDrawingsFilterButtons());
          // Add hide all drawings button to companies filter
          llCompanies.addView(CompaniesColorFilterView.createHideAllDrawingsFilterButtons());

          FragmentHotspotsList.hotspotsButtonLayout = rlHotspotsOnCanvas;
          // Sizes from dimensions
          DRAW_VIEW.setBrushSize(getResources().getInteger(R.integer.small_size));
          // Set on drawn listener on drawing canvas
          setDragListener();
          // initiate bottom string
          twBottomStatus
                    .setText("Today:" + Utils.formatDate(new Date()) + ", Plan Name: " + GlobalConstants.SITE_PLAN_IMAGE_NAME + ", Floor:" + GlobalConstants.CURRENT_FLOOR);
     }

     // Toggles progress spinner visibility when we conduct a search
     private void toggleIndeterminateSpinner(int state)
     {
          // Indeterminate Progress Spinner
          ProgressBar progressSpinner = (ProgressBar) findViewById(R.id.pb);
          /*
           * switch (state)
           * {
           * case HIDE:
           * progressSpinner.setVisibility(View.VISIBLE);
           * break;
           * case SHOW:
           * progressSpinner.setVisibility(View.GONE);
           * break;
           * }
           */
     }

     @Override public void onBackPressed() {
          if ( sideSlideMenu.getMenu().isMenuShowing() ) {
               sideSlideMenu.getMenu().showContent();
          } else {
               super.onBackPressed();
          }
     }

     public void saveAndSendDrawing(final boolean askSave) {
          ObjectOutputStream out = null;
          try {
               File file = new File(getApplicationContext().getCacheDir(), System.currentTimeMillis() + ".paths");
               List <PathSerializable> listPathes = DRAW_VIEW.getPaths();
               out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(listPathes);
               out.close();
               SaveDrawingPathsRequest request = new SaveDrawingPathsRequest(new FileSystemResource(file));
               getSpiceManager()
                         .execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveDrawingPathsRequestListener(DrawingCompaniesActivity.this, file, askSave));
          } catch (Exception e) {
               Utils.logw(e.getMessage());
          }
     }

     public void showSaveDrawingDialog(final boolean askSave) {
          DialogUtils.showSaveDrawingDialog(askSave, this);
     }

     /**
      * Load image on drawing panel from server
      * 
      * @param date
      * @param floor
      */
     public void loadImage(final Date date, final String floor) {
          AlertDialog.Builder builder = new AlertDialog.Builder(DrawingCompaniesActivity.this);
          builder.setTitle("Select drawing");
          ListView modeList = new ListView(DrawingCompaniesActivity.this);
          List <String> floors = null;
          saveAndSendDrawing(false);
          String selectedDate = (null != date) ? Utils.formatDate(date) : GlobalConstants.SITE_PLAN_IMAGE_NAME;

          for ( int i = 0; i < DataAccess.datestoHighlight.size(); i++ ) {
               if ( DataAccess.datestoHighlight.get(i).date.equals(selectedDate) ) {
                    if ( !DataAccess.datestoHighlight.isEmpty() ) {
                         GlobalConstants.CURRENT_FLOOR = DataAccess.datestoHighlight.get(i).floors.get(0);
                    } else {
                         Utils.showCustomToast(DrawingCompaniesActivity.this, "Error obtaining floors!", R.drawable.hide_hotspot);
                         return;
                    }
                    floors = DataAccess.datestoHighlight.get(i).floors;
               }
          }

          ArrayAdapter <String> modeAdapter = new ArrayAdapter <String>(DrawingCompaniesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, floors
                    .toArray(new String[floors.size()]));
          modeList.setAdapter(modeAdapter);
          builder.setView(modeList);
          dialog = builder.create();

          modeList.setOnItemClickListener(new OnItemClickListener() {

               @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                    final String floor = adapter.getItemAtPosition(pos).toString();
                    GlobalConstants.CURRENT_FLOOR = floor;
                    Utils.showCustomToast(DrawingCompaniesActivity.this, String.valueOf("Loading ... " + floor), R.drawable.floors);
                    if ( null != date ) {
                         GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(date).toString();
                    }
                    DownloadSitePlanWithFloorRequest request = new DownloadSitePlanWithFloorRequest();
                    getSpiceManager()
                              .execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DownloadSitePlanWithFloorRequestListener(DrawingCompaniesActivity.this));
                    dialog.dismiss();
               }
          });
          if ( !floors.isEmpty() ) {
               dialog.show();
          }
     }

     public void createHotspot(Map <String, String> vars) {
          CreateHotspotRequest request = new CreateHotspotRequest(vars);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateHotspotRequestListener(this));
     }

     public void loadPathes() {
          INSTANCE.setProgressBarIndeterminateVisibility(true);
          DownloadDrawingPathsRequest request = new DownloadDrawingPathsRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DownloadDrawingPathsRequestListener(INSTANCE));
     }

     @OptionsItem void homeSelected() {
          if ( DRAW_VIEW.isNeedAutoSave() ) {
               showSaveDrawingDialog(true);
          } else {
               onBackPressed();
          }
     }

     public void showOrHideResources() {
          GetTradesRequest request = new GetTradesRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetTradesRequestListener(DrawingCompaniesActivity.this));
     }

     public static CompaniesDrawingView getDrawView() {
          return DRAW_VIEW;
     }

     public static ActionBarDrawingCompanies getCustomActionBar() {
          return customActionBar;
     }

     public static ProgressDialog getProgressDialog() {
          return progressDialog;
     }

     public void setFilterLayoutVisibility(boolean isChecked) {
          if ( isChecked ) {
               llFilters.setVisibility(View.VISIBLE);
               llFilters.startAnimation(AnimationManager.load(R.anim.push_left_in, 500));
          } else {
               AnimationManager.animateMenu(llFilters, View.GONE, R.anim.push_left_out, 1000);
          }
     }

     @Click void menuStatusArrow() {
          if ( sideSlideMenu.getMenu().isMenuShowing() ) {
               sideSlideMenu.getMenu().showContent();
          } else {
               sideSlideMenu.getMenu().showMenu();
          }
     }

     /**
      * Refresh drawing (download all pathes and invalidate canvas)
      */
     @Click void refresh() {
          refresh.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          loadPathes();
     }

     @OnActivityResult ( GlobalConstants.CAPTURE_CAMERA_PHOTO ) void onResult(int resultCode, Intent data) {
          if ( resultCode == Activity.RESULT_OK ) {
               MultiValueMap <String, Object> parts = new LinkedMultiValueMap <String, Object>();
               parts.add("image", new FileSystemResource(GlobalConstants.capturedPhotoFilename));
               parts.add("id", String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));

               UploadCapturedPhotoRequest request = new UploadCapturedPhotoRequest(parts);
               getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new UploadCapturedPhotoRequestListener(this));
          }
     }

     public static ScrollView getSvTrades() {
          return svTrades;
     }

     public static LinearLayout getLlTrades() {
          return llTrades;
     }

     private void setDragListener() {
          DRAW_VIEW.setOnDragListener(new OnDragListener() {
               LayoutParams params;

               @Override public boolean onDrag(View v, final DragEvent event) {
                    switch (event.getAction()) {
                         case DragEvent.ACTION_DROP:

                              params = new RelativeLayout.LayoutParams(60, 60);
                              params.leftMargin = (int) (event.getX() - 30);
                              params.topMargin = (int) (event.getY() - 30);
                              final String type = event.getClipData().getDescription().getLabel().toString();

                              final Map <String, String> requestParams = new HashMap <String, String>();

                              requestParams.put(GlobalConstants.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                              requestParams.put(GlobalConstants.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                              requestParams.put(GlobalConstants.X, String.valueOf((event.getX() - 30) / CompaniesDrawingView.WIDTH));
                              requestParams.put(GlobalConstants.Y, String.valueOf((event.getY() - 30) / CompaniesDrawingView.HEIGHT));

                              // TRADE HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("trade") ) {
                                   String tradeId = type.substring(0, type.indexOf("_"));
                                   final String description = DataAccess.findTradeHotspotById(Integer.parseInt(tradeId)).description;
                                   requestParams.put(GlobalConstants.ID, tradeId);
                                   requestParams.put(GlobalConstants.AMOUNT, String.valueOf(1));
                                   requestParams.put(GlobalConstants.DESCRIPTION, description);
                                   requestParams.put(GlobalConstants.TYPE, GlobalConstants.Hotspots.TRADE_HOTSPOT);
                                   requestParams.put(GlobalConstants.FLOOR, GlobalConstants.CURRENT_FLOOR);

                                   CreateTradeHotspotRequest request = new CreateTradeHotspotRequest(requestParams);
                                   getSpiceManager()
                                             .execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateTradeHotspotRequestListener(DrawingCompaniesActivity.this, description));

                                   return false;
                              }

                              // SIMPLE HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("hotspot") ) {
                                   requestParams.put(GlobalConstants.ID, String.valueOf("-1"));
                                   requestParams.put(GlobalConstants.TYPE, type);

                                   dialog.setTitle(null != DataAccess.LAST_CLICKED_COMPANY ? type + " for " + DataAccess.LAST_CLICKED_COMPANY.companyName : type);
                                   dialog.setContentView(R.layout.dialog_input_hotspot_detail);

                                   final EditText editTextDescription = (EditText) dialog.findViewById(R.id.dialog_edittext_input_text_to_draw);
                                   final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner_permits_type);

                                   final TextView twValidFrom = (TextView) dialog.findViewById(R.id.dialog_hotspot_detail_textview_valid_from);
                                   final TextView twValidTo = (TextView) dialog.findViewById(R.id.dialog_hotspot_detail_textview_valid_to);
                                   String date = Utils.DATE_FORMAT.format(new Date());
                                   twValidFrom.setText(date);
                                   twValidTo.setText(date);

                                   if ( type.equals(Hotspots.PERMITS_HOTSPOT) ) {
                                        editTextDescription.setVisibility(View.GONE);
                                        // Create an ArrayAdapter using the string array and a default spinner layout
                                        ArrayAdapter <CharSequence> adapter = ArrayAdapter
                                                  .createFromResource(DrawingCompaniesActivity.this, R.array.permits_types, android.R.layout.simple_spinner_item);
                                        // Specify the layout to use when the list of choices appears
                                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                        // Apply the adapter to the spinner
                                        spinner.setAdapter(adapter);

                                        ImageButton ibValidFrom = (ImageButton) dialog.findViewById(R.id.dialog_hotspot_detail_imagebutton_valid_from);
                                        ImageButton ibValidTo = (ImageButton) dialog.findViewById(R.id.dialog_hotspot_detail_imagebutton_valid_to);

                                        ibValidFrom.setOnClickListener(new OnClickListener() {

                                             @Override public void onClick(View v) {
                                                  final CaldroidFragment calendar = new CaldroidFragment();
                                                  calendar.setCaldroidListener(new CaldroidListener() {

                                                       @Override public void onSelectDate(Date date, View view) {

                                                            Date now = new Date();
                                                            if ( !DateUtils.isSameDay(date, now) && now.compareTo(date) > 0 ) {
                                                                 Utils.showToast(getApplicationContext(), "Start date could not be in past", true);
                                                                 return;
                                                            }
                                                            Date validTo;
                                                            try {
                                                                 validTo = Utils.DATE_FORMAT.parse(twValidTo.getText().toString());
                                                                 if ( date.after(validTo) ) {
                                                                      twValidTo.setText(Utils.DATE_FORMAT.format(date));
                                                                 }
                                                            } catch (Exception e) {
                                                                 Utils.logw(e.getMessage());
                                                            }
                                                            twValidFrom.setText(Utils.DATE_FORMAT.format(date));
                                                            calendar.dismiss();
                                                       }
                                                  });
                                                  calendar.show(getSupportFragmentManager(), GlobalConstants.LOG_TAG);
                                             }
                                        });

                                        ibValidTo.setOnClickListener(new OnClickListener() {

                                             @Override public void onClick(View v) {
                                                  final CaldroidFragment calendar = new CaldroidFragment();
                                                  calendar.setCaldroidListener(new CaldroidListener() {

                                                       @Override public void onSelectDate(Date date, View view) {

                                                            try {
                                                                 String validFrom = twValidFrom.getText().toString();
                                                                 Date dateFrom = Utils.DATE_FORMAT.parse(validFrom);
                                                                 if ( dateFrom.after(date) ) {
                                                                      Utils.showToast(getApplicationContext(), "Date should be equal or greater: " + validFrom, true);
                                                                      return;
                                                                 }
                                                            } catch (Exception e) {
                                                                 Utils.logw(e.getMessage());
                                                            }

                                                            twValidTo.setText(Utils.DATE_FORMAT.format(date));
                                                            calendar.dismiss();
                                                       }
                                                  });
                                                  calendar.show(getSupportFragmentManager(), GlobalConstants.LOG_TAG);
                                             }
                                        });

                                   } else {
                                        ((LinearLayout) dialog.findViewById(R.id.permits_details_linear_layout)).setVisibility(View.GONE);
                                   }

                                   Button buttonCreateHotspotCancel = (Button) dialog.findViewById(R.id.dialog_cancel);
                                   final Button buttonCreateHotspotOk = (Button) dialog.findViewById(R.id.dialog_button_ok);

                                   buttonCreateHotspotCancel.setOnClickListener(new OnClickListener() {

                                        @Override public void onClick(View v) {
                                             dialog.dismiss();
                                        }
                                   });

                                   buttonCreateHotspotOk.setOnClickListener(new View.OnClickListener() {

                                        @Override public void onClick(View v) {

                                             if ( null == DataAccess.LAST_CLICKED_COMPANY ) {
                                                  Utils.showToast(getApplicationContext(), "Choose company before creating hotpost", true);
                                                  dialog.dismiss();
                                                  return;
                                             }

                                             String hotspotDescription = "";

                                             requestParams.put(GlobalConstants.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                             requestParams.put(GlobalConstants.COMPANY_ID, String.valueOf(DataAccess.LAST_CLICKED_COMPANY.companyId));

                                             if ( type.equals(Hotspots.PERMITS_HOTSPOT) ) {
                                                  hotspotDescription = spinner.getSelectedItem().toString();
                                                  requestParams.put("validfromdate", twValidFrom.getText().toString());
                                                  requestParams.put("validtodate", twValidTo.getText().toString());

                                             } else {
                                                  hotspotDescription = editTextDescription.getText().toString();
                                             }
                                             if ( Utils.isBlank(hotspotDescription) ) {
                                                  Utils.showToast(getApplicationContext(), "Description cannot be empty", true);
                                             } else {
                                                  buttonCreateHotspotOk.setClickable(false);
                                                  buttonCreateHotspotOk.setText("Creating hotspot....");
                                                  requestParams.put(GlobalConstants.DESCRIPTION, hotspotDescription);
                                                  createHotspot(requestParams);
                                                  dialog.dismiss();
                                             }
                                        }
                                   });
                                   dialog.show();
                              } else {
                                   // assign Unassign hotspot
                                   requestParams.put("id", String.valueOf(type));
                                   createHotspot(requestParams);
                              }

                              break;
                         default:
                              return true;
                    }
                    return true;
               }
          });
     }
}
