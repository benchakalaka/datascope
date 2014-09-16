package com.touchip.organizer.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.amazon.geo.maps.GeoPoint;
import com.amazon.geo.maps.MapController;
import com.amazon.geo.maps.MapView;
import com.squareup.timessquare.sample.R;

/**
 * Description: class represents all functionaity which is available on Google
 * map
 * 
 * @author Ihor Karpachev
 *         All content is activity property of Datascope Systems Ltd. Date: 13 Dec 2013
 *         Time: 18:21:35
 */

@EActivity ( R.layout.test_map ) public class KindleMapActivity extends com.amazon.geo.maps.MapActivity {

     @AfterViews void afterViews() {
          // Provide setContentView a reference to the XML layout file for your app;
          // the file should contain a MapView element.
          // setContentView(R.layout.test_map);
          // You must obtain a reference to the map view, in order to work with it.
          MapView mMapView = (MapView) findViewById(R.id.mapview);

          // Now you can obtain a reference to the map controller.
          MapController mMapController = mMapView.getController();

          // The GeoPoint type represents a coordinate's latitude and longitude in microdegrees;
          // that is, a number of standard coordinate degrees multiplied by one million.
          final GeoPoint capitolHillLocation = new GeoPoint(47620079, -122320926);

          // This sets the map center to the fixed GeoPoint we've defined above.
          mMapController.setCenter(capitolHillLocation);

          // Zoom in sufficiently to display individual businesses.
          mMapController.setZoom(17);
     }

     // Calling isRouteDisplayed is required, but it exists for interface compatibility
     // with the Google Maps External Library only. Recommend setting to false.
     @Override protected boolean isRouteDisplayed() {
          return false;
     }
}