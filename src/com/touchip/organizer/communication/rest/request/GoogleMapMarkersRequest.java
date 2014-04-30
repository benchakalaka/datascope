package com.touchip.organizer.communication.rest.request;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.GoogleMapMarkersList;
import com.touchip.organizer.utils.Utils;

public class GoogleMapMarkersRequest extends SpringAndroidSpiceRequest <GoogleMapMarkersList> {

     public GoogleMapMarkersRequest () {
          super(GoogleMapMarkersList.class);
          Utils.logw("Post....." + RestAddresses.GET_GOOGLE_MAP_LOCATIONS);
     }

     /**
      * Network request
      */
     @Override public GoogleMapMarkersList loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_GOOGLE_MAP_LOCATIONS, null, GoogleMapMarkersList.class);
     }

     /**
      * This method generates a unique cache key for this request.
      * 
      * @return string representation of cache word
      */
     public String createCacheKey() {
          return getClass().getSimpleName() + ".cache";
     }
}
