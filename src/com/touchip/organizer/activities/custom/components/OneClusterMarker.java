package com.touchip.organizer.activities.custom.components;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.touchip.organizer.communication.rest.model.GoogleMapMarkersList.POJORoboMarkers;

public class OneClusterMarker implements ClusterItem {

     private final LatLng          mPosition;
     private final POJORoboMarkers marker;

     public OneClusterMarker ( POJORoboMarkers pojoRoboMarkers ) {
          marker = pojoRoboMarkers;
          mPosition = new LatLng(pojoRoboMarkers.latitude, pojoRoboMarkers.longitude);
     }

     @Override public LatLng getPosition() {
          return mPosition;
     }

     public POJORoboMarkers getMarkerData() {
          return marker;
     }

}
