package com.touchip.organizer.activities.custom.components;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.touchip.organizer.communication.rest.model.GoogleMapMarkersList.POJORoboMarkers;

/**
 * Class represents single marker on mapview
 * 
 * @author Karpachev Ihor
 */
public class OneClusterMarker implements ClusterItem {

     // Represents one position
     private final LatLng          mPosition;
     // Marker data
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
