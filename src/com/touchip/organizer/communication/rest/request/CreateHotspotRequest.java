package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.CompaniesAndHotspots;
import com.touchip.organizer.utils.Utils;

public class CreateHotspotRequest extends SpringAndroidSpiceRequest <CompaniesAndHotspots> {

     // Variable array for POST request
     private static Map <String, String> vars;

     public CreateHotspotRequest ( Map <String, String> variables ) {
          super(CompaniesAndHotspots.class);
          vars = variables;
          Utils.logw("Post....." + RestAddresses.CREATE_HOTSPOT);
     }

     @Override public CompaniesAndHotspots loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.CREATE_HOTSPOT, vars, CompaniesAndHotspots.class);
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
