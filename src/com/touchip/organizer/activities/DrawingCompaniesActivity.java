package com.touchip.organizer.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.text.method.PasswordTransformationMethod;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
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
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList.ListViewUnsignedHotspotsAdapter;
import com.touchip.organizer.communication.rest.model.ModelFullSiteInfo;
import com.touchip.organizer.communication.rest.model.ModelWhiteboards;
import com.touchip.organizer.communication.rest.model.PathId;
import com.touchip.organizer.communication.rest.request.AssignUnassignHotspotRequest;
import com.touchip.organizer.communication.rest.request.CreateAssetHotspotRequest;
import com.touchip.organizer.communication.rest.request.CreateHotspotRequest;
import com.touchip.organizer.communication.rest.request.CreateTradeHotspotRequest;
import com.touchip.organizer.communication.rest.request.DownloadDrawingPathsRequest;
import com.touchip.organizer.communication.rest.request.GetAssetsRequest;
import com.touchip.organizer.communication.rest.request.GetDeliveriesListRequest;
import com.touchip.organizer.communication.rest.request.GetTradesRequest;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.UpdateHotspotPositionRequest;
import com.touchip.organizer.communication.rest.request.UploadCapturedPhotoRequest;
import com.touchip.organizer.communication.rest.request.listener.AssignUnassignHotspotRequestListener;
import com.touchip.organizer.communication.rest.request.listener.CreateAssetHotspotRequestListener;
import com.touchip.organizer.communication.rest.request.listener.CreateHotspotRequestListener;
import com.touchip.organizer.communication.rest.request.listener.CreateTradeHotspotRequestListener;
import com.touchip.organizer.communication.rest.request.listener.DownloadDrawingPathsRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetAssetsRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetDeliveriesListRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetPathsCreationTimeRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetTradesRequestListener;
import com.touchip.organizer.communication.rest.request.listener.ResponseFullSitePlanInfo;
import com.touchip.organizer.communication.rest.request.listener.ResponseSaveDrawingPathsOnFingerRelease;
import com.touchip.organizer.communication.rest.request.listener.UpdateHotspotPositionRequestListener;
import com.touchip.organizer.communication.rest.request.listener.UploadCapturedPhotoRequestListener;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.HotspotManager.Hotspots;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

/**
 * Description: class represents drawing functions on site plans
 * 
 * @author Ihor Karpachev Copyright (c) 2013-2014 Datascope Systems Ltd. All
 *         Rights Reserved. Date: 17 Dec 2013 Time: 11:24:58
 */

