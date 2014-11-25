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
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QSystem;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.method.PasswordTransformationMethod;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarDrawingCompanies;
import com.touchip.organizer.activities.custom.components.ActionBarDrawingCompanies_;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogAssignUnassignedHotspot_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCreateAssetHotspot_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCreateHighRiskHotspot_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCreateOnTheFlyHotspot_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCreateQuickNote_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogCreateTradeHotspot_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogListOfAreas_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogSmartFilter_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogTypeOfResources_;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogTypeOfShape_;
import com.touchip.organizer.activities.fragments.FragmentHotspotsList;
import com.touchip.organizer.activities.fragments.FragmentUnsignedHotspotsList.ListViewUnsignedHotspotsAdapter;
import com.touchip.organizer.communication.rest.model.ModelAssetsList;
import com.touchip.organizer.communication.rest.model.ModelCompaniesAndHotspots;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList;
import com.touchip.organizer.communication.rest.model.ModelHotspotsList;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList;
import com.touchip.organizer.communication.rest.model.ModelSignRegisterList;
import com.touchip.organizer.communication.rest.request.GetDeliveriesListRequest;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SaveDrawingPathsOnFingerReleaseRequest;
import com.touchip.organizer.communication.rest.request.SaveDrawingPathsRequest;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.UploadCapturedPhotoRequest;
import com.touchip.organizer.communication.rest.request.listener.DatesToHighlightDrawingActivityRequestListener;
import com.touchip.organizer.communication.rest.request.listener.ResponseCreateAssetHotspot;
import com.touchip.organizer.communication.rest.request.listener.ResponseCreateHotspot;
import com.touchip.organizer.communication.rest.request.listener.ResponseDownloadDrawingPaths;
import com.touchip.organizer.communication.rest.request.listener.ResponseDownloadSitePlanWithFloor;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetAssets;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetDeliveriesList;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetPathsCreationTime;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetSignRegisterList;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetTrades;
import com.touchip.organizer.communication.rest.request.listener.ResponseUpdateHotspotPosition;
import com.touchip.organizer.communication.rest.request.listener.SaveDrawingPathsOnFingerReleaseRequestListener;
import com.touchip.organizer.communication.rest.request.listener.SaveDrawingPathsRequestListener;
import com.touchip.organizer.communication.rest.request.listener.UploadCapturedPhotoRequestListener;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.DialogUtils;
import com.touchip.organizer.utils.FilterManager;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.GlobalConstants.Hotspots;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

/**
 * Description: class represents drawing functions on site plans
 * 
 * @author Ihor Karpachev Copyright (c) 2013-2014 Datascope Systems Ltd. All
 *         Rights Reserved. Date: 17 Dec 2013 Time: 11:24:58
 */

