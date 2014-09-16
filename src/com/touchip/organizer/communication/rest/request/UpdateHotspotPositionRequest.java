package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.CompaniesAndHotspots;
import com.touchip.organizer.utils.Utils;

public class UpdateHotspotPositionRequest extends SpringAndroidSpiceRequest <CompaniesAndHotspots> {

     // Variable array for POST request
     private static Map <String, String> vars;

     public UpdateHotspotPositionRequest ( Map <String, String> variables ) {
          super(CompaniesAndHotspots.class);
          vars = variables;
          Utils.logw("Post....." + RestAddresses.UPDATE_HOTSPOT_POSITION);
     }

     @Override public CompaniesAndHotspots loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.UPDATE_HOTSPOT_POSITION, vars, CompaniesAndHotspots.class);
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