@EActivity ( R.layout.fragments_companies_drawing ) public class DrawingCompaniesActivity extends SpiceFragmentActivity {

     // get drawing view
     // @ViewById ImageButton menuStatusArrow;

     @ViewById public static CompaniesDrawingView DRAW_VIEW;
     @ViewById LinearLayout                       llCompanies;
     @ViewById static LinearLayout                llFilters;
     @ViewById static LinearLayout                llTrades;
     @ViewById static LinearLayout                llAssets;
     // @ViewById static LinearLayout llThumnailsAreasAndFloors;
     // @ViewById TextView twBottomStatus;
     @ViewById TextView                           twTotalAmountOfPeople , tvSPN;
     @ViewById static RelativeLayout              rlHotspotsOnCanvas;
     @ViewById ImageView                          ibUndo , ibRedo , ibBrushSize , ibWb , ibResources , ibHotspots , ibChangeFloor , ibChangeDate , ibRefresh;

     // ListView ASSETS and Trades
     @ViewById static ListView                    lwAssets;

     @ViewById static ListView                    lwTrades;
     @ViewById public static ImageView            ivCompanyColor;

     public static DrawingCompaniesActivity       INSTANCE;

     public static ActionBarDrawingCompanies      customActionBar;

     private Dialog                               dialog;

     private static ProgressDialog                progressDialog;

     @AfterViews void afterViews() {

          INSTANCE = this;

          FragmentHotspotsList.hotspotsButtonLayout = rlHotspotsOnCanvas;

          // load all views
          customActionBar = ActionBarDrawingCompanies_.build(DrawingCompaniesActivity.this);

          // sideSlideMenu.setMenuControlButton(menuStatusArrow);
          Utils.configureCustomActionBar(getActionBar(), customActionBar);
          getActionBar().setIcon(R.drawable.menu);

          // preapare progress dialog
          progressDialog = new ProgressDialog(DrawingCompaniesActivity.this);
          progressDialog.setMessage(Utils.getResources(R.string.loading));
          progressDialog.setCancelable(true);

          dialog = new Dialog(DrawingCompaniesActivity.this);
          dialog.setCancelable(false);

          for ( int i = 0; i < GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.size(); i++ ) {
               CompaniesColorFilterView singleTradeItem = CompaniesColorFilterView_.build(DrawingCompaniesActivity.this);
               singleTradeItem.setCompany(GlobalConstants.SITE_PLAN_FULL_INFO.companyWrappersList.get(i));
               llCompanies.addView(singleTradeItem);
          }

          // Add show all drawings button to companies filter
          llCompanies.addView(CompaniesColorFilterView.createShowAllDrawingsFilterButtons());
          // Add hide all drawings button to companies filter
          llCompanies.addView(CompaniesColorFilterView.createHideAllDrawingsFilterButtons());

          // Sizes from dimensions
          DRAW_VIEW.setBrushSize(getResources().getInteger(R.integer.small_size));
          DRAW_VIEW.setDrawingCacheEnabled(true);
          setDragListener();
          // initiate bottom string
          // twBottomStatus.setText(String.format("Today:%s, Plan Name:%s, Floor:%s", Utils.formatDate(new Date()), GlobalConstants.SITE_PLAN_IMAGE_NAME,
          // GlobalConstants.CURRENT_AREA));
          customActionBar.setUpCurrentSiteInfo(GlobalConstants.SITE_PLAN_FULL_INFO.today, GlobalConstants.SITE_PLAN_IMAGE_NAME, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);

          Thread updateHs = new Thread(new Runnable() {

               @Override public void run() {
                    try {
                         Thread.sleep(1000);
                         DrawingCompaniesActivity.this.runOnUiThread(new Runnable() {

                              @Override public void run() {
                                   FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList(Hotspots.HOTSPOTS_NAMES[Hotspots.SHOW_ALL]);
                              }
                         });

                    } catch (InterruptedException e) {
                         e.printStackTrace();
                    }

               }
          });

          updateHs.start();

     }

     @Override protected void onResume() {
          super.onResume();
          tvSPN.setText(GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
     }

     @Click void ibChangeFloor() {
          loadImage(null);
     }

     @Click void ibRefresh() {
          DrawingCompaniesActivity.loadPathes();
     }

     @Click void ibWb() {
          showProgressDialog();
          Map <String, String> params = new HashMap <String, String>();

          params.put(HTTP_PARAMS.AREA_NAME, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
          params.put(HTTP_PARAMS.SITE_ID, GlobalConstants.SITE_ID);
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);

          SuperRequest <ModelWhiteboards> request = new SuperRequest <ModelWhiteboards>(ModelWhiteboards.class, RestAddresses.GET_WHITEBOARDS, null, params);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetPathsCreationTimeRequestListener(this));
     }

     @Click void ibChangeDate() {
          // TODO
          Utils.showToast(this, "NOT implemented", false);
     }

     @Click void ibBrushSize() {
          changeBrushSize();
     }

     @Click void ibHotspots() {
          llShowFilters();
     }

     @Click void ibUndo() {
          DRAW_VIEW.undo();
          saveAndSendDrawingOnBackgroundThread();
     }

     @Click void ibRedo() {
          DRAW_VIEW.redo();
          saveAndSendDrawingOnBackgroundThread();
     }

     @Click void ibResources() {
          showResourcesPopup();
     }

     public static LinearLayout getllFilters() {
          return llFilters;
     }

     public void setTotalAmountOfPeople(int amount) {
          twTotalAmountOfPeople.setText(String.format("Total: %s", amount));
     }

     @Background public void saveAndSendDrawingOnBackgroundThread() {

          MultiValueMap <String, Object> params = new LinkedMultiValueMap <String, Object>();

          ObjectOutputStream out = null;
          FileSystemResource snapshot = null;
          try {
               File file = new File(DrawingCompaniesActivity.INSTANCE.getCacheDir(), System.currentTimeMillis() + ".paths");
               List <PathSerializable> listPathes = DrawingCompaniesActivity.DRAW_VIEW.getPaths();
               out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(listPathes);
               out.close();
               if ( null != file ) {
                    params.add(HTTP_PARAMS.DRAWING_DATA, new FileSystemResource(file));
               }
          } catch (Exception e) {
               Utils.logw(e.getMessage());
          }

          // attempt to save drawing
          try {
               String saveResponseFilePath = Media.insertImage(DrawingCompaniesActivity.INSTANCE.getContentResolver(), DrawingCompaniesActivity.DRAW_VIEW.getDrawingCache(), Utils.getCurrentDate() + ".png", "drawing");
               if ( null != saveResponseFilePath ) {
                    snapshot = new FileSystemResource(new File(Utils.getRealPathFromURI(DrawingCompaniesActivity.INSTANCE.getApplicationContext(), Uri.parse(saveResponseFilePath))));
               }

               if ( null != snapshot ) {
                    params.add(HTTP_PARAMS.SNAPSHOT, snapshot);
               }
          } catch (Exception ex) {
               ex.printStackTrace();
          }
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.MULTIPART_FORM_DATA);

          SuperRequest <PathId> request = null;

          // Create new path
          if ( GlobalConstants.SITE_PLAN_FULL_INFO.pathId == 0 ) {
               params.add(HTTP_PARAMS.SITE_ID, GlobalConstants.SITE_ID);
               params.add(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
               params.add(HTTP_PARAMS.AREA_NAME, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
               request = new SuperRequest <PathId>(PathId.class, RestAddresses.CREATE_SITE_PLAN_DRAWING_PATHES, new FormHttpMessageConverter(), params, headers);
          } else {
               // update exisiting one
               params.add(HTTP_PARAMS.PATH_ID, String.valueOf(GlobalConstants.SITE_PLAN_FULL_INFO.pathId));
               request = new SuperRequest <PathId>(PathId.class, RestAddresses.UPDATE_SITE_PLAN_DRAWING_PATHES, new FormHttpMessageConverter(), params, headers);
          }

          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseSaveDrawingPathsOnFingerRelease(this, false));

     }

     /**
      * Load image on drawing panel from server
      * 
      * @param date
      * @param floor
      */
     public void loadImage(final Date date) {
          AlertDialog.Builder builder = new AlertDialog.Builder(DrawingCompaniesActivity.this);
          builder.setTitle(R.string.select_drawing);
          ListView modeList = new ListView(DrawingCompaniesActivity.this);

          ArrayAdapter <String> modeAdapter = new ArrayAdapter <String>(DrawingCompaniesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, GlobalConstants.SITE_PLAN_FULL_INFO.areas);
          modeList.setAdapter(modeAdapter);
          builder.setView(modeList);
          dialog = builder.create();

          modeList.setOnItemClickListener(new OnItemClickListener() {

               @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                    final String selectedArea = adapter.getItemAtPosition(pos).toString();
                    Utils.showCustomToast(DrawingCompaniesActivity.this, String.valueOf("Loading area - " + selectedArea), R.drawable.floors);

                    GlobalConstants.SITE_PLAN_FULL_INFO.currentArea = selectedArea;

                    if ( null != date ) {
                         GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(date).toString();
                    }
                    HashMap <String, String> params = new HashMap <String, String>();
                    params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                    params.put(HTTP_PARAMS.SITE_ID, GlobalConstants.SITE_ID);
                    params.put(HTTP_PARAMS.AREA_NAME, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);

                    SuperRequest <ModelFullSiteInfo> request = new SuperRequest <ModelFullSiteInfo>(ModelFullSiteInfo.class, RestAddresses.DOWNLOAD_SITE_PLAN, null, params);
                    getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseFullSitePlanInfo(DrawingCompaniesActivity.this));
                    dialog.dismiss();
               }
          });
          if ( !GlobalConstants.SITE_PLAN_FULL_INFO.areas.isEmpty() ) {
               dialog.show();
          }
     }

     public void assignUnassignHotspotRequest(Map <String, String> vars) {
          progressDialog.show();
          AssignUnassignHotspotRequest request = new AssignUnassignHotspotRequest(vars);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new AssignUnassignHotspotRequestListener(this));
     }

     public void createHotspot(Map <String, String> vars) {
          progressDialog.show();
          CreateHotspotRequest request = new CreateHotspotRequest(vars);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateHotspotRequestListener(this));
     }

     private void createAssetHotspot(Map <String, String> requestParams) {
          progressDialog.show();
          CreateAssetHotspotRequest request = new CreateAssetHotspotRequest(requestParams);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateAssetHotspotRequestListener(this));
     }

     public static void loadPathes() {
          progressDialog.show();
          DownloadDrawingPathsRequest request = new DownloadDrawingPathsRequest();
          INSTANCE.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DownloadDrawingPathsRequestListener(INSTANCE));
     }

     @OptionsItem void homeSelected() {
          saveAndSendDrawingOnBackgroundThread();
          startActivity(new Intent(DrawingCompaniesActivity.this, AMenuModules_.class));
     }

     public void showTradesPanel() {
          showProgressDialog();
          GetTradesRequest request = new GetTradesRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetTradesRequestListener(DrawingCompaniesActivity.this));
     }

     public void showOrHideAssets() {
          showProgressDialog();
          GetAssetsRequest request = new GetAssetsRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetAssetsRequestListener(DrawingCompaniesActivity.this));
     }

     public static CompaniesDrawingView getDrawView() {
          return DRAW_VIEW;
     }

     public static ActionBarDrawingCompanies getCustomActionBar() {
          return customActionBar;
     }

     @Override public ProgressDialog getProgressDialog() {
          return progressDialog;
     }

     @Override public void showProgressDialog() {
          if ( null != progressDialog ) {
               progressDialog.show();
          }
     }

     @Override public void dissmissProgressDialog() {
          if ( null != progressDialog ) {
               progressDialog.dismiss();
          }
     }

     @Click void changeBrushSize() {
          // Configure dialog
          dialog.setTitle(R.string.set_brush_size);
          dialog.setContentView(R.layout.dialog_brush_size_chooser);

          Button buttonOk = (Button) dialog.findViewById(R.id.dialog_chooseBrushSize_button_ok);
          Button buttonCancel = (Button) dialog.findViewById(R.id.dialog_chooseBrushSize_button_cancel);

          final SeekBar seekbar = (SeekBar) dialog.findViewById(R.id.dialog_seekbar_brush_chooser);
          buttonOk.setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    Utils.showCustomToast(DrawingCompaniesActivity.this, String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), seekbar.getProgress()), R.drawable.brush);
                    DrawingCompaniesActivity.getDrawView().setBrushSize(seekbar.getProgress());
                    dialog.dismiss();
               }
          });
          buttonCancel.setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialog.dismiss();
               }
          });
          // max brush size
          seekbar.setMax(30);
          // set current brush size
          seekbar.setProgress( /* getResources().getInteger(R.integer.small_size) */DRAW_VIEW.getBrushSize());

          seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
               @Override public void onStopTrackingTouch(SeekBar seekBar) {
               }

               @Override public void onStartTrackingTouch(SeekBar seekBar) {
               }

               @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    dialog.setTitle(String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), seekbar.getProgress()));
               }
          });
          onBackPressed();
          dialog.show();
     }

     @OnActivityResult ( GlobalConstants.CAPTURE_CAMERA_PHOTO ) void onResult(int resultCode, Intent data) {
          if ( resultCode == Activity.RESULT_OK ) {
               MultiValueMap <String, Object> parts = new LinkedMultiValueMap <String, Object>();

               parts.add(HTTP_PARAMS.IMAGE, new FileSystemResource(GlobalConstants.capturedPhotoFilename));
               parts.add(HTTP_PARAMS.ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
               parts.add(HTTP_PARAMS.DATE, String.valueOf(GlobalConstants.SITE_PLAN_IMAGE_NAME));

               UploadCapturedPhotoRequest request = new UploadCapturedPhotoRequest(parts);
               getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new UploadCapturedPhotoRequestListener(this));
          }
     }

     public static ListView getLvTrades() {
          return lwTrades;
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

                              try {
                                   Date today = DateUtils.parseDate(GlobalConstants.SITE_PLAN_FULL_INFO.today, GlobalConstants.DATE_FORMAT);
                                   Date sitePlanDate = DateUtils.parseDate(GlobalConstants.SITE_PLAN_IMAGE_NAME, GlobalConstants.DATE_FORMAT);

                                   if ( !DateUtils.isSameDay(today, sitePlanDate) && today.compareTo(sitePlanDate) > 0 ) {
                                        Utils.showToast(getApplicationContext(), "Could not create hs, date in past", true);
                                        return false;
                                   }
                              } catch (Exception e1) {
                                   e1.printStackTrace();
                              }

                              params = new RelativeLayout.LayoutParams(60, 60);
                              params.leftMargin = (int) (event.getX() - 30);
                              params.topMargin = (int) (event.getY() - 30);
                              // All data is containing in tag of component which has been dragged to canvas
                              final String type = event.getClipData().getDescription().getLabel().toString();

                              final Map <String, String> requestParams = new HashMap <String, String>();

                              requestParams.put(HTTP_PARAMS.X, String.valueOf((event.getX() - 30) / CompaniesDrawingView.WIDTH));
                              requestParams.put(HTTP_PARAMS.Y, String.valueOf((event.getY() - 30) / CompaniesDrawingView.HEIGHT));

                              // UPDATE HS POSITION
                              if ( type.contains("update") ) {
                                   updateHotspotLocation(requestParams);
                                   return true;
                              }

                              requestParams.put(/* HTTP_PARAMS.SITE_ID */"markerId", GlobalConstants.SITE_ID);
                              requestParams.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);

                              // ASSET HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("asset") ) {
                                   String assetDescription = type.substring(0, type.indexOf("_"));
                                   requestParams.put(HTTP_PARAMS.ID, String.valueOf("-1"));
                                   requestParams.put(HTTP_PARAMS.DESCRIPTION, assetDescription);
                                   requestParams.put(HTTP_PARAMS.TYPE, Hotspots.HOTSPOTS_NAMES[Hotspots.ASSET]);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                                   requestParams.put(HTTP_PARAMS.ASSET_ID, String.valueOf(GlobalConstants.LAST_CLICKED_ASSET.id));
                                   createAssetHotspot(requestParams);
                                   return false;
                              }

                              // TRADE HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("trade") ) {
                                   String description = type.substring(0, type.indexOf("_"));
                                   // requestParams.put(HTTP_PARAMS.ID, tradeId);
                                   requestParams.put(HTTP_PARAMS.AMOUNT, String.valueOf(1));
                                   requestParams.put(HTTP_PARAMS.DESCRIPTION, description);
                                   // requestParams.put(HTTP_PARAMS.TYPE,GlobalConstants.Hotspots.TRADE_HOTSPOT);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));

                                   showProgressDialog();
                                   CreateTradeHotspotRequest request = new CreateTradeHotspotRequest(requestParams);
                                   getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new CreateTradeHotspotRequestListener(DrawingCompaniesActivity.this, description));
                                   return false;
                              }

                              // SIMPLE HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("hotspot") ) {

                                   requestParams.put(HTTP_PARAMS.ID, String.valueOf("-1"));
                                   requestParams.put(HTTP_PARAMS.TYPE, type);

                                   dialog.setTitle(null != GlobalConstants.LAST_CLICKED_COMPANY ? type + " for " + GlobalConstants.LAST_CLICKED_COMPANY.companyName : type);
                                   dialog.setContentView(R.layout.dialog_input_hotspot_detail);

                                   final EditText editTextDescription = (EditText) dialog.findViewById(R.id.dialog_edittext_input_text_to_draw);
                                   final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner_permits_type);

                                   final TextView twValidFrom = (TextView) dialog.findViewById(R.id.dialog_hotspot_detail_textview_valid_from);
                                   final TextView twValidTo = (TextView) dialog.findViewById(R.id.dialog_hotspot_detail_textview_valid_to);
                                   String date = Utils.DATE_FORMAT.format(new Date());
                                   twValidFrom.setText(date);
                                   twValidTo.setText(date);

                                   if ( type.equals(Hotspots.HOTSPOTS_NAMES[Hotspots.PERMIT]) ) {
                                        editTextDescription.setVisibility(View.GONE);
                                        // Create an ArrayAdapter using the string array and
                                        // a default spinner layout
                                        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(DrawingCompaniesActivity.this, R.array.permits_types, android.R.layout.simple_spinner_item);
                                        // Specify the layout to use when the list of
                                        // choices appears
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
                                                                      Utils.showToast(getApplicationContext(), Utils.getResources(R.string.date_should_equal_or_grater) + validFrom, true);
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

                                             if ( null == GlobalConstants.LAST_CLICKED_COMPANY ) {
                                                  Utils.showToast(getApplicationContext(), R.string.choose_company_before_creating_hotspot, true);
                                                  dialog.dismiss();
                                                  return;
                                             }

                                             String hotspotDescription = "";

                                             requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
                                             requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));

                                             if ( type.equals(Hotspots.HOTSPOTS_NAMES[Hotspots.PERMIT]) ) {
                                                  hotspotDescription = spinner.getSelectedItem().toString();
                                                  requestParams.put(HTTP_PARAMS.DATE_VALID_FROM, twValidFrom.getText().toString());
                                                  requestParams.put(HTTP_PARAMS.DATE_VALID_TO, twValidTo.getText().toString());
                                             } else {
                                                  hotspotDescription = editTextDescription.getText().toString();
                                             }
                                             if ( Utils.isBlank(hotspotDescription) ) {
                                                  Utils.showToast(getApplicationContext(), R.string.description_cannot_be_empty, true);
                                             } else {

                                                  if ( type.equals(Hotspots.HOTSPOTS_NAMES[Hotspots.CAMERA]) ) {
                                                       dialog.dismiss();
                                                       AlertDialog.Builder alert = new AlertDialog.Builder(DrawingCompaniesActivity.this);

                                                       alert.setTitle("Input admin password");
                                                       // Set an EditText view to get
                                                       // user input
                                                       final EditText input = new EditText(DrawingCompaniesActivity.this);
                                                       input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                       alert.setView(input);
                                                       final String cameraDescr = hotspotDescription;
                                                       alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override public void onClick(DialogInterface dialog, int whichButton) {
                                                                 String value = input.getText().toString();
                                                                 if ( value.equals("1234") ) {
                                                                      buttonCreateHotspotOk.setClickable(false);
                                                                      buttonCreateHotspotOk.setText("Creating camera hotspot....");
                                                                      requestParams.put(HTTP_PARAMS.DESCRIPTION, cameraDescr);
                                                                      createHotspot(requestParams);
                                                                 } else {
                                                                      Utils.showToast(INSTANCE, R.string.cannot_create_camera, false);
                                                                 }
                                                            }
                                                       });

                                                       alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                            @Override public void onClick(DialogInterface dialog, int whichButton) {
                                                                 // Canceled.
                                                            }
                                                       });

                                                       alert.show();
                                                  } else {
                                                       buttonCreateHotspotOk.setClickable(false);
                                                       buttonCreateHotspotOk.setText("Creating hotspot....");
                                                       requestParams.put(HTTP_PARAMS.DESCRIPTION, hotspotDescription);
                                                       createHotspot(requestParams);
                                                       dialog.dismiss();
                                                  }
                                             }
                                        }
                                   });
                                   dialog.show();
                              } else {
                                   // assign Unassign hotspot (type = [hotspotId])
                                   requestParams.put(HTTP_PARAMS.ID, type);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(ListViewUnsignedHotspotsAdapter.draggedUnsignedHotspot.companyId));

                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
                                   requestParams.put(HTTP_PARAMS.TYPE, String.valueOf(ListViewUnsignedHotspotsAdapter.draggedUnsignedHotspot.type));
                                   requestParams.put(HTTP_PARAMS.DESCRIPTION, ListViewUnsignedHotspotsAdapter.draggedUnsignedHotspot.description);

                                   assignUnassignHotspotRequest(requestParams);
                              }

                              break;
                         default:
                              return true;
                    }
                    return true;
               }
          });
     }

     private void updateHotspotLocation(Map <String, String> requestParams) {
          Utils.logw("Updating hotspot with id " + GlobalConstants.LAST_CLICKED_HOTSPOT.id);
          progressDialog.show();
          requestParams.put(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
          UpdateHotspotPositionRequest request = new UpdateHotspotPositionRequest(requestParams);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new UpdateHotspotPositionRequestListener(this));
     }

     public static ListView getLwAssets() {
          return lwAssets;
     }

     public static LinearLayout getLlAssets() {
          return llAssets;
     }

     private void showResourcesPopup() {
          PopupMenu popup = new PopupMenu(this, ibResources);
          popup.getMenuInflater().inflate(R.menu.popup_menu_resources, popup.getMenu());
          popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
               @Override public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                         case R.id.action_deliveries:
                              showProgressDialog();
                              HashMap <String, String> params = new HashMap <String, String>();
                              params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                              params.put(HTTP_PARAMS.SITE_ID, String.valueOf(1));
                              showProgressDialog();
                              GetDeliveriesListRequest request = new GetDeliveriesListRequest(params);
                              getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetDeliveriesListRequestListener(DrawingCompaniesActivity.this));
                              break;
                         // ////////////////////////////////////////////////////////////////////////////////////////////////
                         case R.id.action_assets:
                              // if user has not choosen any company - show appropriate message and uncheck toggle button
                              if ( null == GlobalConstants.LAST_CLICKED_COMPANY ) {
                                   Utils.showCustomToast(DrawingCompaniesActivity.this, R.string.choose_company, R.drawable.trade);
                                   break;
                              }

                              if ( DrawingCompaniesActivity.getLlAssets().getVisibility() == View.VISIBLE ) {
                                   AnimationManager.animateMenu(DrawingCompaniesActivity.getLlAssets(), View.GONE, R.anim.push_left_out, 200);
                              } else {
                                   showOrHideAssets();
                              }
                              break;
                         // ////////////////////////////////////////////////////////////////////////////////////////////////
                         case R.id.action_trades:
                              // if user has not choosen any company - show appropriate message and uncheck toggle button
                              if ( (null == GlobalConstants.LAST_CLICKED_COMPANY) ) {
                                   Utils.showCustomToast(DrawingCompaniesActivity.this, R.string.choose_company, R.drawable.trade);
                                   break;
                              }

                              if ( DrawingCompaniesActivity.getLlTrades().getVisibility() == View.VISIBLE ) {
                                   AnimationManager.animateMenu(DrawingCompaniesActivity.getLlTrades(), View.GONE, R.anim.push_left_out, 200);
                              } else {
                                   // if menu is invisible (visibility == View.GONE) if user choose company from left companies list
                                   showTradesPanel();
                              }
                              break;
                    }
                    return true;
               }
          });
          popup.show();

     }

     /**
      * Show or hide filters based on value of toggle button
      * 
      * @param toggleButton
      *             flter button
      * @param isChecked
      *             current toggle button state
      */
     private void llShowFilters() {
          if ( DrawingCompaniesActivity.getllFilters().getVisibility() == View.GONE ) {
               DrawingCompaniesActivity.getllFilters().setVisibility(View.VISIBLE);
               DrawingCompaniesActivity.getllFilters().startAnimation(AnimationManager.load(R.anim.push_left_in, 500));
               DrawingCompaniesActivity.getLlAssets().setVisibility(View.GONE);
               DrawingCompaniesActivity.getLlTrades().setVisibility(View.GONE);
          } else {
               AnimationManager.animateMenu(DrawingCompaniesActivity.getllFilters(), View.GONE, R.anim.push_left_out, 200);
          }
     }
}
