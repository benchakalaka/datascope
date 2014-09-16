package com.touchip.organizer.communication.rest.request.listener;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.MapActivity;
import com.touchip.organizer.activities.custom.components.OneClusterMarker;
import com.touchip.organizer.communication.rest.model.GoogleMapMarkersList;
import com.touchip.organizer.communication.rest.model.GoogleMapMarkersList.POJORoboMarkers;
import com.touchip.organizer.utils.Utils;

public class GoogleMapMarkerRequestListener implements RequestListener <GoogleMapMarkersList> {

     // Reference to activity, for updating ui components
     protected MapActivity                  activity;

     private static List <OneClusterMarker> items = new ArrayList <OneClusterMarker>();

     // Constructor that accept activity
     public GoogleMapMarkerRequestListener ( Activity act ) {
          activity = (MapActivity) act;
     }

     // Have got error from server or problem with internet connection (there is no cash available)
     @Override public void onRequestFailure(SpiceException e) {
          Utils.logw(e.getMessage());
          Utils.showToast(activity, R.string.connection_problem, true);
          activity.setProgressBarIndeterminateVisibility(false);
     }

     // Request succesfull, update UI
     @Override public void onRequestSuccess(GoogleMapMarkersList markers) {
          // if list exist and has elements, clear it instead of creating new one
          if ( null != items && !items.isEmpty() ) {
               items.clear();
          }
          // create one cluster item
          for ( POJORoboMarkers marker : markers ) {
               items.add(new OneClusterMarker(marker));
          }
          // refresh clusters list and
          activity.refreshMarkersOnMap(items);
          activity.setProgressBarIndeterminateVisibility(false);
     }
}
