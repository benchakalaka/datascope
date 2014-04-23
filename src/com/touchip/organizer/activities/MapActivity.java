package com.touchip.organizer.activities;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.roomorama.caldroid.CaldroidFragment;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.OneClusterMarker;
import com.touchip.organizer.activities.custom.components.SlideMenuMapActivity;
import com.touchip.organizer.activities.custom.components.SlideMenuMapActivity_;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList.POJORoboOneDateToHighlight;
import com.touchip.organizer.communication.rest.request.DatesToHighlightRequest;
import com.touchip.organizer.communication.rest.request.DownloadSitePlanRequest;
import com.touchip.organizer.communication.rest.request.GoogleMapMarkersRequest;
import com.touchip.organizer.communication.rest.request.listener.DatesToHighlightRequestListener;
import com.touchip.organizer.communication.rest.request.listener.DownloadSitePlanRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GoogleMapMarkerRequestListener;
import com.touchip.organizer.utils.DataAccess;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

/**
 * Description: class represents all functionaity which is available on Google
 * map
 * 
 * @author Ihor Karpachev
 *         All content is activity property of Datascope Systems Ltd. Date: 13 Dec 2013
 *         Time: 18:21:35
 */

@EActivity ( R.layout.activity_map ) public class MapActivity extends SpiceFragmentActivity implements OnCameraChangeListener ,
          OnClusterItemClickListener <OneClusterMarker> {

     // Load views
     @ViewById ImageButton                                refreshMap;
     @ViewById ImageButton                                showMenu;

     // Google map instance
     public GoogleMap                                     googleMap;
     // Calendar view
     public CaldroidFragment                              dialogCaldroidFragment = new CaldroidFragment();
     // represents center of UK
     private static final LatLng                          APPROX_CENTER_OF_UK    = new LatLng(52.6368778, -1.139759199999957);
     public ClusterManager <OneClusterMarker>             clusterManager;
     private SlideMenuMapActivity                         sideSlideMenu;

     // Setup listener
     public final com.roomorama.caldroid.CaldroidListener onDateChangeListener   = new com.roomorama.caldroid.CaldroidListener() {
                                                                                      @Override public void onSelectDate(final Date date, View view) {
                                                                                           GlobalConstants.SITE_PLAN_IMAGE_NAME = Utils.formatDate(date);
                                                                                           for ( POJORoboOneDateToHighlight singleDate : DataAccess.datestoHighlight ) {
                                                                                                if ( singleDate.date.equals(GlobalConstants.SITE_PLAN_IMAGE_NAME) ) {
                                                                                                     if ( (singleDate.floors) != null && (!singleDate.floors.isEmpty()) ) {
                                                                                                          GlobalConstants.CURRENT_FLOOR = singleDate.floors.get(0);
                                                                                                     } else {
                                                                                                          Utils.showCustomToast(MapActivity.this, "Error obtaining areas/floors!", R.drawable.hide_hotspot);
                                                                                                          return;
                                                                                                     }
                                                                                                }
                                                                                           }
                                                                                           MapActivity.this.setProgressBarIndeterminateVisibility(true);
                                                                                           DownloadSitePlanRequest request = new DownloadSitePlanRequest();
                                                                                           getSpiceManager()
                                                                                                     .execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DownloadSitePlanRequestListener(MapActivity.this));
                                                                                      }

                                                                                      @Override public void onChangeMonth(int month, int year) {
                                                                                      }

                                                                                      @Override public void onLongClickDate(Date date, View view) {
                                                                                      }

                                                                                      @Override public void onCaldroidViewCreated() {
                                                                                      }
                                                                                 };

     @AfterViews void afterViews() {
          // obtaining map object
          googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
          // set-up cluster manager
          clusterManager = new ClusterManager <OneClusterMarker>(this, googleMap);
          clusterManager.setOnClusterItemClickListener(this);
          googleMap.setOnMarkerClickListener(clusterManager);

          // temporary trick, capture event when map has been downloaded ................................................
          googleMap.setOnCameraChangeListener(this);
          // Configure action bar
          Utils.configureCustomActionBar(getActionBar(), null);

          // create directory
          File newdir = new File(GlobalConstants.APP_PHOTO_DIRECTORY);
          if ( !newdir.exists() ) {
               newdir.mkdirs();
          }
          // display start message
          Utils.showCustomToast(MapActivity.this, R.string.click_to_show_menu, R.drawable.gmaps);

          sideSlideMenu = SlideMenuMapActivity_.build(MapActivity.this);
          sideSlideMenu.setMenuControlButton(showMenu);
          // Set strict mode.
          StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
          animateCamera(APPROX_CENTER_OF_UK, 7);
     }

     /**
      * Execute post request, clear map, obtain valid data from server, and
      * display all merkers on map
      */
     @Click void refreshMap() {
          // show progress in right top corner
          this.setProgressBarIndeterminateVisibility(true);
          // generate request and execute it
          GoogleMapMarkersRequest request = new GoogleMapMarkersRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GoogleMapMarkerRequestListener(this));
     }

     @Click void showMenu() {
          if ( sideSlideMenu.getMenu().isMenuShowing() ) {
               sideSlideMenu.getMenu().showContent();
          } else {
               sideSlideMenu.getMenu().showMenu();
          }
     }

     private void getDatesToHighliht() {
          this.setProgressBarIndeterminateVisibility(true);

          DatesToHighlightRequest request = new DatesToHighlightRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DatesToHighlightRequestListener(this));
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
               case android.R.id.home:
                    startActivity(new Intent(MapActivity.this, StartActivity_.class));
                    return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     @Override protected void onResume() {
          super.onResume();
          try {
               dialogCaldroidFragment.dismiss();
          } catch (Exception e) {
          }
     }

     @Override protected void onCreate(Bundle savedInstanceState) {
          requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
          super.onCreate(savedInstanceState);
     }

     /**
      * Animate camera to new map position with given zoom
      * 
      * @param position
      * @param zoom
      */
     private void animateCamera(LatLng position, float zoom) {
          CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, zoom);
          googleMap.animateCamera(cameraUpdate, 3000, null);
     }

     public void refreshMarkersOnMap(List <OneClusterMarker> items) {
          clusterManager.clearItems();
          clusterManager.addItems(items);
          clusterManager.cluster();
          googleMap.setOnCameraChangeListener(clusterManager);
     }

     @Override public void onCameraChange(CameraPosition arg0) {
          refreshMap();
          // map has been downloaded, refresh map and destroy listener
          googleMap.setOnCameraChangeListener(null);
     }

     @Override public boolean onClusterItemClick(OneClusterMarker item) {
          GlobalConstants.LAST_CLICKED_MARKER_ID = String.valueOf(item.getMarkerData().id);
          getDatesToHighliht();
          return false;
     }

}