@EActivity ( R.layout.fragments_companies_drawing ) public class ADrawingCompanies extends SuperActivity {

     // ---------------------------------------------------------------- VIEWS
     // Canvas for drawing
     @ViewById public static CompaniesDrawingView DRAW_VIEW;
     // Right hand panel with resources (assets, trades, hotspots)
     @ViewById static LinearLayout                llFilters , llAssets , llTrades;
     // total amount of trades on llTrades, SPN- site plan name, tvToday/Yesyerda/Everybody - views from llTrades
     @ViewById TextView                           twTotalAmountOfPeople , tvSPN , tvToday , tvYesterday , tvEverybody;
     // Overlay layout for hotspots
     @ViewById RelativeLayout                     rlHotspotsOnCanvas;
     // control button for show/hide panel, prev/next area and control button on bottom of the screen
     @ViewById ImageView                          ibSignRegister , ivLeftPanel , ivRightPanel , ivNextArea , ivPrevArea , ibUndo , ibRedo , ibShapes , ibWb , ibResources , ibHotspots , ibChangeFloor , ibRefresh;
     // List of trades and assets
     @ViewById static ListView                    lwTrades , lwAssets;
     // for displayint current selected color
     @ViewById public static ImageView            ivCompanyColor;
     // today date
     static Date                                  today = null;

     private static ADrawingCompanies             INSTANCE;
     public static ActionBarDrawingCompanies      customActionBar;
     public Dialog                                dialog;

     @AfterViews void afterViews() {
          INSTANCE = this;
          FragmentHotspotsList.hotspotsButtonLayout = rlHotspotsOnCanvas;
          // load all views
          customActionBar = ActionBarDrawingCompanies_.build(ADrawingCompanies.this);
          Utils.configureCustomActionBar(getActionBar(), customActionBar);
          getActionBar().setIcon(R.drawable.menu);

          dialog = Utils.getConfiguredDialog(this);
          // Sizes from dimensions from resources
          DRAW_VIEW.setBrushSize(getResources().getInteger(R.integer.small_size));
          DRAW_VIEW.setDrawingCacheEnabled(true);
          setDragListener();
          customActionBar.setUpCurrentSiteInfo(GlobalConstants.TODAY_FROM_SERVER, GlobalConstants.SITE_PLAN_IMAGE_NAME, GlobalConstants.CURRENT_FLOOR);
          updateCanvasWithHotspots();

          if ( null != GlobalConstants.LAST_CLICKED_COMPANY ) {
               ivCompanyColor.setBackgroundColor(Color.parseColor(GlobalConstants.LAST_CLICKED_COMPANY.colour));
               ivCompanyColor.startAnimation(AnimationManager.load(R.anim.bounce));
          }
     }

     /**
      * Static accesor for class
      * 
      * @return itself
      */
     public static ADrawingCompanies getInstance() {
          return INSTANCE;
     }

     /**
      * Handle today click in the trades layout
      */
     @Click void tvToday() {
          // play animation for user feedback
          tvToday.startAnimation(AnimationManager.load(R.anim.fade));

          // unselect tvYesterday and tv Everybody
          tvYesterday.setBackgroundColor(Color.TRANSPARENT);
          tvEverybody.setBackgroundColor(Color.TRANSPARENT);

          // select tvToday
          tvToday.setBackgroundResource(R.drawable.transparent_inside_and_white_border);
     }

     /**
      * Handle yesterday click in the trades layout
      */
     @Click void tvYesterday() {
          // play animation for user feedback
          tvYesterday.startAnimation(AnimationManager.load(R.anim.fade));

          // unselect tvToday and tvEverybody
          tvToday.setBackgroundColor(Color.TRANSPARENT);
          tvEverybody.setBackgroundColor(Color.TRANSPARENT);

          // select tvYesterday
          tvYesterday.setBackgroundResource(R.drawable.transparent_inside_and_white_border);
     }

     /**
      * Handle everybody click in the trades layout
      */
     @Click void tvEverybody() {
          // play animation for user feedback
          tvEverybody.startAnimation(AnimationManager.load(R.anim.fade));

          // unselect tvToday and tvYesterday
          tvToday.setBackgroundColor(Color.TRANSPARENT);
          tvYesterday.setBackgroundColor(Color.TRANSPARENT);

          // select tvEverybody
          tvEverybody.setBackgroundResource(R.drawable.transparent_inside_and_white_border);
     }

     /**
      * Loading previous area (if current area is the first -> last area will be loaded, like in cycle)
      */
     @Click void ivPrevArea() {
          ivPrevArea.startAnimation(AnimationManager.load(R.anim.fade_in));
          loadPrevArea();
     }

     /**
      * Loading next area (if current area is the last -> first area will be loaded, like in cycle)
      */
     @Click void ivNextArea() {
          ivNextArea.startAnimation(AnimationManager.load(R.anim.fade_in));
          loadNextArea();
     }

     /**
      * Show/Hide (depends on current visibility) left companies panel
      */
     @Click void ivLeftPanel() {
          ivLeftPanel.startAnimation(AnimationManager.load(R.anim.fade_in));
          final LinearLayout llCompaniesList = (LinearLayout) findViewById(R.id.llCompaniesList);
          Animation anim = AnimationManager.load(R.anim.push_up_out);

          anim.setAnimationListener(new AnimationListener() {
               @Override public void onAnimationStart(Animation animation) {
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    llCompaniesList.setVisibility(llCompaniesList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    if ( llCompaniesList.getVisibility() == View.VISIBLE ) {
                         llCompaniesList.startAnimation(AnimationManager.load(R.anim.push_up_in));
                    }
                    ivLeftPanel.setImageResource(llCompaniesList.getVisibility() == View.VISIBLE ? R.drawable.next : R.drawable.prev);
                    // update hotspots position on canvas and refresh drawing
                    updateCanvasWithHotspotsWithoutDelay();
                    loadPathes();
               }
          });
          llCompaniesList.startAnimation(anim);
     }

     /**
      * Show/Hide (depends on current visibility) right hotspots panel
      */
     @Click void ivRightPanel() {
          ivRightPanel.startAnimation(AnimationManager.load(R.anim.fade_in));
          final LinearLayout llHotspots = (LinearLayout) findViewById(R.id.drawing_markerTypes_linearLayout);

          Animation anim = AnimationManager.load(llHotspots.getVisibility() == View.VISIBLE ? R.anim.push_up_out : R.anim.push_up_in);

          anim.setAnimationListener(new AnimationListener() {
               @Override public void onAnimationStart(Animation animation) {
               }

               @Override public void onAnimationRepeat(Animation animation) {
               }

               @Override public void onAnimationEnd(Animation animation) {
                    llHotspots.setVisibility(llHotspots.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    ivRightPanel.setImageResource(llHotspots.getVisibility() == View.VISIBLE ? R.drawable.prev : R.drawable.next);
                    // update hotspots position on canvas and refresh drawing
                    updateCanvasWithHotspotsWithoutDelay();
                    loadPathes();
               }
          });
          llHotspots.startAnimation(anim);
     }

     /**
      * Loading previous area
      */
     private void loadPrevArea() {
          List <String> floors = null;
          for ( int i = 0; i < GlobalConstants.datestoHighlight.size(); i++ ) {
               // find appropriate date and get all floors
               if ( GlobalConstants.datestoHighlight.get(i).date.compareTo(GlobalConstants.SITE_PLAN_IMAGE_NAME) < 1 ) {
                    floors = GlobalConstants.datestoHighlight.get(i).floors;
                    break;
               }
          }

          if ( null != floors ) {
               // get index of current floor in array and decrement this value (we need previous floor)
               int indexOfCurrentFloor = floors.indexOf(GlobalConstants.CURRENT_FLOOR) - 1;
               // current floor was first, get index of last floor
               if ( indexOfCurrentFloor < 0 ) {
                    indexOfCurrentFloor = floors.size() - 1;
               }

               GlobalConstants.CURRENT_FLOOR = floors.get(indexOfCurrentFloor);
               Utils.showCustomToast(ADrawingCompanies.this, String.valueOf("Loading ... " + GlobalConstants.CURRENT_FLOOR), R.drawable.floorss);

               changeFloorRequest();
          }
     }

     /**
      * Request of the floor data from server
      */
     private void changeFloorRequest() {
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);

          SuperRequest <byte[]> request = new SuperRequest <byte[]>(ADrawingCompanies.this, byte[].class, RestAddresses.DOWNLOAD_SITE_PLAN, new ByteArrayHttpMessageConverter(), params);
          execute(request, new ResponseDownloadSitePlanWithFloor(ADrawingCompanies.this));
     }

     private void loadNextArea() {
          List <String> floors = null;
          for ( int i = 0; i < GlobalConstants.datestoHighlight.size(); i++ ) {
               // find appropriate date and get all floors
               if ( GlobalConstants.datestoHighlight.get(i).date.compareTo(GlobalConstants.SITE_PLAN_IMAGE_NAME) < 1 ) {
                    floors = GlobalConstants.datestoHighlight.get(i).floors;
                    break;
               }
          }

          if ( null != floors ) {
               // get index of current floor in array and increment this value (we need next floor)
               int indexOfCurrentFloor = floors.indexOf(GlobalConstants.CURRENT_FLOOR) + 1;
               // check if floor was last floor, in this case load first floor
               if ( indexOfCurrentFloor == floors.size() ) {
                    indexOfCurrentFloor = 0;
               }

               GlobalConstants.CURRENT_FLOOR = floors.get(indexOfCurrentFloor);
               Utils.showCustomToast(ADrawingCompanies.this, String.valueOf("Loading ... " + GlobalConstants.CURRENT_FLOOR), R.drawable.floorss);

               changeFloorRequest();
          }
     }

     /**
      * Immidate update hotspots
      */
     @Background void updateCanvasWithHotspotsWithoutDelay() {
          updateHs();
     }

     /**
      * Update with delay of one sec (1 sec for DRAWING VIEW to be loaded)
      */
     @Background void updateCanvasWithHotspots() {
          QSystem.sleep(1000);
          updateHs();
     }

     @UiThread void updateHs() {
          FragmentHotspotsList.ADAPTER.updateHotspotsButtonsList();
     }

     @Override protected void onResume() {
          super.onResume();
          tvSPN.setText("Today " + GlobalConstants.TODAY_FROM_SERVER);
          DRAW_VIEW.setDrawingShape(ShapesType.STANDART_DRAWING);
     }

     @Click void ibShapes() {
          // if user has not choosen any company - show appropriate message and uncheck toggle button
          if ( (null == GlobalConstants.LAST_CLICKED_COMPANY) ) {
               Utils.showCustomToast(ADrawingCompanies.this, R.string.choose_company, R.drawable.different_shapes);
               return;
          }
          Dialog d = Utils.getConfiguredDialog(INSTANCE);
          d.setContentView(CDialogTypeOfShape_.build(INSTANCE, d));
          d.show();
     }

     @Click void ibChangeFloor() {
          loadImage(null, null);
     }

     @Click void ibRefresh() {
          dialog.setContentView(CDialogSmartFilter_.build(this));
          dialog.show();
     }

     @Click void ibWb() {
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);

          SuperRequest <ModelPathsCreationTimeList> request = new SuperRequest <ModelPathsCreationTimeList>(this, ModelPathsCreationTimeList.class, RestAddresses.GET_TIMES_FOR_PATHS, null, params);
          execute(request, new ResponseGetPathsCreationTime(this));
     }

     @Click void ibHotspots() {
          // if user has not choosen any company - show appropriate message and uncheck toggle button
          if ( (null == GlobalConstants.LAST_CLICKED_COMPANY) ) {
               Utils.showCustomToast(ADrawingCompanies.this, R.string.choose_company, R.drawable.hide_hotspot);
               return;
          }
          llShowFilters();
     }

     @Click void ibSignRegister() {
          ibSignRegister.startAnimation(AnimationManager.load(R.anim.fade));

          HashMap <String, String> params = QCollection.newHashMap();
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);

          SuperRequest <ModelSignRegisterList> request = new SuperRequest <ModelSignRegisterList>(this, ModelSignRegisterList.class, RestAddresses.GET_LIST_OF_MEETINGS, null, params);
          execute(request, new ResponseGetSignRegisterList(this));

     }

     @Click void ibUndo() {
          ADrawingCompanies.DRAW_VIEW.setIsNeedToStopOnDrawMethod(true);
          DRAW_VIEW.undo();
          saveAndSendDrawingOnBackgroundThread(GlobalConstants.CURRENT_FLOOR);
     }

     @Click void ibRedo() {
          ADrawingCompanies.DRAW_VIEW.setIsNeedToStopOnDrawMethod(true);
          DRAW_VIEW.redo();
          saveAndSendDrawingOnBackgroundThread(GlobalConstants.CURRENT_FLOOR);
     }

     @Click void ibResources() {
          // if user has not choosen any company - show appropriate message and uncheck toggle button
          if ( (null == GlobalConstants.LAST_CLICKED_COMPANY) ) {
               Utils.showCustomToast(ADrawingCompanies.this, R.string.choose_company, R.drawable.trade);
               return;
          }

          Dialog d = Utils.getConfiguredDialog(INSTANCE);
          d.setContentView(CDialogTypeOfResources_.build(INSTANCE, d));
          d.show();
     }

     public static LinearLayout getllFilters() {
          return llFilters;
     }

     public void setTotalAmountOfPeople(int amount) {
          twTotalAmountOfPeople.setText(String.format("Total: %s", amount));
     }

     @Override public void onBackPressed() {
     }

     public void saveAndSendDrawing(final boolean askSave, String floor) {
          ObjectOutputStream out = null;
          try {
               File file = new File(getApplicationContext().getCacheDir(), System.currentTimeMillis() + ".paths");
               List <PathSerializable> listPathes = DRAW_VIEW.getPaths();
               out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(listPathes);
               out.close();
               progressDialog.show();

               // attempt to save drawing
               FileSystemResource snapshotSpringWrapper = null;
               try {
                    DRAW_VIEW.setDrawingCacheEnabled(true);
                    String saveResponseFilePath = Media.insertImage(getContentResolver(), DRAW_VIEW.getDrawingCache(), Utils.getCurrentDate() + ".png", "drawing");
                    // Preparing feedback for user
                    boolean isSuccessfullySaved = saveResponseFilePath != null;
                    String userFeedback = (isSuccessfullySaved) ? Utils.getResources(R.string.drawing_saved_to_gallery) : Utils.getResources(R.string.cannot_save_image);
                    if ( isSuccessfullySaved ) {
                         snapshotSpringWrapper = new FileSystemResource(new File(Utils.getRealPathFromURI(getApplicationContext(), Uri.parse(saveResponseFilePath))));
                    }
                    DRAW_VIEW.destroyDrawingCache();
               } catch (Exception ex) {
                    ex.printStackTrace();
               }

               SaveDrawingPathsRequest request = new SaveDrawingPathsRequest(new FileSystemResource(file), snapshotSpringWrapper, floor);
               getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveDrawingPathsRequestListener(ADrawingCompanies.this, file, askSave));
          } catch (Exception e) {
               QLog.debug(e.getMessage());
          }
     }

     public static void saveAndSendDrawingOnBackgroundThread(String floor) {
          SaveDrawingPathsOnFingerReleaseRequest request = new SaveDrawingPathsOnFingerReleaseRequest(null, null, floor);
          ADrawingCompanies.INSTANCE.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveDrawingPathsOnFingerReleaseRequestListener(ADrawingCompanies.INSTANCE, false));
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
          Dialog d = Utils.getConfiguredDialog(this);
          d.setContentView(CDialogListOfAreas_.build(this, date, d));
          d.show();
     }

     public void assignUnassignHotspotRequest(Map <String, Object> params) {

          Dialog d = Utils.getConfiguredDialog(INSTANCE);
          d.setContentView(CDialogAssignUnassignedHotspot_.build(INSTANCE, params, d));
          d.show();

     }

     public void createHotspot(Map <String, Object> params) {
          SuperRequest <ModelCompaniesAndHotspots> request = new SuperRequest <ModelCompaniesAndHotspots>(ADrawingCompanies.this, ModelCompaniesAndHotspots.class, RestAddresses.CREATE_HOTSPOT, params);
          execute(request, new ResponseCreateHotspot(this));
     }

     private void createAssetHotspot(Map <String, Object> params) {
          SuperRequest <ModelCompaniesAndHotspots> request = new SuperRequest <ModelCompaniesAndHotspots>(ADrawingCompanies.this, ModelCompaniesAndHotspots.class, RestAddresses.CREATE_HOTSPOT, params);
          execute(request, new ResponseCreateAssetHotspot(this));
     }

     public static void loadPathes() {

          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
          params.put(HTTP_PARAMS.TYPE, GlobalConstants.DrawingType.SITE_PLAN_DRAWING);

          SuperRequest <byte[]> request = new SuperRequest <byte[]>(INSTANCE, byte[].class, RestAddresses.DOWNLOAD_DRAWING_PATHES, new ByteArrayHttpMessageConverter(), params);
          INSTANCE.execute(request, new ResponseDownloadDrawingPaths(INSTANCE));
     }

     @OptionsItem void homeSelected() {
          showSaveDrawingDialog(false);
          QSystem.navigateToActivity(ADrawingCompanies.this, AMenuModules_.class);
     }

     public void showTradesPanel() {
          HashMap <String, String> requestParams = QCollection.newHashMap();

          if ( null != GlobalConstants.LAST_CLICKED_COMPANY ) {
               requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
               requestParams.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
               requestParams.put(HTTP_PARAMS.SITE_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
               requestParams.put(HTTP_PARAMS.USER_ID, String.valueOf(GlobalConstants.CURRENT_USER.userId));

               SuperRequest <ModelHotspotsList> request = new SuperRequest <ModelHotspotsList>(INSTANCE, ModelHotspotsList.class, RestAddresses.GET_TRADES, requestParams);
               execute(request, new ResponseGetTrades(ADrawingCompanies.this));
          }
     }

     public void showOrHideAssets() {
          HashMap <String, String> params = QCollection.newHashMap();

          params.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
          params.put(HTTP_PARAMS.MARKER_ID, String.valueOf(GlobalConstants.LAST_CLICKED_MARKER_ID));

          SuperRequest <ModelAssetsList> request = new SuperRequest <ModelAssetsList>(INSTANCE, ModelAssetsList.class, RestAddresses.GET_ASSETS_LIST, params);
          execute(request, new ResponseGetAssets(ADrawingCompanies.this));
     }

     public static CompaniesDrawingView getDrawView() {
          return DRAW_VIEW;
     }

     public static ActionBarDrawingCompanies getCustomActionBar() {
          return customActionBar;
     }

     @Override protected void onPostCreate(Bundle savedInstanceState) {
          super.onPostCreate(savedInstanceState);
          FilterManager.activateAllCompaniesDrawing();
          FilterManager.setFilterHotstpotsState(true);
          FilterManager.displayHSId = true;
          ADrawingCompanies.DRAW_VIEW.setCompanyColourFilter(3);
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
                                   today = DateUtils.parseDate(GlobalConstants.TODAY_FROM_SERVER, GlobalConstants.DATE_FORMAT);
                                   Date sitePlanDate = DateUtils.parseDate(GlobalConstants.SITE_PLAN_IMAGE_NAME, GlobalConstants.DATE_FORMAT);

                                   if ( !DateUtils.isSameDay(today, sitePlanDate) && today.compareTo(sitePlanDate) > 0 ) {
                                        QNotifications.showShortToast(getApplicationContext(), "Could not create hs, date in past");
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

                              final Map <String, Object> requestParams = QCollection.newHashMap();

                              requestParams.put(HTTP_PARAMS.X, String.valueOf((event.getX() - 30) / CompaniesDrawingView.WIDTH));
                              requestParams.put(HTTP_PARAMS.Y, String.valueOf((event.getY() - 30) / CompaniesDrawingView.HEIGHT));
                              requestParams.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                              requestParams.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                              requestParams.put(HTTP_PARAMS.USER_ID, String.valueOf(GlobalConstants.CURRENT_USER.userId));

                              // UPDATE HS POSITION
                              if ( type.contains("update") ) {
                                   updateHotspotLocation(requestParams);
                                   return true;
                              }

                              // ASSET HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("asset") ) {
                                   String assetDescription = type.substring(0, type.indexOf("_"));
                                   requestParams.put(HTTP_PARAMS.ID, String.valueOf("-1"));
                                   requestParams.put(HTTP_PARAMS.DESCRIPTION, assetDescription);
                                   requestParams.put(HTTP_PARAMS.TYPE, GlobalConstants.Hotspots.ASSET_HOTSPOT);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                                   requestParams.put(HTTP_PARAMS.ASSET_ID, String.valueOf(GlobalConstants.LAST_CLICKED_ASSET.id));

                                   Dialog d = Utils.getConfiguredDialog(INSTANCE);
                                   d.setContentView(CDialogCreateAssetHotspot_.build(INSTANCE, requestParams, d));
                                   d.show();

                                   return false;
                              }

                              // ON THE FLY HOTSPOT
                              if ( type.equals(GlobalConstants.Hotspots.ON_THE_FLY) ) {
                                   requestParams.put(HTTP_PARAMS.TYPE, GlobalConstants.Hotspots.ON_THE_FLY);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                                   dialog.setContentView(CDialogCreateOnTheFlyHotspot_.build(ADrawingCompanies.this, requestParams, dialog));
                                   dialog.show();
                                   return false;
                              }

                              // HIGH RISK HOTSPOT
                              if ( type.equals(Hotspots.HIGH_RISK) ) {
                                   requestParams.put(HTTP_PARAMS.TYPE, Hotspots.HIGH_RISK);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                                   dialog.setContentView(CDialogCreateHighRiskHotspot_.build(ADrawingCompanies.this, requestParams, dialog));
                                   dialog.show();
                                   return false;
                              }

                              // QUICK NOTE HOTSPOT
                              if ( type.equals(Hotspots.QUICK_NOTE_HOTSPOT) ) {
                                   requestParams.put(HTTP_PARAMS.TYPE, GlobalConstants.Hotspots.QUICK_NOTE_HOTSPOT);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                                   dialog.setContentView(CDialogCreateQuickNote_.build(ADrawingCompanies.this, requestParams, dialog));
                                   dialog.show();
                                   return false;
                              }

                              // TRADE HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( type.contains("trade") ) {
                                   String description = type.substring(0, type.indexOf("_"));

                                   requestParams.put(HTTP_PARAMS.AMOUNT, String.valueOf(1));
                                   requestParams.put(HTTP_PARAMS.DESCRIPTION, description);
                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));
                                   requestParams.put(HTTP_PARAMS.USER_ID, String.valueOf(GlobalConstants.CURRENT_USER.userId));

                                   Dialog d = Utils.getConfiguredDialog(INSTANCE);
                                   d.setContentView(CDialogCreateTradeHotspot_.build(INSTANCE, requestParams, d));
                                   d.show();

                                   return false;
                              }

                              // SIMPLE HOTSPOT HAS BEEN DRAGED TO CANVAS
                              if ( StringUtils.containsIgnoreCase(type, "hotspot") ) {

                                   requestParams.put(HTTP_PARAMS.ID, String.valueOf("-1"));
                                   requestParams.put(HTTP_PARAMS.TYPE, type);

                                   dialog.setTitle(null != GlobalConstants.LAST_CLICKED_COMPANY ? type + " for " + GlobalConstants.LAST_CLICKED_COMPANY.companyName : type);
                                   dialog.setContentView(R.layout.dialog_input_hotspot_detail);

                                   final EditText editTextDescription = (EditText) dialog.findViewById(R.id.dialog_edittext_input_text_to_draw);
                                   final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner_permits_type);

                                   final TextView twValidFrom = (TextView) dialog.findViewById(R.id.dialog_hotspot_detail_textview_valid_from);
                                   final TextView twValidTo = (TextView) dialog.findViewById(R.id.dialog_hotspot_detail_textview_valid_to);
                                   twValidFrom.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);
                                   twValidTo.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME);

                                   ImageButton ibValidFrom = (ImageButton) dialog.findViewById(R.id.dialog_hotspot_detail_imagebutton_valid_from);
                                   ImageButton ibValidTo = (ImageButton) dialog.findViewById(R.id.dialog_hotspot_detail_imagebutton_valid_to);

                                   ibValidFrom.setOnClickListener(new OnClickListener() {

                                        @Override public void onClick(View v) {
                                             final CaldroidFragment calendar = Utils.getConfiguredCaldroid(today, null);
                                             calendar.setCaldroidListener(new CaldroidListener() {

                                                  @Override public void onSelectDate(Date date, View view) {

                                                       Date now = new Date();
                                                       if ( !DateUtils.isSameDay(date, now) && now.compareTo(date) > 0 ) {
                                                            QNotifications.showShortToast(getApplicationContext(), "Start date could not be in past");
                                                            return;
                                                       }
                                                       Date validTo;
                                                       try {
                                                            validTo = Utils.DATE_FORMAT.parse(twValidTo.getText().toString());
                                                            if ( date.after(validTo) ) {
                                                                 twValidTo.setText(Utils.DATE_FORMAT.format(date));
                                                            }
                                                       } catch (Exception e) {
                                                            QLog.debug(e.getMessage());
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
                                             final CaldroidFragment calendar = Utils.getConfiguredCaldroid(today, null);
                                             calendar.setCaldroidListener(new CaldroidListener() {

                                                  @Override public void onSelectDate(Date date, View view) {

                                                       try {
                                                            String validFrom = twValidFrom.getText().toString();
                                                            Date dateFrom = Utils.DATE_FORMAT.parse(validFrom);
                                                            if ( dateFrom.after(date) ) {
                                                                 QNotifications.showShortToast(getApplicationContext(), Utils.getResources(R.string.date_should_equal_or_grater) + validFrom);
                                                                 return;
                                                            }
                                                       } catch (Exception e) {
                                                            QLog.debug(e.getMessage());
                                                       }

                                                       twValidTo.setText(Utils.DATE_FORMAT.format(date));
                                                       calendar.dismiss();
                                                  }
                                             });
                                             calendar.show(getSupportFragmentManager(), GlobalConstants.LOG_TAG);
                                        }
                                   });

                                   if ( type.equals(Hotspots.PERMITS_HOTSPOT) ) {
                                        editTextDescription.setVisibility(View.GONE);
                                        // Create an ArrayAdapter using the string array and
                                        // a default spinner layout
                                        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(ADrawingCompanies.this, R.array.permits_types, android.R.layout.simple_spinner_item);
                                        // Specify the layout to use when the list of
                                        // choices appears
                                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                        // Apply the adapter to the spinner
                                        spinner.setAdapter(adapter);
                                   }

                                   ((ImageButton) dialog.findViewById(R.id.dialog_keyboard)).setOnClickListener(new OnClickListener() {

                                        @Override public void onClick(View v) {
                                             InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                             imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                        }
                                   });

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
                                                  QNotifications.showShortToast(getApplicationContext(), R.string.choose_company_before_creating_hotspot);
                                                  dialog.dismiss();
                                                  return;
                                             }

                                             String hotspotDescription = "";

                                             requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                             requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(GlobalConstants.LAST_CLICKED_COMPANY.companyId));

                                             if ( type.equals(Hotspots.PERMITS_HOTSPOT) ) {
                                                  hotspotDescription = spinner.getSelectedItem().toString();
                                             } else {
                                                  hotspotDescription = editTextDescription.getText().toString();
                                             }

                                             requestParams.put(HTTP_PARAMS.DATE_VALID_FROM, twValidFrom.getText().toString());
                                             requestParams.put(HTTP_PARAMS.DATE_VALID_TO, twValidTo.getText().toString());

                                             if ( type.equals(Hotspots.CAMERA_HOTSPOT) ) {
                                                  dialog.dismiss();
                                                  AlertDialog.Builder alert = new AlertDialog.Builder(ADrawingCompanies.this);

                                                  alert.setTitle("Input admin password");
                                                  // Set an EditText view to get
                                                  // user input
                                                  final EditText input = new EditText(ADrawingCompanies.this);
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
                                                                 QNotifications.showShortToast(INSTANCE, R.string.cannot_create_camera);
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
                                   });
                                   dialog.show();
                              } else {
                                   // assign Unassign hotspot (type = [hotspotId])
                                   requestParams.put(HTTP_PARAMS.ID, type);
                                   requestParams.put(HTTP_PARAMS.COMPANY_ID, String.valueOf(ListViewUnsignedHotspotsAdapter.draggedUnsignedHotspot.companyId));

                                   requestParams.put(HTTP_PARAMS.FLOOR, GlobalConstants.CURRENT_FLOOR);
                                   requestParams.put(HTTP_PARAMS.TYPE, ListViewUnsignedHotspotsAdapter.draggedUnsignedHotspot.type);
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

     private void updateHotspotLocation(Map <String, Object> params) {
          params.put(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));

          SuperRequest <ModelCompaniesAndHotspots> request = new SuperRequest <ModelCompaniesAndHotspots>(this, ModelCompaniesAndHotspots.class, RestAddresses.UPDATE_HOTSPOT_POSITION, params);
          execute(request, new ResponseUpdateHotspotPosition(this));
     }

     public ListView getLwAssets() {
          return lwAssets;
     }

     public static LinearLayout getLlAssets() {
          return llAssets;
     }

     public void displayTrades() {
          // if user has not choosen any company - show appropriate message and uncheck toggle button
          if ( (null == GlobalConstants.LAST_CLICKED_COMPANY) ) {
               Utils.showCustomToast(ADrawingCompanies.this, R.string.choose_company, R.drawable.trade);
               return;
          }

          if ( ADrawingCompanies.getLlTrades().getVisibility() == View.VISIBLE ) {
               AnimationManager.animateMenu(ADrawingCompanies.getLlTrades(), View.GONE, R.anim.push_left_out, 200);
          } else {
               // if menu is invisible (visibility == View.GONE) if user choose company from left companies list
               showTradesPanel();
          }

     }

     public void displayAssets() {
          // if user has not choosen any company - show appropriate message and uncheck toggle button
          if ( null == GlobalConstants.LAST_CLICKED_COMPANY ) {
               Utils.showCustomToast(ADrawingCompanies.this, R.string.choose_company, R.drawable.trade);
               return;
          }

          if ( ADrawingCompanies.getLlAssets().getVisibility() == View.VISIBLE ) {
               AnimationManager.animateMenu(ADrawingCompanies.getLlAssets(), View.GONE, R.anim.push_left_out, 200);
          } else {
               showOrHideAssets();
          }

     }

     public void displayDeliveries() {
          showProgressDialog();
          HashMap <String, String> params = QCollection.newHashMap();
          params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          params.put(HTTP_PARAMS.MARKER_ID, String.valueOf(GlobalConstants.LAST_CLICKED_MARKER_ID));
          showProgressDialog();
          GetDeliveriesListRequest request = new GetDeliveriesListRequest(params);
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseGetDeliveriesList(ADrawingCompanies.this));

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
          if ( ADrawingCompanies.getllFilters().getVisibility() == View.GONE ) {
               ADrawingCompanies.getllFilters().setVisibility(View.VISIBLE);
               ADrawingCompanies.getllFilters().startAnimation(AnimationManager.load(R.anim.push_left_in, 500));
               ADrawingCompanies.getLlAssets().setVisibility(View.GONE);
               ADrawingCompanies.getLlTrades().setVisibility(View.GONE);
          } else {
               AnimationManager.animateMenu(ADrawingCompanies.getllFilters(), View.GONE, R.anim.push_left_out, 200);
          }
     }

     public void getDatesToHighliht() {
          HashMap <String, String> params = QCollection.newHashMap();
          params.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          SuperRequest <ModelDatesToHighlightList> requestGetDatesToHighlight = new SuperRequest <ModelDatesToHighlightList>(this, ModelDatesToHighlightList.class, RestAddresses.GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR, null, params);
          getSpiceManager().execute(requestGetDatesToHighlight, requestGetDatesToHighlight.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DatesToHighlightDrawingActivityRequestListener(this));
     }
}